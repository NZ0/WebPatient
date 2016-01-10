(function() {
    'use strict';
    angular.module('webPatient')
        .controller('SettingsCtrl', SettingsCtrl);

        SettingsCtrl.$inject = ['$http'];

        function SettingsCtrl($http) {
            var vm = this;
            vm.settings = {};
            vm.saveSettings = saveSettings;
            vm.saving = false;


            $http.get('/settings/list').then(function(response) {
                vm.settings = response.data;
            });

            function saveSettings() {
                vm.saving = true;
                $http.post('/settings', vm.settings).then(function() {
                    vm.saving = false;
                });
            };
        };
})();