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
                templateUrl: 'views/patient.html',
                controller: 'ShowPatientController',
                controllerAs: 'vm'
            })
            .when('/patient/add', {
                templateUrl: 'views/addpatient.html',
                controller: 'AddPatientController',
                controllerAs: 'vm'
            })
            .when('/patients', {
                templateUrl: 'views/patientList.html',
                controller: 'ListPatientController',
                controllerAs: 'vm'
            })
            .when('/consultation/add', {
                templateUrl: 'views/consult/addconsult.html',
                controller: 'AddConsultationCtrl',
                controllerAs: 'vm'
            })
            .when('/consultation', {
                templateUrl:'views/consult/consult.html',
                controller: 'ShowConsultationCtrl',
                controllerAs: 'vm'
            })
            .when('/settings', {
                templateUrl: 'views/settings.html',
                controller: 'SettingsCtrl',
                controllerAs: 'vm'
            })
            .when('/bills', {
                templateUrl: 'views/billsList.html',
                controller: 'ListBillsController',
                controllerAs: 'vm'
            })
            .when('/calendar', {
                templateUrl: 'views/calendar.html',
                controller: 'CalendarCtrl',
                controllerAs: 'vm'
            })
            .when('/backup', {
                templateUrl: 'views/backup.html',
                controller: 'BackupCtrl',
                controllerAs: 'vm'
            });
    });