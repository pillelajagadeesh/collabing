(function() {
    'use strict';

    angular
        .module('collabApp')
        .controller('CallHistoryDeleteController',CallHistoryDeleteController);

    CallHistoryDeleteController.$inject = ['$uibModalInstance', 'entity', 'CallHistory'];

    function CallHistoryDeleteController($uibModalInstance, entity, CallHistory) {
        var vm = this;

        vm.callHistory = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CallHistory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
