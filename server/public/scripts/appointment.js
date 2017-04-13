
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
            url: "http://127.0.0.1:8080/index.html#!/appointment?id=12121&form=23123",
            completed: true
        },
        {
            name: "allergies.pdf",
            url: "http://127.0.0.1:8080/index.html#!/appointment?id=45645&form=87979",
            completed: false
        }
    ];


    $scope.addForm = function(){
        $scope.toAddForms.push($scope.toAddForms.length);
    };

    $scope.saveChanges = function(){
        $log.debug("Save");
        $scope.submitForms();

    };

    $scope.sendAccess = function() {
        $log.debug("Access");
    };

    $scope.logout = function(){
        $location.url("/");
    };

    $scope.goToCalendar = function(){
        $location.url("/calendar");
    };

    $scope.submitForms = function () {
        var fd = new FormData();
        $(".file-form").each(function(){
            var file = $(this).prop("files")[0];
            fd.append("file", file);
        });

        $.ajax({
            url: '/upload',
            type: 'POST',
            data: fd,
            processData: false,
            contentType: false,
            success: function(data){
                console.log('upload successful!');
                $scope.toAddForms = [0];
            }
        });
    }
}]);
