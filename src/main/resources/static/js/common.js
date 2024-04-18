// 1. 로그인 여부 확인 요청 [ JS 열렸을때 마다 ]
$.ajax({
    url : '/member/login/check',
    method : 'get',
    success : (r)=>{  console.log(r);
        // 1. 어디에
        let headMemberflexBox = document.querySelector('.headMemberflexBox');
        // 2. 무엇을
        let html = ``;
        if( r != '' ){ // 로그인 했을때
            $.ajax({
                url : '/member/login/info',
                method:'get',
                data: {"memberid":r},
                async : false , // 응답이 오기전까지 대기 상태
                success:(r2)=>{
                    console.log( r2 );

                    console.log("memberGrant : " + r2.memberGrant);
                    if(r2.memberGrant > 10){ // 관리자일때
                        html +=    `<li class="nav-item"><a class="nav-link" href="/admin/bus/">관리자 페이지</a></li>
                        `
                    }
                    html += `<li class="nav-item">   <a class="nav-link" href="javascript:logout()">로그아웃</a>   </li>
                                  <li class="nav-item">  ${ r } 님 </li>`
                }
             });
        }else{ // 로그인 안했을떄
            html += `<div style="display: flex;">
            <li class="nav-item">   <a class="nav-link" href="/member/login" >로그인</a>        </li>
                      <li class="nav-item">   <a class="nav-link" href="/member">회원가입</a>     </li>
                      </div>`;
        }
        // 3. 대입
        headMemberflexBox.innerHTML = html;
    } // s end
}); // ajax end

// 2. 로그아웃
function logout(){
    $.ajax({
        url : `/member/logout`,
        method: 'get' ,
        success : (r)=>{
            if( r ){
                alert('로그아웃 했습니다.');
                location.href='/'; // 로그아웃 성공시 메인페이지로
            }else{
                alert('로그아웃 실패[관리자에게 문의');
            }
        }
    })
}