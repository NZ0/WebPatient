(function() {
    'use strict';
    angular
        .module('webPatient')
        .controller('ShowConsultationCtrl', ShowConsultationCtrl);

        ShowConsultationCtrl.$inject = ['ConsultationService', 'PatientService', '$routeParams'];

        function ShowConsultationCtrl(ConsultationService, PatientService, $routeParams) {
            var vm = this;
            vm.loading = true;
            vm.displayToolbar = false;

            activate();

            function successPatient(response) {
                vm.patient = response.data;
                vm.loading = false;
                vm.patient.age = moment(vm.consultation.date).diff(moment(vm.patient.birthday), 'years');
            }

            function successConsultation(response) {
                vm.consultation = response.data;
                PatientService.show(vm.consultation.patientId).then(successPatient);
            }

            function errorConsultation() {
                alert('an error occured');
            }

            function activate() {
                ConsultationService.show($routeParams.id).then(successConsultation, errorConsultation);
            }
        }
})();