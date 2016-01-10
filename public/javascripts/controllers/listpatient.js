(function() {
    'use strict';
    angular
        .module('webPatient')
        .controller('ListPatientController', ListPatientController);

    ListPatientController.$inject = ['$routeParams', 'PatientService'];

    function ListPatientController($routeParams, PatientService) {
        var vm = this;
        vm.patients = {};
        vm.maxSize = 5;
        vm.loadingData = true;
        vm.pageChanged = pageChanged;

        if ($routeParams.page) {
            vm.currentPage = $routeParams.page;
        } else {
            vm.currentPage = 1;
        }

        var success = function(response) {
            var data = response.data;
            vm.patients = data.items;
            vm.totalNbPatients = data.totalItems;
            vm.patientPerPage = data.itemsPerPages;
            vm.loadingData = false;
        };

        PatientService.list(vm.currentPage).then(success);

        function pageChanged(page) {
            vm.loadingData = true;
            PatientService.list(page).then(success);
        }
    }
})();