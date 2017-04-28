(function() {
    'use strict';

    angular
        .module('flightlateApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('flight-my-suffix', {
            parent: 'entity',
            url: '/flight-my-suffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Flights'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/flight/flightsmySuffix.html',
                    controller: 'FlightMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('flight-my-suffix-detail', {
            parent: 'flight-my-suffix',
            url: '/flight-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Flight'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/flight/flight-my-suffix-detail.html',
                    controller: 'FlightMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Flight', function($stateParams, Flight) {
                    return Flight.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'flight-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('flight-my-suffix-detail.edit', {
            parent: 'flight-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/flight/flight-my-suffix-dialog.html',
                    controller: 'FlightMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Flight', function(Flight) {
                            return Flight.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('flight-my-suffix.new', {
            parent: 'flight-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/flight/flight-my-suffix-dialog.html',
                    controller: 'FlightMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                year: null,
                                month: null,
                                dayOfMonth: null,
                                dayOfWeek: null,
                                depTime: null,
                                flightNum: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('flight-my-suffix', null, { reload: 'flight-my-suffix' });
                }, function() {
                    $state.go('flight-my-suffix');
                });
            }]
        })
        .state('flight-my-suffix.edit', {
            parent: 'flight-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/flight/flight-my-suffix-dialog.html',
                    controller: 'FlightMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Flight', function(Flight) {
                            return Flight.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('flight-my-suffix', null, { reload: 'flight-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('flight-my-suffix.delete', {
            parent: 'flight-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/flight/flight-my-suffix-delete-dialog.html',
                    controller: 'FlightMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Flight', function(Flight) {
                            return Flight.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('flight-my-suffix', null, { reload: 'flight-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
