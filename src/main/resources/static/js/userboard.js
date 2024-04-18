console.log('userboard.js')
//==============페이지 관련 객체================
let pageObject = {
    page : 1 ,           //현재 페이지
    pageBoardSize : 5 ,   // 현재 페이지당 표시할 게시물 수
    bcno : 0 ,            //현재 카테고리
    key : 'b.btitle',       //현재 검색 key
    keyword : ''            //현재 검색 keyword
}

//전체 출력용 : 함수 : 매개변수 x 반환 x 언제실행껀지 페이지 실행될때
doViewList( 1 );
function doViewList( page ){

    pageObject.page = page;
    console.log(pageObject.key)

    $.ajax({
        url : "/userboard/do",
        method : "get",
        data : pageObject ,
        success : (r)=>{ console.log(r);
            //어디에
            let boardTableBody = document.querySelector("#boardTableBody");
            //무엇을
            let html =``;
            let index = (pageObject.page-1)*5;
                //서버가 보내준 데이터를 출력
                r.list.forEach( userboard =>{
                console.log(userboard);
                  index++;
                html +=  ` <tr>
                     <th>${index}</th>
                      <td><a href="/userboard/view?bno=${userboard.bno}">${userboard.btitle}<a/></td>
                        <td>${userboard.bcontent}</td>
                     <td>${userboard.bdate}</td>
                   </tr>`;
                })
            //출력
            boardTableBody.innerHTML = html;

            //==========페이지네이션==================
            //1. 어디에
            let pagination = document.querySelector('.pagination');
            //2. 무엇을
            let pagehtml = ``;
                //이전
                pagehtml += `<li class="page-item"><a class="page-link" onclick="doViewList(${page-1 < 1 ? 1 : page-1 })">이전</a></li>`
                //페이지 번호
                   for(let i = r.startBtn ; i <= r.endBtn; i++ ){
                   //만약에 i가 현재페이지와 같으면 active 클래스 삽입 아니면 생략
                    pagehtml += ` <li class="page-item ${ i == page ? 'active' : '' }"><a class="page-link" onclick="doViewList( ${ i } )"> ${ i } </a></li>`
                   }

                //다음버튼
                pagehtml += `<li class="page-item"><a class="page-link" onclick="doViewList(${page+1 > r. totalPage ? r.totalPage : page +1 })">다음</a></li>`

           pagination.innerHTML = pagehtml;

        //부가 출력
        document.querySelector('.totalPage').innerHTML = r.totalPage;
        document.querySelector('.totalBoardSize').innerHTML = r.totalBoardSize;


        } //success end
    }); //ajax 끝

return ;
} //f end

//2. 페이지당 게시물 수
function onPageBoardSize(object){
    console.log(object);
    console.log(object.value);


    pageObject.pageBoardSize = object.value;
    doViewList(1);

}

//4.검색함수
function onSearch(){

//1. 입력받은 값 가져오기
let key = document.querySelector('.key').value;
let keyword = document.querySelector('.keyword').value;

//2. 서버에 전송할 객체에 담아주고
pageObject.key = key;
pageObject.keyword = keyword;
//3. 출력 함수 호출
doViewList(1);


}