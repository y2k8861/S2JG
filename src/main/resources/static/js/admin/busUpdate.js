let position = ['안산', '대구', '부산', '인천'];
let busNo = new URL( location.href ).searchParams.get('busNo');

onView();
function onView(){
    $.ajax({
        url : "/admin/bus/update.do",
        method : "get",
        data : { "busNo" : busNo },
        success : (r)=>{
            console.log(r);

            document.querySelector('.busNumber').value = r.busNumber;
            document.querySelector('.busClassNo').value = r.busClassNo;
            document.querySelector('.busPosition').value = r.busPosition;

            document.querySelector('.doUpdate').innerHTML = `
                <button type="button" class="btn" onclick="onUpdate(${busNo})">수정하기</button>
                <button type="button" class="btn" onclick="location.href='/admin/bus/'">돌아가기</button>
            `;
        }
    });
}

function onUpdate( busNo ){
    let busNumber = document.querySelector('.busNumber').value;
    let busClassNo = document.querySelector('.busClassNo').value;
    let busPosition = document.querySelector('.busPosition').value;

    let count = 0;
    if(busNumber=='' && busPosition==''){ alert("차량번호와 위치를 입력해주세요"); return; }
    else if(busNumber==''){ alert("차량번호를 입력해주세요"); return; }
    else if(busPosition==''){ alert("차량위치를 입력해주세요"); return; }

    for(let i=0; i<position.length; i++){ if(busPosition == position[i]) count++; }
    if(count == 0){ alert("등록된 지역이 아닙니다"); return;}

    let info = { "busNo" : busNo, "busNumber" : busNumber,
                 "busClassNo" : busClassNo, "busPosition" : busPosition };
    $.ajax({
        url : "/admin/bus/doUpdate",
        method : "put",
        data : info,
        success : (r)=>{
            alert("버스 수정 성공");
            location.href = '/admin/bus/';
        }
    });
}