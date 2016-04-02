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
                create : create,
                get: get,
                update: update,
                delete: remove
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

            function get(id) {
                return $http.get('/billing/' + id + '?json=1');
            }

            function create(bill) {
                return $http.put('/billing', bill)
            }

            function update(bill) {
                return $http.post('/billing/' + bill.id, bill);
            }

            function remove(hash) {
                return $http.delete('/billing/' + hash);
            }
        }
})();
