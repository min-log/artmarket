const mainContentBtn = document.querySelector('.main-content-btn')

mainContentBtn.addEventListener('click', function () {
    location.href = 'agree.html'
})

if (localStorage.getItem('email')) {
    localStorage.removeItem('email')
}

if (localStorage.getItem('joinIdentity')) {
    localStorage.removeItem('joinIdentity')
}