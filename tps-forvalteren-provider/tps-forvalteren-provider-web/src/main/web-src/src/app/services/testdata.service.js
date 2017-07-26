
angular.module('tps-forvalteren.service')
    .service('testdataService', ['$http', '$q', function($http, $q) {

        var self =  this;
        var url = 'api/v1/testdata/';
        var kodeverkUrl = 'api/v1/kodeverk/';

        self.getTestpersoner = function(id){
            var defer = $q.defer();
            $http.get(url + 'gruppe/' + id).then(
                function (data) {
                    defer.resolve(data);
                },
                function (error) {
                    defer.reject(error);
                }
            );
            return defer.promise;
        };

        self.opprettTestpersoner = function(gruppeId, kriterier){
            var defer = $q.defer();
            $http.post(url + 'personer/' + gruppeId, {personKriterierListe: kriterier}).then(
                function (data) {
                    defer.resolve(data);
                },
                function (error) {
                    defer.reject(error);
                }
            );
            return defer.promise;
        };

        self.sletteTestpersoner = function(identer){
            var defer = $q.defer();
            $http.post(url + 'deletepersoner', {ids: identer}).then(
                function (data) {
                    defer.resolve(data);
                },
                function (error) {
                    defer.reject(error);
                }
            );
            return defer.promise;
        };

        self.oppdaterTestpersoner = function(personer){
            var defer = $q.defer();
            $http.post(url + 'updatepersoner', personer).then(
                function (data) {
                    defer.resolve(data);
                },
                function (error) {
                    defer.reject(error);
                }
            );
            return defer.promise;
        };

        self.validerListe = function(identer){
            var defer = $q.defer();
            $http.post(url + 'checkpersoner', identer).then(
                function (data) {
                    defer.resolve(data);
                },
                function (error) {
                    defer.reject(error);
                }
            );
            return defer.promise;
        };

        self.opprettFraListe = function(gruppeId, identer){
            var defer = $q.defer();
            $http.post(url + 'createpersoner/' + gruppeId, identer).then(
                function (data) {
                    defer.resolve(data);
                },
                function (error) {
                    defer.reject(error);
                }
            );
            return defer.promise;
        };

        self.hentTestgrupper = function () {
            var defer = $q.defer();
            $http.get(url + 'grupper').then(
                function (data) {
                    defer.resolve(data);
                },
                function (error) {
                    defer.reject(error);
                }
            );
            return defer.promise;
        };

        self.lagreTestgruppe = function (gruppe) {
            var defer = $q.defer();
            $http.post(url + 'gruppe', gruppe).then(
                function (data) {
                    defer.resolve(data);
                },
                function (error) {
                    defer.reject(error);
                }
            );
            return defer.promise;
        };

        self.sletteTestgruppe = function (gruppeId) {
            var defer = $q.defer();
            $http.post(url + 'deletegruppe/' + gruppeId).then(
                function (data) {
                    defer.resolve(data);
                },
                function (error) {
                    defer.reject(error);
                }
            );
            return defer.promise;
        };

        self.sendTilTps = function (gruppeId, miljoer) {
            var defer = $q.defer();
            $http.post(url + 'tps/' + gruppeId, miljoer).then(
                function (data) {
                    defer.resolve(data);
                },
                function (error) {
                    defer.reject(error);
                }
            );
            return defer.promise;
        };

        self.hentKommuner = function () {
            var defer = $q.defer();
            $http.get(kodeverkUrl + 'knr').then(
                function (data) {
                    defer.resolve(data);
                },
                function (error) {
                    defer.reject(error);
                }
            );
            return defer.promise;
        };

        self.hentPostnummer = function () {
            var defer = $q.defer();
            $http.get(kodeverkUrl + 'postnummer').then(
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
