// npm install express
// npm install requets-ip  -> 접속자의 ip 주소를 얻어오기 위한 모듈
// npm install mysql2      -> DB 연동 모듈
// npm install ejs

var http = require('http');
var express = require('express');
var fs = require('fs');
var ejs = require('ejs');
var requsetip = require('request-ip');

//서버 생성
var app = express();
var server = http.createServer(app);

//--POST 방식 접속시 데이터 req르 ㄹ위한 설정
var bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({extended:true}));//한글인코딩

//mysql 연결
var mysqldb = require('mysql2');
const { URLSearchParams } = require('url');
const res = require('express/lib/response');
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
      //  console.log(result);
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

//글쓰기 폼
app.get('/write', (req, res)=>{
    fs.readFile(__dirname +'/write.html','utf-8',function(error, data){
        //에러가 존재하지 않으면
        if(!error){
            res.writeHead(200,{"Content-type":"text/html; charset=utf-8"});
            res.end(data);
        }

    });
});

//글쓰기 DB등록
app.post("/writeOk", (req,res)=>{
    var userid = req.body.userid;
    var subject = req.body.subject;
    var content = req.body.content;
    var ip = requsetip.getClientIp(req).substring(7); //접솓자 아이피 구하기

    //쿼리문 작성
    var sql = "insert into board(userid, subject, content,ip) values(?,?,?,?)";
    var bindData = [userid,subject, content, ip];
    connection.execute(sql, bindData, function(error, result){
        console.log(result);
        if(error || result.affectedRows < 1){ //글쓰기 실패시
            res.redirect('/write');
        }
        else{
            res.redirect('/list');
        }
    })
    //

});

//글 내용 보기
app.get('/view',(req,res)=>{
    //get 방식 접속 데이터 
    let url = req.url;
    let params = url.substring(url.indexOf('?')+1); 
    let noObj = new URLSearchParams(params);
    var bindData = [noObj.get("no")];

    console.log("start");
    //조회수 증가
    var sql = "update board set hit=hit+1 where no=?";
    connection.execute(sql, bindData, (e, r)=>{
        console.log("hit update result : " + r);

    })
    // 글내용 가져오기
    sql = "select no , userid, subject, content, hit, writedate from board where no =?";
    connection.execute(sql, bindData, (e,r)=>{
        if(e){
            console.log(e);
            res.redirect("/list");
        }else{
            console.log(r);
            fs.readFile(__dirname+'/view.ejs','utf-8',function(error,tag){
                res.writeHead(200,{"Content-type":"text/html; charset=utf-8"});
                res.end(
                    ejs.render(tag,{result:r})
                )
            });
        }
    });
});

//글수정
app.get('/edit',(req,res) =>{

    var params = new URLSearchParams(req.url.substring(req.url.indexOf('?')+1));

    var sql = "select no, subject, content from board where no=?";

    connection.execute(sql,[params.get('no')], (error, records)=>{
        if(error){
            console.log(error);
            res.redirect('/view?no='+params.get('no'));
        }else{
            fs.readFile(__dirname+'/edit.ejs', 'utf-8',(error,tag)=>{
                res.writeHead(200,{"Content-type":"text/html; charset=utf-8"});
                res.end(
                    ejs.render(tag,{record:records})
                )
            })
        }
    })
});

//글 수정 DB
app.post('/editOk', (req, res)=>{
    var no = req.body.no;
    var subject = req.body.subject;
    var content = req.body.content;

    var sql = "update board set subject=?, content=? where no=?";
    var bindData = [subject, content, no];
    console.log(no + " " + subject + " " + content);

    connection.execute(sql,bindData, (error,result)=>{
        console.log(result);
        if(error || result < 1){
            //수정 안됨 or 에러
            res.redirect("edit?no="+no);
        }
        else{
            res.redirect("view?no="+no);
        }
        
    });
})

//삭제

app.get('/del',(req,res)=>{
    var params =new URLSearchParams(req.url.substring(req.url.indexOf('?')+1));

    var sql = "delete from board where no=?";
    connection.execute(sql, [params.get('no')],(error, result)=>{
        if(error){
            //삭제 실패
            res.redirect('/view?no='+params.get('no'));
        }else{
            res.redirect('/list');
        }
    });
});
server.listen(10010,function(){
    console.log("server start.... http://127.0.0.1:10010/index");
});