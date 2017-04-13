var app = angular.module("formed", ["ngRoute", 'ngResource']);

app.config(function($routeProvider){
    $routeProvider
        .when("/", {
            title: "Form'd | Login",
            templateUrl: "login.html",
            controller: "login"
        })
        .when("/calendar", {
            title: "Form'd | Calendar",
            templateUrl: "calendar.html",
            controller: "calendar"
        })
        .when("/appointment", {
            title: "Form'd | Appointment",
            templateUrl: "appointment.html",
            controller: "appointment"
        })
});


app.run(['$rootScope', function($rootScope) {
    $rootScope.$on('$routeChangeSuccess', function (event, current, previous) {
        $rootScope.title = current.$$route.title;
    });
}]);