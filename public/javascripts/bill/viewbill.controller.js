(function() {
    angular.module('webPatient')
        .controller('ViewBillController', ViewBillController);

    ViewBillController.$inject = ['$routeParams', '$scope'];

    function ViewBillController($routeParams, $scope) {
        var vm = this;
        vm.billId = $routeParams.id;
        $scope.pdfUrl = '/billing/' + $routeParams.id;
    }
})();