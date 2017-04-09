/**
 * Created by Ale on 4/8/2017.
 */

//Class format
module.exports = class PDFreader{

    constructor(arg1, arg2){
        this.field1 = arg1;
        this.field2 = arg2;
    }

    readFile(path){
        return jsonPDF;
    }
};

//Use it
var PDFreader = require("./pdfreader");
let pdf = new PDFreader();





//Singleton Module
var pdfreader = require("moduleName");
// var ....

//Public
exports.funct = function(){

};

//Private
function funct2(){

}

//Use it
var myModule = require("./pdfreader");
myModule.funct();