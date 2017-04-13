/**
 * Created by Ale on 4/12/2017.
 */

var storage = require("./storage");
var pdfwritter = require("./3rd Party/Java/pdfwriter");

module.exports = function(app, sockets){

    //Get forms
    app.get("/android", function(req, res){

        var patientID = req.url.split("=")[1];
        var appointment = storage.getAppointment(patientID);
        res.send(appointment.listForms[0]);
    });

    //Update forms
    app.post("/android", function(req, res){
        // var formID = req.body.formID;
        var form = req.body.form;
        var formID = form.formID;

        pdfwritter.updatePDF(formID, form);
        res.send("OK");
    });

    //Send update that mobile is in the proximity
    app.put("/android", function (req, res){
        var patientID = req.body.androidID;
        var appointment = storage.getAppointment(patientID);

        for(var socket of sockets){
            socket.emit("patient-arrived", appointment);
        }
        res.send("OK");
    });
};


