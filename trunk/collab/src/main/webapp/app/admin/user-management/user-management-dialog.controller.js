(function() {
    'use strict';

    angular
        .module('collabApp')
        .controller('UserManagementDialogController',UserManagementDialogController);

    UserManagementDialogController.$inject = ['$rootScope','$scope','$stateParams', '$state',  'entity', 'User','Principal'];
    
   
    function UserManagementDialogController ($rootScope ,$scope,$stateParams, $state, entity, User,Principal) {
       
    	$scope.EMAIL_REGEXP =/^[_A-Za-z0-9-\+]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})$/;
        $scope.PHONE_REGEXP =/^[0-9]+$/;
    	
    	var vm = this;

        //vm.authorities = ['ROLE_USER','ROLE_USER_ADMIN'];
        vm.clear = clear;
        vm.languages = null;
        vm.save = save;
        vm.user = entity;


        Principal.identity().then(function(identity){
        	/*if(identity.authorities && identity.authorities.indexOf('ROLE_SUPER_ADMIN') !==- 1){
        		vm.authorities = ['ROLE_USER_ADMIN'];
   			 
        	}else if(identity.authorities && identity.authorities.indexOf('ROLE_USER_ADMIN') !==- 1){*/
        		vm.authorities = ['ROLE_USER','ROLE_USER_ADMIN'];
        		
   			 
        	//}
        	if(vm.user.id ==null){
        		vm.user.langKey='en';
        		//vm.user.authorities=vm.authorities;
        		vm.user.fromTime=$rootScope.getUTChour(8);
        		vm.user.toTime=$rootScope.getUTChour(20);
        	
        	}
        
        });
        
        

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function onSaveSuccess (result) {
            vm.isSaving = false;
            //$uibModalInstance.close(result);
            $state.go('user-management');
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        function save () {
            vm.isSaving = true;
            if (vm.user.id !== null) {
                User.update(vm.user, onSaveSuccess, onSaveError);
            } else {
                vm.user.langKey = 'en';
                User.save(vm.user, onSaveSuccess, onSaveError);
            }
        }
    }
})();
