(function(){
    'use strict';
    angular
        .module('webPatient')
        .directive('wploadingtable', loadingTable);

    function loadingTable() {
        var directive = {
            require: ['^ui.bootstrap'],
            restrict: 'A',
            transclude: true,
            templateUrl: 'directives/templates/loadingtable.html',
            scope: {
                totalItems: '=',
                firstText: '@',
                previousText: '@',
                nextText: '@',
                lastText: '@',
                onPageChange: '=',
                itemsPerPage: '=',
                currentPage: '=',
                loading: '='
            },
            controller : WpLoadingTableCtrl,
            controllerAs : 'vm',
            bindToController: true
        };
        return directive;
    };

    function WpLoadingTableCtrl() {
        var vm = this;
        vm.update = updatePage;

        //bug in angular that trigger change before model update.
        function updatePage() {
            vm.onPageChange(vm.currentPage);
        }
    };
 })();