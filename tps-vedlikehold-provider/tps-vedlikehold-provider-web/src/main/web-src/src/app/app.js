require('angular');
require('angular-ui-router');
require('angular-animate');
require('angular-material');
require('angular-messages');
require('angular-material-icons');
require('angular-highlightjs');
require('angular-moment');

require('./components/login/login.module');
require('./components/service-rutine/service-rutine.module');

require('./components/login/login.controller');
require('./components/service-rutine/service-rutine.controller');

require('./services/service.module');
require('./services/location.service');
require('./services/session.service');
require('./services/utils.service');
require('./services/authentication.service');
require('./services/server-service-rutine.service');
require('./services/server-environment.service');

var app = angular.module('tps-vedlikehold', ['ui.router', 'ngMaterial', 'ngMdIcons', 'angularMoment', 'tps-vedlikehold.login',
    'tps-vedlikehold.service', 'tps-vedlikehold.service-rutine']);

require('./factory/service-rutine.factory');

require('./shared/header/header.controller');
require('./shared/side-navigator/side-navigator.controller');

require('./directives/input-field.directive');
require('./directives/output-field.directive');

require('./settings/form.config');


app.config(['$stateProvider', '$httpProvider', '$urlRouterProvider', '$mdThemingProvider',
    function($stateProvider, $httpProvider, $urlRouteProvider, $mdThemingProvider) {

    $urlRouteProvider.otherwise("/");

    $stateProvider

    .state('login', {
        url: "/login",
        views: {
            'content@' : {
                templateUrl: "app/components/login/login.html",
                controller: 'LoginCtrl'
            }
        }
    })
    .state('servicerutine', {
        url: "/",
        params: {
            serviceRutinenavn: null
        },
        resolve: {
            servicerutinerPromise: "serverServicerutineService",
            environmentsPromise: "serverEnvironmentService"
        },
        views: {
            'content@': {
                templateUrl: "app/components/service-rutine/service-rutine.html",
                controller: 'ServicerutineCtrl'

            },
            'header@': {
                templateUrl: "app/shared/header/header.html",
                controller: 'HeaderCtrl'
            },
            'side-navigator@': {
                templateUrl: "app/shared/side-navigator/side-navigator.html",
                controller: 'SideNavigatorCtrl'
            }
        }
    });

    $httpProvider.defaults.headers.common["X-Requested-With"] = "XMLHttpRequest";

    var extended_grey = $mdThemingProvider.extendPalette('grey', { '50': '#ffffff'});
    $mdThemingProvider.definePalette('tps-vk-grey', extended_grey);

    $mdThemingProvider.theme('default')
        .primaryPalette('indigo', {
            'default': '500'
        })
        .accentPalette('blue', {
            'default': 'A200'
        })
        .backgroundPalette('tps-vk-grey', {
            'default': '50'
        });
        
}]);

app.run(['$rootScope', '$state', 'authenticationService', 'sessionService', 'locationService',
    function($rootScope, $state, authenticationService, sessionService, locationService){
        
    $rootScope.$on('$stateChangeStart', function(event, toState){
        if (toState.name === 'login') {
            return;
        }

        var authenticated = sessionService.getIsAuthenticated();
        
        if (authenticated) { return; }
        else {
            event.preventDefault();
            locationService.redirectToLoginState();
        }
    });
}]);
