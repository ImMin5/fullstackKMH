var http = require("http");
const { URLSearchParams } = require("url");

var server = http.createServer(function(request, response){
    console.log(request.url);

    //requset 에서 서버로 전송받은 파라미터 값을 얻기 위해서는 URLSearchParams() 객체를 생성해야 한다. 
    var params = new URLSearchParams(request.url.substring(2));
    console.log(params);

    response.writeHead(200,{'Content-Type' :'text/html; charset=utf-8'});
    response.write(params.get("num"));
    response.write(params.get("name"));
    response.write(params.get("tel")+"<br/>");

    response.write("<form method='post' action='http://127.0.0.1:10002'>");
    response.write("<input type='text' name='userid'/><br/>");
    response.write("<input type='password' name='userpwd'/><br/>");
    response.write("<input type='submit' value='post'/>");
    response.write("<input type='text' name='username'/><br/>");
    response.write("<form/>")

});


//접속 대기
//10001 포트로 접속하면 function을 실행한다.
server.listen(10003,function(){
    console.log("server start ... http://127.0.0.1:10003/?num=1234&name=홍길동&tel=010-1111-1111");
})