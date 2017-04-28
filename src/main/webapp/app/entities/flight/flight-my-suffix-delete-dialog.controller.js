(function() {
    'use strict';

    angular
        .module('flightlateApp')
        .controller('FlightMySuffixDeleteController',FlightMySuffixDeleteController);

    FlightMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Flight'];

    function FlightMySuffixDeleteController($uibModalInstance, entity, Flight) {
        var vm = this;

        vm.flight = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Flight.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
