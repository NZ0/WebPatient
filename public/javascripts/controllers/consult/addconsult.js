(function() {
    'use strict';
    angular
        .module('webPatient')
        .controller('AddConsultationCtrl', AddConsultationCtrl);

        AddConsultationCtrl.$inject = ['$scope', '$routeParams', '$http', 'PatientService', 'ConsultationService', '$location', '$modal'];

        function AddConsultationCtrl($scope, $routeParams, $http, PatientService, ConsultationService, $location, $modal) {
            var vm = this;
            vm.addConsult = addConsult;
            vm.consultation = {date: new Date()};
            vm.open = open;
            vm.patient = {};
            vm.imc = imc;

            $scope.$watch('vm.consultation.weight', function(newValue, oldValue) {
                vm.consultation.imc = vm.imc(vm.consultation.size / 100, newValue);
            });

            $scope.$watch('vm.consultation.date', function(newValue, oldValue) {
                vm.age = moment(newValue).diff(moment(vm.patient.birthday), 'years');
            });

            $scope.$watch('vm.consultation.size', function(newValue, oldValue) {
                vm.consultation.imc = vm.imc(newValue / 100, vm.consultation.weight);
            });

            activate();

            function imc(size, weight) {
                if (size > 0 && weight > 0) {
                    return weight / (size * size);
                }
            }

            function addConsult() {
                if (vm.consultation.id) {
                    ConsultationService.edit(vm.consultation);
                } else {
                    vm.consultation.patientId = vm.patient.id;
                    ConsultationService.add(vm.consultation, function(data) {
                        vm.consultation = data;
                    });
                }
            }

            function open() {
                $http.get('/settings/list').success(function (data) {
                    var modalInstance = $modal.open({
                        templateUrl: 'modalBillForm.html',
                        controller: 'ModalBillFormInstanceCtrl',
                        controllerAs: 'modal',
                        resolve : {
                            consultId : function() {
                                return vm.consultation.id;
                            },
                            settings : function() {
                                return data;
                            }
                        }
                    });
                });
            }

            function patientSuccess(response) {
                vm.patient = response.data;
                vm.age = moment().diff(moment(vm.patient.birthday), 'years');
            }

            function patientError(response) {
                $location.path('/patients').replace();
            }

            function lastConsultSuccess(response) {
                if (response.data != null) {
                    vm.consultation.weight = response.data.weight;
                    vm.consultation.size = response.data.size;
                }
            }

            function activate() {
                PatientService.show($routeParams.patient).then(patientSuccess, patientError);
                ConsultationService.last($routeParams.patient).then(lastConsultSuccess);
            }
        }
})();

(function() {
    'use strict';
    angular
        .module('webPatient')
        .controller('ModalBillFormInstanceCtrl', ModalBillFormInstanceCtrl);

        ModalBillFormInstanceCtrl.$inject = ['$modalInstance', 'BillingService', '$window', 'consultId', 'settings'];

        function ModalBillFormInstanceCtrl($modalInstance, BillingService, $window, consultId, settings) {
            var modal = this;
            modal.createBill = createBill;
            modal.cancel = cancel;
            modal.bill = { price : settings.price,
                detail : 'Consultation en ostéopathie',
                consultId : consultId
            };

            function createBill() {
                BillingService.create(modal.bill).then(function(response) {
                    $window.open('/billing/' + response.data);
                    $modalInstance.close();
                });
            }

            function cancel() {
                $modalInstance.dismiss();
            }

        }
})();