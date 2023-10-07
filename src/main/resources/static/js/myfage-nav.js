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
  <div id="myfage-nav-profile-identity-mod">회원전환 신청하기</div>
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
