
app.controller("appointment", ["$scope", "$log", "$http", "$location", function($scope, $log, $http, $location){
    $scope.socket = io();

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

    $scope.id = getParameterByName("id", window.location.href);
    console.log($scope.id);
    $scope.socket.emit("get-appointment", $scope.id);
    $scope.socket.on("get-appointment", function(data){
        console.log(data);
        $scope.date = data.date;
        $scope.doctor = data.doctorName;
        $scope.patient = data.patientName;
        $scope.patientID = data.patientID;
        $scope.time = data.time;
        $scope.email = data.email;
        $scope.phone = data.phone;
        $scope.description = data.description;
        $scope.$apply();
    });

    $scope.addForm = function(){
        $scope.toAddForms.push($scope.toAddForms.length);
    };

    $scope.saveChanges = function(){
        $log.debug("save");
        var appointment = {
            id: $scope.id,
            date: $scope.date,
            doctorName: $scope.doctor,
            patientName: $scope.patient,
            patientID: $scope.patientID,
            time: $scope.time,
            email: $scope.email,
            phone: $scope.phone,
            description: $scope.description
        };
        $scope.socket.emit("save", appointment);
        $scope.submitForms();
        swal("Saved", "Your appointment has been saved", "success");
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
            url: '/upload?id='+$scope.id,
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


function getParameterByName(name, url) {
    if (!url) {
        url = window.location.href;
    }
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}
