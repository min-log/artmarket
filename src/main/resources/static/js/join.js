// const joinIdentityGeneral = document.querySelector('#join-identity-general')
// const joinIdentityAuthor = document.querySelector('#join-identity-author')

// const joinGeneralChecked = document.querySelector('.join-general-checked')
// const joinAuthorChecked = document.querySelector('.join-author-checked')

// joinGeneralChecked.setAttribute('value', '1')
// joinAuthorChecked.setAttribute('value', '0')

// joinIdentityGeneral.addEventListener('click', function () {
//     modIdentityCheck(joinGeneralChecked)
//     modIdentityCheck(joinAuthorChecked)
// })

// joinIdentityAuthor.addEventListener('click', function () {
//     modIdentityCheck(joinAuthorChecked)
//     modIdentityCheck(joinGeneralChecked)
// })

// function modIdentityCheck(identityTag) {

//     const imgSrc = identityTag.getAttribute("value") === "1" ? 'non-checked' : 'checked'
//     identityTag.setAttribute('src', `css/icon/${imgSrc}.png`)

//     if (imgSrc === 'non-checked') {
//         identityTag.setAttribute('value', '0')
//     } else {
//         identityTag.setAttribute('value', '1')
//     }
// }


// 정규식을 위한 tag -search
const joinListName = document.querySelector('.join-list-name')
const joinName = document.querySelector('.join-name')

const joinListId = document.querySelector('.join-list-id')
const joinId = document.querySelector('.join-id')

const joinListNickname = document.querySelector('.join-list-nickname')
const joinNickname = document.querySelector('.join-nickname')

const joinListPassword = document.querySelector('.join-list-password')
const joinPassword = document.querySelector('.join-password')

const joinListRepassword = document.querySelector('.join-list-repassword')
const joinRepassword = document.querySelector('.join-repassword')

const joinListPhone = document.querySelector('.join-list-phone')
const joinPhone = document.querySelector('.join-phone')

const joinListEmail = document.querySelector('.join-list-email')
const joinEmail = document.querySelector('.join-email')

// 에러 메세지 스타일링
function joinErrMsgStyle(errMsgTag) {
    errMsgTag.textContent = '값을 입력해주세요.'
    errMsgTag.style.position = 'absolute'
    errMsgTag.style.marginTop = '1.7rem'
    errMsgTag.style.marginLeft = '4rem'
    errMsgTag.style.fontSize = '0.7rem'
    errMsgTag.style.color = 'red'
    errMsgTag.style.display = 'none'
}

// 에러 메세지 변수 생성
const nameErrMsg = document.createElement('div')
nameErrMsg.setAttribute("class", "name-err-msg")
joinErrMsgStyle(nameErrMsg)
joinListName.appendChild(nameErrMsg)

const idErrMsg = document.createElement('span')
idErrMsg.setAttribute("class", "id-err-msg")
joinErrMsgStyle(idErrMsg)
joinListId.appendChild(idErrMsg)

const nicknameErrMsg = document.createElement('span')
nicknameErrMsg.setAttribute("class", "nickname-err-msg")
joinErrMsgStyle(nicknameErrMsg)
nicknameErrMsg.style.marginLeft = '4.2rem'
joinListNickname.appendChild(nicknameErrMsg)

const passwordErrMsg = document.createElement('div')
passwordErrMsg.setAttribute("class", "password-err-msg")
joinErrMsgStyle(passwordErrMsg)
joinListPassword.appendChild(passwordErrMsg)

const repasswordErrMsg = document.createElement('div')
repasswordErrMsg.setAttribute("class", "repassword-err-msg")
joinErrMsgStyle(repasswordErrMsg)
repasswordErrMsg.style.marginLeft = '4.2rem'
joinListRepassword.appendChild(repasswordErrMsg)

const phoneErrMsg = document.createElement('div')
phoneErrMsg.setAttribute("class", "phone-err-msg")
joinErrMsgStyle(phoneErrMsg)
joinListPhone.appendChild(phoneErrMsg)

const joinTagErrMap = new Map()
joinTagErrMap.set(joinName, nameErrMsg)
joinTagErrMap.set(joinId, idErrMsg)
joinTagErrMap.set(joinNickname, nicknameErrMsg)
joinTagErrMap.set(joinPassword, passwordErrMsg)
joinTagErrMap.set(joinRepassword, repasswordErrMsg)
joinTagErrMap.set(joinPhone, phoneErrMsg)

const joinTagReg = new Map()
joinTagReg.set(joinName, commonNameReg)
joinTagReg.set(joinId, commonIdReg)
joinTagReg.set(joinNickname, commonNicknameReg)
joinTagReg.set(joinPassword, commonPassReg)
joinTagReg.set(joinPhone, commonPhoneReg)

function joinErrMsgShow(joinInputTag) {

    const joinErrTag = joinTagErrMap.get(joinInputTag)
    joinErrTag.style.display = 'block'
    joinInputTag.setAttribute("name", 0)


    if (joinInputTag.value === '') {
        joinErrTag.textContent = '값을 입력해주세요.'

    } else if ((joinTagReg.get(joinInputTag)).test(joinInputTag.value) === false) {
        joinErrTag.textContent = '잘못된 형식입니다.'

    } else {
        joinErrTag.style.display = 'none'
        joinInputTag.setAttribute("name", 1)
    }
}

joinName.addEventListener('focusout', function () {
    joinErrMsgShow(joinName)
    jointBtnMod()
})

joinId.addEventListener('focusout', function () {
    joinErrMsgShow(joinId)
    jointBtnMod()
})

joinNickname.addEventListener('focusout', function () {
    joinErrMsgShow(joinNickname)
    jointBtnMod()
})

joinPassword.addEventListener('focusout', function () {
    joinErrMsgShow(joinPassword)
    jointBtnMod()
})

joinRepassword.addEventListener('focusout', function () {

    joinTagErrMap.get(joinRepassword).style.display = 'block'
    joinRepassword.setAttribute("name", 0)

    if (joinPassword.value === joinRepassword.value && joinRepassword.value !== '') {
        joinTagErrMap.get(joinRepassword).style.display = 'none'
        joinRepassword.setAttribute("name", 1)
    } else if (joinPassword.value !== joinRepassword.value && joinRepassword.value !== '') {
        joinTagErrMap.get(joinRepassword).textContent = '입력한 비밀번호와 다릅니다.'
    } else if (joinRepassword.value === '') {
        joinTagErrMap.get(joinRepassword).textContent = '값을 입력해주세요.'
    }

    jointBtnMod()
})

joinPhone.addEventListener('focusout', function () {
    joinErrMsgShow(joinPhone)
    jointBtnMod()
})


// input 태그 List
const joinInputList = []
joinInputList.push(joinName)
joinInputList.push(joinId)
joinInputList.push(joinNickname)
joinInputList.push(joinPassword)
joinInputList.push(joinRepassword)
joinInputList.push(joinPhone)

const joinBtn = document.querySelector('.join-btn')

function jointBtnMod() {
    let joinBtnMod = 0

    for (var i = 0; i < joinInputList.length; i++) {
        joinBtnMod += Number(joinInputList[i].getAttribute('name'))
        console.log(joinBtnMod)
    }

    if (joinBtnMod === 6) {
        joinBtn.style.backgroundColor = 'rgba(88, 88, 88, 1)'
        joinBtn.style.cursor = 'pointer'
    }
}

joinBtn.addEventListener('click', function () {

})