document.getElementById('myfage-nav-profile-identity-mod').addEventListener('click', function () {
    console.log('Clicked');
    const id = sessionStorage.getItem('id');

    fetch('http://61.97.189.178:8070/mypage-update-identity', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        mode: 'no-cors',
        body: JSON.stringify({'id': id})
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            alert('작가 회원으로 전환되었습니다.');
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
            alert('작가 회원 전환에 실패했습니다.');
        });
});
