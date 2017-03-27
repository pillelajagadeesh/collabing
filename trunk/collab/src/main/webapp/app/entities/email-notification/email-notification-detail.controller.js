(function() {
    'use strict';

    angular
        .module('collabApp')
        .controller('EmailNotificationDetailController', EmailNotificationDetailController);

    EmailNotificationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'EmailNotification', 'User'];

    function EmailNotificationDetailController($scope, $rootScope, $stateParams, previousState, entity, EmailNotification, User) {
        var vm = this;

        vm.emailNotification = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('collabApp:emailNotificationUpdate', function(event, result) {
            vm.emailNotification = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
