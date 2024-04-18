let position = ['안산', '대구', '부산', '인천', '동서울', '광주'];

function getInsertBus(){

    let busNumber = document.querySelector('.busNumber').value;
    let busClassNo = document.querySelector('.busClassNo').value;
    let busPosition = document.querySelector('.busPosition').value;

    let bus = { busNumber : busNumber,
                busClassNo : busClassNo,
                busPosition : busPosition
    }

    let count = 0;
    if(busNumber=='' && busPosition==''){ alert("차량번호와 위치를 입력해주세요"); return; }
    else if(busNumber==''){ alert("차량번호를 입력해주세요"); return; }
    else if(busPosition==''){ alert("차량위치를 입력해주세요"); return; }

    for(let i=0; i<position.length; i++){ if(busPosition == position[i]) count++; }
    if(count == 0){ alert("등록된 지역이 아닙니다"); return;}

    $.ajax({
        url : "/admin/bus/create.do",
        method : "post",
        data : bus,
        success : (r) =>{
            if(r){
                console.log("버스등록 성공");
                alert("버스등록 성공");
                location.href = "/admin/bus/"
            }
            else{
                console.log("버스등록 실패")
                alert("버스등록 실패");
            }
        }
    });
}