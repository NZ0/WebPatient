(function() {
    'use strict';
    angular.module('webPatient')
        .controller('CalendarCtrl', CalendarCtrl);

    //Need to inject $scope. It's the only way to access fullCalendar object in order to refresh json stream.
    // It's seems to be a bug in angular-ui-calendar
    CalendarCtrl.$inject = ['CalendarService', 'PatientService', 'uiCalendarConfig', '$modal', '$scope'];

    function CalendarCtrl(CalendarService, PatientService, uiCalendarConfig, $modal, $scope) {
        var vm = this;
        vm.loading = true;
        vm.error = false;
        vm.registered = false;
        vm.uiConfig = {
            calendar: {
                height: 450,
                editable: true,
                header:{
                    left: 'month,agendaWeek,agendaDay',
                    center: 'title',
                    right: 'today prev,next'
                },
                lang: 'fr',
                defaultView: 'agendaWeek',
                eventClick: displayAppointment,
                dayClick: addAppointment,
                eventDrop: eventDrop
            }
        };
        activate();
        vm.events = [];

        function activate() {
            return CalendarService.isRegistered().then(function(response) {
                vm.loading = false;
                vm.error = false;
                vm.registered = true;
                vm.events.push({url: "/calendar/list", className: 'gcal-event', currentTimezone: "Europe/Paris"});
                return vm.registered;
            }, function(response) {
                vm.loading = false;
                vm.error = response.status !== 401;
                vm.registered = response.status !== 401;
                return vm.registered;
            });
        }

        function addAppointment(date, jsEvent, view) {
            var appointment = {id: null, patient: null, start: new Date(date.format()), duration: 3600000};
            var modalInstance = $modal.open({
                templateUrl: 'modalAppointment.html',
                controller: 'ModalAppointmentCtrl',
                controllerAs: 'modal',
                resolve : {
                    appointment : function() {
                        return appointment;
                    }
                }
            });
            modalInstance.result.then(function(refresh) {
                if (refresh) {
                    doRefresh();
                }
            });
        }

        function displayAppointment(date, jsEvent, view) {
            var appointment = {id: date.id,
                patient: date.title,
                start: new Date(date.start.valueOf()),
                duration: date.end - date.start
            };
            var modalInstance = $modal.open({
                templateUrl: 'modalAppointment.html',
                controller: 'ModalAppointmentCtrl',
                controllerAs: 'modal',
                resolve : {
                    appointment : function() {
                        return appointment;
                    }
                }
            });
            modalInstance.result.then(function(refresh) {
                if (refresh) {
                    doRefresh();
                }
            });
        }

        function doRefresh() {
            $scope.calendar.fullCalendar('refetchEvents');
        }

        function eventDrop(event, jsEvent, ui, view) {
            //trick to convert UTC into user timezone keeping the same value
            CalendarService.update(event.title, moment(event.start.toISOString()), moment(event.end.toISOString()), event.userId, event.id)
        }
    };
})();

(function() {
    'use strict';
    angular.module('webPatient')
        .controller('ModalAppointmentCtrl', ModalAppointmentCtrl);

    ModalAppointmentCtrl.$inject = ['CalendarService', 'PatientService', '$modalInstance', 'appointment'];

    function ModalAppointmentCtrl(CalendarService, PatientService, $modalInstance, appointment) {
        var modal = this;
        modal.appointment = appointment;
        modal.cancel = cancel;
        modal.save = save;
        modal.search = search;
        modal.deleteAppointment = deleteEvent;

        if (modal.appointment.id !== null) {
            modal.isEdition = true;
        } else {
            modal.isEdition = false;
        }

        function close(refresh) {
            $modalInstance.close(true);
        }

        function save() {
            var data = modal.appointment;
            var action;
            if (data.id !== null) {
                action = CalendarService.update;
            } else {
                action = CalendarService.create;
            }
            if (typeof data.patient === 'string') {
                action(data.patient, data.start, new Date(data.start.getTime() + data.duration), null, data.id)
                    .then(function() {
                        close(true);
                     });
            } else {
                action(data.patient.firstName + ' ' + data.patient.name, data.start,
                        new Date(data.start.getTime() + data.duration), data.patient.id, data.id)
                    .then(function() {
                        close(true);
                    });
            }
        }

        function deleteEvent() {
            CalendarService.deleteEvent(modal.appointment.id).then(function() {
                close(true);
            });
        }

        function cancel() {
            $modalInstance.dismiss(false);
        }

        function search(name) {
            return PatientService.search(name);
        }
    }
})();