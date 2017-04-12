
app.controller("appointment", ["$scope", "$log", "$http", "$location", function($scope, $log, $http, $location){
    console.log("Hi from Appointment");
    createCalendar();

    $scope.doctor = "";
    $scope.patient = "";
    $scope.id = "";
    $scope.time = "";
    $scope.email = "";
    $scope.phone = "";


    $scope.toAddForms = [0];
    $scope.forms = [
        {
            name: "prescriptions.pdf",
            url: "http://127.0.0.1:8080/home.html#!/appointment?id=12121&form=23123",
            completed: true
        },
        {
            name: "allergies.pdf",
            url: "http://127.0.0.1:8080/home.html#!/appointment?id=45645&form=87979",
            completed: false
        }
    ];


    $scope.addForm = function(){
        $scope.toAddForms.push($scope.toAddForms.length);
    };

    $scope.saveChanges = function(){
        $log.debug("Save");
    };

    $scope.sendAccess = function() {
        $log.debug("Access");
    };
}]);
