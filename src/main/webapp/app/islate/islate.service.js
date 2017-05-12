(function() {
    'use strict';
    angular
        .module('flightlateApp')
        .factory('FlightView', FlightView);

    FlightView.$inject = ['$resource'];

    function FlightView ($resource) {
        var resourceUrl =  'api/flight';

        return $resource(resourceUrl, {}, {
            'query': { method: 'POST',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            }
        });
    }
})();
