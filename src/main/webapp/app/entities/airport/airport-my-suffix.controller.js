(function() {
    'use strict';

    angular
        .module('flightlateApp')
        .controller('AirportMySuffixController', AirportMySuffixController);

    AirportMySuffixController.$inject = ['Airport'];

    function AirportMySuffixController(Airport) {

        var vm = this;

        vm.airports = [];

        loadAll();

        function loadAll() {
            Airport.query(function(result) {
                vm.airports = result;
                vm.searchQuery = null;
            });
        }
    }
})();
