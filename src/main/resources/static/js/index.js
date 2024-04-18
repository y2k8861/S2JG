let indexPageObject = {
    key : 'result.key' ,                    // 현재 검색 key
    value : 'result.value',         // 현재 검색 value
    routeStartTerminal : " ",
    routeEndTerminal : " ",
}

let pageObject = {
    page : 1 ,           //현재 페이지
    pageBoardSize : 10 ,   // 현재 페이지당 표시할 게시물 수
    key : 'routeTimeNo',       //현재 검색 key
    keyword : ''   ,         //현재 검색 keyword
    routeNo : 0
}






// 3. 출발지 구하는 함수
doGetStartRouteSearch(indexPageObject.routeEndTerminal);
function doGetStartRouteSearch(routeEndTerminal){
    console.log('doGetStartRouteSearch()')
    console.log(routeEndTerminal);
    $.ajax({
        url : "/member/startroute" ,
        method : "GET" ,
        async : false ,
        data : {"routeEndTerminal" : routeEndTerminal},
        success : (r) => {
            console.log(r)
            let html = `<option value=" " checked>출발지 선택</option>`;
            r.forEach( r2 =>{
                html += `<option value="${r2.routeStartTerminal}">${r2.routeStartTerminal}</option>`
            })


            document.querySelector('#startSelectBox').innerHTML = html;
        }
    })
}
// 4. 도착지 구하는 함수
doGetEndRouteSearch(indexPageObject.routeStartTerminal);
function doGetEndRouteSearch(routeStartTerminal){
    console.log('doGetEndRouteSearch()')
    console.log(routeStartTerminal);
    $.ajax({
        url : "/member/endroute" ,
        method : "GET" ,
        async : false ,
        data : {"routeStartTerminal" : routeStartTerminal},
        success : (r) => {
            console.log(r)
            let html = `<option value=" " checked>도착지 선택</option>`;
            r.forEach( r2 =>{
                html += `<option value="${r2.routeEndTerminal}">${r2.routeEndTerminal}</option>`
            })
            document.querySelector('#endSelectBox').innerHTML = html;
        }
    })
}

// 5. 출발지 기준 검색 or 도착지 기준 검색

function doGetRouteSearch(routeStandard,e){

    if(e.value != " "){
        if(routeStandard == "start"){
            if(document.querySelector("#endSelectBox").value != " "){return;}
            indexPageObject.routeStartTerminal = e.value;
            console.log(e.value)
            console.log(indexPageObject.routeStartTerminal)
            doGetEndRouteSearch(indexPageObject.routeStartTerminal)
        }else{
        if(document.querySelector("#startSelectBox").value != " "){return;}
            indexPageObject.routeEndTerminal = e.value;
            console.log(e.value);
            doGetStartRouteSearch(indexPageObject.routeEndTerminal)
        }
    }
}

function doGetRouteNo(){

    let startSelectBox = document.querySelector('#startSelectBox').value
    let endSelectBox = document.querySelector('#endSelectBox').value
    let position = { startSelectBox : startSelectBox, endSelectBox : endSelectBox }
    if(startSelectBox==null || endSelectBox==null){
        alert("출발/도착지를 정확하게 선택해주세요");
    }
    $.ajax({
        url : "/route/time/routeNo",
        method : "post",
        data : position,
        success : (r) =>{
            console.log(r);
            pageObject.routeNo = r;
            routeTime( 1 );
        }


    });

}