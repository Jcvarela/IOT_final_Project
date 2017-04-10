
app.controller("appointment", ["$scope", "$log", "$http", "$location", function($scope, $log, $http, $location){
    console.log("Hi from Appointment");
    createCalendar();
}]);
