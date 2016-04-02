'use strict';
function ConsultationCtrl(BillingService, ConfirmationFactory, $scope, $http, $modal, $window) {
    var vm = this;
    vm.bill = null;
    vm.consultation = null;
    vm.existingBill = function() {
        return vm.bill;
    };
    vm.imc = imc;
    vm.loading = true;
    vm.openBillForm = openBillForm;
    vm.patient = null;
    vm.showBill = showBill;
    vm.init = init;
    vm.saveBill = saveBill;
    vm.deleteBill = deleteBill;

    function init() {
        $scope.$watch('vm.consultation.weight', function(newValue, oldValue) {
            vm.consultation.imc = vm.imc(vm.consultation.size / 100, newValue);
        });

        $scope.$watch('vm.consultation.date', function(newValue, oldValue) {
            vm.age = moment(newValue).diff(moment(vm.patient.birthday), 'years');
        });

        $scope.$watch('vm.consultation.size', function(newValue, oldValue) {
            vm.consultation.imc = vm.imc(newValue / 100, vm.consultation.weight);
        });
    }

    function imc(size, weight) {
        if (size > 0 && weight > 0) {
            return weight / (size * size);
        }
    }

    function openBillForm() {
        $http.get('/settings/list').then(function (response) {
            var modalInstance = $modal.open({
                templateUrl: 'bill/views/modalbillform.html',
                controller: 'ModalBillController',
                controllerAs: 'modal',
                resolve : {
                    consultId : function() {
                        return vm.consultation.id;
                    },
                    settings : function() {
                        return response.data;
                    },
                    existingBill : vm.existingBill
                }
            });
            modalInstance.result.then(vm.saveBill);
        });
        return false;
    }

    function saveBill(bill) {
        if (!bill.id) {
            BillingService.create(bill).then(function(response) {
                BillingService.get(response.data).then(function(response) {
                    vm.bill = response.data;
                });
                var billUrl = 'http://localhost:9000/billing/' + response.data;
                $window.open('/webjars/pdf-js/1.1.3/web/viewer.html?file=' + billUrl);
            });
        } else {
            BillingService.update(bill);
        }
    }

    function showBill() {
        var billUrl = 'http://localhost:9000/billing/' + vm.bill.id;
        $window.open('/webjars/pdf-js/1.1.3/web/viewer.html?file=' + billUrl);
    }

    function deleteBill() {
        var modalInstance = ConfirmationFactory.create('Suppression', 'Êtes vous sûr de vouloir supprimer cette facture ?', vm.bill.id);
        modalInstance.then(function(id) {
            BillingService.delete(id).then(function() {
                vm.bill = null;
            });
        });
    }
}