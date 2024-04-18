console.log('개별글 조회 실행~')

let ino = new URL( location.href ).searchParams.get('ino');
console.log(ino);

// 개별글 조회
doGetInquiryInoView();
function doGetInquiryInoView(){
    console.log('doGetInquiryInoView()');

    $.ajax({
        url : "/inquiryView.do" ,
        method : "GET" ,
        data : { 'ino' : ino } ,
        success : ( r ) => {
            console.log(r);
            document.querySelector('.ititle').innerHTML = r.ititle;
            document.querySelector('.memberId').innerHTML = r.memberId;
            document.querySelector('.idate').innerHTML = r.idate
            document.querySelector('.icontent').innerHTML = r.icontent

            $.ajax({
                url : "/member/login/check" ,
                method : "get"  ,
                success : ( r2 ) => {
                    console.log( r2 );
                    console.log( r.memberId );
                    if( r2 == r.memberId ){
                        let btnHTML = `<button class="btn" type="button" onclick="onDelete( ${ r.ino } )">삭제</button>`
                        btnHTML += `<button class="btn" type="button" onclick="location.href='/inquiryUpdate?ino=${ r.ino }' ">수정</button>`
                        document.querySelector('.btnBox').innerHTML += btnHTML;
                    }
                } // success 2 end
            }); // ajax 2 end
        }
    })
}

// 2. 글 삭제 함수
function onDelete( ino ){
    $.ajax({
        url : "inquiryDelete.do" ,
        method : "DELETE" ,
        data : { "ino" : ino } ,
        success : ( r ) => {
            console.log(r);
            if( r ){ alert('삭제성공'); location.href="/inquiry" }
                else{ alert('삭제실패') }
            }

    })
}