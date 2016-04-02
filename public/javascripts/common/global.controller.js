'use strict';

angular.module('webPatient')
    .controller('GlobalCtrl', ['$scope', 'PatientService', '$location', '$http', function($scope, PatientService, $location, $http) {
        $scope.search = function (name) {
            return PatientService.search(name);
        };

        $scope.searchSelect = function($item, $model, $label) {
            $location.path('/patient').search({id : $model.id});
        }

        $http.get('/settings/list').error(function() {
            $location.path('/settings/').replace();
        });

    }]);