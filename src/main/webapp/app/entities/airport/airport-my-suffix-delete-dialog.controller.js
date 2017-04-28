(function() {
    'use strict';

    angular
        .module('flightlateApp')
        .controller('AirportMySuffixDeleteController',AirportMySuffixDeleteController);

    AirportMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Airport'];

    function AirportMySuffixDeleteController($uibModalInstance, entity, Airport) {
        var vm = this;

        vm.airport = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Airport.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
