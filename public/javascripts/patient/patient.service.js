(function() {
    'use strict';
    angular
        .module('webPatient')
        .service('PatientService', PatientService);

    PatientService.$inject = ['$http'];

    function PatientService($http) {
        var SERVICE_URL = '/patient';
        var patientService = {
            add: add,
            show: show,
            search: search,
            edit: edit,
            list: list
        };

        return patientService;

        function add(patient) {
            return $http.put(SERVICE_URL, patient);
        }

        function show(id) {
            return $http.get(SERVICE_URL +'/' + id);
        }

        function search(val) {
            return $http.get(SERVICE_URL + '/search', {params : {'name' : val}})
                .then(function (result) {
                    return result.data;
                });
        }

        function edit(patient) {
            return $http.post(SERVICE_URL +'/' + patient.id, patient);
        }

        function list(page) {
            return $http.get(SERVICE_URL + '/list/' + page);
        }
    }
})();
