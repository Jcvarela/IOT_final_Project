/**
 * Created by Ale on 4/12/2017.
 */

var pdfreader = require("./pdfreader");
var fs = require("fs");
var path = require("path");

exports.appointmentList = [];

fs.readFile("uploads/appointments.json", function (err, data) {
    if(data == "") return;
    var list = [];
    for(var appt of JSON.parse(data)){
        list.push(generateAppointment(appt));
    }
    exports.appointmentList = list;
});

exports.addAppointment = function(appointment){
    exports.appointmentList.push(appointment);
    fs.writeFile("uploads/appointments.json", JSON.stringify(exports.appointmentList));
};

exports.deleteAppointment = function(id){
    var i = 0;
    for(var apt of exports.appointmentList){
        if(apt.id == id){
            exports.appointmentList.splice(i,1);
        }
        i++;
    }
    fs.writeFile("uploads/appointments.json", JSON.stringify(exports.appointmentList));
};

exports.getAppointment = function(patientID){
    for(var apt of exports.appointmentList){
        if(apt.patientID == patientID){
            return apt;
        }
    }
};

exports.getAppointmentByID = function(id){
    for(var apt of exports.appointmentList){
        if(apt.id == id){
            return apt;
        }
    }
};

exports.getAppointmentsByDate = function(date){
    var list = [];
    for(var apt of exports.appointmentList){
        if(apt.date == date){
            list.push(apt);
        }
    }
    return list;
};


exports.updateAppointment = function(appt){
    exports.deleteAppointment(appt.id);
    var appointment = generateAppointment(appt);
    exports.addAppointment(appointment);
};

exports.addAppointmentForm = function (id, filePath, fileName) {
    var appointment = exports.getAppointmentByID(id);
    console.log("class: "+appointment.constructor.name);
    appointment.addForm(filePath, fileName);
};

exports.Appointment = class {
    constructor(id, doctor, patientName, patientID, date, time, email, phone, description){
        this.id = id;
        this.date = date;
        this.doctorName = doctor;
        this.patientName = patientName;
        this.patientID = patientID;
        this.time = time;
        this.email = email;
        this.phone = phone;
        this.description = description;
        this.listForms = [];
    }

    addForm(filePath, fileName){
        var scope = this;
        var finaPath = path.join(filePath, fileName);
        pdfreader.readFile(finaPath, function(form){
            form.formID = scope.id+"-"+fileName;
            console.log(form);
            scope.listForms.push(form);
            console.log(scope.listForms);
            fs.writeFile("uploads/appointments.json", JSON.stringify(exports.appointmentList));
        });
    }
};

function generateAppointment(appt) {
    var appointment = new exports.Appointment(appt.id, appt.doctorName, appt.patientName, appt.patientID,
        appt.date, appt.time, appt.email, appt.phone, appt.description);
    if(appt.listForms != null)
        appointment.listForms = appt.listForms;
    return appointment;
}


