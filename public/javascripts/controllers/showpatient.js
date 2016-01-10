(function() {
    'use strict';
    angular
        .module('webPatient')
        .controller('ShowPatientController', ShowPatientController);

    ShowPatientController.$inject = ['PatientService', 'ConsultationService', '$routeParams'];

    function ShowPatientController(PatientService, ConsultationService, $routeParams) {
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
    }
})();