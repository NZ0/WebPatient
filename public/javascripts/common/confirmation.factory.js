(function() {
    'use strict';
    angular.module('webPatient')
        .factory('ConfirmationFactory', ConfirmationFactory);

    ConfirmationFactory.$inject = ['$modal'];

    function ConfirmationFactory($modal) {
        var factory = {
            create: create
        };
        return factory;

        function create(title, message, data) {
            var modalInstance = $modal.open({
                templateUrl: 'common/views/confirmation.html',
                controller: 'PopupController',
                controllerAs: 'modal',
                resolve : {
                    confirmationTitle: function() {
                        return title;
                    },
                    confirmationText: function() {
                        return message;
                    },
                    data: function() {
                        return data;
                    }
                }
            });
            return modalInstance.result;
        }
    }

    angular.module('webPatient')
        .controller('PopupController', PopupController);

    PopupController.$inject = ['$modalInstance', 'confirmationTitle', 'confirmationText', 'data'];

    function PopupController($modalInstance, confirmationTitle, confirmationText, data) {
        var modal = this;
        modal.ok = ok;
        modal.cancel = cancel;
        modal.confirmationText = confirmationText;
        modal.confirmationTitle = confirmationTitle;

        function ok() {
            $modalInstance.close(data);
        }

        function cancel() {
            $modalInstance.dismiss();
        }
    }

})();