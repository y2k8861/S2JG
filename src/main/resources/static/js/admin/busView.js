
let busNo = new URL( location.href ).searchParams.get('busNo');

onView();
function onView(){
    console.log("onView()");
    $.ajax({
        url : "/admin/bus/read.do",
        method : "get",
        data : { "busNo" : busNo },
        success : (r)=>{
            console.log(r);

            let busClassNoKR = '';
            if(r.busClassNo == 1){ busClassNoKR = "일반"; }
            else if(r.busClassNo == 2){ busClassNoKR = "우 등"; }
            else if(r.busClassNo == 3){ busClassNoKR = "프리미엄"; }

            document.querySelector('.busNumber').innerHTML = r.busNumber;
            document.querySelector('.busClassNo').innerHTML = busClassNoKR;
            document.querySelector('.busPosition').innerHTML = r.busPosition;
            document.querySelector('.busDate').innerHTML = r.busDate.split(":")[0]+":"+r.busDate.split(":")[1];


            document.querySelector('.busUpdate').innerHTML =
            `<a href="/admin/bus/update?busNo=${busNo}" class="btn">수정하기</a>`;
        }
    });
}