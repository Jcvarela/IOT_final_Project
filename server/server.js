var pdfFillForm = require('pdfbox-cli-wrap');

const path = require('path');
const readablePdf = path.join(__dirname,'resources/form.pdf')

var PDFreader = require("./pdfreader");
let pdf = new PDFreader();

var jsonPDFObj = pdf.readFile(readablePdf, function(fields){
    //do something with fields
    console.log(fields);
});

console.log(jsonPDFObj);

// pdfFillForm.read(readablePdf)
//     .then(function(result) {
//         pdf.readFile(readablePdf)
//     }, function(err) {
//         console.log(err);
//     });
//
// var fs = require('fs');
//
// pdfFillForm.write('test.pdf', { "myField": "myField fill value" }, { "save": "pdf" } )
//     .then(function(result) {
//         fs.writeFile("test123.pdf", result, function(err) {
//             if(err) {
//                 return console.log(err);
//             }
//             console.log("The file was saved!");
//         });
//     }, function(err) {
//         console.log(err);
//     });
