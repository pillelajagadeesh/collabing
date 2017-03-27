(function() {
    'use strict';

    angular
        .module('collabApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('logs', {
            parent: 'admin',
            url: '/logs',
            data: {
                authorities: ['ROLE_SUPER_ADMIN'],
                pageTitle: 'Logs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/admin/logs/logs.html',
                    controller: 'LogsController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
