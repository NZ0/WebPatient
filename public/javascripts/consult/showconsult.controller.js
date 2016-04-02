(function() {
    'use strict';
    angular
        .module('webPatient')
        .controller('ShowConsultationCtrl', ShowConsultationCtrl);

        ShowConsultationCtrl.prototype = Object.create(ConsultationCtrl.prototype);

        ShowConsultationCtrl.$inject = ['$scope', '$http', '$modal', '$window', '$routeParams', 'ConsultationService',
                                            'PatientService', 'BillingService', 'ConfirmationFactory'];

        function ShowConsultationCtrl($scope, $http, $modal, $window, $routeParams,
                                        ConsultationService, PatientService, BillingService, ConfirmationFactory) {
            ConsultationCtrl.call(this, BillingService, ConfirmationFactory, $scope, $http, $modal, $window);
            var vm = this;
            vm.displayToolbar = false;
            vm.saveConsult = updateConsult;

            activate();

            function successBill(response) {
                vm.bill = response.data;
                PatientService.show(vm.consultation.patientId).then(successPatient);
            }

            function successPatient(response) {
                vm.patient = response.data;
                vm.patient.age = moment(vm.consultation.date).diff(moment(vm.patient.birthday), 'years');
                vm.loading = false;
                vm.init();
            }

            function successConsultation(response) {
                vm.consultation = response.data;
                if (vm.consultation.bill !== null) {
                    BillingService.get(vm.consultation.bill).then(successBill);
                } else {
                    PatientService.show(vm.consultation.patientId).then(successPatient);
                }
            }

            function errorConsultation() {
                alert('an error occured');
            }

            function activate() {
                ConsultationService.show($routeParams.id).then(successConsultation, errorConsultation);
            }

            function updateConsult() {
                ConsultationService.edit(vm.consultation);
            }
        }
})();