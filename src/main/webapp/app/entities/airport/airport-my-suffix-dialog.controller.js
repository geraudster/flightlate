(function() {
    'use strict';

    angular
        .module('flightlateApp')
        .controller('AirportMySuffixDialogController', AirportMySuffixDialogController);

    AirportMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Airport'];

    function AirportMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Airport) {
        var vm = this;

        vm.airport = entity;
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
            if (vm.airport.id !== null) {
                Airport.update(vm.airport, onSaveSuccess, onSaveError);
            } else {
                Airport.save(vm.airport, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('flightlateApp:airportUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
