(function() {
    'use strict';
    angular
        .module('webPatient')
        .service('BillingService', BillingService);

        BillingService.$inject = ['$http'];

        function BillingService($http) {
            var billingService = {
                list : list,
                search : search,
                create : create
            };

            return billingService;

            function list(page) {
                if (page <= 0) {
                    page = 1;
                }
                return $http.get('/billing/list/'+page);
            }

            function search(id, page) {
                if (!page || page <= 0) {
                    page  = 1;
                }
                return $http.get('billing/search/'+page, {params : {'id' : id}})
            }

            function create(bill) {
                return $http.put('/billing', bill)
            }
        }
})();
