const myfageNav = document.querySelector('.myfage-nav')

myfageNav.insertAdjacentHTML('afterend', `<div class="myfage-nav-content">
<div class="myfage-nav-profile">
  <div class="myfage-nav-profile-img">
    <img class="myfage-nav-profile-img-tag" src="" />
  </div>
  <div class="myfage-nav-profile-identity"></div>
  <div class="myfage-nav-profile-greeting"></div>
</div>
<div class="myfage-nav-profile-menu">
  <div id="myfage-nav-profile-img-mod">프로필이미지 수정하기</div>
  <div id="myfage-nav-profile-intro-mod">소개글 수정하기</div>
  <div id="myfage-nav-profile-identity-mod">작가로 전환하기</div>
</div>
<div class="myfage-nav-menu">
  <div class="myfage-nave-menu-list" id="myfage-chat">
    <img src="./css/icon/nav-chat.png" />
    <div>채팅 목록</div>
  </div>
  <div class="myfage-nave-menu-list" id="myfage-info">
    <img src="./css/icon/nav-info.png" />
    <div>회원 정보</div>
  </div>
  <div class="myfage-nave-menu-list" id="myfage-order">
    <img src="./css/icon/nav-order.png" />
    <div>주문 관리</div>
  </div>
  <div class="myfage-nave-menu-list" id="myfage-article">
    <img src="./css/icon/nav-article.png" />
    <div>작품 관리</div>
  </div>
  <div class="myfage-nave-menu-list" id="myfage-exit">
    <img src="./css/icon/nav-exit.png" />
    <div>회원 탈퇴</div>
  </div>
</div>
</div>
<div>
<img class="myfage-nav-img" src="./css/img/myfage-nav-img.png" />
</div>`)

const myfageNavProfileIdentity = document.querySelector('.myfage-nav-profile-identity')
myfageNavProfileIdentity.textContent = sessionStorage.getItem('identity') === 'GENERAL' ? '일반 회원' : '작가 회원'

function myfageNavSet() {
    const myfageNavProfileGreeting = document.querySelector('.myfage-nav-profile-greeting')
    const myfageNavProfileImgTag = document.querySelector('.myfage-nav-profile-img-tag')

    fetch(`/myfage/${sessionStorage.getItem('id')}`, {
        method: 'GET',
        headers: {
            'content-type': 'application/json'
        }
    }).then(response => {
        resStatusCode = response.status
        return response.json()
    }).then(data => {
        if (resStatusCode === 200) {
            myfageNavProfileGreeting.textContent = `안녕하세요. ${data.nickname}님`
            sessionStorage.setItem('nickname', `${data.nickname}`)
            sessionStorage.setItem('intro', `${data.intro}`)
            if (data.profileImg === null) {
                myfageNavProfileImgTag.setAttribute('src', `./css/icon/default-profile-img.png`)
            } else {
                myfageNavProfileImgTag.setAttribute('src', `${baseUrl}/${data.profileImg}`)
            }
        }
    })
}

myfageNavSet()

const myfageNavChat = document.querySelector('#myfage-chat')
const myfageNavInfo = document.querySelector('#myfage-info')
const myfageNavOrder = document.querySelector('#myfage-order')
const myfageNavArticle = document.querySelector('#myfage-article')
const myfageNavExit = document.querySelector('#myfage-exit')

if (sessionStorage.getItem('identity') === 'GENERAL') {
    myfageNavArticle.style.display = 'none'
}
// 소개글 수정하기
const introModButton = document.querySelector('#myfage-nav-profile-intro-mod');

introModButton.addEventListener('click', function () {
    const introPopup = document.createElement('div');
    introPopup.classList.add('intro-popup');

    const inputField = document.createElement('textarea'); // textarea로 변경
    inputField.classList.add('intro-input');
    inputField.placeholder = '새로운 소개글을 입력하세요 (최대 20자)'; // 최대 길이 추가

// 텍스트 입력 필드 스타일을 적용합니다.
    inputField.style.width = '100%';
    inputField.style.height = '120px'; // 20px * 6줄 = 120px
    inputField.style.borderRadius = '5px';

    const saveButton = document.createElement('button');
    saveButton.textContent = '변경';
    saveButton.classList.add('save-button');

    saveButton.addEventListener('click', function () {
        const newIntro = inputField.value;
        if (newIntro.trim() !== '') {
            if (newIntro.length > 20) {
                alert('소개글은 최대 20자까지 입력 가능합니다.');
                return;
            }
            const id = sessionStorage.getItem('id');
            const url = 'http://localhost:8070/mypage-update-intro';
            const data = {
                id: id,
                memberIntro: newIntro
            };
            fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            })
                .then(response => {
                    if (response.ok) {
                        console.log('Success:', response);
                        alert('소개글이 업데이트 되었습니다.');
                        introPopup.remove();
                    } else {
                        console.error('Error:', response);
                        alert('소개글 업데이트에 실패했습니다.');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('소개글 업데이트에 실패했습니다.');
                });
        }
    });

    introPopup.appendChild(inputField);
    introPopup.appendChild(saveButton);

    // 버튼의 위치 정보를 가져와서 팝업 위치를 조정합니다.
    const buttonRect = introModButton.getBoundingClientRect();
    introPopup.style.position = 'absolute';
    introPopup.style.top = `${buttonRect.top}px`;
    introPopup.style.left = `${buttonRect.right + 5}px`;

    document.body.appendChild(introPopup);
});
//

//작가 회원으로 전환 및 작가회원일 때 숨기기
const myfageNavIdentityMod = document.querySelector('#myfage-nav-profile-identity-mod')

myfageNavIdentityMod.addEventListener('click', function () {
    const id = sessionStorage.getItem('id');

    const confirmation = confirm('작가 회원으로 전환하시겠습니까?');
    if (!confirmation) return; // 사용자가 취소를 누르면 함수 종료

    fetch('http://localhost:8070/mypage-update-identity', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({id: id})
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            sessionStorage.setItem('identity', 'AUTHOR');
            sessionStorage.setItem('login-profile-img', './css/icon/login-author.png');
            alert('작가 회원으로 전환되었습니다.');
            location.reload(); // 페이지 새로고침
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
            alert('작가 회원 전환에 실패했습니다.');
        });
});
const myfageNavProfileIdentityMod = document.querySelector('#myfage-nav-profile-identity-mod');
if (sessionStorage.getItem('identity') === 'AUTHOR') {
    myfageNavProfileIdentityMod.style.display = 'none';
}
//
