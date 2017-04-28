'use strict';

describe('Controller Tests', function() {

    describe('Flight Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockFlight, MockAirport, MockCarrier;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockFlight = jasmine.createSpy('MockFlight');
            MockAirport = jasmine.createSpy('MockAirport');
            MockCarrier = jasmine.createSpy('MockCarrier');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Flight': MockFlight,
                'Airport': MockAirport,
                'Carrier': MockCarrier
            };
            createController = function() {
                $injector.get('$controller')("FlightMySuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'flightlateApp:flightUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
