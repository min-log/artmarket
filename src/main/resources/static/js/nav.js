/*------------------------태그 추가------------------------- */

const nav = document.querySelector('nav')

nav.insertAdjacentHTML('beforeend', `
<div class="nav-logo">
    <img src="./css/img/ARTMARKET.png">
</div>
<div class="nav-category">
    <div class="illust">ILLUST</div>
    <div class="live">LIVE2D•3D</div>
    <div class="character">CHARACTER</div>
    <div class="design">DESIGN</div>
    <div class="video">VIDEO</div>
</div>
<div class="nav-menu">
    <div class="login">LOGIN</div>
    <div class="myfage">MYPAGE</div>
    <div class="alram">ALRAM</div>
    <div class="home">HOME</div>
    <div class="login-profile">
        <img class="login-profile-img" />
        <div class="login-profile-intro">
        </div>
    </div>
</div>`)


const illust = document.querySelector('.illust')

illust.addEventListener('click', function () {
    location.href = 'category.html'
    sessionStorage.setItem('selectcategory', 'illust')
})


const live = document.querySelector('.live')
live.addEventListener('click', function () {
    location.href = 'category.html'
    sessionStorage.setItem('selectcategory', 'live')
})


const character = document.querySelector('.character')

character.addEventListener('click', function () {
    location.href = 'category.html'
    sessionStorage.setItem('selectcategory', 'character')
})


const design = document.querySelector('.design')

design.addEventListener('click', function () {
    location.href = 'category.html'
    sessionStorage.setItem('selectcategory', 'design')
})


const video = document.querySelector('.video')
video.addEventListener('click', function () {
    location.href = 'category.html'
    sessionStorage.setItem('selectcategory', 'video')
})


/*------------------------이동------------------------- */

// 여기서 변경된 부분입니다.
function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
}

const loginType = getCookie('loginType'); // 쿠키에서 loginType 값을 가져오는 함수

if (loginType === 'SOCIAL') {
    const loginTrueId = getCookie('loginTrueId');
    const loginTrueName = getCookie('loginTrueName');
    const loginTrueIdentity = getCookie('loginTrueIdentity');
    const loginId = getCookie('loginId');

    if (loginTrueId && loginTrueName && loginTrueIdentity && loginId) {
        sessionStorage.setItem('id', loginTrueId);
        sessionStorage.setItem('identity', loginTrueIdentity);
        sessionStorage.setItem('loginId', loginId);

        if (loginTrueIdentity == 'GENERAL') {
            sessionStorage.setItem('login-profile-img', './css/icon/login-general.png')
        } else {
            sessionStorage.setItem('login-profile-img', './css/icon/login-author.png')
        }
        sessionStorage.setItem('login-profile-intro', `${loginTrueName}님, 어서오세요`)
        sessionStorage.setItem('name', loginTrueName)
// 쿠키 삭제
        document.cookie = 'loginTrueId=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
        document.cookie = 'loginTrueName=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
        document.cookie = 'loginTrueIdentity=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
        document.cookie = 'loginId=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
    }
}
const navLogo = document.querySelector('.nav-logo')
navLogo.addEventListener("click", function () {
    location.href = "index.html"
})


const myfage = document.querySelector('.myfage')
const alram = document.querySelector('.alram')

if (sessionStorage.getItem('id') === null) {
    myfage.style.display = 'none'
    alram.style.display = 'none'
} else {
    myfage.addEventListener('click', function () {
        location.href = 'myfage.html'
    })
}

const loginProfile = document.querySelector('.login-profile')
const loginProfileImg = document.querySelector('.login-profile-img')
const loginProfileIntro = document.querySelector('.login-profile-intro')
const navCategory = document.querySelector('.nav-category')


if (sessionStorage.getItem('id') === null) {
    loginProfile.style.display = 'none'
    nav.style.padding = '0.4rem 1rem'
    nav.style.margin = '0.5rem'
    navCategory.style.paddingRight = '10rem'
} else {
    nav.style.padding = '0.4rem 1rem'
    nav.style.marginBottom = '0.3rem'
    loginProfile.style.display = 'flex'
    navCategory.style.paddingLeft = '7.5rem'

    loginProfileImg.setAttribute('src', sessionStorage.getItem('login-profile-img'))
    loginProfileIntro.textContent = sessionStorage.getItem('login-profile-intro')
}

const home = document.querySelector('.home')
home.addEventListener("click", function () {
    location.href = "index.html"
})
const login = document.querySelector('.login')

if (sessionStorage.getItem("id") !== null) {
    login.textContent = 'LOGOUT'
    login.setAttribute("class", "logout")
} else {
    login.textContent = 'LOGIN'
    login.setAttribute("class", "login")
}

if (login) {
    login.addEventListener('click', function () {
        location.href = 'login.html'
    })
}
const logout = document.querySelector('.logout')
if (logout) {
    logout.addEventListener("click", function () {
        if (sessionStorage.getItem("id") !== null) {
            sessionStorage.removeItem("id")
            sessionStorage.removeItem("identity")
            sessionStorage.removeItem('login-profile-img')
            sessionStorage.removeItem('login-profile-intro')
            sessionStorage.removeItem('loginId')
            sessionStorage.removeItem('name')
            sessionStorage.removeItem('intro')
            sessionStorage.removeItem('nickname')
            document.cookie = 'loginType=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
            login.textContent = 'LOGIN'
            alert('로그아웃 되었습니다.')
            location.href = 'index.html'
        } else {
            location.href = "login.html"
        }
    })
}

