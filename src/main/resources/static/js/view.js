    // * 경로(URL)상의 쿼리스트링(매개변수)호출하기.
    //new URL(location.href):현재 페이지의 경로호출
    console.log(new URL(location.href).searchParams.get('bno'));

    console.log('콘솔');
    let bno = new URL(location.href).searchParams.get('bno');
    //1. 게시물 개별 조회
    getonView();
    function getonView(){
        console.log("getonView(왜 아무것도 안들어옴)");

        $.ajax({
        url : "/board/view.do",
        method : "get",
        data : {"bno" : bno},
        success : (r)=>{
        console.log(r+'전달');

        document.querySelector('.btitle').innerHTML = r.btitle
        document.querySelector('.bcontent').innerHTML = r.bcontent;
           document.querySelector('.bdate').innerHTML = r.bdate;

             let btnHTML = `<button type="button" class="btn" onclick="onDelete()">삭제</button>`;
             btnHTML += `<button type="button" class="btn" onclick="location.href='/board/update?bno=${r.bno}'">수정</button>`;
             btnHTML += `<a href="/userboard" class="btn"> 목록보기 </a>`

         document.querySelector('.btnBox').innerHTML = btnHTML




          }
        })
    }

  //2. 게시물 삭제 함수
    function onDelete(){

        $.ajax({
            url : "/board/delete.do",
            method : "delete" ,
            data : {'bno' : bno },
            success : (r)=>{
            if(r){
            alert('삭제 성공');
            location.href="/board";
            }
            else{alert('삭제 실패'); }
            }
        });
    }