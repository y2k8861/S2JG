console.log('문의함 실행~');
let pageObject = {
    page : 1 ,              // 현제 페이지
    pageBoardSize : 5 ,     // 현재 페이지당 표시할 게시물 수
    key : ' ' ,      // 현재 검색 key
    value : ' ' ,        // 현재 검색 keyword
}
doGetInquiryView( 1 );
function doGetInquiryView( page ){
    console.log('doGetInquiryView()')

     pageObject.page = page;

    $.ajax({
        url : "/inquiry.do" ,
        method : "GET" ,
        data : pageObject ,
        success : ( r ) => {
            console.log(r);
            console.log(r.list[0].ititle)

            let inquiryTableBody = document.querySelector("#inquiryTableBody");

            let html = '';
            let index = (pageObject.page-1)*5;
            r.list.forEach( inquiry => {
                console.log(inquiry)
                index++;
            html += `<tr>
                        <td>${index}</td>
                        <td><a href="/inquiryView?ino=${inquiry.ino}">${inquiry.ititle}</a></td>
                        <td>${inquiry.idate}</td>
                        <td>${inquiry.istatus}</td>
            </tr>`

            })
            inquiryTableBody.innerHTML = html;

            // 1. 어디에
            let pagination = document.querySelector('.pagination');
            // 2. 무엇을
                let pagehtml = '';
                    // 이전 버튼 ( 만약에 현제페이지가 1페이지이면 1페이지 고정)
                    pagehtml += `<li class="page-item"><a class="page-link" onclick="doGetInquiryView(${ page-1 < 1 ? 1 : page-1 })">이전</a></li>`
                    // 페이지번호 버튼 ( 1페이지부터 마지막페이지(totalPage) 까지
                    for( let i = r.startBtn ; i <= r.endBtn ; i++ ){
                        // + 만약에 i가 현재페이지와 같으면 active 클래스 삽입 아니면 생략 ( *조건부 렌더링 )
                    pagehtml +=`<li class="page-item ${ i == page ? 'active' : '' }"><a class="page-link" onclick="doGetInquiryView(${ i })">${ i }</a></li>`
                    }
                    // 다음 버튼 ( 만약에 현재페이지가 마지막 페이지 이면 현재 페이지 고정 )
                    pagehtml +=`<li class="page-item"><a class="page-link" onclick="doGetInquiryView(${ page+1 > r.totalPage ? r.totalPage : page+1})">다음</a></li>`

                // 3. 출력
                pagination.innerHTML = pagehtml;

        }
    })
    return;
}

// 2. 페이지당 게시물 수
function onPageBoardSize( object ){
    console.log("onPageBoardSize()");
    console.log( object );
    console.log( object.value );

    pageObject.pageBoardSize = object.value;
    doViewList( 1 );
}

// 3. 검색 함수
function onSearch(){
    console.log('onSearch()');
    // 1. 입력받은 값 가져오기
    let key = document.querySelector('.key').value;
    let keyword = document.querySelector('.keyword').value;
    // 2. 서버에 전송할 객체에 담아주고
    pageObject.key = key;
    pageObject.value = keyword;
    // 3. 출력 함수 호출
    doGetInquiryView(1);
}