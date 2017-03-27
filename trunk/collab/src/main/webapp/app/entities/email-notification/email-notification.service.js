(function() {
    'use strict';
    angular
        .module('collabApp')
        .factory('EmailNotification', EmailNotification);

    EmailNotification.$inject = ['$resource','DateUtils'];

    function EmailNotification ($resource,DateUtils) {
        var resourceUrl =  'api/email-notifications/:id';

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
