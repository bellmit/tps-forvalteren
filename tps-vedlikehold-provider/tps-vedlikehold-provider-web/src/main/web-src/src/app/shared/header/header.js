/**
 * @author Frederik Gørvell (Visma Consulting AS).
 * */
angular.module('tps-vedlikehold').controller('headerController', ['$scope', '$location', 'authenticationService', '$mdSidenav', function($scope, $location, authenticationService, $mdSidenav) {

    $scope.logout = function(){
        authenticationService.invalidateSession(function(){
            console.log("Logging out");
            $location.path('/');
        });
    };

    $scope.toggleSideNav = function(menuId) {
        $mdSidenav(menuId).toggle();
    };

    /*
     * Hopper til element og setter focus. Brukes av skip navigasjon
     */

    // $scope.jumpToElement = function(id){
    //     console.log("hopp til " + id);
    //     var elem = document.getElementById(id);
    //     if (elem) {
    //         elem.focus();
    //     }
    // };
}]);
