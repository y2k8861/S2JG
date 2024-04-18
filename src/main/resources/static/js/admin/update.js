
// 썸머노트 실행
$(document).ready(function() {

    // 썸머노트 옵션
    let option = {
        lang : 'ko-KR' , // 한글 패치
        height : 500 ,  // 에디터 세로 크기
    }

  $('#summernote').summernote( option );


});

console.log(new URL(location.href).searchParams.get('bno'));


let bno = new URL(location.href).searchParams.get('bno');
console.log('update.js실행');
//1. 게시물 개별 조회
getonView();
function getonView(){
    console.log("getonView()");

    $.ajax({
    url : "/board/view.do",
    method : "get",
    data : {"bno" : bno},
    success : (r)=>{
    console.log(r);

    document.querySelector('.btitle').value = r.btitle
    document.querySelector('.bcontent').value = r.bcontent;

    }
  })
}
// 게시물 수정
function onUpdate(){
console.log('onUpdate()실행');
//    //입력받은 값 가져와
//   let btitle = document.querySelector('.btitle').value; console.log(btitle);
//   let bcontent = document.querySelector('.bcontent').value; console.log(bcontent);
//    //객체화
//    let info = {btitle : btitle, bcontent : bcontent };
//     console.log(info);
    let boardUpdateForm = document.querySelector('.boardUpdateForm');
    //2. 폼객체화
    let boardUpdateFormData = new FormData(boardUpdateForm);
        //- 폼 객체에 데이터 추가.[HTML 입력 폼 외 데이터 삽입 가능]
    boardUpdateFormData.set('bno',bno);
    console.log(bno);
    console.log(boardUpdateForm);
    // 폼 전송
    $.ajax({
        url : "/board/update.do",
        method : 'put',
        data : boardUpdateFormData ,
        contentType : false , processData : false,
        success : (r)=>{
            if(r){alert('수정성공'); location.href="/board/view?bno="+bno;}
            else{alert('수정 실패');}
        }
    })

}