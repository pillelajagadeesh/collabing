(function() {
    'use strict';

    angular
        .module('collabApp')
        .controller('CallHistoryDetailController', CallHistoryDetailController);

    CallHistoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CallHistory', 'User'];

    function CallHistoryDetailController($scope, $rootScope, $stateParams, previousState, entity, CallHistory, User) {
        var vm = this;

        vm.callHistory = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('collabApp:callHistoryUpdate', function(event, result) {
            vm.callHistory = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
