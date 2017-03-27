(function() {
    'use strict';

    angular
        .module('collabApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('user-management', {
            parent: 'admin',
            url: '/user-management?page&sort',
            data: {
            	authorities: ['ROLE_SUPER_ADMIN','ROLE_USER_ADMIN'],
            	 pageTitle: 'userManagement.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/admin/user-management/user-management.html',
                    controller: 'UserManagementController',
                    controllerAs: 'vm'
                }
            },            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                }
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort)
                    };
                }]
            }        })
   
            
              .state('user-management-detail', {
            parent: 'admin',
            url: '/user/:login',
            data: {
//            	 authorities: ['ROLE_USER_ADMIN'],
//                pageTitle: 'user-management.detail.title'
                	 authorities: ['ROLE_SUPER_ADMIN','ROLE_USER_ADMIN'],
                     pageTitle: 'user-management.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/admin/user-management/user-management-detail.html',
                    controller: 'UserManagementDetailController',
                    controllerAs: 'vm'
                }
            },
        })
        
          .state('user-management.new', {
            parent: 'user-management',
            url: '/create',
            data: {
            	 authorities: ['ROLE_SUPER_ADMIN','ROLE_USER_ADMIN']
            },
			views:{
				'content@':{
					templateUrl: 'app/admin/user-management/user-management-dialog.html',
                    controller: 'UserManagementDialogController',
                    controllerAs: 'vm'
				}
			},
			 resolve: {
                        entity: function () {
                            return {
                            	 id: null, login: null, firstName: null, lastName: null, email: null,
                                 activated: true, langKey: null, createdBy: null, createdDate: null,
                                 lastModifiedBy: null, lastModifiedDate: null, resetDate: null,
                                 resetKey: null, authorities: null
                            };
                        }
                    }
            
        })
		
        
         .state('user-management.edit', {
            parent: 'user-management',
            url: '/{login}/edit',
            data: {
//            	 authorities: ['ROLE_USER_ADMIN']
            	 authorities: ['ROLE_SUPER_ADMIN','ROLE_USER_ADMIN']
            },
			views:{
				'content@':{
					templateUrl: 'app/admin/user-management/user-management-dialog.html',
                    controller: 'UserManagementDialogController',
                    controllerAs: 'vm'
				}
			},
			resolve: {
				entity: ['$stateParams', 'User', function($stateParams, User) {
                    return User.get({login : $stateParams.login}).$promise;
                }],
            }
			
            
        })

        
        .state('user-management.delete', {
            parent: 'user-management',
            url: '/{login}/delete',
            data: {
//            	 authorities: ['ROLE_USER_ADMIN'],
            	authorities: ['ROLE_SUPER_ADMIN','ROLE_USER_ADMIN'],
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/admin/user-management/user-management-delete-dialog.html',
                    controller: 'UserManagementDeleteController',
                    controllerAs: 'vm',
					size: 'sm',
                    resolve: {
                        entity: ['User', function(User) {
                            return User.get({login : $stateParams.login});
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-management', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
        
    }
})();
