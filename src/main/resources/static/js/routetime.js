
//==============페이지 관련 객체================
//let pageObject = {
//    page : 1 ,           //현재 페이지
//    pageBoardSize : 10 ,   // 현재 페이지당 표시할 게시물 수
//    key : 'routeTimeNo',       //현재 검색 key
//    keyword : ''            //현재 검색 keyword
//}


//전체 출력용 : 함수 : 매개변수 x 반환 List(routeTimeNo,routeTimeStartTime,routeTimeEndTime) 언제실행껀지 페이지 실행될때
//routeTime(1);
function routeTime( page ){

    pageObject.page = page;

    $.ajax({
        url : "/route/time.do",
        method : "get",
         data: pageObject ,
        success : (r)=>{ console.log(r);
            //어디에
            let boardTableBody = document.querySelector("#boardTableBody");
            //무엇을
            let html =``;
            let busClassNoKR = '';
            let no = 10*(page-1);

                //서버가 보내준 데이터를 출력
                r.list.forEach( routeTime =>{
                no++;
                console.log(routeTime);
                html +=  ` <tr>
                     <th>${no}</th>
                      <td><a href="/route/time/reserve?routeTimeNo=${routeTime.routeTimeNo}">${routeTime.routeTimeStartTime}</a></td>
                      <td>${routeTime.routeTimeEndTime}</td>
                      <td>${routeTime.busClassName}</td>
                      <td>${routeTime.busClassPrice + routeTime.routePrice}</td>
                   </tr>`;

                })


            //출력
            boardTableBody.innerHTML = html;

            let pagination = document.querySelector('.pagination');

            let pagehtml = ``;
                //이전
                pagehtml += `<li class="page-item"><a class="page-link" onclick="routeTime(${page-1 < 1 ? 1 : page-1 })">이전</a></li>`
                //페이지 번호
                   for(let i = r.startBtn ; i <= r.endBtn; i++ ){
                   //만약에 i가 현재페이지와 같으면 active 클래스 삽입 아니면 생략
                    pagehtml += ` <li class="page-item ${ i == page ? 'active' : '' }"><a class="page-link" onclick="routeTime( ${ i } )"> ${ i } </a></li>`
                   }

                //다음버튼
                pagehtml += `<li class="page-item"><a class="page-link" onclick="routeTime(${page+1 > r. totalPage ? r.totalPage : page +1 })">다음</a></li>`

           pagination.innerHTML = pagehtml;



        } //success end
    }); //ajax 끝

return ;
} //f end


//2. 페이지당 게시물 수
function onPageBoardSize(object){
    console.log(object);
    console.log(object.value);

    pageObject.pageBoardSize = object.value;
    routeTime(1);

}


//3.검색함수
function onSearch() {
  // 검색 기능 실행
  console.log('onSearch()');

  // 1. 입력받은 값 가져오기
  let searchSelect = document.querySelector(".searchSelect").value;
  let keyword = document.querySelector(".keyword").value;

  console.log(searchSelect);
  console.log(keyword);

  // 2. 서버에 전송할 객체에 담아주고
  pageObject.key = searchSelect;
  pageObject.keyword = keyword;
   console.log(  pageObject.key);
   console.log( pageObject.keyword);
  // 3. 출력 함수 호출
   routeTime(1);
}

//날짜 검색 함수
function ondate(){
    console.log('ondate()');

    let routedate = document.querySelector(".key_word").value;
    console.log(routedate);

    pageObject.key_word = routedate;
     console.log(pageObject.key);
     routeTime(1);
}





