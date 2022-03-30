var fs = require("fs");
var http = require("http");
var mime = require("mime");

// 모듈 추가하는 방법 : mime
// npm 명령어를 이용하여 추가
// >npm install mime@2

var server = http.createServer(function(request, response){
    var addr = request.url;
    console.log(addr);

    if(addr == '/home'){
        //home.html파일의 내용을 읽어 response에 쓰기를 한다.
        fs.readFile(__dirname+'/home.html','utf-8', function(e,d){
            if(e){
                console.log("파일 읽기 실패...home.html");
                console.log(e);
            }
            else{
                response.writeHead(200,{"Content-Type":"text/html; charset=utf-8"});
                response.end("content :" + d);
            }
        });
    }
    else if(addr.indexOf('/img') == 0){
       // 이미지일 경우

       //마임구하기
       var mimeType = mime.getType(addr.substring(1));
       console.log(mimeType);

       fs.readFile(__dirname+addr,function(error,imgData){
            if(!error){
                response.writeHead(200,{"Content-Type":mimeType});
                response.end(imgData);
            }
       });

    }
})

server.listen(10007,function(){
    console.log("server start ... http://127.0.0.1:10007/home");
})