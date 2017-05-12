(function() {
    'use strict';

    angular
        .module('flightlateApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('islate', {
                parent: 'entity',
                url: '/islate',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Flights'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/islate/islate.html',
                        controller: 'FlightMySuffixController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                }
            });
    }

})();
