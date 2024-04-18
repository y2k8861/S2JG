// ========= 페이지 정보 관련 객체 = 여러개 변수 묶음 ============= //
let pageObject = {
    page : 1 ,              // 현제 페이지
    pageBoardSize : 5 ,     // 현재 페이지당 표시할 게시물 수
    key : 'memberName' ,    // 현재 검색 key
    value : '' ,            // 현재 검색 value
    memberGrant : 0
}


// 1. 전체 출력용 : 함수 : 매개변수 = page , 반환 x , 언제 실행할껀지 : 페이지 열릴때(JS)
$.ajax({
    url : '/admin/login/check',
    method : 'get',
    success : (r)=>{  console.log(r);

        if( r != '' ){ // 로그인 했을때
            $.ajax({
                url : '/admin/login/info',
                method:'get',
                data: {"memberid":r},
                async : false , // 응답이 오기전까지 대기 상태
                success:(r2)=>{
                    console.log( r2 );
                    if(r2.memberGrant <= 10){ // 관리자가 아닐 때
                        alert("관리자가 아닙니다.");
                        location.href = "/";

                    }
                    else{
                       document.querySelector('#loadingBox').style = 'display:none';
                    }
                    pageObject.memberGrant = r2.memberGrant;
                    doViewList(1);

                }
             });
        }else{ // 로그인 안했을떄
            alert("로그인이 필요합니다.");
            location.href = "/member/login";

        }
    } // s end
}); // ajax end
 // 첫페이지 실행
function doViewList( page ){
    console.log("doViewList()");
    console.log(pageObject.memberGrant);

    pageObject.page = page; // 매개변수로 들어온 페이지를 현재페이지에 대입

    $.ajax({
       url : '/admin/reservation/do',
       method : 'GET',
       data : pageObject ,
       async : false ,
       success : ( result ) => {
            console.log(result);
            // 테이블 레코드 구성
            // 1. 어디에
            let boardTableBody = document.querySelector("#boardTableBody");
            // 2. 무엇을
            let html = '';
            console.log(result.list)
                // 서버가 보내준 데이터를 출력
                result.list.forEach( board => {
                    console.log( board );
                html += `            <tr>
                                         <th>${board.reservationNo}</th>
                                         <td>${board.memberName}</td>
                                         <td>${board.routeStartTerminal}</td>
                                         <td>${board.routeTimeStartTime}
                                         <td>${board.routeEndTerminal}</td>
                                         <td>${board.routeTimeEndTime}</td>
                                         <td>${board.reservationSeatNum}</td>
                                         <td>${board.reservationPrice}</td>
                                         <td>${board.reservationDate.split(":")[0]+":"+board.reservationDate.split(":")[1]}</td>
                                     </tr>`
                })
            // 3. 출력
            boardTableBody.innerHTML = html;

            // == 페이지네이션 구성 ========================
            // 1. 어디에
            let pagination = document.querySelector('.pagination');
            // 2. 무엇을
            let pagehtml = '';
                // 이전 버튼 ( 만약에 현제페이지가 1페이지이면 1페이지 고정)
                pagehtml += `<li class="page-item"><a class="page-link" onclick="doViewList(${ page-1 < 1 ? 1 : page-1 })">이전</a></li>`

                // 페이지번호 버튼 ( 1페이지부터 마지막페이지(totalPage) 까지
                for( let i = result.startBtn ; i <= result.endBtn ; i++ ){
                    // + 만약에 i가 현재페이지와 같으면 active 클래스 삽입 아니면 생략 ( *조건부 렌더링 )
                pagehtml +=`<li class="page-item ${ i == page ? 'active' : '' }"><a class="page-link" onclick="doViewList(${ i })">${ i }</a></li>`
                }
                // 다음 버튼 ( 만약에 현재페이지가 마지막 페이지 이면 현재 페이지 고정 )
                pagehtml +=`<li class="page-item"><a class="page-link" onclick="doViewList(${ page+1 > result.totalPage ? result.totalPage : page+1})">다음</a></li>`

            // 3. 출력
            pagination.innerHTML = pagehtml;
       } // success end
    }); // ajax end
    return;
} // f end

// 2. 페이지당 게시물 수
function onPageBoardSize( object ){
    console.log("onPageBoardSize()");
    console.log( object );
    console.log( object.value );

    pageObject.pageBoardSize = object.value;
    doViewList( 1 );
}

//// 3. 카테고리 변경 함수
//function onBcno( bcno ){
//    // bcno : 카테고리 식별번호 [ 0 : 전체 , 1 : 자유 , 2 : 노하우 ]
//    pageObject.bcno = bcno;
//    doViewList(1);
//    // 검색 제거 ( 검색이 없다는 기준 데이터 )
//    pageObject.key = 'b.btitle';
//    pageObject.value = '';
//    document.querySelector('.key').value = 'b.btitle';
//    document.querySelector('.value').value = '';


    // 카테고리 활성화 css 적용 ( 해당 버튼에 categoryActive 클래스 대입 )
    // 1. 모든 카테고리 버튼 호출
//    let categoryBtns = document.querySelectorAll(".boardCategoryBox > button");
//    console.log( categoryBtns );
//    // 2. 선택된 카테고리번호(매개변수bcno) 에 class 대입
//        // dom객체.classList.add("새로운 클래스명")
//        // dom객체.classList.remove("제거할 클래스명")
//
//        // 1. 활성화 초기화
//    for( let i = 0 ; i < categoryBtns.length ; i++ ){
//        categoryBtns[i].classList.remove("categoryActive");
//    }
//        // 2. 활성화 대입
//    categoryBtns[bcno].classList.add("categoryActive")
//    // 재출력
//    doViewList(1);


// 4. 검색 함수
function onSearch(){
    console.log('onSearch()');
    // 1. 입력받은 값 가져오기
    let key = document.querySelector('.key').value;
    let value = document.querySelector('.value').value;
    // 2. 서버에 전송할 객체에 담아주고
    pageObject.key = key;
    pageObject.value = value;
    // 3. 출력 함수 호출
    doViewList( 1 );
}

// 엑살 다운로드
function onDownload(){
    console.log("파일 다운로드 함수 실행");

    $.ajax({
        url : "/admin/reservation/fileDownload",
        method : "get",
        success : (r)=>{
            if(r){
                alert("파일 다운로드 성공");
            }
            else{
                alert("파일 다운로드 실패")
            }
        }
    });
}
