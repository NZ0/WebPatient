(function() {
    'use strict';
    angular
        .module('webPatient')
        .controller('AddConsultationCtrl', AddConsultationCtrl);

        AddConsultationCtrl.prototype = Object.create(ConsultationCtrl.prototype);

        AddConsultationCtrl.$inject = ['$scope', '$http', '$modal', '$routeParams', '$window', 'PatientService',
                                            'ConsultationService', 'BillingService', 'ConfirmationFactory', '$location'];

        function AddConsultationCtrl($scope, $http, $modal, $routeParams, $window, PatientService,
                                        ConsultationService, BillingService, ConfirmationFactory, $location) {
            ConsultationCtrl.call(this, BillingService, ConfirmationFactory, $scope, $http, $modal, $window);
            var vm = this;
            vm.saveConsult = addConsult;
            vm.consultation = {date: new Date()};
            vm.open = open;
            vm.patient = {};

            activate();

            function addConsult() {
                if (vm.consultation.id) {
                    ConsultationService.edit(vm.consultation);
                } else {
                    vm.consultation.patientId = vm.patient.id;
                    ConsultationService.add(vm.consultation, function(data) {
                        vm.consultation = data;
                    });
                }
            }

            function patientSuccess(response) {
                vm.patient = response.data;
                vm.age = moment().diff(moment(vm.patient.birthday), 'years');
                vm.loading = false;
                vm.init();
            }

            function patientError(response) {
                $location.path('/patients').replace();
            }

            function lastConsultSuccess(response) {
                if (response.data != null) {
                    vm.consultation.weight = response.data.weight;
                    vm.consultation.size = response.data.size;
                }
            }

            function activate() {
                PatientService.show($routeParams.patient).then(patientSuccess, patientError);
                ConsultationService.last($routeParams.patient).then(lastConsultSuccess);
            }
        }
})();