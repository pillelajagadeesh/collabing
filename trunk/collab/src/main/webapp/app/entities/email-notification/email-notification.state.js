(function() {
    'use strict';

    angular
        .module('collabApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        /*.state('email-notification', {
            parent: 'entity',
            url: '/email-notification?page&sort&search',
            data: {
                authorities: ['ROLE_USER','ROLE_USER_ADMIN'],
                pageTitle: 'EmailNotifications'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/email-notification/email-notifications.html',
                    controller: 'EmailNotificationController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })*/
        
        

        .state('email-notification', {
            parent: 'entity',
            url: '/email-notification?page&sort&search',
            data: {
            	authorities: ['ROLE_USER','ROLE_USER_ADMIN'],
                pageTitle: 'EmailNotifications'
            },
            views: {
            'content@': {
            	templateUrl: 'app/entities/email-notification/email-notifications.html',
                controller: 'EmailNotificationController',
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
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                        
                    };
                }]
            }       
			})
        
     /*   
        .state('email-notification-detail', {
            parent: 'entity',
            url: '/email-notification/{id}',
            data: {
                authorities: ['ROLE_USER','ROLE_USER_ADMIN'],
                pageTitle: 'EmailNotification'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/email-notification/email-notification-detail.html',
                    controller: 'EmailNotificationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'EmailNotification', function($stateParams, EmailNotification) {
                    return EmailNotification.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'email-notification',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })*/
			
			
			.state('email-notification-detail', {
            parent: 'entity',
            url: '/email-notification/{id}',
            data: {
                authorities: ['ROLE_USER','ROLE_USER_ADMIN'],
                pageTitle: 'EmailNotifications'
            },
            views: {
                'content@': {
                	  templateUrl: 'app/entities/email-notification/email-notification-detail.html',
                      controller: 'EmailNotificationDetailController',
                      controllerAs: 'vm'
                }
            },
            resolve: {
             /*   translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tenant');
                    return $translate.refresh();
                }],*/
                entity: ['$stateParams', 'EmailNotification', function($stateParams, EmailNotification) {
                    return EmailNotification.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'email-notification',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
			
			
			
			
			
       /* .state('email-notification-detail.edit', {
            parent: 'email-notification-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER','ROLE_USER_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/email-notification/email-notification-dialog.html',
                    controller: 'EmailNotificationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EmailNotification', function(EmailNotification) {
                            return EmailNotification.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })*/
        
        
          .state('email-notification-detail.edit', {
        	  parent: 'email-notification-detail',
              url: '/detail/edit',
            data: {
            	 authorities: ['ROLE_USER','ROLE_USER_ADMIN']
            },
            views: {
                'content@': {
                	 templateUrl: 'app/entities/email-notification/email-notification-dialog.html',
                     controller: 'EmailNotificationDialogController',
                     controllerAs: 'vm'
                }
            },
            resolve: {
               /* translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('geofence');
                    return $translate.refresh();
                }],*/
                entity: ['$stateParams', 'EmailNotification', function($stateParams, EmailNotification) {
                    return EmailNotification.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'email-notification',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
		
        
        
        
        
        
        
        /*
        .state('email-notification.new', {
            parent: 'email-notification',
            url: '/new',
            data: {
                authorities: ['ROLE_USER','ROLE_USER_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/email-notification/email-notification-dialog.html',
                    controller: 'EmailNotificationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                subject: null,
                                message: null,
                                sentDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('email-notification', null, { reload: 'email-notification' });
                }, function() {
                    $state.go('email-notification');
                });
            }]
        })*/
        
        
        
         .state('email-notification.new', {
        	 parent: 'email-notification',
             url: '/new',
            data: {
            	 authorities: ['ROLE_USER','ROLE_USER_ADMIN']
            },
            views: {
                'content@': {
                	templateUrl: 'app/entities/email-notification/email-notification-dialog.html',
                    controller: 'EmailNotificationDialogController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
               
            	 entity: function () {
                     return {
                    	 subject: null,
                         message: null,
                         sentDate: null,
                         id: null
                     };
                 }
            }
        })
		
       
        
        
        /*
        .state('email-notification.edit', {
            parent: 'email-notification',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER','ROLE_USER_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/email-notification/email-notification-dialog.html',
                    controller: 'EmailNotificationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EmailNotification', function(EmailNotification) {
                            return EmailNotification.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('email-notification', null, { reload: 'email-notification' });
                }, function() {
                    $state.go('^');
                });
            }]
        })*/
        
        
        .state('email-notification.edit', {
            
        	  parent: 'email-notification',
              url: '/{id}/edit',
            data: {
            	 authorities: ['ROLE_USER','ROLE_USER_ADMIN']
            },
            views: {
                'content@': {
                	templateUrl: 'app/entities/email-notification/email-notification-dialog.html',
                    controller: 'EmailNotificationDialogController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
              /*  translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('geofence');
                    return $translate.refresh();
                }],*/
                entity: ['$stateParams', 'EmailNotification', function($stateParams, EmailNotification) {
                    return EmailNotification.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'email-notification',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        
        
    /*    
        .state('email-notification.delete', {
            parent: 'email-notification',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER','ROLE_USER_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/email-notification/email-notification-delete-dialog.html',
                    controller: 'EmailNotificationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['EmailNotification', function(EmailNotification) {
                            return EmailNotification.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('email-notification', null, { reload: 'email-notification' });
                }, function() {
                    $state.go('^');
                });
            }]
        });*/
        
        
        
        
        
        
        .state('email-notification.delete', {
        	 parent: 'email-notification',
             url: '/{id}/delete',
            data: {
            	 authorities: ['ROLE_USER','ROLE_USER_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                	 templateUrl: 'app/entities/email-notification/email-notification-delete-dialog.html',
                     controller: 'EmailNotificationDeleteController',
                     controllerAs: 'vm',
                    size: 'sm',
                    resolve: {
                        entity: ['EmailNotification', function(EmailNotification) {
                            return EmailNotification.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('email-notification', null, { reload: 'email-notification' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
        
        
        
    }

})();
