// ======== 페이지 정보 관련 객체 = 여러개 변수 묶음 ======== //
let pageObject = {
    page : 1,
    pageBoardSize : 10,
    bcno : 0,
    key : 'busNumber',
    keyword : ''
}

document.querySelector("#allCheckInput").addEventListener("change", function (e) {
    e.preventDefault();
    console.log(e)
    var list = document.querySelectorAll("input[name=deleteCheck]");
    for (var i = 0; i < list.length; i++) {
        list[i].checked = this.checked;
    }
});

doViewBus( 1 );
function doViewBus( page ){
    pageObject.page = page;

    $.ajax({
        url : "/admin/bus/do",
        method : "get",
        data : pageObject,
        success : (r)=>{
            let busTableBody = document.querySelector("#busTableBody");

            let html = ``;
            let busClassNoKR = '';
            let no = 10*(page-1);

            r.list.forEach( bus => {
                console.log( bus );

                if(bus.busClassNo == 1){ busClassNoKR = "일반"; }
                else if(bus.busClassNo == 2){ busClassNoKR = "우 등"; }
                else if(bus.busClassNo == 3){ busClassNoKR = "프리미엄"; }
                no++;
                html += `<tr>
                             <th> <input type="checkbox" class="deleteCheck${bus.busNo}" name="deleteCheck" value="${bus.busNo}" /></th>
                             <th> ${ no }</th>
                             <td> <a href="/admin/bus/update?busNo=${bus.busNo}"> ${ bus.busNumber }</a></td>
                             <td> ${ busClassNoKR } </td>
                             <td> ${ bus.busPosition } </td>
                         </tr>`

            });
            // 3. 출력
            busTableBody.innerHTML = html;

            let  pagination = document.querySelector('.pagination');
                        // 2. 무엇을
                        let pagehtml = ``;
                            // 이전 버튼 ( 만약에 현재페이지가 1페이지이면 1페이지 고정 )
                            pagehtml += `<li class="page-item"><a class="page-link" onclick="doViewBus( ${ page-1 < 1 ? 1 : page-1 } )">이전</a></li>`
                            // 페이지번호 버튼 ( 1페이지부터 마지막페이지(totalPage)까지
                            for( let i = r.startBtn ; i <= r.endBtn ; i++ ){
                                // + 만약에 i가 현재페이지와 같으면 active 클래스 삽입 아니면 생략 ( *조건부 렌더링 )
                                pagehtml += ` <li class="page-item ${ i == page ? 'active' : '' }"><a class="page-link" onclick="doViewBus( ${ i } )"> ${ i } </a></li>`
                            }
                            // 다음 버튼 ( 만약에 현재페이지가 마지막 페이지 이면 현재 페이지 고정 )
                            pagehtml += `<li class="page-item"><a class="page-link" onclick="doViewBus( ${ page+1 > r.totalPage ? r.totalPage : page+1 } )">다음</a></li>`
                        // 3. 출력
                        pagination.innerHTML = pagehtml;
        }
    });
}

function onPageBoardSize( object ){     console.log( object );console.log( object.value );
    pageObject.pageBoardSize = object.value;
    doViewBus( 1 );
}

// 등급별 검색 기능
function onBcno(bcno){
    console.log("등급변경");
    pageObject.bcno = bcno;
    pageObject.key = 'busNumber';
    pageObject.keyword = '';
    document.querySelector('.key').value = 'busNumber'
    document.querySelector('.keyword').value = '';

    doViewBus( 1 );
}

// 검색 기능
function onSearch(){
    let key =  document.querySelector('.key').value;
    let keyword = document.querySelector('.keyword').value;
    pageObject.key = key;
    pageObject.keyword = keyword;
    doViewBus( 1 );
}

// 삭제 기능
function onDelete(){
    console.log("onDelete()");
    let list = document.querySelectorAll("input[name=deleteCheck]");
    let listCheck = [];


    for(let i =0; i<list.length; i++){
        if(list[i].checked){
            console.log(list[i].value);
            listCheck.push(list[i].value);
        }
    }
    if(listCheck[0] == null){
        alert("삭제할 버스를 체크해주세요");
        return;
    }

    $.ajax({
        url : '/admin/bus/delete.do',
        method : 'delete',
        data : {"listCheck":listCheck},
        dataType: "json",
        success : (r)=>{
            console.log(r);
            if(r){
                alert("삭제 성공");
                doViewBus(pageObject.page);
            } else {
                alert('삭제 실패');
            }
        }
    });
}

// 엑셀파일 업로드
function onUpload(){
    console.log("파일 업로드 함수 실행")
    let excelUploadForm = document.querySelector('.excelUploadForm');
    let excelUploadFormData = new FormData( excelUploadForm );

    console.log("excelUploadForm : " +excelUploadForm)

    $.ajax({
        url : "/admin/bus/upload",
        method : "post" ,
        data : excelUploadFormData ,
        contentType : false,
        processData : false,
        success : (r)=>{
            if(r){
                alert("파일 업로드 성공");
                location.reload();
            }
            else{
                alert("파일을 선택해주세요")
            }
        }
    });
}

function onDownload(){
    console.log("파일 다운로드 함수 실행");

    $.ajax({
            url : "/admin/bus/download",
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