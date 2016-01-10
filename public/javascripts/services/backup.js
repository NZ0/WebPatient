(function() {
    'use strict';
    angular
        .module('webPatient')
        .service('BackupService', BackupService);

    BackupService.$inject = ['$http', '$q', '$timeout'];

    function BackupService($http, $q, $timeout) {
        var service = {
            create: create,
            restore: restore,
            list: list,
            upload: upload,
            backupStatus: backupStatus,
            restoreStatus: pollRestoreStatus,

        };

        return service;

        function create() {
            return $q(function(resolve, reject) {
                $http.put('/backup').then(function(response) {
                    pollStatus(response.data.token, backupStatus, resolve, reject);
                }, function(response) {
                    reject(response);
                });
            });
        }

        function backupStatus(token) {
            return $http.get('/backup/' + token);
        }

        function restore(backup) {
            return $q(function (resolve, reject) {
               $http.post('/restore/' + backup).then(function(response){
                    pollStatus(response.data.token, restoreStatus, resolve, reject);
               }, function (response) {
                    reject(response);
               });
            });
        }

        function restoreStatus(token) {
            return $http.get('/restore/' + token);
        }

        function pollRestoreStatus(token) {
            return $q(function(resolve, reject){
                pollStatus(token, restoreStatus, resolve, reject);
            });
        }

        function list() {
            return $http.get('/backup/list');
        }

        function upload(file, progress) {
            //angularjs $http doesn't support event listener (for now) so need to create our own xhr object
            //this will be embeded into a promise. We could create our full httpbackend but not worth it.
            return $q(function (resolve, reject) {
                var data = new FormData();
                var xhr = new XMLHttpRequest();

                data.append('backup', file, 'backup.zip');

                xhr.upload.addEventListener("progress", progress, false);

                xhr.onload = function() {
                    return resolvePromise(xhr, resolve, reject);
                };
                xhr.onerror = function() {
                    return resolvePromise(xhr, resolve, reject);
                };

                xhr.open('POST', '/restore');
                xhr.send(data);
            });

        }

        function resolvePromise(xhr, resolve, reject) {
            var response = ('response' in xhr) ? xhr.response : xhr.responseText;
            var status = xhr.status;
            var statusText = xhr.statusText || '';

            ((200 <= status && status < 300) ? resolve : reject)({
                data: JSON.parse(response),
                status: status,
                headers: xhr.getAllResponseHeaders(),
                statusText: statusText
            });
        }

        function pollStatus(token, action, resolve, reject) {
            $timeout(function() {
                action(token).then(function(response) {
                    if (response.data === true) {
                        resolve(response);
                    } else {
                        pollStatus(token, action, resolve, reject);
                    }
                }, function(response) {
                    reject(response);
                })
            }, 5000);
        }
    }
})();