/*------------------------태그 추가------------------------- */

const nav = document.querySelector('nav')

const navMenu = document.createElement('div')
navMenu.setAttribute("class", "nav-menu")

const login = document.createElement('div')
login.textContent = 'LOGIN'
login.setAttribute("class", "login")

const myfage = document.createElement('div')
myfage.textContent = 'MYFAGE'
myfage.setAttribute("class", "myfage")

const alram = document.createElement('div')
alram.textContent = 'ALRAM'
alram.setAttribute("class", "alram")

const home = document.createElement('div')
home.textContent = 'HOME'
home.setAttribute("class", "home")

const center = document.createElement('div')
center.textContent = 'CENTER'
center.setAttribute("class", "center")

navMenu.appendChild(login)
navMenu.appendChild(myfage)
navMenu.appendChild(alram)
navMenu.appendChild(center)
navMenu.appendChild(home)

const navLogo = document.createElement('div')
navLogo.setAttribute("class", "nav-logo")

const navLogoImg = document.createElement('img')
navLogoImg.setAttribute("src", "./css/img/ARTMARKET.png")

navLogo.appendChild(navLogoImg)

const navCategory = document.createElement('div')
navCategory.setAttribute("class", "nav-category")

const illust = document.createElement('div')
illust.setAttribute("class", "illust")
illust.textContent = "ILLUST"

const live = document.createElement('div')
live.setAttribute("class", "live")
live.textContent = "LIVE2D•3D"

const character = document.createElement('div')
character.setAttribute("class", "character")
character.textContent = "CHARACTER"

const design = document.createElement('div')
design.setAttribute("class", "design")
design.textContent = "DESIGN"

const video = document.createElement('div')
video.setAttribute("class", "video")
video.textContent = "VIDEO"

navCategory.appendChild(illust)
navCategory.appendChild(live)
navCategory.appendChild(character)
navCategory.appendChild(design)
navCategory.appendChild(video)

nav.appendChild(navLogo)
nav.appendChild(navCategory)
nav.appendChild(navMenu)

/*------------------------이동------------------------- */

function notAcessMyfage() {
    Swal.fire({
        text: '로그인 후 접근 가능한 페이지입니다.',
        icon: 'error'
    })
    setTimeout(function () {
        location.href = "login.html"
    }, 2000)
}

navLogo.addEventListener("click", function () {
    location.href = "index.html"
})

myfage.addEventListener("click", function () {
    if (sessionStorage.getItem('memberId') === null) {
        notAcessMyfage()
    } else {
        location.href = "myfage.html"
    }
})

home.addEventListener("click", function () {
    location.href = "index.html"
})

login.addEventListener("click", function () {
    location.href = "login.html"
})