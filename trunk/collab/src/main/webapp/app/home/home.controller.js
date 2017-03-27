(function() {
    'use strict';

    angular
        .module('collabApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state','$rootScope'];

    function HomeController ($scope, Principal, LoginService, $state,$rootScope) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
        	  getAccount();
              $rootScope.listAuthorities(); 
            //  validateStates();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
                if(vm.isAuthenticated()==true){
                	validateStates();
                }
            });
        }
        function validateStates(){
        	if($rootScope.has_ROLE_USER_ADMIN === true){
            	$state.go('user-management')
            }else if($rootScope.has_ROLE_SUPER_ADMIN ==true){
            	$state.go('tenant');
            }else if($rootScope.has_ROLE_USER ==true){
            	$state.go('call-history');
            }
        }
        
        
        
        function register () {
            $state.go('register');
        }
    }
})();
