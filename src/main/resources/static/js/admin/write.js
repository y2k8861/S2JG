// 썸머노트 실행
$(document).ready(function() {

    // 썸머노트 옵션
    let option = {
        lang : 'ko-KR' , // 한글 패치
        height : 500 ,  // 에디터 세로 크기
    }

  $('#summernote').summernote( option );


});




//1. 글쓰기
function onWrite(){
console.log("onWrite()");
    // 1. 폼 dom 가져온다
let boardWriteForm = document.querySelector('.boardWriteForm');
console.log(boardWriteForm);
    // 2. 폼 바이트(바이너리) 객체 변환
let boardWriteFormdata = new FormData(boardWriteForm);
    // 3. ajax 첨부파일 폼 전송
    console.log(boardWriteFormdata);
$.ajax({

    url : "/board/write.do",
    method : "post" ,
    data : boardWriteFormdata ,
    contentType : false,
    processData : false,
    success : (r)=>{
     console.log(r);
     if(r == 0){
        alert('글쓰기 성공')
     location.href="/board";

     }else if(r == -1){
     alert('글쓰기 실패 : 관리자에게 문의');
     }else if( r == -2){
      alert('로그인 세션이 존재하지 않습니다 : 관리자에게 문의');
      location.href="/member/login"
     }else if( r >= 1){
     alert('글쓰기 성공')
     location.href='/board';
     }
}
});

}