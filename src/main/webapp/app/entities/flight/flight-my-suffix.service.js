(function() {
    'use strict';
    angular
        .module('flightlateApp')
        .factory('Flight', Flight);

    Flight.$inject = ['$resource'];

    function Flight ($resource) {
        var resourceUrl =  'api/flights/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
