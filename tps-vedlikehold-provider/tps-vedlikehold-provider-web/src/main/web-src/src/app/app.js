require('angular');
require('angular-ui-router');
require('angular-animate');
require('angular-material');
require('angular-messages');
require('angular-material-icons');
require('angular-highlightjs');
require('angular-moment');

require('./components/login/login');
require('./components/servicerutine/servicerutine');

require('./services/serviceModule');
require('./services/locationService');
require('./services/sessionService');
require('./services/utilsService');
require('./services/authenticationService');
require('./services/serverServicerutineService');
require('./services/serverEnvironmentService');

var app = angular.module('tps-vedlikehold', ['ui.router', 'ngMaterial', 'ngMdIcons', 'angularMoment', 'tps-vedlikehold.login',
    'tps-vedlikehold.service', 'tps-vedlikehold.servicerutine']);

require('./factory/servicerutineFactory');
require('./shared/header/header');
require('./shared/side-navigator/side-navigator');
require('./directives/inputField');
require('./directives/outputField');
require('./directives/responseForm');

require('./settings/formConfig');


app.config(['$stateProvider', '$httpProvider', '$urlRouterProvider', '$mdThemingProvider',
    function($stateProvider, $httpProvider, $urlRouteProvider, $mdThemingProvider) {

    $urlRouteProvider.otherwise("/");

    $stateProvider

    .state('login', {
        url: "/login",
        views: {
            'content@' : {
                templateUrl: "app/components/login/login.html",
                controller: 'loginController'
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
                templateUrl: "app/components/serviceRutine/serviceRutine.html",
                controller: 'servicerutineController'
            },
            'header@': {
                templateUrl: "app/shared/header/header.html",
                controller: 'headerController'
            },
            'side-navigator@': {
                templateUrl: "app/shared/side-navigator/side-navigator.html",
                controller: 'navigatorController'
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
