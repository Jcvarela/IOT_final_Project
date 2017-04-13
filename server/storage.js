/**
 * Created by Ale on 4/12/2017.
 */

var pdfreader = require("./pdfreader");
var fs = require("fs");

exports.appointmentList = [];

fs.readFile("uploads/appointments.json", function (err, data) {
    if(data == "") return;
    exports.appointmentList = JSON.parse(data);
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
        console.log(apt.id+" VS "+id);
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
        this.listForms = []; //TODO:not sure if is going to work!!!!
    }

    addForm(fileName){
        var directory = "resource/"+fileName;

        pdfreader.readFile(directory, function(form){
            form.formID = this.id+"-"+fileName;
            this.listForms.push(form);
        });
    }
};

function generateAppointment(appt) {
    var appointment = new exports.Appointment(appt.id, appt.doctorName, appt.patientName, appt.patientID,
        appt.date, appt.time, appt.email, appt.phone, appt.description);
    return appointment;
}
