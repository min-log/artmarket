const myfageRight = document.querySelector('.myfage-right')
myfageChatRightAddTag()

function myfageChatRightAddTag() {
  myfageRight.insertAdjacentHTML('afterbegin', `<div class="myfage-right-chat-not-default">
      <div class="myfage-right-chat-not-select">선택된 대화방이 없습니다.</div>
      <div class="myfage-right-chat-not-select-advice">대화방을 선택해주세요!</div>
      <img class="myfage-right-chat-not-select-icon" src="./css/icon/chat-not-select-icon.png"/>
    </div>`)
}