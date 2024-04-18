console.log('개별글 수정 실행~')
// 썸머노트 실행
$(document).ready(function() {

    // 썸머노트 옵션
    let option = {
        lang : 'ko-KR' , // 한글 패치
        height : 500 ,  // 에디터 세로 크기
    }

  $('#summernote').summernote( option );


});
let ino = new URL( location.href ).searchParams.get('ino');

console.log(ino);

doGetInquiryInoView();
function doGetInquiryInoView(){
    console.log('doGetInquiryInoView()');
    $.ajax({
        url : '/member/login/check',
        method : 'get',
        success : (r)=>{ console.log(r);
        $.ajax({
                        url : '/member/login/info',
                        method:'get',
                        data: {"memberid":r},
                        async : false , // 응답이 오기전까지 대기 상태
                        success:(r2)=>{
                            console.log( r2 );
    $.ajax({
        url : "/inquiryView.do" ,
        method : "GET" ,
        data : { 'ino' : ino } ,
        success : ( r ) => {
            console.log(r);
            console.log(r.ititle)
            document.querySelector('.ititle').value = r.ititle; console.log( r.ititle )
//            document.querySelector('.memberId').innerHTML = r.memberId;
//            document.querySelector('.idate').innerHTML = r.idate
            document.querySelector('.icontent').innerHTML = r.icontent



                        }
                    })
                }
            })
        }
    })
}


function onInquiryUpdate(){
    // 폼 가져오기
    let inquiryUpdateForm = document.querySelector('.inquiryUpdateForm');

    // 폼 객체화
    let inquiryUpdateFormData = new FormData( inquiryUpdateForm );

        // 폼 객체에 데이터 추가
        inquiryUpdateFormData.set( 'ino' , ino );

    console.log(inquiryUpdateFormData)
    $.ajax({
        url : "inquiryUpdate.do" ,
        method : "PUT" ,
        data : inquiryUpdateFormData ,
        contentType : false ,
        processData : false ,
        success : ( r ) => {
            console.log(r);   console.log(r.ino);
                                       if( r ){

                alert('수정성공'); location.href="/inquiry?ino=`${r.ino}`"
                }else{
                    alert('수정실패')
                }
        }
    })
}