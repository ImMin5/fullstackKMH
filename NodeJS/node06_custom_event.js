var http = require('http');
var events = require('events');
const { addListener } = require('process');

//이벤트 정의되어 있는 
var event_obj = new events.EventEmitter();

//이벤트가 발생시 실행할 함수를 정의하기
//    on(), addListener(), once()

//이벤트 종류, 발생 실행시 실행될 함수 

event_obj.on('call',()=>{
    console.log('call 이벤트 발생함....');
});

var server = http.createServer((req, res) => {
    //서버에 접속을 하면 이벤트를 발생시킴 : emit()
    event_obj.emit('call'); //call 이벤트 발생시킴

    res.writeHead(200,{'Content-Type':'text/html; charset=utf-8'});
    res.end("<h1>이벤트 테스트중 ...</h1>");
});

server.listen(10004, ()=>{
    console.log('server start... http://127.0.0.1:10004');
})