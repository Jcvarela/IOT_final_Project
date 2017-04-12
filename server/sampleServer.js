/**
 * Created by Ale on 4/10/2017.
 */

var express = require("express");
var app = express();

app.listen(80);

app.get("/", function (req, res) {
    res.send("Hello from Ale's server");
});