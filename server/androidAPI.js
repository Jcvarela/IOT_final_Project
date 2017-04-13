/**
 * Created by Ale on 4/12/2017.
 */

var storage = require("./storage");

module.exports = function(app){

    //Get forms
    app.get("/android", function(req, res){
        // var patientID = req.androidID;
        // var appointment = storage.getAppointment(patientID);
        // res.send(appointment.listForms);
        res.send("Hello form GET");

    });

    //Update forms
    app.post("/android", function(req, res){
        // var formID = req.body.formID;
        var form = req.body.form;
        var formID = form.formID;

    });
};


