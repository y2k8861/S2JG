let checkArray = [false,false,false]
let routeNo = new URL(location.href).searchParams.get("no");
if(routeNo > 0){
    document.querySelector(".createBtn").setAttribute("onclick",`doUpdateRoute(${routeNo})`);
    $.ajax({
        url :"/admin/route/view.do",
        data : {"routeNo":routeNo},
        method : "get",
        success : (r)=>{
            document.querySelector("#routeStartTerminal").value = r.routeStartTerminal;
            document.querySelector("#routeEndTerminal").value = r.routeEndTerminal;
            document.querySelector("#routePrice").value = r.routePrice;
        }
    });
}

function routeStartTerminalCheck(){
    let routeStartTerminal = document.querySelector("#routeStartTerminal").value;
    let rule = /^[가-힣]{2,20}$/;
    let msg = "";
    checkArray[0] = false;
    let check = document.querySelector(".routeStartTerminalCheck");
    if(rule.test(routeStartTerminal)){
        msg = "통과";
        checkArray[0] = true;
        check.classList.remove('colorRed')
        check.classList.add("colorGreen");
    } else {
        msg="한글 2~20글자";
        check.classList.remove('colorGreen')
        check.classList.add("colorRed");
        checkArray[0] = false;
    }
    check.innerHTML = msg;
    console.log(checkArray);
}

function routeEndTerminalCheck(){
    let routeEndTerminal = document.querySelector("#routeEndTerminal").value;
    let rule = /^[가-힣]{2,20}$/;
    let msg = "";
    checkArray[1] = false;
    let check = document.querySelector(".routeEndTerminalCheck");
    if(rule.test(routeEndTerminal)){
        msg = "통과";
        checkArray[1] = true;
        check.classList.remove('colorRed')
        check.classList.add("colorGreen");
    } else {
        msg="한글 2~20글자";
        checkArray[1] = false;
        check.classList.remove("colorGreen");
        check.classList.add("colorRed");
    }
    check.innerHTML = msg;
    console.log(checkArray);
}

function routePriceCheck(){
    let routePrice = document.querySelector("#routePrice").value;
    let rule = /^[0-9]{0,20}$/;
    checkArray[2] = false;
        let check = document.querySelector(".routePriceCheck");
        if(rule.test(routePrice)){
            msg = "통과";
            checkArray[2] = true;
            check.classList.remove('colorRed')
            check.classList.add("colorGreen");
        } else {
            msg="숫자만 입력해주세요.";
            checkArray[2] = false;
            check.classList.remove('colorGreen')
            check.classList.add("colorRed");
        }
        check.innerHTML = msg;
        console.log(checkArray);
}

function doPostRoute(){
    console.log("doPostRout");
    for(let i = 0; i<checkArray.length; i++){
        if(!checkArray[i]){
            alert("입력사항들을 모두 입력해주세요.");
            return;
        }
    }

    let routeStartTerminal = document.querySelector("#routeStartTerminal").value;
    let routeEndTerminal = document.querySelector("#routeEndTerminal").value;
    let routePrice = document.querySelector("#routePrice").value;

    if(routeStartTerminal == routeEndTerminal) {
        alert("출발지와 도착지를 다르게 입력해 주세요.");
        return;
    }
     $.ajax({
        url : '/admin/route/create',
        method : 'post',
        data : {"routePrice":routePrice,"routeStartTerminal":routeStartTerminal,"routeEndTerminal":routeEndTerminal},
        success : (r)=>{
            if(r){
                alert("등록 성공")
                location.href='/admin/route';
            } else {
                alert('등록 실패')
            }
        }
    });
}

// 수정 메소드
function doUpdateRoute(routeNumber){
    console.log(routeNumber);
    let routeStartTerminal = document.querySelector("#routeStartTerminal").value;
    let routeEndTerminal = document.querySelector("#routeEndTerminal").value;
    let routePrice = document.querySelector("#routePrice").value;
    $.ajax({
        url : '/admin/route/update.do',
        method : 'put',
        data : {"routeNo":routeNumber, "routePrice":routePrice,"routeStartTerminal":routeStartTerminal,"routeEndTerminal":routeEndTerminal},
        success : (r)=>{
            console.log(r);
            if(r){
                location.href='/admin/route';
            } else {
                alert("수정 실패");
            }
        }
    });
}