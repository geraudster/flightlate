(function() {
    'use strict';

    angular
        .module('flightlateApp')
        .controller('CarrierMySuffixDetailController', CarrierMySuffixDetailController);

    CarrierMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Carrier'];

    function CarrierMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Carrier) {
        var vm = this;

        vm.carrier = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('flightlateApp:carrierUpdate', function(event, result) {
            vm.carrier = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
