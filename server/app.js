/**
 * Created by Ale on 4/10/2017.
 */

//Require socket and http objects
var websockets = require("socket.io");
var http = require("http");

//Require Express and body-parser
var express = require("express");
var bodyParser = require("body-parser");
var app = express();


//Setup socket connections
var server = http.Server(app);
var io = websockets(server);
var request = require('request');
server.listen(8080);


//Setup parsing requests
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));


//Allow for public views and scripts
app.use(express.static("public"));


//Listen on Sockets
var sockets = [];
io.on("connection", function (socket) {
    sockets.push(socket);
    //Calendar Controllers
    socket.on("createApt", createAppointment);
    socket.on("deleteApt", deleteAppointment);
    socket.on("load", function(date){
        var list = storage.getAppointmentsByDate(date);
        socket.emit("load", list);
    });

    //Appointment Controllers
    socket.on("save", saveAppointment);
    socket.on("get-appointment", function(id){
        var appointment = storage.getAppointmentByID(id);
        socket.emit("get-appointment", appointment);
    });
});


var storage = require("./storage");
var Appointment = storage.Appointment;

function saveAppointment(appt){
    storage.updateAppointment(appt);
}

function createAppointment(appt){
    var appointment = new Appointment(appt.id, appt.doctorName, appt.patientName, appt.patientID,
        appt.date, appt.time, appt.email, appt.phone, appt.description);

    storage.addAppointment(appointment);
}

function deleteAppointment(id){
    storage.deleteAppointment(id);
}

//File uploads
var pdfUploader = require("./pdfUploader");
pdfUploader(app);

//Initialize android API
var androidAPI = require("./androidAPI");
androidAPI(app,sockets);


function sendAccess(phone, message){
    request('https://textbelt.com/text', {
        body: {
            phone: phone,
            message: message,
            key: '060a811d287c5ae84ecdf7df5c0a41115c5495c2Yh7YuWbri4WNQCbyPpunj8YiR',
        },
    })
}