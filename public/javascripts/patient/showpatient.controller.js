(function() {
    'use strict';
    angular
        .module('webPatient')
        .controller('ShowPatientController', ShowPatientController);

    ShowPatientController.$inject = ['PatientService', 'ConsultationService', 'BillingService', 'ConfirmationFactory',
                                        '$routeParams', '$window', '$modal'];

    function ShowPatientController(PatientService, ConsultationService, BillingService, ConfirmationFactory,
                                        $routeParams, $window, $modal) {
        var vm = this;
        vm.patientLoading = {loaded: false, error: false};
        vm.consultLoading = {loaded: false, error: false};
        vm.editSaving = {saving: false, error: false};
        vm.patient = {
            name : '',
            firstName : '',
            phone : '',
            email : '',
            address : '',
            birthday : '',
            sex : ''
        };
        vm.currentPage = 1;
        vm.editPatient = editPatient;
        vm.pageChanged = pageChanged;
        vm.deleteBill = deleteBill;
        vm.showUpdateBill = showUpdateBill;
        vm.showBill = showBill;

        activate();


        function activate() {
            PatientService.show($routeParams.id).then(patientLoadingCallback, patientLoadingCallback);
            ConsultationService.list($routeParams.id, 0).then(successListConsult, errorListConsult);
        }

        function editPatient() {
            if (vm.patientForm.$valid) {
                vm.editSaving.saving = true;
                vm.editSaving.error = false;
                PatientService.edit(vm.patient).then(function() {
                    vm.editSaving.saving = false;
                }, function() {
                    vm.editSaving.saving = false;
                    vm.editSaving.error = true;
                });
            }
        }

        function pageChanged(page) {
            if (page < 1) {
                page = 1;
            }
            vm.currentPage = page;
            vm.consultLoading.loaded = false;
            ConsultationService.list($routeParams.id, page).then(successListConsult, errorListConsult);
        }

        function successListConsult(response) {
            var data = response.data;
            vm.consultations = data.items;
            vm.totalConsult = data.totalItems;
            vm.nbConsultPage = data.itemsPerPages;
            vm.consultLoading.loaded = true;
        }

        function errorListConsult() {
             vm.consultLoading.loaded = true;
             vm.consultLoading.error = true;
        }

        function patientLoadingCallback(response) {
            if (response.status === 200) {
                vm.patient = response.data;
            } else {
                vm.patientLoading.error = true;
            }
            vm.patientLoading.loaded = true;
        }

        function showUpdateBill(id, consultation) {
            BillingService.get(id).then(function(response) {
               var modalInstance = $modal.open({
                   templateUrl: 'bill/views/modalbillform.html',
                   controller: 'ModalBillController',
                   controllerAs: 'modal',
                   resolve : {
                       consultId: function() {
                           return consultation;
                       },
                       settings: function() {
                           return null
                       },
                       existingBill : function() {
                           return response.data;
                       }
                   }
               });
               modalInstance.result.then(updateBill);
            });
        }

        function deleteBill(id) {
            var modalInstance = ConfirmationFactory.create('Suppression', 'Êtes vous sûr de vouloir supprimer cette facture ?', id);
            modalInstance.then(function(id) {
                BillingService.delete(id).then(function() {
                    vm.consultLoading.loaded = false;
                    ConsultationService.list($routeParams.id, vm.currentPage).then(successListConsult, errorListConsult);
                });
            });
        }

        function updateBill(bill) {
            BillingService.update(bill);
        }

        function showBill(id) {
            var billUrl = 'http://localhost:9000/billing/' + id;
            $window.open('/webjars/pdf-js/1.1.3/web/viewer.html?file=' + billUrl);
        }
    }
})();