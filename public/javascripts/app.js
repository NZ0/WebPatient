'use strict';
moment.locale('fr');
angular
    .module('webPatient', [
        'ngRoute',
        'ui.bootstrap',
        'ui.bootstrap.datetimepicker',
        'ui.calendar'
     ])
    .config(function ($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'views/index.html'
            })
            .when('/patient', {
                templateUrl: 'patient/views/patient.html',
                controller: 'ShowPatientController',
                controllerAs: 'vm'
            })
            .when('/patient/add', {
                templateUrl: 'patient/views/add.html',
                controller: 'AddPatientController',
                controllerAs: 'vm'
            })
            .when('/patients', {
                templateUrl: 'patient/views/list.html',
                controller: 'ListPatientController',
                controllerAs: 'vm'
            })
            .when('/consultation/add', {
                templateUrl: 'consult/views/add.html',
                controller: 'AddConsultationCtrl',
                controllerAs: 'vm'
            })
            .when('/consultation', {
                templateUrl:'consult/views/add.html',
                controller: 'ShowConsultationCtrl',
                controllerAs: 'vm'
            })
            .when('/settings', {
                templateUrl: 'settings/views/settings.html',
                controller: 'SettingsCtrl',
                controllerAs: 'vm'
            })
            .when('/bills', {
                templateUrl: 'bill/views/list.html',
                controller: 'ListBillsController',
                controllerAs: 'vm'
            })
            .when('/calendar', {
                templateUrl: 'calendar/calendar.html',
                controller: 'CalendarCtrl',
                controllerAs: 'vm'
            })
            .when('/backup', {
                templateUrl: 'backup/views//backup.html',
                controller: 'BackupCtrl',
                controllerAs: 'vm'
            });
    });