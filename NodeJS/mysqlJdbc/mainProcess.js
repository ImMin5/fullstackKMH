// npm install express
// npm install requets-ip  -> 접속자의 ip 주소를 얻어오기 위한 모듈
// npm install mysql2      -> DB 연동 모듈
// npm install ejs

var http = require('http');
var express = require('express');
var fs = require('fs');
var ejs = require('ejs');
//서버 생성
var app = express();
var server = http.createServer(app);


//mysql 연결
var mysqldb = require('mysql2');
//mysqldb.autoCommit();// 자동커밋

//db 연결정보
var connection = mysqldb.createConnection({
    host : '127.0.0.1',
    port : 3306,
    user : 'root',
    password : 'root',
    database : 'campusdb',
})


// get(), post()
//홈페이지로 이동하기 : http://127.0.0.1"10010/index
app.get('/index',function(req,res){
    fs.readFile(__dirname+'/index.html','utf-8',function(error,indexData){
        res.writeHead(200, {"Contet-Type":"text/html; charset=utf-8"});
        res.end(indexData);
    });
});


//게시판 리스트
app.get('/list',function(req,res){
    var sql = "select no, subject, userid, hit, date_format(writedate, '%m-%d %H:%m') writedate";
    sql += " from board order by no desc";

    //쿼리문 실행
    connection.execute(sql,function(error, result){
        // 선택한 레코드 수 
        var totalRecord = result.length;
        console.log(result);
        console.log(__dirname);
        if(result.length > 0){
            fs.readFile(__dirname+'/list.ejs', 'utf-8', function(e,d){
                res.writeHead(200,{"Content-Type":"text/html; charset=utf-8"});
                res.end(
                    ejs.render(d,{
                        results:result,
                        parsing:{
                            totalRecord : totalRecord,
                            nowPage : 1,
                            startPage : 1,
                            onePageRecord : 5
                        }
                    })
                );
            });
        }
    });
});

server.listen(10010,function(){
    console.log("server start.... http://127.0.0.1:10010/index");
});