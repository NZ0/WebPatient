(function() {
    'use strict';
    angular
        .module('webPatient')
        .controller('ModalBillController', ModalBillController);

        ModalBillController.$inject = ['$modalInstance', 'BillingService', '$window', 'consultId', 'settings', 'existingBill'];

        function ModalBillController($modalInstance, BillingService, $window, consultId, settings, existingBill) {
            var modal = this;
            modal.save = save;
            modal.cancel = cancel;
            if (existingBill !== null) {
                modal.bill = existingBill;
            } else {
                modal.bill = { price : settings.price,
                    details : 'Consultation en ostéopathie',
                    consultId : consultId,
                    type: 'CASH'
                };
            }

            function save() {
                $modalInstance.close(modal.bill);
            }

            function cancel() {
                $modalInstance.dismiss();
            }

        }
})();