(function() {
    'use strict';

    angular
        .module('collabApp')
        .controller('CallHistoryDialogController', CallHistoryDialogController);

    CallHistoryDialogController.$inject = ['$timeout', '$scope', '$stateParams',  'entity', 'CallHistory', 'User','$state'];

    function CallHistoryDialogController ($timeout, $scope, $stateParams, entity, CallHistory, User,$state) {
        var vm = this;

        vm.callHistory = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            //$uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.callHistory.id !== null) {
                CallHistory.update(vm.callHistory, onSaveSuccess, onSaveError);
            } else {
                CallHistory.save(vm.callHistory, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('collabApp:callHistoryUpdate', result);
           // $uibModalInstance.close(result);
            vm.isSaving = false;
            $state.go('call-history');

        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
