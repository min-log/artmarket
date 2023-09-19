const loginId = document.getElementById('loginId')
const loginPassword = document.getElementById('loginPassword')
const loginSub = document.querySelector('.login-sub')
const loginBtn = document.querySelector(".login-btn")
const loginIdError = document.createElement('div')
const loginPassError = document.createElement('div')
const loginToJoin = document.querySelector('.login-info-join')
const logoAuto = document.querySelector('.login-auto')

let btnModCheckId = false;
let btnModCheckPass = false;
let loginAutoResult = false;

loginToJoin.addEventListener('mouseover', function () {
    loginToJoin.textContent = "회원가입 하러가기"
})

loginToJoin.addEventListener('mouseout', function () {
    loginToJoin.textContent = "아직 회원이 아니신가요?"
})

loginToJoin.addEventListener('click', function () {
    location.href = "join.html"
})

logoAuto.addEventListener('click', function () {
    loginAutoResult = !(loginAutoResult)
})

function setErrorCss(el) {
    el.style.color = 'red'
    el.style.fontSize = '0.7rem'
    el.style.visibility = 'hidden'
    el.style.padding = '0'
}

setErrorCss(loginIdError)
setErrorCss(loginPassError)

loginId.after(loginIdError)
loginPassword.after(loginPassError)

const btnModCheckList = []
btnModCheckList.push(btnModCheckId)
btnModCheckList.push(btnModCheckPass)


function loginErrorMsgOnOff(check, msg, tag) {

    if ((check.getAttribute('name') === 'check')) {
        tag.textContent = msg
    } else if (msg === '') {
        tag.style.visibility = 'hidden'
    } else {
        tag.style.visibility = 'visible'
        tag.textContent = msg
        check.setAttribute('name', 'check')
    }
}

function btnModCheck() {
    if (btnModCheckList[0] && btnModCheckList[1]) {
        loginBtn.style.backgroundColor = 'rgb(94, 94, 235)'
        loginBtn.style.cursor = 'pointer'
    } else {
        loginBtn.style.backgroundColor = 'rgba(94, 94, 235, 0.4)';
        loginBtn.style.cursor = 'not-allowed';
    }
}

function loginErrorMsgMod(inTag, regTag, errMsgTag, inTagStatus) {

    const btnCheckIndex = (inTagStatus === '아이디' ? 0 : 1)
    let btnCheckResult = false

    if (inTag.value === '') {
        loginErrorMsgOnOff(inTag, `${inTagStatus} 값을 입력해주세요.`, errMsgTag)
    } else if (regTag.test(inTag.value) === false) {
        loginErrorMsgOnOff(inTag, `${inTagStatus} 형식에 맞지 않습니다.`, errMsgTag)
    } else if (regTag.test(inTag.value) === true) {
        loginErrorMsgOnOff(inTag, '', errMsgTag)
        btnCheckResult = true;
    }

    btnModCheckList[btnCheckIndex] = btnCheckResult
}

loginId.addEventListener('focusout', function () {
    loginErrorMsgMod(loginId, commonIdReg, loginIdError, '아이디')
    btnModCheck()
})

loginPassword.addEventListener('focusout', function () {
    loginErrorMsgMod(loginPassword, commonPassReg, loginPassError, '비밀번호')
    btnModCheck()
})


loginBtn.addEventListener("click", function () {

    console.log(typeof loginId.value)
    console.log(typeof loginPassword.value)
    console.log(typeof loginAutoResult)
    if (btnModCheckList[0] && btnModCheckList[1]) {
        fetch("/login", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json; charset=UTF-8'
            },
            body: JSON.stringify({
                loginId: `${loginId.value}`,
                loginPassword: `${loginPassword.value}`,
                autoLogin: `${loginAutoResult}`,
            }),
        }).then(res => console.log(res))
            .catch(error => console.log(error))
    }
})
