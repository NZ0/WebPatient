(function() {
    'use strict';
    angular
        .module('webPatient')
        .controller('ListBillsController', ListBillsController);

    ListBillsController.$inject = ['$routeParams', 'BillingService'];

    function ListBillsController($routeParams, BillingService) {
        var vm = this;
        var lastSearchUpdate = new Date();

        vm.patients = {};
        vm.maxSize = 5;
        vm.loadingData = true;
        vm.autoSearch = autoSearch;
        vm.pageChanged = pageChanged;

        if ($routeParams.page) {
            vm.page = $routeParams.page;
        } else {
            vm.page = 1;
        }

        activate();

        function autoSearch() {
            if (vm.searchedId.length >= 3) {
                vm.loadingData = true;
                BillingService.search(vm.searchedId, 1).then(success);
            } else if (vm.searchedId.length === 0) {
                vm.loadingData = true;
                BillingService.list(0).then(success);
            }
            lastSearchUpdate = Date.now();
        }

        function pageChanged(page) {
            vm.loadingData = true;
            if (vm.searchedId && vm.searchedId !== '') {
                BillingService.search(vm.searchedId, page).then(success);
            } else {
                BillingService.list(page).then(success);
            }
        }

        function success(response) {
            var data = response.data;
            vm.bills = data.items;
            vm.totalNbBills = data.totalItems;
            vm.billsPerPage = data.itemsPerPages;
            vm.loadingData = false;
        }

        function activate() {
            BillingService.list(vm.page).then(success);
        }
    }
})();