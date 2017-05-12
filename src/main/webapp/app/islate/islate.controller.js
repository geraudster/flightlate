(function() {
    'use strict';

    angular
        .module('flightlateApp')
        .controller('IslateController', IslateController);

    IslateController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance'];

    function IslateController ($timeout, $scope, $stateParams, $uibModalInstance) {
        var vm = this;

        vm.flight = entity;
        vm.clear = clear;
        vm.save = save;
        vm.airports = Airport.query();
        vm.carriers = Carrier.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            console.log('Hi there, in save function');
            vm.isSaving = true;
            if (vm.flight.id !== null) {
                Flight.update(vm.flight, onSaveSuccess, onSaveError);
            } else {
                Flight.save(vm.flight, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('flightlateApp:flightUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }
    }
})();
