var fs = require("fs");
var http = require("http");

var server = http.createServer(function(req,res){
    fs.readFile(__dirname+'/01.jpg',function(e, imgSrc){
        if(!e){
            res.writeHead(200,{"Content-Type":"image/jpeg"});
            res.write(imgSrc);
            res.end();
        }
    });
});


server.listen(10006,function(){
    console.log("server start.... http://127.0.0.1:10006");
})