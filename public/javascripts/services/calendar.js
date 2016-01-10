(function() {
    'use strict';
    var SERVICE_URL = '/calendar';

    angular.module('webPatient')
        .service('CalendarService', CalendarService);

    CalendarService.$inject = ['$http'];

    function CalendarService($http) {
        var service = {
            list: list,
            create: create,
            deleteEvent: deleteEvent,
            update: update,
            isRegistered: isRegistered
        };

        return service;

        function list(startTime, endTime) {
            var url = SERVICE_URL + '/list';
            if (startTime) {
                url += '?minTime=' + startTime;
            }

            if (endTime) {
                url += '&maxTime=' + endTime;
            }
            return $http.get(url);
        }

        function create(title, startTime, endTime, userId) {
            var url = SERVICE_URL;
            return $http.put(url, {title: title, start: startTime, end: endTime, userId: userId, allDay: false});
        }

        function update(title, startTime, endTime, userId, id) {
            var url = SERVICE_URL;
            return $http.post(url, {id: id, title: title, start: startTime, end: endTime, userId: userId, allDay: false});
        }

        function deleteEvent(id) {
            var url = SERVICE_URL + '/'+id;
            return $http.delete(url);
        }

        function isRegistered() {
            var url = SERVICE_URL + '/registered';
            return $http.get(url);
        }
    }
})();