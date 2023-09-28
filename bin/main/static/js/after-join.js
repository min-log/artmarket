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
    joinIdentityHover(joinIdentityGeneral, joinGeneralTypeKo, joinGeneralTypeEn, joinGeneralList)
    joinGeneralImg.setAttribute('src', './css/icon/join-general-unchecked.png')
    joinIdentityNotHover(joinIdentityAuthor, joinAuthorTypeKo, joinAuthorTypeEn, joinAuthorList)
    joinAuthorImg.setAttribute('src', './css/icon/join-author-checked.png')
    joinIdentityGeneral.setAttribute('value', 'true')
    joinIdentityAuthor.setAttribute('value', 'false')
})

joinIdentityAuthor.addEventListener('click', function () {
    joinIdentityHover(joinIdentityAuthor, joinAuthorTypeKo, joinAuthorTypeEn, joinAuthorList)
    joinAuthorImg.setAttribute('src', './css/icon/join-author-unchecked.png')
    joinIdentityNotHover(joinIdentityGeneral, joinGeneralTypeKo, joinGeneralTypeEn, joinGeneralList)
    joinGeneralImg.setAttribute('src', './css/icon/join-general-checked.png')
    joinIdentityAuthor.setAttribute('value', 'true')
    joinIdentityGeneral.setAttribute('value', 'false')
})

joinIdentityBtn.addEventListener('click', function () {
    if (joinIdentityAuthor.getAttribute('value') === null && joinIdentityGeneral.getAttribute('value') === null) {
        alert('회원 유형을 선택해주세요.')
    } else {
        joinIdentityGeneral.getAttribute('value') === 'true' ? localStorage.setItem('joinIdentity', 'general') : localStorage.setItem('joinIdentity', 'author')
    }
})