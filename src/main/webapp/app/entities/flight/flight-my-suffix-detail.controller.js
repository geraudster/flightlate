(function() {
    'use strict';

    angular
        .module('flightlateApp')
        .controller('FlightMySuffixDetailController', FlightMySuffixDetailController);

    FlightMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Flight', 'Airport', 'Carrier'];

    function FlightMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Flight, Airport, Carrier) {
        var vm = this;

        vm.flight = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('flightlateApp:flightUpdate', function(event, result) {
            vm.flight = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
