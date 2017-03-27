(function() {
    'use strict';

    angular
        .module('collabApp')
        .controller('TenantDetailController', TenantDetailController);

    TenantDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Tenant', 'User'];

    function TenantDetailController($scope, $rootScope, $stateParams, previousState, entity, Tenant, User) {
        var vm = this;

        vm.tenant = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('collabApp:tenantUpdate', function(event, result) {
            vm.tenant = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
