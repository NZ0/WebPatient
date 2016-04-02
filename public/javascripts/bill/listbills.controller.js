(function() {
    'use strict';
    angular
        .module('webPatient')
        .controller('ListBillsController', ListBillsController);

    ListBillsController.$inject = ['$routeParams', 'BillingService', 'ConfirmationFactory', '$modal'];

    function ListBillsController($routeParams, BillingService, ConfirmationFactory, $modal) {
        var vm = this;
        var lastSearchUpdate = new Date();

        vm.patients = {};
        vm.maxSize = 5;
        vm.loadingData = true;
        vm.autoSearch = autoSearch;
        vm.pageChanged = pageChanged;
        vm.edit = edit;
        vm.delete = remove;

        if ($routeParams.page) {
            vm.page = $routeParams.page;
        } else {
            vm.page = 1;
        }

        if ($routeParams.search) {
            vm.searchedId = $routeParams.search;
        } else {
            vm.searchedId = null;
        }

        activate();

        function autoSearch() {
            if (vm.searchedId != null && vm.searchedId.length >= 3) {
                vm.loadingData = true;
                BillingService.search(vm.searchedId, 1).then(success);
            } else if (vm.searchedId === null || vm.searchedId.length === 0) {
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
            autoSearch();
        }

        function edit(id) {
            BillingService.get(id).then(function(response) {
                openPopup(response.data);
            });
        }

        function updateBill(bill) {
            BillingService.update(bill);
        }

        function openPopup(data) {
            var modalInstance = $modal.open({
                templateUrl: 'bill/views/modalbillform.html',
                controller: 'ModalBillController',
                controllerAs: 'modal',
                resolve : {
                    consultId: function() {
                        return data.id;
                    },
                    settings: function() {
                        return null
                    },
                    existingBill : function() {
                        return data;
                    }
                }
            });
            modalInstance.result.then(updateBill);
        }

        function remove(id) {
            var modalInstance = ConfirmationFactory.create('Suppression', 'Êtes vous sûr de vouloir supprimer cette facture ?', id);
            modalInstance.then(function(id) {
                BillingService.delete(id).then(function() {
                    autoSearch();
                });
            });
        }
    }
})();