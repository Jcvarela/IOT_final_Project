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
server.listen(8080);


//Setup parsing requests
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));


//Allow for public views and scripts
app.use(express.static("public"));


//Listen on Sockets
io.on("connection", function (socket) {
    //Calendar Controllers
    socket.on("createApt", createAppointment);
    socket.on("deleteApt", deleteAppointment);

    //Appointment Controllers
});


function createAppointment(appointment){
    console.log("Create apt: \n"+JSON.stringify(appointment));
}

function deleteAppointment(id){
    console.log("Delete apt: "+id);
}

//File uploads
var pdfUploader = require("./pdfUploader");
pdfUploader(app);