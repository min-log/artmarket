const joinIdentityGeneral = document.querySelector('#join-identity-general')
const joinGeneralImg = document.querySelector('.join-general-img')
const joinGeneralTypeKo = document.querySelector('.join-general-type-ko')
const joinGeneralTypeEn = document.querySelector('.join-general-type-en')
const joinGeneralList = document.querySelectorAll('.join-general-list')

const joinIdentityAuthor = document.querySelector('#join-identity-author')
const joinAuthorImg = document.querySelector('.join-author-img')
const joinAuthorTypeKo = document.querySelector('.join-author-type-ko')
const joinAuthorTypeEn = document.querySelector('.join-author-type-en')
const joinAuthorList = document.querySelectorAll('.join-author-list')

const joinIdentityBtn = document.querySelector('.join-identity-btn')

const joinIngLine = document.querySelectorAll('.join-ing-line')
const joinIngImg = document.querySelectorAll('.join-ing-img')

const joinNameErrMsg = document.createElement('div')
const joinIdErrMsg = document.createElement('div')
const joinNickNameErrMsg = document.createElement('div')
const joinPassErrMsg = document.createElement('div')
const joinRePassErrMsg = document.createElement('div')
const joinPhoneErrMsg = document.createElement('div')
const joinEmailErrMsg = document.createElement('div')

const joinNameLabel = document.createElement('label')
joinNameLabel.textContent = '이\u00a0\u00a0\u00a0\u00a0\u00a0름'
const joinNameInput = document.createElement('input')
joinNameInput.setAttribute('type', 'text')
joinNameLabel.appendChild(joinNameInput)

const joinIdLabel = document.createElement('label')
joinIdLabel.textContent = '아 이 디'
const joinIdInput = document.createElement('input')
joinIdInput.setAttribute('type', 'text')
joinIdLabel.appendChild(joinIdInput)

const joinNickNameLabel = document.createElement('label')
joinNickNameLabel.textContent = '닉 네 임'
const joinNickNameInput = document.createElement('input')
joinNickNameInput.setAttribute('type', 'text')
joinNickNameLabel.appendChild(joinNickNameInput)

const joinPassLabel = document.createElement('label')
joinPassLabel.textContent = `암\u00a0\u00a0\u00a0\u00a0\u00a0호`
const joinPassInput = document.createElement('input')
joinPassInput.setAttribute('type', 'password')
joinPassLabel.appendChild(joinPassInput)

const joinRePassLabel = document.createElement('label')
joinRePassLabel.textContent = '재 입 력'
const joinRePassInput = document.createElement('input')
joinRePassInput.setAttribute('type', 'password')
joinRePassLabel.appendChild(joinRePassInput)

const joinPhoneLabel = document.createElement('label')
joinPhoneLabel.textContent = '휴 대 폰'
const joinPhoneInput = document.createElement('input')
joinPhoneInput.setAttribute('type', 'phone')
joinPhoneInput.setAttribute('placeholder', '010-1234-5678')
joinPhoneLabel.appendChild(joinPhoneInput)

const joinEmailLabel = document.createElement('label')
joinEmailLabel.textContent = '이 메 일'
const joinEmailInput = document.createElement('input')
joinEmailInput.setAttribute('type', 'email')
joinEmailLabel.appendChild(joinEmailInput)

const joinInfoLabelArray = []
const joinInfoInputArray = []
const joinInfoErrMsgArray = []

joinInfoLabelArray.push(joinNameLabel)
joinInfoLabelArray.push(joinIdLabel)
joinInfoLabelArray.push(joinNickNameLabel)
joinInfoLabelArray.push(joinPassLabel)
joinInfoLabelArray.push(joinRePassLabel)
joinInfoLabelArray.push(joinPhoneLabel)
joinInfoLabelArray.push(joinEmailLabel)

joinInfoInputArray.push(joinNameInput)
joinInfoInputArray.push(joinIdInput)
joinInfoInputArray.push(joinNickNameInput)
joinInfoInputArray.push(joinPassInput)
joinInfoInputArray.push(joinRePassInput)
joinInfoInputArray.push(joinPhoneInput)
joinInfoInputArray.push(joinEmailInput)

const joinErrMsgReg = new Map()
joinErrMsgReg.set(joinInfoInputArray[0], commonNameReg)
joinErrMsgReg.set(joinInfoInputArray[1], commonIdReg)
joinErrMsgReg.set(joinInfoInputArray[2], commonNicknameReg)
joinErrMsgReg.set(joinInfoInputArray[3], commonPassReg)
joinErrMsgReg.set(joinInfoInputArray[4], '')
joinErrMsgReg.set(joinInfoInputArray[5], commonPhoneReg)


joinInfoErrMsgArray.push(joinNameErrMsg)
joinInfoErrMsgArray.push(joinIdErrMsg)
joinInfoErrMsgArray.push(joinNickNameErrMsg)
joinInfoErrMsgArray.push(joinPassErrMsg)
joinInfoErrMsgArray.push(joinRePassErrMsg)
joinInfoErrMsgArray.push(joinPhoneErrMsg)
joinInfoErrMsgArray.push(joinEmailErrMsg)

function joinInfoErrMsgStyle(joinInfoErrMsgTag) {
    joinInfoErrMsgTag.style.marginLeft = '6.5rem'
    joinInfoErrMsgTag.style.fontSize = '0.8rem'
}

for (var i = 0; i < joinInfoErrMsgArray.length; i++) {
    joinInfoErrMsgArray[i].textContent = '값을 입력해주세요.'
    joinInfoErrMsgArray[i].style.visibility = 'hidden'
    joinInfoErrMsgStyle(joinInfoErrMsgArray[i])
}

function joinIdentityHover(joinIdentityBoxTag, joinIdentityListTag1, joinIdentityListTag2, joinIdentityListTag3) {

    joinIdentityBoxTag.style.border = 'solid 0.1rem rgba(41, 45, 50, 1)'
    joinIdentityBoxTag.style.backgroundColor = 'white'
    joinIdentityBoxTag.style.borderRadius = '1rem'
    joinIdentityListTag1.style.color = 'rgba(41, 45, 50, 1)'
    joinIdentityListTag1.style.fontWeight = '800'
    joinIdentityListTag2.style.color = 'rgba(41, 45, 50, 1)'
    joinIdentityListTag2.style.fontWeight = '800'
    for (var i = 0; i < joinIdentityListTag3.length; i++) {
        joinIdentityListTag3[i].style.color = 'rgba(41, 45, 50, 1)'
        joinIdentityListTag3[i].style.fontWeight = '800'
    }
}

function joinIdentityNotHover(joinIdentityBoxTag, joinIdentityListTag1, joinIdentityListTag2, joinIdentityListTag3) {
    joinIdentityBoxTag.style.border = 'none'
    joinIdentityBoxTag.style.backgroundColor = 'rgba(41, 45, 50, 1)'
    joinIdentityBoxTag.style.borderRadius = '1rem'
    joinIdentityListTag1.style.color = 'white'
    joinIdentityListTag1.style.fontWeight = '500'
    joinIdentityListTag2.style.color = 'white'
    joinIdentityListTag2.style.fontWeight = '700'
    for (var i = 0; i < joinIdentityListTag3.length; i++) {
        joinIdentityListTag3[i].style.color = 'white'
        joinIdentityListTag3[i].style.fontWeight = '300'
    }
}

joinIdentityGeneral.addEventListener('mouseover', function () {
    joinIdentityHover(joinIdentityGeneral, joinGeneralTypeKo, joinGeneralTypeEn, joinGeneralList)
    joinGeneralImg.setAttribute('src', './css/icon/join-general-unchecked.png')
})

joinIdentityGeneral.addEventListener('mouseout', function () {
    if (joinIdentityGeneral.getAttribute('value') !== 'true') {
        joinIdentityNotHover(joinIdentityGeneral, joinGeneralTypeKo, joinGeneralTypeEn, joinGeneralList)
        joinGeneralImg.setAttribute('src', './css/icon/join-general-checked.png')
    } else {
        joinIdentityHover(joinIdentityGeneral, joinGeneralTypeKo, joinGeneralTypeEn, joinGeneralList)
        joinGeneralImg.setAttribute('src', './css/icon/join-general-unchecked.png')
    }
})

joinIdentityAuthor.addEventListener('mouseover', function () {
    joinIdentityHover(joinIdentityAuthor, joinAuthorTypeKo, joinAuthorTypeEn, joinAuthorList)
    joinAuthorImg.setAttribute('src', './css/icon/join-author-unchecked.png')
})

joinIdentityAuthor.addEventListener('mouseout', function () {
    if (joinIdentityAuthor.getAttribute('value') !== 'true') {
        joinIdentityNotHover(joinIdentityAuthor, joinAuthorTypeKo, joinAuthorTypeEn, joinAuthorList)
        joinAuthorImg.setAttribute('src', './css/icon/join-author-checked.png')
    } else {
        joinIdentityHover(joinIdentityAuthor, joinAuthorTypeKo, joinAuthorTypeEn, joinAuthorList)
        joinAuthorImg.setAttribute('src', './css/icon/join-author-unchecked.png')
    }
})


joinIdentityGeneral.addEventListener('click', function () {
    joinBtnAfterStyle()
    joinIdentityHover(joinIdentityGeneral, joinGeneralTypeKo, joinGeneralTypeEn, joinGeneralList)
    joinGeneralImg.setAttribute('src', './css/icon/join-general-unchecked.png')
    joinIdentityNotHover(joinIdentityAuthor, joinAuthorTypeKo, joinAuthorTypeEn, joinAuthorList)
    joinAuthorImg.setAttribute('src', './css/icon/join-author-checked.png')
    joinIdentityGeneral.setAttribute('value', 'true')
    joinIdentityAuthor.setAttribute('value', 'false')
})

joinIdentityAuthor.addEventListener('click', function () {
    joinBtnAfterStyle()
    joinIdentityHover(joinIdentityAuthor, joinAuthorTypeKo, joinAuthorTypeEn, joinAuthorList)
    joinAuthorImg.setAttribute('src', './css/icon/join-author-unchecked.png')
    joinIdentityNotHover(joinIdentityGeneral, joinGeneralTypeKo, joinGeneralTypeEn, joinGeneralList)
    joinGeneralImg.setAttribute('src', './css/icon/join-general-checked.png')
    joinIdentityAuthor.setAttribute('value', 'true')
    joinIdentityGeneral.setAttribute('value', 'false')
})

function joinInfoStyle(joinInfoLabelTag, joinInfoInputTag) {
    joinInfoLabelTag.style.margin = '0.2rem '
    joinInfoInputTag.style.marginLeft = '2.5rem'
    joinInfoInputTag.style.padding = '0.6rem 8rem 0.6rem 0.5rem'
    joinInfoInputTag.style.borderRadius = '0.5rem'
    joinInfoInputTag.style.outline = 'none'
    joinInfoInputTag.style.border = 'none'
    joinInfoInputTag.style.border = 'solid 0.1rem rgba(41, 45, 50, 0.3)'
}

for (var i = 0; i < joinInfoLabelArray.length; i++) {
    joinInfoLabelArray[i].appendChild(joinInfoErrMsgArray[i])
}

function joinErrMsgStyle(joinInfoInputTag, joinInfoErrTag) {

    joinInfoInputTag.style.border = 'solid 0.1rem red'
    joinInfoErrTag.style.color = 'red'
    joinInfoErrTag.style.visibility = 'visible'

    if (joinInfoInputTag.value === '') {
        joinInfoErrTag.textContent = '값을 입력해주세요.'
        joinInfoInputTag.setAttribute('name', 0)
    } else if (joinErrMsgReg.get(joinInfoInputTag).test(joinInfoInputTag.value) === false) {
        joinInfoErrTag.textContent = '형식에 맞지 않지 않습니다.'
        joinInfoInputTag.setAttribute('name', 0)
    } else if (joinErrMsgReg.get(joinInfoInputTag).test(joinInfoInputTag.value)) {
        joinInfoInputTag.style.border = 'solid 0.1rem rgba(40, 154, 0, 0.72)'
        joinInfoErrTag.style.color = 'rgba(40, 154, 0, 0.72)'
        joinInfoErrTag.style.visibility = 'hidden'
        joinInfoInputTag.setAttribute('name', 1)
    }

}

joinInfoInputArray[0].addEventListener('focusout', function () {
    joinErrMsgStyle(joinInfoInputArray[0], joinInfoErrMsgArray[0])
})

joinInfoInputArray[1].addEventListener('focusout', function () {
    joinErrMsgStyle(joinInfoInputArray[1], joinInfoErrMsgArray[1])
})

joinInfoInputArray[2].addEventListener('focusout', function () {
    joinErrMsgStyle(joinInfoInputArray[2], joinInfoErrMsgArray[2])
})

joinInfoInputArray[3].addEventListener('focusout', function () {
    joinErrMsgStyle(joinInfoInputArray[3], joinInfoErrMsgArray[3])
})

// 비밀번호 재입력
joinInfoInputArray[4].addEventListener('focusout', function () {
    if (joinInfoInputArray[3].value !== joinInfoInputArray[4].value) {
        joinInfoInputArray[4].style.border = 'solid 0.1rem red'
        joinInfoErrMsgArray[4].style.color = 'red'
        joinInfoErrMsgArray[4].style.visibility = 'visible'
        joinInfoErrMsgArray[4].textContent = '비밀번호와 다릅니다.'
        joinInfoInputArray[4].setAttribute('name', 0)
    } else if (joinInfoInputArray[3].value === joinInfoInputArray[4].value) {
        joinInfoInputArray[4].style.border = 'solid 0.1rem rgba(40, 154, 0, 0.72)'
        joinInfoErrMsgArray[4].style.color = 'rgba(40, 154, 0, 0.72)'
        joinInfoErrMsgArray[4].style.visibility = 'hidden'
        joinInfoInputArray[4].setAttribute('name', 1)
    }
})

joinInfoInputArray[5].addEventListener('focusout', function () {
    joinErrMsgStyle(joinInfoInputArray[5], joinInfoErrMsgArray[5])
})

// 이메일
// joinInfoInputArray[6]

function joinBtnAfterStyle() {
    joinIdentityBtn.style.color = 'white'
    joinIdentityBtn.style.fontWeight = '700'
    joinIdentityBtn.style.backgroundColor = 'rgba(41, 45, 50, 1)'
    joinIdentityBtn.style.cursor = 'pointer'
}

function joinBtnBeforeStyle() {
    joinIdentityBtn.style.border = 'none'
    joinIdentityBtn.style.border = 'solid 0.1rem rgba(41, 45, 50, 1)'
    joinIdentityBtn.style.borderRadius = '0.5rem'
    joinIdentityBtn.style.backgroundColor = 'white'
    joinIdentityBtn.style.color = 'rgba(41, 45, 50, 1)'
    joinIdentityBtn.style.cursor = 'not-allowed'
}

function joinInfoSubmit() {
    let submit = 0
    for (var i = 0; i < joinInfoInputArray.length - 1; i++) {
        submit += joinInfoInputArray[i].getAttribute('name')
    }
    return submit
}

joinIdentityBtn.addEventListener('click', function () {
    if (joinIdentityAuthor.getAttribute('value') === null && joinIdentityGeneral.getAttribute('value') === null) {
        alert('회원 유형을 선택해주세요.')
    } else if (joinIdentityAuthor.getAttribute('value') !== null && joinIdentityGeneral.getAttribute('value') !== null) {
        joinBtnBeforeStyle()
        joinIdentityGeneral.getAttribute('value') === 'true' ? localStorage.setItem('joinIdentity', 'general') : localStorage.setItem('joinIdentity', 'author')
        joinIdentityGeneral.style.display = 'none'
        joinIdentityAuthor.style.display = 'none'
        const afterJoinIdentityTitleListMain = document.querySelector('.after-join-identity-title-list-main')
        afterJoinIdentityTitleListMain.textContent = '회원정보 입력'

        const afterJoinIdentityTitleListSub = document.querySelector('.after-join-identity-title-list-sub')
        afterJoinIdentityTitleListSub.textContent = '항목에 맞는 가입정보를 입력해주세요.'
        afterJoinIdentityTitleListSub.style.marginBottom = '3rem'

        joinIdentityBtn.setAttribute('value', '회원가입')
        joinIdentityBtn.style.fontSize = '1rem'
        joinIdentityBtn.style.marginTop = '1rem'

        joinIngLine[0].style.borderBottom = 'solid 0.11rem rgba(41, 45, 50, 1)'
        joinIngImg[0].style.animationName = 'none'

        joinIngImg[1].style.animationDuration = '1.5s'
        joinIngImg[1].style.animationName = 'ingImg'
        joinIngImg[1].style.animationIterationCount = 'infinite'
        joinIngImg[1].style.opacity = '1'

        const afterJoinIdentity = document.querySelector('.after-join-identity')

        for (var i = 0; i < joinInfoLabelArray.length; i++) {
            afterJoinIdentity.appendChild(joinInfoLabelArray[i])
            joinInfoStyle(joinInfoLabelArray[i], joinInfoInputArray[i])
        }

        afterJoinIdentity.style.display = 'flex'
        afterJoinIdentity.style.flexDirection = 'column'

        joinIdentityBtn.addEventListener('click', function () {
            console.log(joinInfoSubmit())
        })
    }
})