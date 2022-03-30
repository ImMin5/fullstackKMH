//파일 읽기 쓰기를 처리하는 모듈 fs : FileSystem

var fs = require('fs');

//파일을 처리하기 위해서
//[1] __filename : 현재 실행된느 파일의 경로와 파일명을 정대주소로 가진다.
//[2] __dirnanme : 현재 실행중인 파일의 경로를 절대주소로 가진다.

console.log(__filename);
console.log(__dirname);
//비동기식으로 파일 읽는 방법 : 읽기쓰기 명려이 바로 실행되지 ㅇ낳고 스레드로 처리된다.
//파일의 경로와 파일명
fs.readFile(__dirname+ "/file_read.txt", "utf-8", function(error,data){
    //errorㅇㅔ 내용이 있으며 true
    if(error){
        console.log("파일 읽기 실패...");
    }else{
        console.log("파일 읽기 성공")
        console.log(data);
    }
});

//동기 식으로 파일 읽기
var data = fs.readFileSync(__dirname+"/node01_start.js", "utf-8");
console.log("파일2: 동기식으로 파일 읽기 =========");
console.log(data);
//fs.readFile("file_read.txt",,);