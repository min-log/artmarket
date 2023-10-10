const mainContentBtn = document.querySelector('.main-content-btn')

mainContentBtn.addEventListener('click', function () {
    if (sessionStorage.getItem('id')) {
        alert('로그아웃 이후 가입 가능합니다.')
    } else {
        location.href = 'agree.html'
    }

})

