(function() {
    'use strict';

    angular
        .module('flightlateApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('airport-my-suffix', {
            parent: 'entity',
            url: '/airport-my-suffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Airports'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/airport/airportsmySuffix.html',
                    controller: 'AirportMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('airport-my-suffix-detail', {
            parent: 'airport-my-suffix',
            url: '/airport-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Airport'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/airport/airport-my-suffix-detail.html',
                    controller: 'AirportMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Airport', function($stateParams, Airport) {
                    return Airport.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'airport-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('airport-my-suffix-detail.edit', {
            parent: 'airport-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/airport/airport-my-suffix-dialog.html',
                    controller: 'AirportMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Airport', function(Airport) {
                            return Airport.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('airport-my-suffix.new', {
            parent: 'airport-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/airport/airport-my-suffix-dialog.html',
                    controller: 'AirportMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                iata: null,
                                lon: null,
                                lat: null,
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('airport-my-suffix', null, { reload: 'airport-my-suffix' });
                }, function() {
                    $state.go('airport-my-suffix');
                });
            }]
        })
        .state('airport-my-suffix.edit', {
            parent: 'airport-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/airport/airport-my-suffix-dialog.html',
                    controller: 'AirportMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Airport', function(Airport) {
                            return Airport.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('airport-my-suffix', null, { reload: 'airport-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('airport-my-suffix.delete', {
            parent: 'airport-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/airport/airport-my-suffix-delete-dialog.html',
                    controller: 'AirportMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Airport', function(Airport) {
                            return Airport.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('airport-my-suffix', null, { reload: 'airport-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
