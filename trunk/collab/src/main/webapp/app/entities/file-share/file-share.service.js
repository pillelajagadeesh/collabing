(function() {
    'use strict';
    angular
        .module('collabApp')
        .factory('FileShare', FileShare);

    FileShare.$inject = ['$resource','DateUtils'];

    function FileShare ($resource,DateUtils) {
        var resourceUrl =  'api/file-shares/:id';

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
