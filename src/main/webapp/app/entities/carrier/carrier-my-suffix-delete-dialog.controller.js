(function() {
    'use strict';

    angular
        .module('flightlateApp')
        .controller('CarrierMySuffixDeleteController',CarrierMySuffixDeleteController);

    CarrierMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Carrier'];

    function CarrierMySuffixDeleteController($uibModalInstance, entity, Carrier) {
        var vm = this;

        vm.carrier = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Carrier.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
