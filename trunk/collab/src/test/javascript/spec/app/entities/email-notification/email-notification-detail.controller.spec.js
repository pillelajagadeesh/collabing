'use strict';

describe('Controller Tests', function() {

    describe('EmailNotification Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockEmailNotification, MockUser, MockToUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockEmailNotification = jasmine.createSpy('MockEmailNotification');
            MockUser = jasmine.createSpy('MockUser');
            MockToUser = jasmine.createSpy('MockToUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'EmailNotification': MockEmailNotification,
                'User': MockUser,
                'ToUser': MockToUser
            };
            createController = function() {
                $injector.get('$controller')("EmailNotificationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'collabApp:emailNotificationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
