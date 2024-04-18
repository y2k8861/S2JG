// 1. 로그인 여부 확인 요청 [ JS 열렸을때 마다 ]
let memberGrant = 0;

$.ajax({
    url : '/admin/login/check',
    method : 'get',
    success : (r)=>{  console.log(r);

        if( r != '' ){ // 로그인 했을때
            $.ajax({
                url : '/admin/login/info',
                method:'get',
                data: {"memberid":r},
                async : false , // 응답이 오기전까지 대기 상태
                success:(r2)=>{
                    console.log( r2 );
                    if(r2.memberGrant <= 10){ // 관리자가 아닐 때
                        alert("관리자가 아닙니다.");
                        location.href = "/";
                        memberGrant = r2.memberGrant;
                    }
                    else{
                       document.querySelector('#loadingBox').style = 'display:none';
                    }

                }
             });
        }else{ // 로그인 안했을떄
            alert("로그인이 필요합니다.");
            location.href = "/member/login";

        }
    } // s end
}); // ajax end



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