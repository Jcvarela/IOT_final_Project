/**
 * Created by Ale on 4/12/2017.
 */

var pdfreader = require("./pdfreader");


exports.appointmentList = [];

exports.addAppointment = function(appointment){
    exports.appointmentList.add(appointment);
};

exports.getAppointment = function(patientID){
    for(var apt in exports.appointmentList){
        if(apt.patientID == patientID){
            return apt;
        }
    }
};

exports.Appointment = class {
    constructor(id, doctor, patientName, patientID, time, email, phone, description){
        this.id = id;
        this.doctor = doctor;
        this.patientName = patientName;
        this.patientID = patientID;
        this.time = time;
        this.email = email;
        this.phone = phone;
        this.description = description;
        this.listForms = [];
    }

    addForm(fileName){
        var directory = "resource/"+fileName;
        pdfreader.readFile(directory, function(form){
            form.formID = this.id+"-"+fileName;
            this.listForms.add(form);
        });
    }
};

