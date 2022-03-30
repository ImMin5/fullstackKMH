var http = require('http');

var server = http.createServer(function(request,response){
    var postData = '';

    //2.post로 form릐 데이터가 서버에 전송되면 data이벤트가 발생한다.
    request.on('data', function(data){
        postData += data;
    });

    //3.form의 데티어 전송이 완료되면 end이벤트가 발생한다.

    request.on('end', function(){
        console.log(postData);
        var params = new URLSearchParams(postData);

        response.writeHead(200, {'Content-Type' : 'text/html; charset=utf-8'});
        response.write("아이디 = "+params.get("userid")+"<br/>");
        response.write("비밀번호 ="+ params.get("userpwd") + "<br/>");
        response.end("닉네임 ="+ params.get("username") + "<br/>");
    });
});

server.listen(10002,function(){
    console.log("start .... http://127.0.0.1:10002");
});