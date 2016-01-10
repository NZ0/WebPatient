(function() {
    'use strict';
    angular
        .module('webPatient')
        .controller('AddPatientController', AddPatientController);

    AddPatientController.$inject = ['$location', 'PatientService'];

    function AddPatientController($location, PatientService) {
        var vm = this;
        vm.patient = {
            name : '',
            firstName : '',
            phone : '',
            email : '',
            address : '',
            birthday : '',
            sex : '',
            information : null
        };
        vm.patientSaving = {saving : false, error : false};
        vm.submitPatient = submitPatient;

        function submitPatient() {
            if (vm.patientForm.$valid) {
                vm.patientSaving.saving = true;
                PatientService.add(vm.patient).then(function(response) {
                    var data = response.data;
                    $location.path('/patient/').search({id : data.id}).replace();
                }, function() {
                    vm.patientSaving.saving = false;
                    vm.patientSaving.error = true;
                });
            }
        }
    }
})();