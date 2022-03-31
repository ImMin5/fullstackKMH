var http = require('http');
var fs = require('fs');
var mime = require('Mime');
const { start } = require('repl');

var server = http.createServer(function(request, response){
    var mapping = request.url;

    //html 문서보내기
    if(mapping=='/'){
        fs.readFile(__dirname+'/movie_play.html','utf-8',function(error,htmlData){
            if(!error){
                response.writeHead(200,{"Content-Type":"text/html;charset-utf-8"});
                response.end(htmlData);
            }
        });
    }
    //image 보내기
    else if(mapping.indexOf('/img') == 0){
        mimeName = mime.getType(mapping.substring(1));
        fs.readFile(__dirname + mapping, function(e, imgData){
            if(!e){
                response.writeHead(200, {"Content-Type":mimeName});
                response.end(imgData);
            }
        })
    }
    //video 보내기
    else if(mapping.indexOf("/movie")== 0){
        //동영상은 파일이 크기 때문에 스트리밍 처리로 전송한다.

        //1. 스트리밍처리를 위한 객체를 생성한다.
        var stream = fs.createReadStream(mapping.substring(1));//

        //2. 스트리밍 처리를 하기 위해서 여러번 데이터를 전송해야하며 이벤트를 이용하여 처리한다.

        // data 이벤트 : 데이터가 read된 경우 호출되는 이벤트
        var cnt  = 1;
        stream.on('data', function(movieData){
            response.write(movieData);
            console.log(cnt++ + '번째 전송됨. ' + movieData.length);
        });

        //end 이벤트 : 데이터가 read의 마지막일때 호출되는 이벤트
        stream.on('end', function(){
            response.end();
            console.log("stream end......");
        });

        //데이터가 read된 경우 호출되는 이벤트
        stream.on("error", function(){
            response.end();
            console.log("error stream....");
        });
        //데이터가 read

    }


})

server.listen(10008,function(){
    console.log('server start.... http://127.0.0.1:10008');

})