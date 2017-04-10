const pdfboxCliWrap = require('pdfbox-cli-wrap');

module.exports = class PDFwriter {

    writeFile(path, data, outPdfPath){
        pdfboxCliWrap.embedFormFields(path, data, outPdfPath)
            .then(()=>{
                console.log("success")
            })
            .catch(e=>console.error(e))
    }
};