
// 회원가입 입력 검증 처리

// 계정 입력 검증
const $idInput = document.getElementById('user_id');
let duplicateFlag = false;
const joinCheck = {
    account: false,
    password: false,
    passwordCheck: false,
    name: false,
    email: false
}

// 계정 중복검사 비동기 요청 보내기
async function fetchDuplicateCheck(type, value) {
    const res = await fetch(`http://localhost:8383/members/check?type=${type}&keyword=${value}`)
    const flag = await res.json();
    // console.log(flag);
    // idFlag = flag;
    duplicateFlag = flag;
}


$idInput.addEventListener('keyup', async (e) => {

    // 아이디 검사 정규표현식
    const accountPattern = /^[a-zA-Z0-9]{4,14}$/;

    // 입력값 읽어오기
    const idValue = $idInput.value;
    // const idValue = this.value; 화살표 함수 땐 this 못 씀
    // console.log(idValue)

    // 검증 메시지를 입력할 span
    const $idChk = document.getElementById('idChk');

    if(idValue.trim() === '') {

        $idInput.style.borderColor = 'red';
        $idChk.innerHTML = '<b class="warning">[아이디는 필수 값입니다.]</b>'
        joinCheck.accout = false;
    } else if (!accountPattern.test(idValue)) {

        $idInput.style.borderColor = 'red';
        $idChk.innerHTML = '<b class="warning">[아이디는 4~14글자 사이의 영문, 숫자로 입력하세요]</b>'
        joinCheck.accout = false;
    } else {

        // 아이디 중복검사
        await fetchDuplicateCheck('account', idValue);

        if(duplicateFlag) {
            $idInput.style.borderColor = 'red';
            $idChk.innerHTML = '<b class="warning">[아이디가 중복되었습니다. 다른 아이디를 사용하세요.]</b>'
            joinCheck.accout = false;

        } else {
            $idInput.style.borderColor = 'skyblue';
            $idChk.innerHTML = '<b class="success">[사용가능한 아이디입니다.]</b>'
            joinCheck.accout = true;
        }

    }


});

const $pwInput = document.getElementById('password');

$pwInput.addEventListener('keyup', e => {
    // 패스워드 검사 정규표현식
    const passwordPattern = /([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])/;

    const pwValue = $pwInput.value
    const $pwChk = document.getElementById('pwChk');

    if(pwValue.trim() === '') {
        $pwChk.style.borderColor = 'red';
        $pwChk.innerHTML = '<b class="warning">[비밀번호는 필수 값입니다.]</b>'
        joinCheck.password = false;
    } else if(!passwordPattern.test(pwValue)) {
        $pwChk.style.borderColor = 'red';
        $pwChk.innerHTML = '<b class="warning">[비밀번호는 영문과 특수문자를 포함한 최소 8글자입니다.]</b>'
        joinCheck.password = false;
    } else {
        $pwChk.style.borderColor = 'skyblue';
        $pwChk.innerHTML = '<b class="success">[사용가능한 비밀번호입니다.]</b>'
        joinCheck.password = true;
    }

});

const $pwCheckInput = document.getElementById('password_check');
$pwCheckInput.addEventListener('keyup', e => {

    const pwChkValue = $pwCheckInput.value;
    const pwInput = $pwInput.value;
    const $pwChk2 = document.getElementById('pwChk2');

    if(pwChkValue !== pwInput) {
        $pwCheckInput.style.borderColor = 'red';
        $pwChk2.innerHTML = '<b class="warning">[비밀번호가 일치하지 않습니다.]</b>'
        joinCheck.passwordCheck = false;
    } else {
        $pwCheckInput.style.borderColor = 'skyblue';
        $pwChk2.innerHTML = '<b class="success">[비밀번호가 일치합니다.]</b>'
        joinCheck.passwordCheck = true;
    }

});


const $nameInput = document.getElementById('user_name');
$nameInput.addEventListener('keyup', e => {

    // 이름 검사 정규표현식
    const namePattern = /^[가-힣]+$/;
    const nameValue = $nameInput.value;
    const $nameChk = document.getElementById('nameChk');

    if(nameValue.trim() === '') {
        $nameInput.style.borderColor = 'red';
        $nameChk.innerHTML = '<b class="warning">[이름은 필수 값입니다.]</b>'
        joinCheck.name = false;
    } else if(!namePattern.test(nameValue)) {
        $nameInput.style.borderColor = 'red';
        $nameChk.innerHTML = '<b class="warning">[이름은 최대 6글자입니다.]</b>'
        joinCheck.name = false;
    } else {
        $nameInput.style.borderColor = 'skyblue';
        $nameChk.innerHTML = '<b class="success">[사용가능한 이름입니다.]</b>'
        joinCheck.name = true;
    }

});



const $emailInput = document.getElementById('user_email');
$emailInput.addEventListener('keyup', async (e) => {

    // 이메일 검사 정규표현식
    const emailPattern = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;

    const emailValue = $emailInput.value;
    const $emailChk = document.getElementById('emailChk');

    if(emailValue.trim() === '') {

        $emailInput.style.borderColor = 'red';
        $emailChk.innerHTML = '<b class="warning">[이메일은 필수 값입니다.]</b>'
        joinCheck.email = false;
    } else if (!emailPattern.test(emailValue)) {

        $emailInput.style.borderColor = 'red';
        $emailChk.innerHTML = '<b class="warning">[이메일 형식이 아닙니다.]</b>'
        joinCheck.email = false;
    } else {

        await fetchDuplicateCheck('email', emailValue);

        if(duplicateFlag) {
            $emailInput.style.borderColor = 'red';
            $emailChk.innerHTML = '<b class="warning">[이메일이 중복되었습니다. 다른 이메일을 사용하세요.]</b>'
            joinCheck.email = false;

        } else {
            $emailInput.style.borderColor = 'skyblue';
            $emailChk.innerHTML = '<b class="success">[사용가능한 이메일입니다.]</b>'
            joinCheck.email = true;
        }

    }
});

function activeJoinBtn() {
    const $joinBtn = document.getElementById('signup-btn');
    let count = 0;
    for (const key in joinCheck) {
        joinCheck[key] ? count++ : count
    }
    if(count === 5) {
        $joinBtn.style.background = 'black';
        $joinBtn.setAttribute('type', 'submit')
    } else {
        $joinBtn.style.background = 'gray';
        $joinBtn.setAttribute('type', 'button')
    }
    console.log(count)
}
const $form = document.getElementById('signUpForm');
$form.addEventListener('keyup', e => {
    activeJoinBtn();
});