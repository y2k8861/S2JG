

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
        url : "/userboard/view.do",
        method : "get",
        data : {"bno" : bno},
        success : (r)=>{
        console.log(r+'전달');

        document.querySelector('.btitle').innerHTML = r.btitle
        document.querySelector('.bcontent').innerHTML = r.bcontent;
           document.querySelector('.bdate').innerHTML = r.bdate;


          }
        })
    }

