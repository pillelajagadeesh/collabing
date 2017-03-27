(function() {
    'use strict';

    angular
        .module('collabApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
       /* .state('call-history', {
            parent: 'entity',
            url: '/call-history?page&sort&search',
            data: {
                authorities: ['ROLE_USER','ROLE_USER_ADMIN'],
                pageTitle: 'CallHistories'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/call-history/call-histories.html',
                    controller: 'CallHistoryController',
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
        
        
        .state('call-history', {
            parent: 'entity',
            url: '/call-history?page&sort&search',
            data: {
            	authorities: ['ROLE_USER','ROLE_USER_ADMIN'],
                pageTitle: 'CallHistories'
            },
            views: {
            'content@': {
                templateUrl: 'app/entities/call-history/call-histories.html',
                controller: 'CallHistoryController',
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
            }       
			})
        
        
        
        /*.state('call-history-detail', {
            parent: 'entity',
            url: '/call-history/{id}',
            data: {
                authorities: ['ROLE_USER','ROLE_USER_ADMIN'],
                pageTitle: 'CallHistory'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/call-history/call-history-detail.html',
                    controller: 'CallHistoryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'CallHistory', function($stateParams, CallHistory) {
                    return CallHistory.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'call-history',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })*/
			
			
			
			
				.state('call-history-detail', {
            parent: 'entity',
            url: '/call-history/{id}',
            data: {
                authorities: ['ROLE_USER','ROLE_USER_ADMIN'],
                pageTitle: 'CallHistory'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/call-history/call-history-detail.html',
                    controller: 'CallHistoryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
             /*   translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tenant');
                    return $translate.refresh();
                }],*/
                entity: ['$stateParams', 'CallHistory', function($stateParams, CallHistory) {
                    return CallHistory.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'call-history',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
			
			
			
			
			
			
			
	/*		
        .state('call-history-detail.edit', {
            parent: 'call-history-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER','ROLE_USER_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/call-history/call-history-dialog.html',
                    controller: 'CallHistoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CallHistory', function(CallHistory) {
                            return CallHistory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })*/
        
        .state('call-history-detail.edit', {
        	 parent: 'call-history-detail',
             url: '/detail/edit',
            data: {
            	 authorities: ['ROLE_USER','ROLE_USER_ADMIN']
            },
            views: {
                'content@': {
                	 templateUrl: 'app/entities/call-history/call-history-dialog.html',
                     controller: 'CallHistoryDialogController',
                     controllerAs: 'vm'
                }
            },
            resolve: {
               /* translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('geofence');
                    return $translate.refresh();
                }],*/
                entity: ['$stateParams', 'CallHistory', function($stateParams, CallHistory) {
                    return CallHistory.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'call-history',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
		
        
        
        
        
       /* 
        .state('call-history.new', {
            parent: 'call-history',
            url: '/new',
            data: {
                authorities: ['ROLE_USER','ROLE_USER_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/call-history/call-history-dialog.html',
                    controller: 'CallHistoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                callType: null,
                                callStatus: null,
                                callDuration: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('call-history', null, { reload: 'call-history' });
                }, function() {
                    $state.go('call-history');
                });
            }]
        })*/
        
         .state('call-history.new', {
        	 parent: 'call-history',
             url: '/new',
            data: {
            	 authorities: ['ROLE_USER','ROLE_USER_ADMIN']
            },
            views: {
                'content@': {
                	 templateUrl: 'app/entities/call-history/call-history-dialog.html',
                     controller: 'CallHistoryDialogController',
                     controllerAs: 'vm'
                }
            },
            resolve: {
               
            	 entity: function () {
                     return {
                    	 callType: null,
                         callStatus: null,
                         callDuration: null,
                         id: null
                     };
                 }
            }
        })
		
       
        
        
        
        
        
        /*.state('call-history.edit', {
            parent: 'call-history',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER','ROLE_USER_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/call-history/call-history-dialog.html',
                    controller: 'CallHistoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CallHistory', function(CallHistory) {
                            return CallHistory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('call-history', null, { reload: 'call-history' });
                }, function() {
                    $state.go('^');
                });
            }]
        })*/
        
        
        
        
        
        
        .state('call-history.edit', {
            
            parent: 'entity',
            url: '/call-history/{id}/edit',
            data: {
            	 authorities: ['ROLE_USER','ROLE_USER_ADMIN']
            },
            views: {
                'content@': {
                	 templateUrl: 'app/entities/call-history/call-history-dialog.html',
                     controller: 'CallHistoryDialogController',
                     controllerAs: 'vm'
                }
            },
            resolve: {
              /*  translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('geofence');
                    return $translate.refresh();
                }],*/
                entity: ['$stateParams', 'CallHistory', function($stateParams, CallHistory) {
                    return CallHistory.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'call-history',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        
        
      /*  
        
        .state('call-history.delete', {
            parent: 'call-history',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER','ROLE_USER_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/call-history/call-history-delete-dialog.html',
                    controller: 'CallHistoryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CallHistory', function(CallHistory) {
                            return CallHistory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('call-history', null, { reload: 'call-history' });
                }, function() {
                    $state.go('^');
                });
            }]
        });*/
        
        .state('call-history.delete', {
            parent: 'call-history',
            url: '/{id}/delete',
            data: {
            	 authorities: ['ROLE_USER','ROLE_USER_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                	 templateUrl: 'app/entities/call-history/call-history-delete-dialog.html',
                     controller: 'CallHistoryDeleteController',
                     controllerAs: 'vm',
                    size: 'sm',
                    resolve: {
                        entity: ['CallHistory', function(CallHistory) {
                            return CallHistory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('call-history', null, { reload: 'call-history' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
        
        
    }

})();
