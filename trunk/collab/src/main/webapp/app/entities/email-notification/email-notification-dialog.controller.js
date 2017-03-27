(function() {
    'use strict';

    angular
        .module('collabApp')
        .controller('EmailNotificationDialogController', EmailNotificationDialogController);

    EmailNotificationDialogController.$inject = ['$timeout', '$scope', '$stateParams',  'entity', 'EmailNotification', 'User','$state'];

    function EmailNotificationDialogController ($timeout, $scope, $stateParams,  entity, EmailNotification, User,$state) {
        var vm = this;

        vm.emailNotification = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();
        vm.tousers = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
         //   $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.emailNotification.id !== null) {
                EmailNotification.update(vm.emailNotification, onSaveSuccess, onSaveError);
            } else {
                EmailNotification.save(vm.emailNotification, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('collabApp:emailNotificationUpdate', result);
          //  $uibModalInstance.close(result);
            vm.isSaving = false;
            $state.go('email-notification');
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
