(function() {
    'use strict';
    angular
        .module('collabApp')
        .factory('CallHistory', CallHistory);

    CallHistory.$inject = ['$resource','DateUtils'];

    function CallHistory ($resource,DateUtils) {
        var resourceUrl =  'api/call-histories/:id';

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
