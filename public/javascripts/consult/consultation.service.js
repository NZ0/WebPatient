'use strict';

angular.module('webPatient')
    .service('ConsultationService', ['$http', function($http) {
        var consultationService = {};

        consultationService.add = function(consultation, success, error) {
            var request = $http.put('/consult', consultation);
            if (success) {
                request.success(success);
            }
            if (error) {
                request.error(error);
            }
        };

        consultationService.edit = function(consultation, success, error) {
            var request = $http.post('/consult/'+consultation.id, consultation);
            if (success) {
                request.success(success);
            }
            if (error) {
                request.error(error);
            }
        };

        consultationService.list = function(patient, page) {
            if (page <= 0) {
                page = 1;
            }
            return $http.get('/consult/list/'+patient+'/'+page);
        };

        consultationService.show = function(id, success, error) {
            return $http.get('/consult/' + id);
        };

        consultationService.last = function(patientId) {
            return $http.get('/consult/last/' + patientId);
        };

        return consultationService;
    }]);
