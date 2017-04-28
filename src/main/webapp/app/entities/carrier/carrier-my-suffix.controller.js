(function() {
    'use strict';

    angular
        .module('flightlateApp')
        .controller('CarrierMySuffixController', CarrierMySuffixController);

    CarrierMySuffixController.$inject = ['Carrier'];

    function CarrierMySuffixController(Carrier) {

        var vm = this;

        vm.carriers = [];

        loadAll();

        function loadAll() {
            Carrier.query(function(result) {
                vm.carriers = result;
                vm.searchQuery = null;
            });
        }
    }
})();
