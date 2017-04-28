(function() {
    'use strict';

    angular
        .module('flightlateApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('carrier-my-suffix', {
            parent: 'entity',
            url: '/carrier-my-suffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Carriers'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/carrier/carriersmySuffix.html',
                    controller: 'CarrierMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('carrier-my-suffix-detail', {
            parent: 'carrier-my-suffix',
            url: '/carrier-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Carrier'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/carrier/carrier-my-suffix-detail.html',
                    controller: 'CarrierMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Carrier', function($stateParams, Carrier) {
                    return Carrier.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'carrier-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('carrier-my-suffix-detail.edit', {
            parent: 'carrier-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carrier/carrier-my-suffix-dialog.html',
                    controller: 'CarrierMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Carrier', function(Carrier) {
                            return Carrier.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('carrier-my-suffix.new', {
            parent: 'carrier-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carrier/carrier-my-suffix-dialog.html',
                    controller: 'CarrierMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                code: null,
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('carrier-my-suffix', null, { reload: 'carrier-my-suffix' });
                }, function() {
                    $state.go('carrier-my-suffix');
                });
            }]
        })
        .state('carrier-my-suffix.edit', {
            parent: 'carrier-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carrier/carrier-my-suffix-dialog.html',
                    controller: 'CarrierMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Carrier', function(Carrier) {
                            return Carrier.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('carrier-my-suffix', null, { reload: 'carrier-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('carrier-my-suffix.delete', {
            parent: 'carrier-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carrier/carrier-my-suffix-delete-dialog.html',
                    controller: 'CarrierMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Carrier', function(Carrier) {
                            return Carrier.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('carrier-my-suffix', null, { reload: 'carrier-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
