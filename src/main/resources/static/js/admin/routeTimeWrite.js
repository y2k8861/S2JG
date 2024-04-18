let checkArray = [true,true,true,true,true]
//function routeStartTerminalCheck(){
//    let routeStartTerminal = document.querySelector("#routeStartTerminal").value;
//    let rule = /^[가-힣]{2,20}$/;
//    let msg = "";
//    checkArray[0] = false;
//    let check = document.querySelector(".routeStartTerminalCheck");
//    if(rule.test(routeStartTerminal)){
//        msg = "통과";
//        checkArray[0] = true;
//        check.classList.remove('colorRed')
//        check.classList.add("colorGreen");
//    } else {
//        msg="한글 2~20글자";
//        check.classList.remove('colorGreen')
//        check.classList.add("colorRed");
//        checkArray[0] = false;
//    }
//    check.innerHTML = msg;
//    console.log(checkArray);
//}
//
//function routeEndTerminalCheck(){
//    let routeEndTerminal = document.querySelector("#routeEndTerminal").value;
//    let rule = /^[가-힣]{2,20}$/;
//    let msg = "";
//    checkArray[1] = false;
//    let check = document.querySelector(".routeEndTerminalCheck");
//    if(rule.test(routeEndTerminal)){
//        msg = "통과";
//        checkArray[1] = true;
//        check.classList.remove('colorRed')
//        check.classList.add("colorGreen");
//    } else {
//        msg="한글 2~20글자";
//        checkArray[1] = false;
//        check.classList.remove("colorGreen");
//        check.classList.add("colorRed");
//    }
//    check.innerHTML = msg;
//    console.log(checkArray);
//}
//
//function routePriceCheck(){
//    let routePrice = document.querySelector("#routePrice").value;
//    let rule = /^[0-9]{0,20}$/;
//    checkArray[2] = false;
//        let check = document.querySelector(".routePriceCheck");
//        if(rule.test(routePrice)){
//            msg = "통과";
//            checkArray[2] = true;
//            check.classList.remove('colorRed')
//            check.classList.add("colorGreen");
//        } else {
//            msg="숫자만 입력해주세요.";
//            checkArray[2] = false;
//            check.classList.remove('colorGreen')
//            check.classList.add("colorRed");
//        }
//        check.innerHTML = msg;
//        console.log(checkArray);
//}
//

function getBusPosition(){
    let busPosition = document.querySelector("#routeNo").value.split("^")[1];
    console.log(busPosition)
    $.ajax({
        url : "/admin/bus/position.do",
        method : "get",
        data : {"busPosition":busPosition} ,
        success : (r)=>{
            let busNo = document.querySelector("#busNo");
            let html = `<option value="0" checked>선택해주세요</option>`;
            r.forEach(r2 =>{
                html += `
                    <option value="${r2.busNo}">${r2.busNumber}</option>
                `;

            })
            busNo.innerHTML = html;

            let routeTimeNo = new URL(location.href).searchParams.get("no");
            if(routeTimeNo > 0){
                document.querySelector(".createBtn").setAttribute("onclick",`doUpdateRouteTime(${routeTimeNo})`);
                $.ajax({
                    url :"/admin/routeTime/view.do",
                    data : {"routeTimeNo":routeTimeNo},
                    method : "get",
                    async : false,
                    success : (r2)=>{
                        console.log(r2);
                        // document.querySelector("#busNo").value = r2.busNo;
                        let el = document.querySelector("#busNo");  //select box
                        let len = el.options.length; //select box의 option 갯수
                        console.log(len)
                        for (let i=0; i<len; i++){
                            //select box의 option value가 입력 받은 value의 값과 일치할 경우 selected
                            if(el.options[i].value == r2.busNo){
                                el.options[i].selected = true;
                            }
                        }
                    }
                });
            }
        }
    });
}

function doPostRouteTime(){
    console.log("doPostRout");
    for(let i = 0; i<checkArray.length; i++){
        if(!checkArray[i]){
            alert("입력사항들을 모두 입력해주세요.");
            return;
        }
    }

    let routeNo = document.querySelector("#routeNo").value.split("^")[0];
    let routeTimeStartTime = document.querySelector("#routeTimeStartTime").value;
    let routeTimeEndTime = document.querySelector("#routeTimeEndTime").value;
    let busNo = document.querySelector("#busNo").value;

     $.ajax({
        url : '/admin/routeTime/create',
        method : 'post',
        data : {"routeNo":routeNo,"routeTimeStartTime":routeTimeStartTime,"routeTimeEndTime":routeTimeEndTime,"busNo":busNo},
        success : (r)=>{
            console.log(r);
            if(r){
                alert("등록 성공")
                location.href='/admin/route';
            } else {
                alert("등록 실패")
            }
        }
    });
}

$.ajax({
    url : "/admin/route/all.do",
    method : "get",
    success : (r)=>{
        let routeNo = document.querySelector("#routeNo");
        let html =``;
        r.forEach(route=>{
            html +=`<option value="${route.routeNo}^${route.routeStartTerminal}">${route.routeStartTerminal} -> ${route.routeEndTerminal}</option>`;
        })
        routeNo.innerHTML += html;

        let routeTimeNo = new URL(location.href).searchParams.get("no");
        if(routeTimeNo > 0){
            document.querySelector(".createBtn").setAttribute("onclick",`doUpdateRouteTime(${routeTimeNo})`);
            $.ajax({
                url :"/admin/routeTime/view.do",
                data : {"routeTimeNo":routeTimeNo},
                method : "get",
                async : false,
                success : (r2)=>{
                    console.log(r2);
                    let el = document.querySelector("#routeNo");  //select box
                    let len = el.options.length; //select box의 option 갯수
                    console.log(len)
                    for (let i=0; i<len; i++){
                        //select box의 option value가 입력 받은 value의 값과 일치할 경우 selected
                        if(el.options[i].value.split("^")[0] == r2.routeNo){
                            el.options[i].selected = true;
                        }
                    }
                    document.querySelector("#routeTimeStartTime").value = r2.routeTimeStartTime;
                    document.querySelector("#routeTimeEndTime").value = r2.routeTimeEndTime;
                    getBusPosition();
                }
            });
        }
    }
});


// 수정 메소드
function doUpdateRouteTime(routeTimeNo){
    console.log(routeTimeNo);
    let routeNo = document.querySelector("#routeNo").value.split("^")[0];
    let routeTimeStartTime = document.querySelector("#routeTimeStartTime").value;
    let routeTimeEndTime = document.querySelector("#routeTimeEndTime").value;
    let busNo = document.querySelector("#busNo").value;
    $.ajax({
        url : '/admin/routeTime/update.do',
        method : 'put',
        data : {"routeTimeNo":routeTimeNo,"routeNo":routeNo,"routeTimeStartTime":routeTimeStartTime,"routeTimeEndTime":routeTimeEndTime,"busNo":busNo},
        success : (r)=>{
            console.log(r);
            if(r){
                alert('수정 성공')
                location.href='/admin/routeTime';
            } else {
                alert("수정 실패");
            }
        }
    });
}