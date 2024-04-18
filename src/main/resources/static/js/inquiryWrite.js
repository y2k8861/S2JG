console.log('문의하기JS 시작~');



// 썸머노트 실행
$(document).ready(function() {

    // 썸머노트 옵션
    let option = {
        lang : 'ko-KR' , // 한글 패치
        height : 500 ,  // 에디터 세로 크기
    }

  $('#summernote').summernote( option );


});

function onInquiryWrite(){
    console.log('onInquiryWrite()');
    let inquiryWriteForm = document.querySelector('.inquiryWriteForm')
    let inquiryWriteFormData = new FormData( inquiryWriteForm )
    console.log( inquiryWriteFormData );

    $.ajax({
        url : "/inquiryWrite.do" ,
        method : "POST" ,
        data :  inquiryWriteFormData ,
        contentType : false ,
        processData : false ,   // 문자형식이 아닌 바이트형식으로 보내는 방법
        success : ( result ) => {
            console.log(result);
            if(result){
                alert('문의성공'); location.href="/inquiry"
            }else{
                alert('로그인후 이용해주세요'); location.href="/member/login"
            }
        }
    })

}