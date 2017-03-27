(function() {
    'use strict';

    angular
        .module('collabApp')
        .controller('FileShareDeleteController',FileShareDeleteController);

    FileShareDeleteController.$inject = ['$uibModalInstance', 'entity', 'FileShare'];

    function FileShareDeleteController($uibModalInstance, entity, FileShare) {
        var vm = this;

        vm.fileShare = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            FileShare.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
