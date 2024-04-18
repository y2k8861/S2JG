
console.log('로그인 테스트js');

function login(){
console.log("login");
//1. HTML 입력 값 호출
 let memberid = document.querySelector('#memberid').value;
 let memberpw = document.querySelector('#memberpw').value;
//2. 객체화
    let info = { memberid:memberid , memberpw,memberpw};
    console.log(info);
    //서버 통신
  $.ajax({
    url : '/member/login',
    method : 'post',
    data : info ,
    success : function(result){
        //결과
        if(result){alert('로그인 성공')
            location.href="/"; //로그인 성공 시 메인으로

        }//if end
        else{ alert('로그인 실패');
        }//else end
    } // suc end

  }); //ajax end

} //f end


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