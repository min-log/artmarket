// myfageInfoMidAddTag()

function myfageInfoMidAddTag() {
    if (myfageMid.childNodes) {
        const childNodesArray = Array.from(myfageMid.childNodes);
        for (const myfageMidTag of childNodesArray) {
            myfageMidTag.remove();
        }
    }

    myfageMid.insertAdjacentHTML('beforeend', `<div class="myfage-info-mid-content">
    <div class="myfage-info-mid-content-normal-title">기본 정보</div>
    <div class="myfage-info-mid-content-normal-content">
      <div class="myfage-info-mid-content-normal-content-top">
        <div class="myfage-info-mid-content-normal-nickname">
          <div
            class="myfage-info-mid-content-normal-nickname-label"
          >이름</div>
          <input
            class="myfage-info-mid-content-normal-nickname-value" placeholder="${sessionStorage.getItem('name')}" disabled />
        </div>
        <div class="myfage-info-mid-content-normal-id">
          <div class="myfage-info-mid-content-normal-id-label">아이디</div>
          <input class="myfage-info-mid-content-normal-id-value" placeholder="${sessionStorage.getItem('loginId')}" disabled />
        </div>
      </div>
      <div class="myfage-info-mid-content-normal-content-top-caution">
        <img src="./css/icon/mypage-caution.png" />이름, 아이디, 닉네임은 고유값으로 변경이
        불가능합니다.
      </div>
      <div class="myfage-info-mid-content-normal-content-mid">
        <div class="myfage-info-mid-content-normal-before-password">
          <div
            class="myfage-info-mid-content-normal-before-password-label"
          >기존 비밀번호</div>
          <input
            class="myfage-info-mid-content-normal-before-password-value" type="password"
          />
        </div>
        <div class="myfage-info-mid-content-normal-new-password">
          <div
            class="myfage-info-mid-content-normal-new-password-label"
          >새로운 비밀번호</div>
          <input
            class="myfage-info-mid-content-normal-new-password-value" type="password"
          />
        </div>
        <input
          type="button"
          class="myfage-info-pass-token"
          value="비밀번호 인증하기"
        />
      </div>
      <div class="myfage-info-mid-content-normal-content-caution">
        <img src="./css/icon/mypage-caution.png" class="myfage-info-pass-caution-img"/><div class="myfage-info-pass-caution-msg">기존 비밀번호 입력 후 비밀번호 변경 가능합니다.</div>
      </div>
      <div class="myfage-info-mid-content-normal-content-bot">
        <div class="myfage-info-mid-content-normal-phone">
          <div class="myfage-info-mid-content-normal-phone-label">휴대폰 번호</div>
          <input type="tel" class="myfage-info-mid-content-normal-phone-value" />
        </div>
        <div class="myfage-info-mid-content-normal-email">
          <div class="myfage-info-mid-content-normal-email-label">이메일 주소</div>
          <input type="email" class="myfage-info-mid-content-normal-email-value" >
        </div>
        <input
          type="button"
          class="myfage-info-email-token"
          value="이메일로 인증하기"
        />
      </div>
      <div class="myfage-info-mid-content-normal-content-caution">
        <img src="./css/icon/mypage-caution.png" class="myfage-info-email-caution-img"/><div class="myfage-info-email-caution-msg">이메일 인증 후 변경이 가능합니다.</div>
      </div>
    </div>
    <div class="myfage-info-mid-content-account-title">계좌 정보</div>
    <div class="myfage-info-mid-content-account-content">
      <div class="myfage-info-mid-content-account-box">
        <div class="myfage-info-mid-content-account-bank">
          <div class="myfage-info-mid-content-account-bank-label">
            은행명
          </div>
          <input class="myfage-info-mid-content-account-bank-value" disabled />
        </div>
        <div class="myfage-info-mid-content-account-bank-num">
          <div class="myfage-info-mid-content-account-bank-num-label">
            계좌번호
          </div>
          <input
            class="myfage-info-mid-content-account-bank-num-value" disabled
          />
        </div>
      </div>
      <div class="myfage-info-mid-content-account-caution">
        <div class="myfage-info-mid-content-account-cautio-list">
          <img src="./css/icon/mypage-caution.png" />
            계좌 정보는 아트마켓에서 검수 후 등록해드리고 있습니다.
        </div>
        <div class="myfage-info-mid-content-account-cautio-list">
          artmarket@gmail.com 으로 아이디, 계좌사본을 보내주세요.
        </div>
        <div class="myfage-info-mid-content-account-cautio-list">
          가입 시 입력한 이름과 계좌주명이 다를 경우 등록이 불가합니다.
        </div>
      </div>
    </div>
  </div>`)

    const myfageInfoMidContentNormalPhoneValue = document.querySelector('.myfage-info-mid-content-normal-phone-value')
    const myfageInfoMidContentNormalEmailValue = document.querySelector('.myfage-info-mid-content-normal-email-value')
    const myfageInfoMidContentAccountBankValue = document.querySelector('.myfage-info-mid-content-account-bank-value')
    const myfageInfoMidContentAccountBankNumValue = document.querySelector('.myfage-info-mid-content-account-bank-num-value')

    function placeholderNone(selectTag) {
        selectTag.setAttribute('placeholder', '')
    }

    function placeholderDone(selectTag) {
        selectTag.setAttribute('placeholder', `${selectTag.getAttribute('name')}`)
    }

    myfageInfoMidContentNormalPhoneValue.addEventListener('click', function () {

        myfageInfoMidContentNormalPhoneValue.disabled = false
        myfageInfoMidContentNormalPhoneValue.style.backgroundColor = ''

        placeholderNone(myfageInfoMidContentNormalPhoneValue)

    })

    myfageInfoMidContentNormalPhoneValue.addEventListener('mouseleave', function () {

        if (myfageInfoMidContentNormalPhoneValue.value !== '') {
            myfageInfoMidContentNormalEmailValue.disabled = true
            myfageInfoMidContentNormalEmailValue.style.backgroundColor = 'rgba(41, 45, 50, 0.2)'
        } else {
            myfageInfoMidContentNormalEmailValue.disabled = false
            myfageInfoMidContentNormalEmailValue.style.backgroundColor = 'white'
        }

        setTimeout(function () {
            placeholderDone(myfageInfoMidContentNormalPhoneValue)
        }, 300)
    })

    myfageInfoMidContentNormalEmailValue.addEventListener('click', function () {

        myfageInfoMidContentNormalEmailValue.style.backgroundColor = 'white'
        myfageInfoMidContentNormalEmailValue.disabled = false

        placeholderNone(myfageInfoMidContentNormalEmailValue)

    })

    myfageInfoMidContentNormalEmailValue.addEventListener('mouseleave', function () {

        if (myfageInfoMidContentNormalEmailValue.value !== '') {
            myfageInfoMidContentNormalPhoneValue.disabled = true
            myfageInfoMidContentNormalPhoneValue.style.backgroundColor = 'rgba(41, 45, 50, 0.2)'
        } else {
            myfageInfoMidContentNormalPhoneValue.disabled = false
            myfageInfoMidContentNormalPhoneValue.style.backgroundColor = 'white'
        }

        setTimeout(function () {
            placeholderDone(myfageInfoMidContentNormalEmailValue)
        }, 300)

    })


    fetch(`/mypage-info/${sessionStorage.getItem('id')}`, {
        method: 'GET',
        headers: {
            'content-type': 'applicaiton/json'
        }
    }).then(response => {
        return response.json()
    }).then(data => {
        myfageInfoMidContentNormalPhoneValue.setAttribute('placeholder', `${data.phone}`)
        myfageInfoMidContentNormalPhoneValue.setAttribute('name', `${data.phone}`)
        myfageInfoMidContentNormalEmailValue.setAttribute('placeholder', `${data.email}`)
        myfageInfoMidContentNormalEmailValue.setAttribute('name', `${data.email}`)

        if (data.bank !== null) {
            myfageInfoMidContentAccountBankValue.setAttribute('placeholder', `${data.bank}`)
        }

        if (data.account !== null) {
            myfageInfoMidContentAccountBankNumValue.setAttribute('placeholder', `${data.account}`)
        }
    })

    const myfageInfoPassToken = document.querySelector('.myfage-info-pass-token')

    // ---------------------
    myfageInfoPassToken.addEventListener('click', function () {

        let afterModPass
        const myfageInfoMidContentNormalBeforePasswordValue = document.querySelector('.myfage-info-mid-content-normal-before-password-value')
        const myfageInfoMidContentNormalNewPasswordValue = document.querySelector('.myfage-info-mid-content-normal-new-password-value')
        const myfageInfoPassCautionMsg = document.querySelector('.myfage-info-pass-caution-msg')
        const myfageInfoPassCautionImg = document.querySelector('.myfage-info-pass-caution-img')

        function myfagePassConfirm(beforePassVal, afterPassVal, cautionMsgTag, cautionImgTag) {

            let checkPassValue = false

            if (beforePassVal.value == afterPassVal.value) {
                cautionMsgTag.textContent = '동일한 비밀번호로 변경 불가능합니다.'
                cautionMsgTag.style.color = 'red'
                cautionImgTag.setAttribute('src', './css/icon/mypage-caution-r.png')
                cautionImgTag.style.opacity = '0.7'

            } else if (!commonPassReg.test(beforePassVal.value) || !commonPassReg.test(afterPassVal.value)) {

                cautionMsgTag.style.color = 'red'
                cautionImgTag.setAttribute('src', './css/icon/mypage-caution-r.png')
                cautionImgTag.style.opacity = '0.7'

                if (beforePassVal.value === '' || afterPassVal.value === '') {
                    cautionMsgTag.textContent = '값을 입력해주세요.'
                } else {
                    cautionMsgTag.textContent = '형식에 맞지 않는 비밀번호입니다.'
                }
            } else {
                checkPassValue = true
                cautionMsgTag.textContent = '통과! 처리중에 있습니다.'
                afterModPass = afterPassVal.value
                cautionMsgTag.style.color = 'green'
                cautionImgTag.setAttribute('src', './css/icon/mypage-caution-g.png')
                cautionImgTag.style.opacity = '0.7'
            }
            return checkPassValue
        }

        const postCheckBol = myfagePassConfirm(myfageInfoMidContentNormalBeforePasswordValue, myfageInfoMidContentNormalNewPasswordValue, myfageInfoPassCautionMsg, myfageInfoPassCautionImg)

        if (postCheckBol) {
            fetch(`/mypage-info/mod-password`, {
                method: 'POST',
                headers: {
                    'content-type': 'application/json'
                },
                body: JSON.stringify({
                    modPassId: `${sessionStorage.getItem('id')}`,
                    modBeforePass: `${myfageInfoMidContentNormalBeforePasswordValue.value}`
                })
            }).then(response => {
                if (response.status === 204) {
                    fetch(`/mypage-info/mod-password`, {
                        method: 'PATCH',
                        headers: {
                            'content-type': 'application/json'
                        },
                        body: JSON.stringify({
                            newPassId: `${sessionStorage.getItem('id')}`,
                            newPassword: `${afterModPass}`
                        })
                    }).then(response => {
                        if (response.status === 204) {
                            myfageInfoPassCautionMsg.textContent = '정상적으로 변경되었습니다.'
                            alert('비밀번호 변경이 완료되었습니다.')
                        }
                    })
                } else if (response.status === 401) {
                    alert('입력하신 기존 비밀번호가 일치하지 않습니다.')
                }
            })
        }
    })
    // ---------------------

    const myfageInfoEmailToken = document.querySelector('.myfage-info-email-token')
    const myfageInfoEmailCautionMsg = document.querySelector('.myfage-info-email-caution-msg')
    const myfageInfoEmailCautionImg = document.querySelector('.myfage-info-email-caution-img')

    let sendModObject = new Object()

    function myfageEmailConfirm(myfagePhonVal, myfageEmailVal, cautionMsgTag, cautionImgTag) {

        let checkValueType = ''

        if (myfagePhonVal.value === '' && myfageEmailVal.value === '') {
            cautionMsgTag.textContent = '값을 입력해주세요.'
            cautionMsgTag.style.color = 'red'
            cautionImgTag.setAttribute('src', './css/icon/mypage-caution-r.png')
            cautionImgTag.style.opacity = '0.7'

        } else if (myfagePhonVal.value !== '' && !commonPhoneReg.test(myfagePhonVal.value)) {

            cautionMsgTag.textContent = '형식에 맞지 않습니다.'
            cautionMsgTag.style.color = 'red'
            cautionImgTag.setAttribute('src', './css/icon/mypage-caution-r.png')
            cautionImgTag.style.opacity = '0.7'

        } else if (myfageEmailVal.value !== '' && !commonEmailReg.test(myfageEmailVal.value)) {

            cautionMsgTag.textContent = '형식에 맞지 않습니다.'
            cautionMsgTag.style.color = 'red'
            cautionImgTag.setAttribute('src', './css/icon/mypage-caution-r.png')
            cautionImgTag.style.opacity = '0.7'

        } else {

            cautionMsgTag.textContent = '통과! 기존 이메일로 인증번호 발송중에 있습니다.'
            cautionMsgTag.style.color = 'green'
            cautionImgTag.setAttribute('src', './css/icon/mypage-caution-g.png')
            cautionImgTag.style.opacity = '0.7'

            if (myfagePhonVal.value !== '') {
                checkValueType = 'phone'
                sendModObject.modPhoneId = `${sessionStorage.getItem('id')}`
                sendModObject.newPhone = `${myfagePhonVal.value}`

            } else if (myfageEmailVal.value !== '') {
                checkValueType = 'email'
                sendModObject.modEmailId = `${sessionStorage.getItem(`id`)}`
                sendModObject.newEmail = `${myfageEmailVal.value}`
            }

        }
        return checkValueType
    }

    // -------------- 인증번호 토큰

    const myfageInfoMidContentNormalContentBot = document.querySelector('.myfage-info-mid-content-normal-content-bot')

    function myfageTokenConfirm(tokenTypeTag, cautionMsgTag, cautionImgTag) {

        let checkTokenValue = false

        if (tokenTypeTag.value === '') {
            cautionMsgTag.textContent = '인증번호를 입력해주세요.'
            cautionMsgTag.style.color = 'red'
            cautionImgTag.setAttribute('src', './css/icon/mypage-caution-r.png')
            cautionImgTag.style.opacity = '0.7'

        } else if (!commonTokenReg.test(tokenTypeTag.value)) {
            cautionMsgTag.textContent = '형식에 맞지 않습니다.'
            cautionMsgTag.style.color = 'red'
            cautionImgTag.setAttribute('src', './css/icon/mypage-caution-r.png')
            cautionImgTag.style.opacity = '0.7'

        } else {
            checkTokenValue = true
            cautionMsgTag.textContent = '통과! 인증번호 검수중에 있습니다.'
            cautionMsgTag.style.color = 'green'
            cautionImgTag.setAttribute('src', './css/icon/mypage-caution-g.png')
            cautionImgTag.style.opacity = '0.7'

        }

        return checkTokenValue
    }


    // --------------

    myfageInfoEmailToken.addEventListener('click', function () {

        let myfageModType = myfageEmailConfirm(myfageInfoMidContentNormalPhoneValue, myfageInfoMidContentNormalEmailValue, myfageInfoEmailCautionMsg, myfageInfoEmailCautionImg)

        if (myfageModType !== '') {


            let patchObjectType = myfageModType

            fetch(`/mypage-info/mod-phone`, {
                method: 'POST',
                headers: {
                    'content-type': 'application/json'
                },
                body: JSON.stringify({
                    modMemberId: `${sessionStorage.getItem('id')}`
                })
            }).then(response => {
                if (response.status === 204) {

                    myfageInfoEmailCautionMsg.textContent = '기존 이메일로 발송된 인증번호를 확인해주세요.'

                    myfageInfoMidContentNormalContentBot.insertAdjacentHTML('afterend', `
          <div class="myfage-info-mid-content-normal-content-bot-token">
            <label>인증번호<input type="${myfageModType}" class="myfage-info-mid-content-normal-content-bot-token-value"></label>
            <input type="button" value="인증번호 보내기" class="myfage-info-mid-content-normal-content-bot-token-btn" />
          </div>
          `)

                    const myfageInfoMidContentNormalContentBotToken = document.querySelector('.myfage-info-mid-content-normal-content-bot-token')
                    const myfageInfoMidContentNormalContentBotTokenValue = document.querySelector('.myfage-info-mid-content-normal-content-bot-token-value')
                    const myfageInfoMidContentNormalContentBotTokenBtn = document.querySelector('.myfage-info-mid-content-normal-content-bot-token-btn')


                    myfageInfoMidContentNormalContentBotTokenBtn.addEventListener('click', function () {

                        let myfageInfoModCheck = myfageTokenConfirm(myfageInfoMidContentNormalContentBotTokenValue, myfageInfoEmailCautionMsg, myfageInfoEmailCautionImg)

                        if (myfageInfoModCheck) {
                            fetch(`/mypage-info/mod-${patchObjectType}`, {
                                method: 'PATCH',
                                headers: {
                                    'content-type': 'application/json'
                                },
                                body: JSON.stringify(sendModObject)
                            }).then(response => {
                                if (response.status === 204) {
                                    myfageInfoMidContentNormalContentBotToken.remove()
                                    myfageInfoEmailCautionMsg.textContent = '변경 완료되었습니다.'
                                    alert('변경이 완료되었습니다!')
                                }
                            })
                        }
                        // if ----
                    })
                    // if ----
                }
                // if --- 204
            })
            // fetch ---

        }

        // if --- ''

    })
    // click event ---

}
