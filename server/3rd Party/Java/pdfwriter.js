/**
 * Created by jcvar on 4/8/2017.
 */


var exec = require('child_process').exec, child;

var json = {
    "Full name": "Jorge Varela",
    "DOB": "08-17-1993",
    "Expeditioncrew No" : "123456789",
    "Name_3" : "Carlos",
    "Age" : "54"
}
                                                                                    //require this part
var parameters = "3 resources\\form.pdf resources\\form2.pdf " + JSON.stringify(json).split('"').join('\\"');

var jarPath = "PdfEditor\\out\\artifacts\\PdfEditor_jar\\PdfEditor.jar";

console.log(parameters);
                                      //args parameters
child = exec('java -jar '+ jarPath +' ' + parameters,
    function (error, stdout, stderr){
        console.log('stdout: ' + stdout); //output
        console.log('stderr: ' + stderr);
        if(error !== null){
            console.log('exec error: ' + error);
        }
});

//var jarPath = "PdfEditor\\out\\artifacts\\PdfEditor_jar\\PdfEditor.jar";

exports.updatePDF = function(formID, form){
    var readPDF = "..\\..\\resources\\" + formID.split("-")[4];
    var writePDF = "..\\..\\resources\\out" + formID.split("-")[4];
    var params = "3 "+readPDF+" "+writePDF+" "+JSON.stringify(form).split('"').join('\\"');

    child = exec('java -jar '+ jarPath +' ' + params,
        function (error, stdout, stderr){
            console.log('stdout: ' + stdout); //output
            console.log('stderr: ' + stderr);
            if(error !== null){
                console.log('exec error: ' + error);
            }
        });
};