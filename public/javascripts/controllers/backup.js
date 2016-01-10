(function() {
    'use strict';
    angular
        .module('webPatient')
        .controller('BackupCtrl', BackupCtrl);

    BackupCtrl.$inject = ['BackupService'];

    function BackupCtrl(BackupService) {
        var vm = this;
        vm.backupInProgress = false;
        vm.backupError = false;
        vm.createBackup = createBackup;
        vm.currentRestore = '';
        vm.errorMessage = '';
        vm.loadingData = true;
        vm.restore = restoreBackup;
        vm.restoreInProgress = false;
        vm.restoreError = false;
        vm.upload = uploadBackup;
        vm.backupFile = '';
        vm.uploadProgress = 0;
        vm.uploadInProgress = false;

        activate();

        function createBackup() {
            vm.backupInProgress = true;
            vm.backupError = false;
            BackupService.create().then(function() {
                vm.backupInProgress = false;
                activate();
            }, function() {
                vm.backupInProgress = false;
                vm.backupError = true;
                vm.errorMessage = 'Une erreur est survenue lors de la création de la sauvegarde.';
            });
            return false;
        }

        function restoreBackup(backup) {
            vm.currentRestore = backup;
            vm.restoreInProgress = true;
            vm.restoreError = false;
            BackupService.restore(backup).then(restoreSuccessCallback, restoreErrorCallback);
        }

        function uploadBackup(file) {
            vm.restoreInProgress = true;
            vm.uploadInProgress = true;
            vm.restoreError = false;
            vm.uploadProgress = 0;
            BackupService.upload(file, progressbar).then(function(response) {
                vm.uploadInProgress = false;
                BackupService.restoreStatus(response.data.token).then(restoreSuccessCallback, restoreErrorCallback);
            }, function() {
                vm.uploadInProgress = false;
                vm.restoreInProgress = false;
                vm.restoreError = true;
                vm.errorMessage = 'Une erreur est survenue lors de l\'envoie du fichier';
            });
        }

        function progressbar(e) {
            if (e.lengthComputable) {
                vm.uploadProgress = Math.round(e.loaded * 100 / e.total);
            }
        }

        function activate() {
            vm.loadingData = true;
            BackupService.list().then(function(response) {
                var data = response.data;
                vm.totalNbBackup = data.totalItems;
                vm.backupPerPage = data.itemsPerPage;
                vm.backups = data.items;
                vm.loadingData = false;
            });
        }

        function restoreErrorCallback() {
            vm.restoreInProgress = false;
            vm.restoreError = true;
            vm.errorMessage = 'Une erreur est survenue lors de la restauration.';
            vm.currentRestore = '';
        }

        function restoreSuccessCallback() {
            vm.restoreInProgress = false;
            vm.currentRestore = '';
        }
    }
})();