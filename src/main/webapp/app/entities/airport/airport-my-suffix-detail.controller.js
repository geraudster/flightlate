(function() {
    'use strict';

    angular
        .module('flightlateApp')
        .controller('AirportMySuffixDetailController', AirportMySuffixDetailController);

    AirportMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Airport'];

    function AirportMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Airport) {
        var vm = this;

        vm.airport = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('flightlateApp:airportUpdate', function(event, result) {
            vm.airport = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
