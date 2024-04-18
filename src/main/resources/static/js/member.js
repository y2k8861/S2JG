console.log('테스트');
let msg = "";   // innerHTML 할 깡통 만들기
let checkArray = [ false , false , false , false , false ]; // 유효성 검사 체크 현황
// ================ 회원가입 ================
function signup(){
    console.log('signup()');

    // * 유효성검사 체크 현황중에 하나라도 false 이면 회원가입 금지
    for( let i = 0 ; i < checkArray.length ; i++ ){
        if ( !checkArray[i]) {
        alert("입력사항들을 모두 정확히 입력해주세요");
            return;
        }
    }

    // 1. 입력값 가져오기
    let id = document.querySelector('#id').value; console.log( id )
    let pw = document.querySelector('#pw').value; console.log( pw )
    let name = document.querySelector('#name').value; console.log( name )
    let email = document.querySelector('#email').value; console.log( email )
    let phone = document.querySelector('#phone').value; console.log( phone )


    // ajax 통신
    $.ajax({
                url : '/member/signup' ,
                method : 'POST' ,
                data : { "memberId" : id , "memberPw" : pw  , "memberName" : name , "memberEmail" : email , "memberPhone" : phone } ,
                success : function ( result ){
                // 4. 결과
                if( result ){
                        alert('회원가입 성공');
                        location.href = '/member/login';
                    }else{
                        alert('회원가입 실패');
                    }
                    console.log(result);
                }
            }) // ajax end
} // f end
// ================ 회원가입 END ================

// =============== 아이디 유효성 검사 ===============
function idCheck(){
    console.log('idCheck()');

    // 1. 입력값 가져오기
    let id = document.querySelector('#id').value; console.log( id );

    // 2. 정규표현식 정하기
    let idRegularExpression = /^[a-z0-9]{5,30}$/;

    // 3. 정규표현식 에 따른 검사
    if( idRegularExpression.test(id) ){

        // 아이디 중복 검사
        $.ajax({
            url :"/member/find/idcheck" ,
            method : "GET" ,
            data : { "memberId" : id } ,
            success : ( result ) => {
                if( result ){   // true : 아이디 중복 , false : 아이디 중복 아님

                    document.querySelector('.idcheckbox').innerHTML = "이미 사용중인 아이디입니다.";
                    document.querySelector('.idcheckbox').classList.remove("colorGreen")
                    document.querySelector('.idcheckbox').classList.add("colorRed")
                    checkArray[0] = false;
                }else{
                    document.querySelector('.idcheckbox').innerHTML = "통과";
                    document.querySelector('.idcheckbox').classList.remove("colorRed")
                    document.querySelector('.idcheckbox').classList.add("colorGreen")
                    checkArray[0] = true;
                }
            } // success end
        }) // ajax end
    }else{

    document.querySelector('.idcheckbox').innerHTML = "영소문자+숫자 조합의 5~30 글자 사이로 입력해주세요.";
    checkArray[0] = false;
    }
} // f end
// =============== 아이디 유효성 검사 END ===============

// =============== 비밀번호 유효성 검사 ===============
function pwCheck(){
    console.log('pwCheck()');

    // 1. 입력값 가져오기
    let pw = document.querySelector('#pw').value; console.log( pw );
    let pwconfirm = document.querySelector('#pwconfirm').value; console.log( pwconfirm );

    checkArray[1] = false;

    // 2. 정규표현식 정하기
    let pwRegularExpression = /^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{5,30}$/

    // 3. 정규표현식 에 따른 검사

    if( pwRegularExpression.test(pw) ){                    // 비밀번호 정규표현식 검사
        if( pwRegularExpression.test(pwconfirm) ){ // 비밀번호확인 정규표현식 검사
                    // 3-1. 비밀번호 와 비밀번호 확인 일치확인
            if( pw == pwconfirm ){ // 일치
                msg = "통과";
                document.querySelector('.pwcheckbox').classList.remove("colorRed")
                document.querySelector('.pwcheckbox').classList.add("colorGreen")
                checkArray[1] = true;
            }else{ // if end
                msg = "비밀번호 불일치";
                document.querySelector('.pwcheckbox').classList.remove("colorGreen")
                document.querySelector('.pwcheckbox').classList.add("colorRed")
            }
        }else{
                msg = "영대소문자 1개 필수 와 숫자 1개 의 조합 5~30글자 사이로 입력해주세요.";
                checkArray[1] = false;
                document.querySelector('.pwcheckbox').classList.remove("colorGreen")
                document.querySelector('.pwcheckbox').classList.add("colorRed")
            }
        }else{
                msg = "영대소문자 1개 필수 와 숫자 1개 의 조합 5~30글자 사이로 입력해주세요.";
                checkArray[1] = false;
                document.querySelector('.pwcheckbox').classList.remove("colorGreen")
                document.querySelector('.pwcheckbox').classList.add("colorRed")
        }

        document.querySelector('.pwcheckbox').innerHTML = msg;
}
// =============== 비밀번호 유효성 검사 END ===============

// =============== 이름 유효성 검사 ===============
function namecheck(){
    console.log("namecheck()");
    // 1. 입력값 가져온다
    let name = document.querySelector('#name').value;
    // 2. 정규표현식 작성한다
    let nameRegularExpression = /^[가-힣]{2,20}$/
    let msg = '한글만 입력해주세요.';
    checkArray[2] = false;
    // 3. 정규표현식 검사한다
    if( nameRegularExpression.test(name) ){ // 4. 정규표현식 검사가 true 일때
        checkArray[2] = true;
        msg = '통과';
        document.querySelector('.namecheckbox').classList.remove("colorRed")
        document.querySelector('.namecheckbox').classList.add("colorGreen")
    }else{
        msg ='한글 2글자 이상 입력해주세요';
        document.querySelector('.namecheckbox').classList.remove("colorGreen")
        document.querySelector('.namecheckbox').classList.add("colorRed")
    }
    document.querySelector('.namecheckbox').innerHTML = msg;
}
// =============== 이름 유효성 검사 END ===============

// =============== 이메일 유효성 검사 ===============
function emailcheck(){
    console.log('emailcheck()');
    // 1. 입력값 가져오기
    let email = document.querySelector('#email').value;

    checkArray[3] = false;
    // 2. 정규표현식 정하기
    let emailRegularExpression = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+\.[a-zA-z]+$/
    if( emailRegularExpression.test(email) ){


                // 이메일 중복 검사
                $.ajax({
                    url :"/member/find/emailcheck" ,
                    method : "GET" ,
                    data : { "memberEmail" : email } ,
                    success : ( result ) => {
                        console.log( result );
                        if( result ){   // true : 이메일 중복 , false : 이메일 중복 아님
                            document.querySelector('.emailcheckbox').innerHTML = "이미 등록된 이메일입니다.";
                            checkArray[3] = false;
                            document.querySelector('.emailcheckbox').classList.remove("colorGreen")
                            document.querySelector('.emailcheckbox').classList.add("colorRed")

                        }else{
                            document.querySelector('.emailcheckbox').innerHTML = "통과";
                            checkArray[3] = true;
                            authreqbtn.disabled = false;
                            document.querySelector('.emailcheckbox').classList.remove("colorRed")
                            document.querySelector('.emailcheckbox').classList.add("colorGreen")
                        }
                    } // success end
                }) // ajax end
            }else{
            document.querySelector('.emailcheckbox').innerHTML = "이메일@도메인 형식으로 작성해주세요.";
            checkArray[3] = false;
            document.querySelector('.emailcheckbox').classList.remove("colorGreen")
            document.querySelector('.emailcheckbox').classList.add("colorRed")
            }
}
// =============== 이메일 유효성 검사 END ===============

// =============== 이메일 인증 처리 함수 ==================
let timer = 0; // 인증 시간
let authbox = document.querySelector('.authbox'); // 인증 구역
let authreqbtn = document.querySelector('.authreqbtn'); // 인증요청 버튼

// 인증요청
function authreq( object ){
    console.log('authreq()');

    // 2. 인증 구역 구성
    let html = `<span class="timebox"></span>
                <input type="text" class="ecodeinput" />
                <button type="button" onclick="auth()"> 인증 </button>
                `;
    // 3. 인증 구역 출력
    authbox.innerHTML = html;

    // ========= 자바에게 인증 요청 ========= //
    $.ajax({
        url : '/auth/email/req',
        method : 'GET',
        data : { "memberEmail" : document.querySelector('#email').value } ,
        success : (result) => {
            if ( result ) {
                // 4. 타이머 함수 설정
                timer = 100; // 인증시간 대입
                ontimer();
                console.log( authreqbtn );

                // 해당 버튼 사용 금지
                authreqbtn.disabled = true;
            }else{
                alert('관리자 에게 문의');
            }
        }
     });
}
// =============== 이메일 인증 처리 함수 END ==================

// =============== 타이머 함수 ===============
let timerInter = null;
// // 테스트
//     // setInterval( 함수 , 밀리초 ); : 특정 밀리초 마다 함수 실행
//     // 종료 : clearInterval( Interval변수 ); : 종료할 Interval의 변수 대입
function ontimer(){
    timerInter = setInterval( () => {

    // 1. timer 를 분/초 로 변환
    let m = parseInt( timer / 60 ); // 분
    let s = parseInt( timer % 60 ); // 분 제외한 초

    // 2. 시간을 두 자릿수로 표현
    m = m < 10 ? "0"+m : m ;    // ex : 8분 -> 08분
    s = s < 10 ? "0"+s : s ;    // ex : 3초 -> 03초

    // 3. 시간 출력
    document.querySelector('.timebox').innerHTML = `${m}:${s}`;

    // 4. 시간 감소
    timer--;

    // 5. 만약에 타이머가 0보다 작아지면
    if( timer < 0 ){
        clearInterval( timerInter );
        authbox.innerHTML = `재 인증 해주세요`; // 인증 구역 없애기
        authreqbtn.disabled = false; // 해당버튼 사용
    }

} , 1000 );
}
// =============== 타이머 함수 END ===============

// =============== 이메일 인증번호 인증 함수 =================
function auth(){

    // 1. 내가 입력한 인증번호
    let ecodeinput = document.querySelector('.ecodeinput').value;

    // ============= 내가 입력한 인증번호를 자바에게 보내기 ============= //
    $.ajax({
        url : '/auth/email/check',
        method : 'GET',
        data : { 'ecodeinput' : ecodeinput } ,
        success : (result) => {

    // 3. 인증 성공시 / 실패시
    if( result ){
        checkArray[3] = true;
        document.querySelector('.emailcheckbox').innerHTML = "통과";
        clearInterval( timerInter );
        authbox.innerHTML = ``; // 인증 구역 없애기
        authreqbtn.disabled = false; // 해당버튼 사용
    }else{
        alert('인증번호가 다릅니다')
    }
}
});
}
// =============== 이메일 인증번호 인증 함수 END =================



// =============== 전화번호 유효성 검사 ===============
function phonecheck(){
    console.log('phonecheck()')

    let phone = document.querySelector('#phone').value; console.log( phone );
    let phoneRegularExpression = /^([0-9]{2,3})+[-]+([0-9]{3,4})+[-]+([0-9]{4})+$/
        if( phoneRegularExpression.test(phone) ){
            // 전화번호 중복 검사
            $.ajax({
                url :"/member/find/phonecheck" ,
                method : "GET" ,
                data : { "memberPhone" : phone } ,
                success : ( result ) => {
                    console.log( result );
                    if( result ){   // true : 전화번호 중복 , false : 전화번호 중복 아님
                        document.querySelector('.phonecheckbox').innerHTML = "이미 등록된 번호입니다.";
                        checkArray[4] = false;
                        document.querySelector('.emailcheckbox').classList.remove("colorGreen")
                        document.querySelector('.emailcheckbox').classList.add("colorRed")
                    }else{
                        document.querySelector('.phonecheckbox').innerHTML = "통과";
                        checkArray[4] = true;
                        document.querySelector('.emailcheckbox').classList.remove("colorRed")
                        document.querySelector('.emailcheckbox').classList.add("colorGreen")
                    }
                } // success end
            }) // ajax end
        }else{
        document.querySelector('.phonecheckbox').innerHTML = "000-0000-0000 또는 00-000-0000 식으로 입력해주세요.";
        document.querySelector('.emailcheckbox').classList.remove("colorGreen")
        document.querySelector('.emailcheckbox').classList.add("colorRed")
        checkArray[4] = false;
        }
}