/**
 * Created by Ale on 3/30/2017.
 */


var pdfFillForm = require('pdf-fill-form');

pdfFillForm.read('test.pdf')
    .then(function(result) {
        console.log(result);
    }, function(err) {
        console.log(err);
    });

var fs = require('fs');

pdfFillForm.write('test.pdf', { "myField": "myField fill value" }, { "save": "pdf" } )
    .then(function(result) {
        fs.writeFile("test123.pdf", result, function(err) {
            if(err) {
                return console.log(err);
            }
            console.log("The file was saved!");
        });
    }, function(err) {
        console.log(err);
    });