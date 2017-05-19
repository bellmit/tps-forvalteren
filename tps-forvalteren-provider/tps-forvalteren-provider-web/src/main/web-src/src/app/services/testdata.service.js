
angular.module('tps-forvalteren.service')
    .service('testdataService', ['$http', '$q', function($http, $q) {

        var self =  this;
        var url = 'api/v1/testdata/';

        self.getTestpersoner = function(){
            var defer = $q.defer();
            $http.get(url + "getTestpersoner").then(
                function (data) {
                    defer.resolve(data);
                },
                function (error) {
                    defer.reject(error);
                }
            );
            return defer.promise;
        };
    }]);