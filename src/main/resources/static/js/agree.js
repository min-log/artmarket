// alert 창 append ParentTag
const agreeCotent = document.querySelector('.agree-content')

const allAgreeTitle = document.querySelector('.all-agree')
allAgreeTitle.setAttribute('name', "off")

const AgreeList1 = document.querySelector('.agree-first')
const AgreeList2 = document.querySelector('.agree-second')
const AgreeList3 = document.querySelector('.agree-third')

const AgreeBtn = document.querySelector('.agree-btn')

const allAgreeListArray = []
allAgreeListArray.push(AgreeList1)
allAgreeListArray.push(AgreeList2)
allAgreeListArray.push(AgreeList3)

// alert 창의 약관보기 태그
const agreeShow1 = document.querySelector('.agree-show1')
const agreeShow2 = document.querySelector('.agree-show2')
const agreeBox1 = document.querySelector('.agree-box1')
const agreeBox2 = document.querySelector('.agree-box2')
const agreeShowClose1 = document.querySelector('.agree-show-close1')
const agreeShowClose2 = document.querySelector('.agree-show-close2')

function agreeShowMove(agreeShowTextTag) {
    agreeShowTextTag.style.display = 'block'
    agreeShowTextTag.style.marginTop = '-20rem'
    agreeShowTextTag.style.marginLeft = '-0.9rem'
}

agreeShow1.addEventListener('click', function () {
    agreeShowMove(agreeBox1)
})

agreeShowClose1.addEventListener('click', function () {
    agreeBox1.style.display = 'none'
})

agreeShow2.addEventListener('click', function () {
    agreeShowMove(agreeBox2)
})

agreeShowClose2.addEventListener('click', function () {
    agreeBox2.style.display = 'none'
})

allAgreeTitle.addEventListener('click', function () {

    let agreeOnOff = allAgreeTitle.getAttribute("name") === "off" ? true : false
    for (var i = 0; i < allAgreeListArray.length; i++) {
        allAgreeListArray[i].checked = agreeOnOff
    }

    if (agreeOnOff) {
        allAgreeTitle.setAttribute('name', 'on')
    } else {
        allAgreeTitle.setAttribute('name', 'off')
    }

    agreeBtnOnOff()
})

function allAgreeOnOff(agreeTag) {
    if (agreeTag.checked === false) {
        allAgreeTitle.checked = false;
        allAgreeTitle.setAttribute('name', 'off')
    } else {
        if (AgreeList1.checked && AgreeList2.checked && AgreeList3.checked) {
            allAgreeTitle.checked = true;
            allAgreeTitle.setAttribute('name', 'on')
        }
    }
    agreeBtnOnOff()
}

allAgreeListArray[0].addEventListener('click', function () {
    allAgreeOnOff(allAgreeListArray[0])
})

allAgreeListArray[1].addEventListener('click', function () {
    allAgreeOnOff(allAgreeListArray[1])
})

allAgreeListArray[2].addEventListener('click', function () {
    allAgreeOnOff(allAgreeListArray[2])
})

function agreeBtnOnOff() {
    if (allAgreeTitle.checked === true) {
        AgreeBtn.style.backgroundColor = 'rgba(27, 27, 27, 1)'
        AgreeBtn.style.cursor = 'pointer'
    } else {
        AgreeBtn.style.backgroundColor = 'rgba(27, 27, 27, 0.4)'
        AgreeBtn.style.cursor = 'not-allowed'
    }
}

// div style 설정
function agreeDivStyle(divTag) {
    divTag.style.borderRadius = '0.3rem'
    divTag.style.display = 'flex'
    divTag.style.justifyContent = 'center'
    divTag.style.width = '283px'
    divTag.style.marginLeft = '0.3rem'
    divTag.style.fontSize = '0.9rem'
    divTag.style.cursor = 'pointer'
    divTag.style.marginBottom = '0.9rem'
}

// 소셜 or 사이트 회원가입 alert 변수 생성
const socialOrSiteBox = document.createElement('div')
socialOrSiteBox.setAttribute('class', 'social-or-site-box')

const socialGoogleBox = document.createElement('img')
socialGoogleBox.setAttribute('class', 'social-google-box')

const socialKaKaoBox = document.createElement('img')
socialKaKaoBox.setAttribute('class', 'social-kakao-box')

const siteBox = document.createElement('div')
siteBox.setAttribute('class', 'site-box')

const laterJoin = document.createElement('div')

// 소셜 or 사이트 회원가입 alert 창 구현
function socialOrSite(parentTag) {

    socialOrSiteBox.style.position = 'absolute'
    socialOrSiteBox.style.display = 'flex'
    socialOrSiteBox.style.justifyContent = 'center'
    socialOrSiteBox.style.flexDirection = 'column'
    socialOrSiteBox.style.padding = '3rem'
    socialOrSiteBox.style.boxShadow = '2px 2px 0.5rem rgba(88, 88, 88, 0.15)'
    socialOrSiteBox.style.borderRadius = '1rem'
    socialOrSiteBox.style.backgroundColor = 'rgba(255, 255, 255, 1)'

    socialGoogleBox.setAttribute("src", "./css/icon/googleJoin.png")
    socialGoogleBox.style.cursor = 'pointer'
    socialGoogleBox.style.marginTop = '0.7rem'
    socialGoogleBox.style.marginBottom = '0.5rem'

    socialKaKaoBox.setAttribute("src", "./css/icon/kakaoJoin.png")
    socialKaKaoBox.style.cursor = 'pointer'
    socialKaKaoBox.style.marginBottom = '0.5rem'

    siteBox.textContent = '아트마켓 회원가입'
    siteBox.style.backgroundColor = 'rgba(52, 52, 52, 1)'
    siteBox.style.color = 'white'
    siteBox.style.padding = '0.7rem 0.5rem'

    agreeDivStyle(siteBox)
    agreeDivStyle(laterJoin)

    laterJoin.textContent = '닫기'
    laterJoin.style.width = '5rem'
    laterJoin.style.color = 'rgba(52, 52, 52, 1)'
    laterJoin.style.margin = '0 auto'
    laterJoin.style.marginTop = '0.7rem'
    laterJoin.style.border = 'solid 0.1rem rgba(52, 52, 52, 0.7)'
    laterJoin.style.borderRadius = '0.8rem'


    socialOrSiteBox.appendChild(socialGoogleBox)
    socialOrSiteBox.appendChild(socialKaKaoBox)
    socialOrSiteBox.appendChild(siteBox)
    socialOrSiteBox.appendChild(laterJoin)

    parentTag.appendChild(socialOrSiteBox)

    laterJoin.addEventListener('click', function () {
        AgreeBtn.style.backgroundColor = 'rgba(27, 27, 27, 0.4)'
        AgreeBtn.style.cursor = 'not-allowed'
        socialOrSiteBox.remove()
    })
}

AgreeBtn.addEventListener('click', function () {
    if (allAgreeTitle.checked === true) {
        allAgreeTitle.setAttribute('name', 'off')
        allAgreeTitle.checked = false;
        for (var i = 0; i < allAgreeListArray.length; i++) {
            allAgreeListArray[i].checked = false;
        }

        const agreeCotent = document.querySelector('.agree-content')
        socialOrSite(agreeCotent)
    }
})

// 백에서 아직 소셜 구현 x
socialGoogleBox.addEventListener('click', function () {

})

const kakaoCode = 'b78977a50a13ce81576485688bc20490'
const kakaoRedirect = 'http://localhost:8070/kakao-login'

const kakaoRequestUrl = `https://kauth.kakao.com/oauth/authorize
?client_id=${kakaoCode}
&redirect_uri=${kakaoRedirect}
&response_type=code`

// 카카오 가입
socialKaKaoBox.addEventListener('click', function () {
    location.href = `${kakaoRequestUrl}`;
});

// URL에서 코드 추출 및 요청 보내기
const urlParams = new URLSearchParams(window.location.search);
const receivedCode = urlParams.get('code');

if (receivedCode) {
    const url = `http://localhost:8070/kakao-login?code=${receivedCode}`;
    const requestOptions = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        }
    };

    fetch(url, requestOptions)
        .then(response => response.json())
        .then(data => {
            console.log(data);
            // 여기서 프론트엔드에서 필요한 작업을 수행하세요.
        })
        .catch(error => console.error('Error:', error));
} else {
    console.error("코드가 없습니다.");
}

// 자사 가입
siteBox.addEventListener('click', function () {
    socialOrSiteBox.remove()
    emailConfirmAlert(agreeCotent)
})


// 이메일 인증 alert 창 변수 생성
const emailConfirmBox = document.createElement('div')
emailConfirmBox.setAttribute('id', 'email')
const emailConfirmTitle = document.createElement('div')
const emailConfirmInput = document.createElement('input')
emailConfirmInput.setAttribute('class', 'email-confirm-input')

const emailConfirmBtn = document.createElement('input')
const emailConfirmLater = document.createElement('div')


// 이메일 인증 alert 창 구현
function emailConfirmAlert(parentTag) {

    emailConfirmBox.style.position = 'absolute'
    emailConfirmBox.style.display = 'flex'
    emailConfirmBox.style.fontWeight = 'bold'
    emailConfirmBox.style.justifyContent = 'center'
    emailConfirmBox.style.alignItems = 'center'
    emailConfirmBox.style.flexDirection = 'column'
    emailConfirmBox.style.marginLeft = '-2rem'
    emailConfirmBox.style.padding = '5rem 5rem 3rem 5rem'
    emailConfirmBox.style.boxShadow = '2px 2px 0.5rem rgba(88, 88, 88, 0.15)'
    emailConfirmBox.style.borderRadius = '1rem'
    emailConfirmBox.style.backgroundColor = 'white'

    emailConfirmTitle.textContent = '인증 받으실 이메일 주소를 입력해주세요.'
    emailConfirmTitle.style.marginBottom = '1rem'

    emailConfirmInput.setAttribute('type', 'text')
    emailConfirmInput.style.padding = '0.6rem 0.5rem'
    emailConfirmInput.style.borderRadius = '0.5rem'
    emailConfirmInput.style.border = 'none'
    emailConfirmInput.style.width = '17rem'
    emailConfirmInput.style.border = 'solid 0.07rem rgba(52, 52, 52, 0.2)'
    emailConfirmInput.style.outline = 'none'
    emailConfirmInput.style.marginBottom = '0.8rem'

    emailConfirmBtn.setAttribute('type', 'button')
    emailConfirmBtn.style.backgroundColor = 'rgba(31, 31, 31, 0.4)'
    emailConfirmBtn.style.border = 'none'
    emailConfirmBtn.style.color = 'white'
    emailConfirmBtn.style.padding = '0.7rem'
    emailConfirmBtn.style.borderRadius = '0.5rem'
    emailConfirmBtn.style.width = '18rem'
    emailConfirmBtn.style.cursor = 'not-allowed'
    emailConfirmBtn.style.fontWeight = 'bold'
    emailConfirmBtn.style.fontSize = '0.9rem'
    emailConfirmBtn.style.marginBottom = '1.5rem'
    emailConfirmBtn.setAttribute('value', '인증번호 받기')


    emailConfirmLater.textContent = '닫기'
    emailConfirmLater.style.fontSize = '0.8rem'
    emailConfirmLater.style.fontWeight = '200'
    emailConfirmLater.style.display = 'flex'
    emailConfirmLater.style.justifyContent = 'center'
    emailConfirmLater.style.width = '4rem'
    emailConfirmLater.style.color = 'rgba(52, 52, 52, 1)'
    emailConfirmLater.style.margin = '0 auto'
    emailConfirmLater.style.cursor = 'pointer'
    emailConfirmLater.style.border = 'solid 0.1rem rgba(52, 52, 52, 0.7)'
    emailConfirmLater.style.borderRadius = '0.7rem'

    emailConfirmBox.appendChild(emailConfirmTitle)
    emailConfirmBox.appendChild(emailConfirmInput)
    emailConfirmBox.appendChild(emailConfirmBtn)
    emailConfirmBox.appendChild(emailConfirmLater)

    parentTag.appendChild(emailConfirmBox)

    const emailConfirmError = document.createElement('div')
    emailConfirmError.style.color = 'red'
    emailConfirmError.style.fontWeight = '300'
    emailConfirmError.style.fontSize = '0.8rem'
    emailConfirmTitle.append(emailConfirmError)
    emailConfirmError.style.display = 'hidden'


    emailConfirmLater.addEventListener('click', function () {
        AgreeBtn.style.backgroundColor = 'rgba(27, 27, 27, 0.4)'
        AgreeBtn.style.cursor = 'not-allowed'
        emailConfirmBox.remove()
    })


    function comfirmErrMsg(confrimIdValue) {

        let errConfirmTag

        if (confrimIdValue === 'email') {
            errConfirmTag = '이메일'
        } else {
            errConfirmTag = '인증번호'
        }

        if (emailConfirmInput.value === '') {
            emailConfirmError.textContent = `${errConfirmTag}을 입력해주세요.`
            emailConfirmError.style.display = 'block'
        } else if (commonEmailReg.test(emailConfirmInput.value) === false) {
            emailConfirmError.textContent = `올바른 ${errConfirmTag} 형식이 아닙니다.`
            emailConfirmError.style.display = 'block'
        } else if (confrimIdValue == '409') {
            emailConfirmError.textContent = `이미 가입된 이메일입니다.`
            emailConfirmError.style.display = 'block'
        } else {
            emailConfirmError.textContent = null
            emailConfirmError.style.display = 'hidden'
            emailConfirmBtn.style.backgroundColor = 'rgba(31, 31, 31, 1)'
            emailConfirmBtn.style.cursor = 'pointer'
        }

    }

    function tokenComfirmAlert(statusCodeValue) {

        sessionStorage.setItem("email", `${emailConfirmInput.value}`)
        emailConfirmInput.value = null

        if (typeof statusCodeValue === 'number') {
            emailConfirmBox.setAttribute('id', 'token')
            emailConfirmTitle.textContent = '이메일로 전송 된 인증번호를 입력해주세요.'
            emailConfirmBtn.setAttribute('value', '인증하기')
        } else {
            alert(`${statusCodeValue}`)
        }
    }

    function tokentConfirm() {

        fetch(`${baseUrl}/join-confirm/${emailConfirmInput.value}`, {
            method: 'GET',
            headers: {
                'content-type': 'application/json'
            }
        }).then(response => {
            if (response.status === 200) {
                location.href = 'join.html'
            } else {
                alert('인증에 실패하였습니다. 이전 화면으로 넘어갑니다.')
                setTimeout(function () {
                    location.href = 'agree.html'
                }, 1000)
            }
        })
    }

    function emailConfirm() {
        fetch(`${baseUrl}/join-confirm`, {
            method: 'POST',
            headers: {
                'content-type': 'application/json'
            },
            body: JSON.stringify({
                confirmEmail: `${emailConfirmInput.value}`
            })
        }).then(response => {
            if (response.status === 201) {
                tokenComfirmAlert(response.status)
            } else if (response.status === 409) {
                comfirmErrMsg(response.status)
                emailConfirmInput.value = ''
            } else {
                tokenComfirmAlert('알수 없는 오류로 메일 발송에 실패했습니다.')
            }
        })
    }


    emailConfirmInput.addEventListener('focusout', function () {
        comfirmErrMsg(emailConfirmBox.getAttribute('id'))
    })

    emailConfirmBtn.addEventListener('click', function () {

        if (emailConfirmBox.getAttribute('id') === 'email') {
            emailConfirm()

            emailConfirmBtn.insertAdjacentHTML('afterend', `
            <div class="email-confirm-wait">
            <div><img class="email-confirm-wait-img" src="./css/icon/send-email.png" /></div>
            <div>이메일 전송중에 있습니다.</div>
            <div class="email-confirm-wait-2">잠시만 기다려주세요.</div>
            </div>
            `)

            const emailConfirmWait = document.querySelector('.email-confirm-wait')
            emailConfirmWait.style.display = 'flex'
            emailConfirmWait.style.position = 'absolute'
            emailConfirmWait.style.flexDirection = 'column'
            emailConfirmWait.style.alignItems = 'center'
            emailConfirmWait.style.padding = '3rem 5rem'
            emailConfirmWait.style.backgroundColor = 'white'
            emailConfirmWait.style.boxShadow = '2px 2px 0.5rem rgba(88, 88, 88, 0.3)'
            emailConfirmWait.style.borderRadius = '1rem'
            emailConfirmWait.style.marginTop = '-0.1rem'

            const emailConfirmWaitSecond = document.querySelector('.email-confirm-wait-2')
            emailConfirmWaitSecond.style.fontSize = '0.9rem'

            setTimeout(function () {
                emailConfirmWait.remove()
            }, 4000)

        } else {
            tokentConfirm()
        }

    })
}

fadeIn(agreeCotent)


