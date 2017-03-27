(function() {
    'use strict';

    angular
        .module('collabApp', [
            'ngStorage', 
            'ngResource',
            'ngCookies',
            'ngAria',
            'ngCacheBuster',
            'ngFileUpload',
            'ui.bootstrap',
            'ui.bootstrap.datetimepicker',
            'ui.router',
            'infinite-scroll',
            // jhipster-needle-angularjs-add-module JHipster will add new module here
            'angular-loading-bar'
        ])
        .run(run)
        //Added for Before and After login design changes
	    .run([
	        '$log', '$rootScope', '$window', '$state', '$location',
	        function($log, $rootScope, $window, $state, $location) {
	            $rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams) {
	         	   
	         	   if (toState.url == "/") {
	         	        $('body').addClass('layout-top-nav').removeClass('sidebar-mini tryitbg');
	         	        $('.main-footer').addClass('home-footer');
	         	    } else {
	         	        $('body').removeClass('layout-top-nav').addClass('sidebar-mini');
	         	        $('.content-wrapper').removeClass('home-container tryitbg');
	         	        $('.main-footer').removeClass('home-footer');
	         	    }
	         	   if (toState.url == "/login" || toState.url == "/reset/request" ||toState.url == "/reset/finish?key" || toState.url == "/accessdenied") {
	         		   $('body').addClass('layout-top-nav').removeClass('sidebar-mini');  
	        	        	$('.content-wrapper').addClass('main-wrapper');
	        	        	$('.main-footer').addClass('login-footer');
	         	   } else { 
	        	        	$('.content-wrapper').removeClass('main-wrapper tryitbg').addClass('sidebar-mini');
	        	        	$('.main-footer').removeClass('login-footer');
	        	    }
					if (toState.url == "/register") {
	         	        $('body').addClass('layout-top-nav tryitbg').removeClass('sidebar-mini');  
	        	        	$('.content-wrapper').addClass('main-wrapper tryitbg');
	         	        $('.main-footer').addClass('home-footer');
	         	    } else {
							 $('body').removeClass('tryitbg').addClass('sidebar-mini');  
	         	        $('.content-wrapper').removeClass('main-wrapper tryittrans').addClass('sidebar-mini');
	         	        $('.main-footer').removeClass('home-footer');
	         	    }
	            });
	        }
	     ])
	     //Added for upload required functionality

    run.$inject = ['stateHandler'];

    function run(stateHandler) {
        stateHandler.initialize();
    }
})();
