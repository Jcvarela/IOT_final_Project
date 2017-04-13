
app.controller("login", ["$scope", "$log", "$http", "$location", function($scope, $log, $http, $location){
    $scope.username = "";
    $scope.password = "";

    $scope.submitLogin = function(){
        $log.debug("Trying to login");
        $log.debug("Username: "+$scope.username);
        $log.debug("Password: "+$scope.password);

        if($scope.username != "" && $scope.password != ""){
            $location.url("/calendar");
        }
    };
}]);

var socket = io();