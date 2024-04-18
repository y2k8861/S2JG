let pageObject = {
    page : 1,           // 현재 페이지
    pageBoardSize : 10,   // 한재 페이지당 게시물 수
    // bcno : 0,           // 현재 카테고리
    key : 'routeStartTerminal',    // 현재 검색 key
    keyword : ''        // 현재 검색 keyword
}
console.log(pageObject);

document.querySelector("#allCheckInput").addEventListener("change", function (e) {
    e.preventDefault();
    console.log(e)
    var list = document.querySelectorAll("input[name=deleteCheck]");
    for (var i = 0; i < list.length; i++) {
        list[i].checked = this.checked;
    }
});


doGetAllRoute(1);
function doGetAllRoute(page){
    pageObject.page = page;
    $.ajax({
        url : '/admin/route/all',
        method : 'get',
        data : pageObject,
        success : (r)=>{
            console.log(r);
            let tableTbody = document.querySelector("#routeAllTb tbody");
            let html = ``;
            let i = (pageObject.page-1)*pageObject.pageBoardSize;
            r.list.forEach(board =>{
                i++;
                html +=`
                    <tr>
                        <th scope="row"><input type="checkbox" class="deleteCheck${board.routeNo}" name="deleteCheck" value="${board.routeNo}" /></th>
                        <th onclick="routeUpdatePage(${board.routeNo})" scope="row">${i}</th>
                        <td onclick="routeUpdatePage(${board.routeNo})">${board.routeStartTerminal}</td>
                        <td onclick="routeUpdatePage(${board.routeNo})">${board.routeEndTerminal}</td>
                        <td onclick="routeUpdatePage(${board.routeNo})">${board.routePrice}</td>
                        <td onclick="routeUpdatePage(${board.routeNo})">${board.routeDate.split(":")[0]+":"+board.routeDate.split(":")[1]}</td>
                    </tr>
                `;
            });

            tableTbody.innerHTML = html;

            let pagenation = document.querySelector(".pagination");

            html = ``;
            html += `
                <li class="page-item ${r.page == 1 ? 'disabled': ''}">
                    <a class="page-link" href="javascript:void(0)" onclick="doGetAllRoute(${r.page - 1})" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
            `;

            for(let i = r.startBtn; i<=r.endBtn; i++){

                html += `<li class="page-item ${r.page == i ? 'active':''}"><a class="page-link" href="javascript:void(0)" onclick="javascript:doGetAllRoute(${i})">${i}</a></li>`;
            }

            html += `
                <li class="page-item page-item ${r.page == r.totalBoardSize ? 'disabled': ''}">
                    <a class="page-link" href="javascript:void(0)" onclick="doGetAllRoute(${r.page + 1})" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            `;
            pagenation.innerHTML = html;
        }
    });
}

function routeSearch(){

    console.log("routeSearch()");
    let searchSelect = document.querySelector(".routeSearchWrap .searchSelect").value;
    let searchword = document.querySelector(".routeSearchWrap #searchword").value;

    console.log(searchSelect);
    console.log(searchword);

    pageObject.key = searchSelect;
    pageObject.keyword = searchword;
    doGetAllRoute(pageObject.page);
}

function routeUpdatePage(routeNo) {
    location.href= "/admin/route/view?no="+routeNo;
}

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
    console.log(listCheck);

    $.ajax({
        url : '/admin/route/delete.do',
        method : 'delete',
        data : {"listCheck":listCheck},
        dataType: "json",
        success : (r)=>{
            console.log(r);
            if(r){
                alert("삭제 성공");
                doGetAllRoute(pageObject.page);
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
        url : "/admin/route/fileUpload",
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

// 엑살 다운로드
function onDownload(){
    console.log("파일 다운로드 함수 실행");

    $.ajax({
            url : "/admin/route/fileDownload",
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