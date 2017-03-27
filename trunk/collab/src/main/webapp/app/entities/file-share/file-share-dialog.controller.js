(function() {
    'use strict';

    angular
        .module('collabApp')
        .controller('FileShareDialogController', FileShareDialogController);

    FileShareDialogController.$inject = ['$timeout', '$scope', '$stateParams',  'DataUtils', 'entity', 'FileShare', 'User','$state'];

    function FileShareDialogController ($timeout, $scope, $stateParams,  DataUtils, entity, FileShare, User,$state) {
        var vm = this;

        vm.fileShare = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.fileShare.id !== null) {
                FileShare.update(vm.fileShare, onSaveSuccess, onSaveError);
            } else {
                FileShare.save(vm.fileShare, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('collabApp:fileShareUpdate', result);
          //  $uibModalInstance.close(result);
            vm.isSaving = false;
            $state.go('file-share');
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setContent = function ($file, fileShare) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        fileShare.content = base64Data;
                        fileShare.contentContentType = $file.type;
                    });
                });
            }
        };

    }
})();
