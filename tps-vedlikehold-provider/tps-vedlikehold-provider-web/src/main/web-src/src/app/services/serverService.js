/**
 * @author Frederik de Lichtenberg (Visma Consulting AS).
 */
angular.module('tps-vedlikehold.service')
    .service('serverService', ['$http', 'servicerutineFactory', function($http, servicerutineFactory) {
        var self = this;
        var urlBase = 'api/v1/service';
        var urlBaseEnv = 'api/v1/environments';

        self.getFromServerServicerutiner = function() {
            console.log('serverService getFromServerServicerutiner');
            $http({method: 'GET', url: urlBase}).then(function (res) {
                servicerutineFactory.setServicerutiner(res.data);
            }, function (error) {
                // servicerutineFactory.setServerError();
                // showAlertApiError('servicerutiner');
            });
            // return $http({method: 'GET', url: urlBase});
            // if (!servicerutineFactory.isSetServicerutiner()) {
            //     servicerutineFactory.getFromServerServicerutiner().then(function (res) {
            //         servicerutineFactory.setServicerutiner(res.data);
            //     }, function (error) {
            //         showAlertApiError('servicerutiner');
            //     });
            // }
            // $http({method: 'GET', url: urlBase}).then(function(res) {
            //     servicerutineFactory.setServicerutiner(res.data);
            // });
        };
        
        self.getFromServerEnvironments = function() {
            $http({method: 'GET', url: urlBaseEnv}).then(function(res) {
                servicerutineFactory.setEnvironments(res);
            });
        };
    }]);