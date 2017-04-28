(function() {
    'use strict';

    angular
        .module('flightlateApp')
        .controller('FlightMySuffixController', FlightMySuffixController);

    FlightMySuffixController.$inject = ['Flight'];

    function FlightMySuffixController(Flight) {

        var vm = this;

        vm.flights = [];

        loadAll();

        function loadAll() {
            Flight.query(function(result) {
                vm.flights = result;
                vm.searchQuery = null;
            });
        }
    }
})();
