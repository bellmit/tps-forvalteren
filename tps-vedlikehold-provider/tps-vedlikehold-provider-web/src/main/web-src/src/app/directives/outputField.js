/**
 * @author Frederik de Lichtenberg (Visma Consulting AS).
 * */
angular.module('tps-vedlikehold').directive('tpsOutputField', function(){

    return {
        restrict: 'E',
        replace: true,
        scope: {
            fieldModel: '=',
            label: '@'
        },
        template:
        '<div layout="column">' +
        '<md-input-container class="tps-output-input-container md-input-focused" >' +
            '<label class="tps-output-label">{{ label }}</label>' +
<<<<<<< HEAD
            '<input class="tps-output-input" ng-class="{filled : fieldModel.trim().length, empty : fieldModel.trim().length == 0}" type="text" ng-model="fieldModel" readonly/>' +
        '</md-input-container>' +
=======
            '<input class="tps-output-input" ng-class="{filled : fieldData.trim().length, empty : fieldData.trim().length == 0}"' +
                'type="text" ng-model="fieldData" readonly tabindex="-1"/>' +
            '</md-input-container>' +
>>>>>>> 30300e0cdad3762881440c1ac10fe47dec4e1dc7
        '</div>'
    };
});
