console.log('busseatjs실행')
let routeTimeNo = new URL( location.href ).searchParams.get('routeTimeNo');

onView();
function onView(){
    $.ajax({
        url : "/route/time/reserve.do",
        method : "get",
        data : { "routeTimeNo" : routeTimeNo },
        success : (r)=>{
            if(r == "login"){
                alert("로그인 후 진행하여주십시오.");
                location.href = "/member/login";
            }
            else{
                document.querySelector('#loadingBox').style = 'display:none';
            }
            if(r == "프리미엄"){
                document.querySelector('.seatBox').innerHTML =
                                `<div class="card card-body" style="width: 400px;">
                                                     <div class="seat-group">
                                                         <h3>프리미엄 (20석)</h3>
                                                         <div class="" >
                                                             <table class="tabBtn" >
                                                                 <tr style="height:100px;">
                                                                     <td ><button class="seat" type="button" onclick="seatbtn(1)" value="1">1</button></td>
                                                                     <td ><button class="seat" type="button" onclick="seatbtn(2)" value="2">2</button></td>
                                                                     <td ><button class="seat" type="button" onclick="seatbtn(3)" value="3">3</button></td>
                                                                     <td ><button class="seat" type="button" onclick="seatbtn(4)" value="4">4</button></td>
                                                                     <td ><button class="seat" type="button" onclick="seatbtn(5)" value="5">5</button></td>
                                                                     <td ><button class="seat" type="button" onclick="seatbtn(6)" value="6">6</button></td>
                                                                     <td ><button class="seat" type="button" onclick="seatbtn(7)" value="7">7</button></td>
                                                                     <td ><button class="seat" type="button" onclick="seatbtn(8)" value="8">8</button></td>
                                                                     <td ><button class="seat" type="button" onclick="seatbtn(9)" value="9">9</button></td>
                                                                     <td ><button class="seat" type="button" onclick="seatbtn(10)" value="10">10</button></td>
                                                                 </tr>
                                                                 <div>
                                                                 <tr class="seatpremium" style=>
                                                                     <td ><button class="seat" type="button" onclick="seatbtn(11)" value="reservationSeatNum">11</button></td>
                                                                     <td ><button class="seat" type="button" onclick="seatbtn(12)" value="reservationSeatNum">12</button></td>
                                                                     <td ><button  class="seat" type="button" onclick="seatbtn(13)" value="reservationSeatNum">13</button></td>
                                                                     <td ><button class="seat" type="button" onclick="seatbtn(14)" value="reservationSeatNum">14</button></td>
                                                                     <td ><button class="seat" type="button" onclick="seatbtn(15)" value="reservationSeatNum">15</button></td>
                                                                     <td ><button class="seat" type="button" onclick="seatbtn(16)" value="reservationSeatNum">16</button></td>
                                                                     <td ><button class="seat" type="button" onclick="seatbtn(17)" value="reservationSeatNum">17</button></td>
                                                                     <td ><button class="seat" type="button" onclick="seatbtn(18)" value="reservationSeatNum">18</button></td>
                                                                     <td ><button class="seat" type="button" onclick="seatbtn(19)" value="reservationSeatNum">19</button></td>
                                                                     <td ><button class="seat" type="button" onclick="seatbtn(20)" value="reservationSeatNum">20</button></td>
                                                                 </tr>
                                                                 </div>
                                                                 <!-- Add more rows as needed -->
                                                             </table>
                                                         </div>
                                                     </div>
                                                 </div>`;
                                                 seatBlock();
            }
            else if(r == "우등"){
                document.querySelector('.seatBox').innerHTML =
                                `<div class="card card-body" style="width: 400px;">
                                       <div class="seat-group">
                                           <h3>우등 (30석)</h3>
                                           <div class="seat-container">
                                               <table style="height:100px;">
                                                   <tr style="height:100px;">
                                                       <td ><button class="seat" type="button" onclick="seatbtn(1)" value="reservationSeatNum">1</button></td>
                                                       <td ><button class="seat" type="button" onclick="seatbtn(2)" value="reservationSeatNum">2</button></td>
                                                       <td ><button class="seat" type="button" onclick="seatbtn(3)" value="reservationSeatNum">3</button></td>
                                                       <td ><button class="seat" type="button" onclick="seatbtn(4)" value="reservationSeatNum">4</button></td>
                                                       <td ><button class="seat" type="button" onclick="seatbtn(5)" value="reservationSeatNum">5</button></td>
                                                       <td ><button class="seat" type="button" onclick="seatbtn(6)" value="reservationSeatNum">6</button></td>
                                                       <td ><button class="seat" type="button" onclick="seatbtn(7)" value="reservationSeatNum">7</button></td>
                                                       <td ><button class="seat" type="button" onclick="seatbtn(8)" value="reservationSeatNum">8</button></td>
                                                       <td ><button class="seat" type="button" onclick="seatbtn(9)" value="reservationSeatNum">9</button></td>
                                                       <td ><button class="seat" type="button" onclick="seatbtn(10)" value="reservationSeatNum">10</button></td>
                                                   </tr>
                                                   <tr style="">
                                                       <td ><button class="seat" type="button" onclick="seatbtn(11)" value="reservationSeatNum">11</button></td>
                                                       <td ><button class="seat" type="button" onclick="seatbtn(12)" value="reservationSeatNum">12</button></td>
                                                       <td ><button class="seat" type="button" onclick="seatbtn(13)" value="reservationSeatNum">13</button></td>
                                                       <td ><button class="seat" type="button" onclick="seatbtn(14)" value="reservationSeatNum">14</button></td>
                                                       <td ><button class="seat" type="button" onclick="seatbtn(15)" value="reservationSeatNum">15</button></td>
                                                       <td ><button class="seat" type="button" onclick="seatbtn(16)" value="reservationSeatNum">16</button></td>
                                                       <td ><button class="seat" type="button" onclick="seatbtn(17)" value="reservationSeatNum">17</button></td>
                                                       <td ><button class="seat" type="button" onclick="seatbtn(18)" value="reservationSeatNum">18</button></td>
                                                       <td ><button class="seat" type="button" onclick="seatbtn(19)" value="reservationSeatNum">19</button></td>
                                                       <td ><button class="seat" type="button" onclick="seatbtn(20)" value="reservationSeatNum">20</button></td>
                                                   </tr>
                                                   <tr>
                                                       <td ><button class="seat" type="button" onclick="seatbtn(21)" value="reservationSeatNum">21</button></td>
                                                       <td ><button class="seat" type="button" onclick="seatbtn(22)" value="reservationSeatNum">22</button></td>
                                                       <td ><button class="seat" type="button" onclick="seatbtn(23)" value="reservationSeatNum">23</button></td>
                                                       <td ><button class="seat" type="button" onclick="seatbtn(24)" value="reservationSeatNum">24</button></td>
                                                       <td ><button class="seat" type="button" onclick="seatbtn(25)" value="reservationSeatNum">25</button></td>
                                                       <td ><button class="seat" type="button" onclick="seatbtn(26)" value="reservationSeatNum">26</button></td>
                                                       <td ><button class="seat" type="button" onclick="seatbtn(27)" value="reservationSeatNum">27</button></td>
                                                       <td ><button class="seat" type="button" onclick="seatbtn(28)" value="reservationSeatNum">28</button></td>
                                                       <td ><button class="seat" type="button" onclick="seatbtn(29)" value="reservationSeatNum">29</button></td>
                                                       <td ><button class="seat" type="button" onclick="seatbtn(30)" value="reservationSeatNum">30</button></td>
                                                   </tr>
                                                   <!-- Add more rows as needed -->
                                               </table>
                                           </div>
                                       </div>
                                   </div>`;
                                   seatBlock();
            }
            else if(r == "일반"){
                document.querySelector('.seatBox').innerHTML =
                                `<div class="card card-body" style="width: 400px;">
                                     <div class="seat-group">
                                         <h3>일반 (40석)</h3>
                                         <div class="seat-container">
                                             <table>
                                                 <tr>
                                                     <td ><button class="seat" type="button" onclick="seatbtn(1)" value="reservationSeatNum">1</button></td>
                                                     <td ><button class="seat" type="button" onclick="seatbtn(2)" value="reservationSeatNum">2</button></td>
                                                     <td ><button class="seat" type="button" onclick="seatbtn(3)" value="reservationSeatNum">3</button></td>
                                                     <td><button class="seat" type="button" onclick="seatbtn(4)" value="reservationSeatNum">4</button></td>
                                                     <td ><button class="seat" type="button" onclick="seatbtn(5)" value="reservationSeatNum">5</button></td>
                                                     <td ><button class="seat" type="button" onclick="seatbtn(6)" value="reservationSeatNum">6</button></td>
                                                     <td><button class="seat" type="button" onclick="seatbtn(7)" value="reservationSeatNum">7</button></td>
                                                     <td ><button class="seat" type="button" onclick="seatbtn(8)" value="reservationSeatNum">8</button></td>
                                                     <td ><button class="seat" type="button" onclick="seatbtn(9)" value="reservationSeatNum">9</button></td>
                                                     <td ><button class="seat" type="button" onclick="seatbtn(10)" value="reservationSeatNum">10</button></td>
                                                 </tr>
                                                 <tr style="height:100px;">
                                                     <td ><button class="seat" type="button" onclick="seatbtn(11)" value="reservationSeatNum">11</button></td>
                                                     <td ><button class="seat" type="button" onclick="seatbtn(12)" value="reservationSeatNum">12</button></td>
                                                     <td ><button class="seat" type="button" onclick="seatbtn(13)" value="reservationSeatNum">13</button></td>
                                                     <td ><button class="seat" type="button" onclick="seatbtn(14)" value="reservationSeatNum">14</button></td>
                                                     <td ><button class="seat" type="button" onclick="seatbtn(15)" value="reservationSeatNum">15</button></td>
                                                     <td ><button class="seat" type="button" onclick="seatbtn(16)" value="reservationSeatNum">16</button></td>
                                                     <td><button class="seat" type="button" onclick="seatbtn(17)" value="reservationSeatNum">17</button></td>
                                                     <td ><button class="seat" type="button" onclick="seatbtn(18)" value="reservationSeatNum">18</button></td>
                                                     <td ><button class="seat" type="button" onclick="seatbtn(19)" value="reservationSeatNum">19</button></td>
                                                     <td ><button class="seat" type="button" onclick="seatbtn(20)" value="reservationSeatNum">20</button></td>
                                                 </tr>
                                                 <tr >
                                                     <td ><button class="seat" type="button" onclick="seatbtn(21)" value="reservationSeatNum">21</button></td>
                                                     <td ><button class="seat" type="button" onclick="seatbtn(22)" value="reservationSeatNum">22</button></td>
                                                     <td ><button class="seat" type="button" onclick="seatbtn(23)" value="reservationSeatNum">23</button></td>
                                                     <td ><button class="seat" type="button" onclick="seatbtn(24)" value="reservationSeatNum">24</button></td>
                                                     <td><button class="seat" type="button" onclick="seatbtn(25)" value="reservationSeatNum">25</button></td>
                                                     <td ><button class="seat" type="button" onclick="seatbtn(26)" value="reservationSeatNum">26</button></td>
                                                     <td ><button class="seat" type="button" onclick="seatbtn(27)" value="reservationSeatNum">27</button></td>
                                                     <td ><button class="seat" type="button" onclick="seatbtn(28)" value="reservationSeatNum">28</button></td>
                                                     <td ><button class="seat" type="button" onclick="seatbtn(29)" value="reservationSeatNum">29</button></td>
                                                     <td ><button class="seat" type="button" onclick="seatbtn(30)" value="reservationSeatNum">30</button></td>
                                                 </tr>
                                                 <tr>
                                                     <td ><button class="seat" type="button" onclick="seatbtn(31)" value="reservationSeatNum">31</button></td>
                                                     <td ><button class="seat" type="button" onclick="seatbtn(32)" value="reservationSeatNum">32</button></td>
                                                     <td ><button class="seat" type="button" onclick="seatbtn(33)" value="reservationSeatNum">33</button></td>
                                                     <td><button class="seat" type="button" onclick="seatbtn(34)" value="reservationSeatNum">34</button></td>
                                                     <td ><button class="seat" type="button" onclick="seatbtn(35)" value="reservationSeatNum">35</button></td>
                                                     <td ><button class="seat" type="button" onclick="seatbtn(36)" value="reservationSeatNum">36</button></td>
                                                     <td ><button class="seat" type="button" onclick="seatbtn(37)" value="reservationSeatNum">37</button></td>
                                                     <td ><button class="seat" type="button" onclick="seatbtn(38)" value="reservationSeatNum">38</button></td>
                                                     <td ><button class="seat" id="seat1" type="button" onclick="seatbtn(39)" value="39">39</button></td>
                                                     <td ><button class="seat" type="button" onclick="seatbtn(40)" value="reservationSeatNum">40</button></td>
                                                 </tr>
                                                 <!-- Add more rows as needed -->
                                             </table>
                                         </div>
                                     </div>
                                 </div>`;
                seatBlock();
//                const aaa = document.querySelectorAll('.seat');
//                console.log("aaa는 : " + aaa);
//                console.log("텍스트는? : "+ aaa[38].value);
//
//                for(let i=0; i<aaa.length; i++){
//                    if(aaa[i].textContent == "39"){
//                        //aaa[i].disabled = true;
//                    }
//                }


            }
        }
    });

}



function seatbtn( seat ) {
    //서버 통신
    $.ajax({
        url : "/route/time/doReserve",
        method : "post",
        data : { seat : seat,  routeTimeNo : routeTimeNo } ,
        success : (r)=>{
            console.log(r);
            if(confirm('등록 하시겠습니까?')) {
                console.log('등록 확인');
                // 좌석 등록 처리
                alert('좌석 등록 성공');
                location.href = '/';
            }else{
            alert('등록 실패');
            } //r end
        }
    }); //ajax end


} // f e



function seatBlock(){

    $.ajax({
        url : "/route/time/block",
        method : "get",
        data : {"routeTimeNo" : routeTimeNo},
        success : (r)=>{
            console.log(r);
            const seat = document.querySelectorAll('.seat');

            for(let i=0; i<seat.length; i++){
                for(let j=0; j<r.length; j++){
                    if(seat[i].textContent == r[j]){
                        seat[i].disabled = true;
                    }
                }
            }

        }
    });
}