(function() {
    'use strict';

    angular
        .module('collabApp')
        .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$state', 'Auth', 'Principal', 'ProfileService', 'LoginService','$rootScope'];

    function NavbarController ($state, Auth, Principal, ProfileService, LoginService,$rootScope) {
        var vm = this;
        vm.showpasswordlink=true;
        
        
        vm.isAuthenticated = Principal.isAuthenticated;
        ProfileService.getProfileInfo().then(function(response) {
            vm.inProduction = response.inProduction;
            vm.swaggerEnabled = response.swaggerEnabled;
        });
        vm.login = login;
        vm.logout = logout;
        vm.toggleNavbar = toggleNavbar;
        vm.collapseNavbar = collapseNavbar;
        vm.$state = $state;
       
        function login() {
            collapseNavbar();
            LoginService.open();
        }

        function logout() {
            collapseNavbar();
            Auth.logout();
            $rootScope.listAuthorities();
            $state.go('home');
           
        }
        vm.isNavbarCollapsed = true;
       /* vm.isAuthenticated = Principal.isAuthenticated;

        ProfileService.getProfileInfo().then(function(response) {
            vm.inProduction = response.inProduction;
            vm.swaggerEnabled = response.swaggerEnabled;
        });

        vm.login = login;
        vm.logout = logout;
        vm.toggleNavbar = toggleNavbar;
        vm.collapseNavbar = collapseNavbar;
        vm.$state = $state;

        function login() {
            collapseNavbar();
            LoginService.open();
        }

        function logout() {
            collapseNavbar();
            Auth.logout();
            $state.go('home');
        }*/

        function toggleNavbar() {
            vm.isNavbarCollapsed = !vm.isNavbarCollapsed;
			$('.treeview-menu').removeClass('menu-close').addClass('menu-open');
        }

        function collapseNavbar() {
            vm.isNavbarCollapsed = true;
            $('.treeview').removeClass('active');
			$('.treeview-menu').addClass('menu-close').removeClass('menu-open');
				$rootScope.close={
				"display": "none"
			}
        }
        
        $rootScope.listAuthorities=function(){
        	
    		$rootScope.has_ROLE_SUPER_ADMIN=false;
       	 $rootScope.has_ROLE_USER_ADMIN=false;
       	 $rootScope.has_ROLE_USER=false;   
           Principal.identity().then(function(identity){
           	if(identity && identity.authorities && identity.authorities.indexOf('ROLE_SUPER_ADMIN') !==- 1){
                   $rootScope.has_ROLE_SUPER_ADMIN=true;
                   $state.go('tenant');
           	}else if(identity && identity.authorities && identity.authorities.indexOf('ROLE_USER_ADMIN') !==- 1){
                   $rootScope.has_ROLE_USER_ADMIN=true;
                   $state.go('home');
           	}else if(identity && identity.authorities && identity.authorities.indexOf('ROLE_USER') !==- 1){
           		$rootScope.has_ROLE_USER=true;  
           		$state.go('home');
           		
           	}
           	// This is to hide password link for trakeye users 
           	if(identity){ 
           		if(identity.tenant != null){
           			if(identity.tenant.trakeyeTenant == true){
           			 vm.showpasswordlink=false;
           			}
           		}
           	   $rootScope.userName = identity.login;
               $rootScope.image = identity.userImage;
           	}
    	})
    	
    }
        $rootScope.getLocalhour = function(hour){
			var d  = new Date();
			d.setUTCHours(hour);
			d.setUTCMinutes(30);
			return d.getHours();
		}
        
        
        $rootScope.getUTChour=function(hour){
			var d = new Date();
			d.setHours(hour);
			d.setMinutes(0);
			return d.getUTCHours();
		}
        $rootScope.listAuthorities();
    }
})();
