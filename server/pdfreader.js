const pdfboxCliWrap = require('pdfbox-cli-wrap')
//npm install pdfbox-cli-wrap

//Class format
exports.readFile = function(readable, callback) {
    pdfboxCliWrap.getFormFields(readable)
        .then(fields=>{
            callback(fields);
        })
        .catch(e=>console.error(e));
};
