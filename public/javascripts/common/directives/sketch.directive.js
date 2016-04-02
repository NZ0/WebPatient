(function(jQ) {
    angular
        .module('webPatient')
        .directive('wpsketch', sketch);

    function sketch() {
        var directive = {
            restrict: 'E',
            templateUrl: 'common/directives/templates/sketch.html',
            scope: {
                displayToolbar: '=',
                imgUrl: '=',
                readOnly: '='
            },
            link: linkFunction
        };

        return directive;

        function linkFunction(scope, el, attr, ctrl) {
            var canvas = document.getElementById('schema');
            var context = canvas.getContext('2d');
            //priority to model
            var updateFromModel = true;

            if (!scope.readOnly) {
                jQ('#schema').sketch();
            }

            scope.$watch('imgUrl', function() {
                console.log('Update from model');
                var imageObj = new Image();
                imageObj.onload = function() {
                  context.drawImage(this, 0, 0);
                };
                if (scope.imgUrl) {
                    imageObj.src = scope.imgUrl;
                }
            });

            canvas.addEventListener('mouseup', function() {
                updateFromModel = false;
                scope.imgUrl = canvas.toDataURL("image/png");
            });
        }
    }
})(jQuery);