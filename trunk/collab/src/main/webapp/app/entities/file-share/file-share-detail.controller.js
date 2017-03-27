(function() {
    'use strict';

    angular
        .module('collabApp')
        .controller('FileShareDetailController', FileShareDetailController);

    FileShareDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'FileShare', 'User'];

    function FileShareDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, FileShare, User) {
        var vm = this;

        vm.fileShare = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('collabApp:fileShareUpdate', function(event, result) {
            vm.fileShare = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
