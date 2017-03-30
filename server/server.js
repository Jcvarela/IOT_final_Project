/**
 * Created by Ale on 3/30/2017.
 */


let pdftohtml = require("pdftohtmljs");
var converter = new pdftohtml('resources/form.pdf', "form.html");


converter.convert('ipad').then(function() {
    console.log("Success");
}).catch(function(err) {
    console.error("Conversion error: " + err);
});
