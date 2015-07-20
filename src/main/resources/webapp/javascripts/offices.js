angular.module('officesModule', [])
    .controller('officesCtrl', function ($scope, $http) {
        $scope.listOffices = function() {
            $http.get('/api/offices').then(function(response) {
                $scope.offices = response.data;
            });
        };
        $scope.listOpenOffices = function() {
            var utc = new Date().getTimezoneOffset()/-60;
            var time = $scope.formatAMPM(new Date());
            var params = {
                utc : utc,
                time : time
            };
            $http.get('/api/offices/open', {params: params}).then(function(response) {
                $scope.openOffices = response.data;
            });
        }
        $scope.newOffice = function() {
            $scope.office = {};
            $scope.originalOffice = angular.copy($scope.office);
        };
        $scope.saveOffice = function() {
            $scope.office.openFrom = $('#openFrom').val();
            $scope.office.openTo = $('#openTo').val();
            $http.put('/api/offices', $scope.office).then(function() {
                $scope.listOffices();
                $scope.newOffice();
            });
        };
        $scope.openOffice = function(officeId) {
            $http.get('/api/offices/' + officeId).then(function(response) {
                $scope.office = response.data;
                $scope.originalOffice = angular.copy($scope.office);
            });
        };
        $scope.revertOffice = function() {
            $scope.office = angular.copy($scope.originalOffice);
        };
        $scope.deleteOffice = function() {
            $http.delete('/api/offices/' + $scope.office.id).then(function() {
                $scope.listOffices();
                $scope.newOffice();
            });
        };
        $scope.cssClass = function(ngModelController) {
            return {
                'has-error': ngModelController.$invalid,
                'has-success': ngModelController.$valid
            };
        };
        $scope.cssClassButton = function(ngModelController) {
            return {
                'btn-success': ngModelController.$valid,
                'btn-danger': ngModelController.$invalid
            };
        };
        $scope.isValid = function(ngModelController) {
            return ngModelController.$valid;
        };
        $scope.canRevertOffice = function() {
            return !angular.equals($scope.office, $scope.originalOffice);
        };
        $scope.canDeleteOffice = function() {
            return (typeof $scope.office !== 'undefined' && typeof $scope.office.id !== 'undefined');
        };
        $scope.formatAMPM = function (date) {
            var hours = date.getHours();
            var minutes = date.getMinutes();
            var ampm = hours >= 12 ? 'PM' : 'AM';
            hours = hours % 12;
            hours = hours ? hours : 12; // the hour '0' should be '12'
            minutes = minutes < 10 ? '0'+minutes : minutes;
            var strTime = hours + ':' + minutes + ' ' + ampm;
            return strTime;
        };
        $scope.listOffices();
        $scope.newOffice();
    });