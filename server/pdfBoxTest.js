/**
 * Created by jcvar on 4/7/2017.
 */

var Document = require('node-pdfbox');

var document = Document.loadSync('/resources/form.pdf');

console.log(document.pagesCountSync());