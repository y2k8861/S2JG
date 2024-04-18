console.log('user.js');



// * 경로(URL)상 의 쿼리스트링(매개변수) 호출하기
    // 1. new URL( location.href ) : 현제 페이지의 경로객체 호출
console.log( new URL( location.href ) );
    // 2. [.searchParams]경로상의 (쿼리스티링) 매개변수들
console.log( new URL( location.href ).searchParams );
    // 3. [.get('queryStringKey')](쿼리스트링) 매개변수 들 에서 특정 매개변수 호출
console.log( new URL( location.href ).searchParams.get('memberNo') );

let memberNo = new URL( location.href ).searchParams.get('memberNo');


doGetUserInfo( );
function doGetUserInfo(  ){
    console.log('doGetUserInfo()');
    $.ajax({
               url : "/member/login/check" ,
               method : 'GET' ,
               success : ( result ) => {
                    console.log(result);

                    $.ajax({
                        url : "/member/login/info" ,
                        method : 'GET' ,
                        data : { 'memberid' : result } ,
                        async : false ,
                        success : ( r2 ) => {
                             console.log( r2 );
                             console.log( r2.memberid )
                            if( result != r2.memberid){
                                alert('로그인후 이용해주세요');
                                location.href="/member/login"
                                return;
                            }
                            document.querySelector('#mName').innerHTML = r2.memberName;

                             memberNo = r2.memberNo;
                             console.log( memberNo );
                             $.ajax({
                                        url : "/member/reservation.do" ,
                                        method : 'GET' ,
                                        data : { 'memberNo' : r2.memberNo } ,
                                        async : false ,
                                        success : ( r3 ) => {
                                             console.log(r3);
                                             let html = '';
                                        if(r3[0] != null){
                                            // 1. 어디에
                                            let userTableBody = document.querySelector("#userTableBody");

                                            // 2. 무엇을

                                            let index = 0;
                                            r3.forEach((reservation) => {
                                                index++;
                                                html += `
                                                    <tr>
                                                     <td>${index}</td>
                                                     <td>${reservation.routeStartTerminal}</td>
                                                     <td>${reservation.routeTimeStartTime.split(":")[0]+":"+reservation.routeTimeStartTime.split(":")[1]}</td>
                                                     <td>${reservation.routeEndTerminal}</td>
                                                     <td>${reservation.routeTimeEndTime.split(":")[0]+":"+reservation.routeTimeEndTime.split(":")[1]}</td>
                                                     <td>${reservation.reservationSeatNum}</td>
                                                     <td>${reservation.reservationPrice}</td>
                                                     <td>${reservation.reservationDate.split(":")[0]+":"+reservation.reservationDate.split(":")[1]}</td>
                                                     <td>`
                                                 if(reservation.reservationStatus == 0){
                                                     html +=`
                                                         <button type="button" class="btn btn01" onclick="doDeleteReservation(${reservation.reservationNo})">예약취소</button>
                                                         <button type="button" class="btn btn01" onclick="doUpdateReservation(${reservation.reservationNo})">탑승완료</button>
                                                     `
                                                 } else {
                                                     html += `탑승완료`;
                                                 }
                                               html +=`  </td>
                                                     </tr>`
                                            });
                                        } else {
                                            html += `
                                                <tr>
                                                    <th colspan="9" rowspan="9" style="text-align:center;"><br /><br /><br />예약내역이 없습니다.<br /><br /><br /><br /></th>
                                                </tr>
                                            `
                                        }


                                        userTableBody.innerHTML = html;
                                     }
                                 });
                             }
                        })
                    }
               })
}


function doUpdateReservation( reservationNo ){
    console.log('doUpdateReservation()')


    $.ajax({
        url : "/member/reservation.doUpdate" ,
        method : "PUT" ,
        data : { 'memberNo' : memberNo , 'reservationNo' : reservationNo } ,
        success : ( r ) => {
            console.log( r )
            console.log( memberNo )
            console.log( reservationNo )
            alert('수정성공');
            doGetUserInfo();

        }
    })
}

function doDeleteReservation( reservationNo ){
    console.log('doDeleteReservation()')

    $.ajax({
            url : "/member/reservation.doDelete" ,
            method : "delete" ,
            data : { 'memberNo' : memberNo , 'reservationNo' : reservationNo } ,
            success : ( r ) => {
                console.log( r )
                console.log( memberNo )
                console.log( reservationNo )
                alert('삭제성공');
                location.href ="/"


            }
        })
}

//// 1. 노선 검색
//routeSearch();
//// 선택 전에 먼저 출발지 목록을 보여줘야 하므로.. 처음에 조건 없이 출력 해줍니다..
//function routeSearch(){
//    console.log('routeSearch()');
////    let keyvalue = document.getElementById("startSelectBox").value;
//    // 여기부터가 문제 인것 같아요...
//    // 셀렉트에서 받아올 값이 1 인가요?? routeStartTerminal인가요?? routeStartTerminal인가요요 거요
////    console.log("keyvalue : "+keyvalue);
//
//    // 일단 routeStartTerminal 까지 가지고 왔어요..
//    // 그 다음 뭐 해야하죠?? routeStartTerminal 요 아이가 그 리스트(리스트가 어디에 있죠??) 안에있는데 요아이의 값을 가져오고싶어요
//
//    $.ajax({
//        url : "/member/startroute" ,
//        method : "GET" ,
//        async : false,
//        // 여기에 데이터를 넣어주는게 없네요..?? 월레있었는데 그 그 전역변수에 뭐 해줘야하는거인거같은데 모르겠어요 ㅎㅎ
//        // key 와 value 를 보내야 할것 같은데요..
////         data : { 'key' : key , 'value' : value } , // 이거아닌가여 네 맞아요... 왜 안보내셨죠?? 그래도 안되요 ㅠ
//        success : (result) =>{
//            console.log(result);
//
//            let startRouteBus = document.querySelector('.startRouteBus');
//
//            console.log(result[0].routeStartTerminal);
//            let html = '';
//            // 흠 아닌가같은데 ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ
//            result.forEach( (route)=>{
//                html += `<button onclick="onBrno()" >${ route.routeStartTerminal}</button>`
//            })
//
//            startRouteBus.innerHTML = html;
//        }
//    });
//}
//
//
//erouteSearch();
//function erouteSearch(){
//    console.log('erouteSearch()');
//
//
//    $.ajax({
//        url : "/member/endroute" ,
//        method : "GET" ,
//        async : false,
//        // 여기에 데이터를 넣어주는게 없네요..?? 월레있었는데 그 그 전역변수에 뭐 해줘야하는거인거같은데 모르겠어요 ㅎㅎ
//        // key 와 value 를 보내야 할것 같은데요..
////         data : { 'key' : key , 'value' : value } , // 이거아닌가여 네 맞아요... 왜 안보내셨죠?? 그래도 안되요 ㅠ
//        success : (result) =>{
//            console.log(result);
//
//            // *여기
//            let endRouteBus = document.querySelector('.endRouteBus');
//
//            let html = '';
//
//            result.forEach( (route)=>{
//                html += `<button class="" onclick="onBrno( this )" >${ route.routeEndTerminal}</button>`
//            })
//            endRouteBus.innerHTML = html;
//        }
//    });
//}
//
//function onBrno( index ){
//    console.log('onBrno()');
//    // 1. 모든 출발지 버튼 호출
//    let startRouteBusBtns = document.querySelectorAll('.startRouteBus > button');
//    console.log(startRouteBusBtns)
////    let startRouteBus = document.querySelector('.startRouteBus > button').value;
////    console.log(startRouteBus);
//    for( let i = 0 ; i < startRouteBusBtns.length ; i++ ){
//        console.log(startRouteBusBtns[i]);
//        startRouteBusBtns[i].classList.remove("busbtnActive");
//        routeSearch();
//    }
//
//    startRouteBusBtns[index].classList.add("busbtnActive");
//
//
//}
/*
이 테스트한 결과값들을 셀렉트박스 안에 넣고싶은데
방법을 모르겠어요 ㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜ
routeStartTerminal 얘를 넣고싶은데 어캐해요

요기에 처음 셀렉트박스에 출발터미널을 넣어야하고
두번째에 도착터미널 넣어야되는데 방법을 모르겠어요 디비에서
꺼내온거아닌가요 요거되면


-- 고새하네요. 코드는 어디에 있죠?
값이 왜저래요
사람이 도착지 먼저 설정할수도있을거같아서
그 이조건이 status 이게 그 뭐지 아니이거말고
어 뭐드라 그 왕복 체큰데 아니 이게 아니라 ㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎ
아무튼 그 도착지를 선택하면 그 도착지인 출발지만 나오게 하고
출발지도 마찬가지로 출발지 선택하면 그 출발지의 도착지만 나오게하고싶어서
디비에 조건을 건건데


지금까지 한 코드는 잠시 잊고 다시 생각해 봐야 할것 같아요.

1. 출발지 셀렉트 박스에 현재 DB에 있는 모든 출발지 목록을 가져와서 셀렉트 박스에 대입해준다.
2. 출발지 셀렉트 박스에서 출발지를 선택하게 되면 그에 맞는 도착지 목록을 DB에 있는 모든 도착지 목록을 가져와서 셀렉트 박스에 대입한다.
3. 도찾기 셀렉트 박스에서 도착지를 선택하면 < 2,3>에서 선택된 출발지와 도착지를 검색 DB에 전달후 결과물을 가져온다.

맞을까요? < 2,3> 이게 무슨뜻이죠 .. 아하 음 그리고 이거 반대로 또

1. 도착지 셀렉트 박스에 현재 db에 있는 모든 도착 이거하고
2. 도착지 셀렉트 박스에서 도착지를 선택하게 되면 그에맞는 출발지 목록을 db에 있는 네 이것도 같이 하고싶은데

도착지를 먼저 선택하는 경우가 있을꺼같아서 , 네 그게 말이 안될것 같아요. 아하 ! 그럴때는 셀렉트 박스 말고 DIV에 그냥 박스형식으로 해서
클릭 이벤트로 해야 될것 같아요. 아하 그럼 박스형식으로 바꿀까요 ? 네 잠시만요.
그 지하철 티켓 사보셨죠? 아니여 ㅎㅎㅎㅋㅋㅋㅋㅋㅋ 알겠죠? ㅋㅋ 네 ㅎㅎ

1. 먼저 모든 출발지를 db에서 꺼내와야 할것 같아요. 네 할수있슴다  몇분 걸리죠? 5~10분 걸릴꺼같습다 10분뒤에 오겠습니다. 감사하빈다

선생님 기억이안나요 설계 오류다 이거 한개씩만 어쩌지 어떠카지 어쩌지
야호 도착지도 해놓을까여  ? ㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎ 왜안되지


이제 출발지 선택하면 도착지 선택 버튼을 활성화 또는 비활성화 상태로 변경해야 할것 같아요..
또는 반대로 도착지 선택시 출발지 선택 버튼을 활성화 또는 비활성화 상태로 변경해야 할것 같아요..

하 음 어떤 조건을 잡고 해야하지요 ?

순서도
1. 출발지 버튼을 클릭시 클릭된 버튼과 클릭안되 버튼을 구분한다( css 로 )
2. 선택된 버튼의 출발지명을 전역변수에 저장한다..

일단 여기까지.. 이해 되나요?? 2번은 알겠는데 1번이 어려워보이고 기억도안나고 음음 언제배운걸까요. 네 게시판에서요..

해 볼수 있겠어요?? 노력하겠습니다 이제 주무셔야져 ㅠㅠ 저는 더 하고 있을게요... 피곤하시면 내일 학원에서 물어보세요~~
그럼 좀만더 해볼깨요 ㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎ


*/