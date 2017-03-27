(function() {
    'use strict';

    angular
        .module('collabApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
       /* .state('file-share', {
            parent: 'entity',
            url: '/file-share?page&sort&search',
            data: {
                authorities: ['ROLE_USER','ROLE_USER_ADMIN'],
                pageTitle: 'FileShares'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/file-share/file-shares.html',
                    controller: 'FileShareController',
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
        
        .state('file-share', {
            parent: 'entity',
            url: '/file-share?page&sort&search',
            data: {
            	 authorities: ['ROLE_USER','ROLE_USER_ADMIN'],
                 pageTitle: 'FileShares'
            },
            views: {
            'content@': {
            	  templateUrl: 'app/entities/file-share/file-shares.html',
                  controller: 'FileShareController',
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
      
        .state('file-share-detail', {
            parent: 'entity',
            url: '/file-share/{id}',
            data: {
                authorities: ['ROLE_USER','ROLE_USER_ADMIN'],
                pageTitle: 'FileShare'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/file-share/file-share-detail.html',
                    controller: 'FileShareDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'FileShare', function($stateParams, FileShare) {
                    return FileShare.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'file-share',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        */
			
			
			
			
			
			
				.state('file-share-detail', {
            parent: 'entity',
            url: '/file-share/{id}',
            data: {
                authorities: ['ROLE_USER','ROLE_USER_ADMIN'],
                pageTitle: 'FileShare'
            },
            views: {
                'content@': {
                	  templateUrl: 'app/entities/file-share/file-share-detail.html',
                      controller: 'FileShareDetailController',
                      controllerAs: 'vm'
                }
            },
            resolve: {
             /*   translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tenant');
                    return $translate.refresh();
                }],*/
                entity: ['$stateParams', 'FileShare', function($stateParams, FileShare) {
                    return FileShare.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'file-share',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        
        
        
        /*
        .state('file-share-detail.edit', {
            parent: 'file-share-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER','ROLE_USER_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/file-share/file-share-dialog.html',
                    controller: 'FileShareDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FileShare', function(FileShare) {
                            return FileShare.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })*/
			
			
			
			
			
			 .state('file-share-detail.edit', {
				 parent: 'file-share-detail',
		            url: '/detail/edit',
            data: {
            	 authorities: ['ROLE_USER','ROLE_USER_ADMIN']
            },
            views: {
                'content@': {
                	templateUrl: 'app/entities/file-share/file-share-dialog.html',
                    controller: 'FileShareDialogController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
               /* translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('geofence');
                    return $translate.refresh();
                }],*/
                entity: ['$stateParams', 'FileShare', function($stateParams, FileShare) {
                    return FileShare.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'file-share',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
		
       /*
			
        .state('file-share.new', {
            parent: 'file-share',
            url: '/new',
            data: {
                authorities: ['ROLE_USER','ROLE_USER_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/file-share/file-share-dialog.html',
                    controller: 'FileShareDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                fileName: null,
                                contentType: null,
                                content: null,
                                contentContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('file-share', null, { reload: 'file-share' });
                }, function() {
                    $state.go('file-share');
                });
            }]
        })*/
        
        
        
        
        
           .state('file-share.new', {
            parent: 'file-share',
            url: '/new',
            data: {
                authorities: ['ROLE_USER','ROLE_USER_ADMIN']
            },
            views: {
                'content@': {
                	templateUrl: 'app/entities/file-share/file-share-dialog.html',
                    controller: 'FileShareDialogController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
               
            	 entity: function () {
                     return {
                    	  fileName: null,
                          contentType: null,
                          content: null,
                          contentContentType: null,
                          id: null
                     };
                 }
            }
        })
		
       
        
        
        
        
        
        /*
        
        .state('file-share.edit', {
            parent: 'file-share',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER','ROLE_USER_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/file-share/file-share-dialog.html',
                    controller: 'FileShareDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FileShare', function(FileShare) {
                            return FileShare.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('file-share', null, { reload: 'file-share' });
                }, function() {
                    $state.go('^');
                });
            }]
        })*/
        
        
         
        .state('file-share.edit', {
            
        	 parent: 'file-share',
             url: '/{id}/edit',
             data: {
                 authorities: ['ROLE_USER','ROLE_USER_ADMIN']
             },
            views: {
                'content@': {
                	templateUrl: 'app/entities/file-share/file-share-dialog.html',
                    controller: 'FileShareDialogController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
              /*  translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('geofence');
                    return $translate.refresh();
                }],*/
                entity: ['$stateParams', 'FileShare', function($stateParams, FileShare) {
                    return FileShare.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'file-share',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        
        
       /* 
        
        .state('file-share.delete', {
            parent: 'file-share',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER','ROLE_USER_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/file-share/file-share-delete-dialog.html',
                    controller: 'FileShareDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['FileShare', function(FileShare) {
                            return FileShare.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('file-share', null, { reload: 'file-share' });
                }, function() {
                    $state.go('^');
                });
            }]
        });*/
        
        
        
       .state('file-share.delete', {
            parent: 'file-share',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER','ROLE_USER_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                	templateUrl: 'app/entities/file-share/file-share-delete-dialog.html',
                    controller: 'FileShareDeleteController',
                    controllerAs: 'vm',
                    size: 'sm',
                    resolve: {
                        entity: ['FileShare', function(FileShare) {
                            return FileShare.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('file-share', null, { reload: 'file-share' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
        
        
        
        
    }

})();
