(function() {
    'use strict';

    angular
        .module('flightlateApp')
        .controller('CarrierMySuffixDialogController', CarrierMySuffixDialogController);

    CarrierMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Carrier'];

    function CarrierMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Carrier) {
        var vm = this;

        vm.carrier = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.carrier.id !== null) {
                Carrier.update(vm.carrier, onSaveSuccess, onSaveError);
            } else {
                Carrier.save(vm.carrier, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('flightlateApp:carrierUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
