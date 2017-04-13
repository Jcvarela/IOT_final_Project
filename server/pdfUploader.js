/**
 * Created by Ale on 4/12/2017.
 */

var storage = require("./storage");
var path = require('path');
var formidable = require('formidable');
var fs = require('fs');


module.exports = function(app){

    app.post('/upload', function(req, res){
        // create an incoming form object
        var form = new formidable.IncomingForm();

        // specify that we want to allow the user to upload multiple files in a single request
        // form.multiples = true;

        // store all uploads in the /uploads directory
        var id = req.originalUrl.split("=")[1];
        var formDir = path.join(__dirname, '/uploads', id);

        fs.mkdir(formDir, function (err) {
            // every time a file has been uploaded successfully,
            // rename it to it's original name
            form.on('file', function(field, file) {
                fs.rename(file.path, path.join(form.uploadDir, file.name), function () {
                    storage.addAppointmentForm(id, formDir, file.name);
                });
            });
        });

        form.uploadDir = formDir;

        // log any errors that occur
        form.on('error', function(err) {
            console.log('An error has occurred: \n' + err);
        });

        // once all the files have been uploaded, send a response to the client
        form.on('end', function() {
            res.end('success');
        });

        // parse the incoming request containing the form data
        form.parse(req);
    });
};
