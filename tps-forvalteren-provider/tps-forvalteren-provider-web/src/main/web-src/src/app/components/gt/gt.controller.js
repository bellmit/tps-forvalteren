angular.module('tps-forvalteren.gt')
    .controller('GTCtrl', ['$scope', '$mdDialog', '$state', 'utilsService', 'environmentsPromise',
        function ($scope, $mdDialog, $state,  utilsService, environmentsPromise) {

            $scope.isServicerutineState = function () {
                return locationService.isServicerutineState();
            };

            $scope.submit = function () {
                $scope.formData.aksjonsKode = "B1";
                var params = utilsService.createParametersFromFormData($scope.formData);
                serviceRutineFactory.getServiceRutineResponse("FS03-FDNUMMER-KERNINFO-O", params).then(function (res) {

                    var response = res.data.response;
                    var xml = res.data.xml;

                    }, function (error) {
                        console.error(error);
                    }
                );
            };

            function init() {
                if (environmentsPromise) {
                    $scope.environments = utilsService.sortEnvironments(serviceRutineFactory.getEnvironments());
                }
            }

            init();
        }]);

