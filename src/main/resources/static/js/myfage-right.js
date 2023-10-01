const myfageRightChatBoxBotSendAttach = document.querySelector('.myfage-right-chat-box-bot-send-attach')

const myfageChatAttachInputTag = document.createElement('input')
myfageChatAttachInputTag.setAttribute('type', 'file')
myfageChatAttachInputTag.style.display = 'none'

const myfageRight = document.querySelector('.myfage-right')

function myfageChatRightAddTag(){
    myfageRight.insertAdjacentHTML('afterend',`
    <div class="myfage-right-chat-not-default">
    <div class="myfage-right-chat-not-select">선택된 대화방이 없습니다.</div>
    <div class="myfage-right-chat-not-select-advice">대화방을 선택해주세요!</div>
    <img class="myfage-right-chat-not-select-icon" src="./css/icon/chat-not-select-icon.png"/>
    </div>`)
}

const myfageRightChatBox = document.querySelector('.myfage-right-chat-box')

function myfageChatListClick(){
    myfageRightChatBox.insertAdjacentHTML('afterend',`
    <div class="myfage-right-chat-box">
    <div class="myfage-right-chat-box-top">
    <div class="myfage-right-chat-box-top-profile">
      <div class="myafge-right-chat-box-top-nickname">웽웽옹</div>
      <div class="myafge-right-chat-box-top-show-article">
        작품 보러가기
      </div>
    </div>
    <input
      class="myafge-right-chat-box-top-show-exit"
      type="button"
      value="X"
    />
  </div>
  <div class="myfage-right-chat-box-mid">
    <div class="myfage-right-chat-box-mid-msg-me">
      <div class="myfage-right-chat-box-mid-msg-me-time">
        2023.09.25 12:26
      </div>
      <div class="myfage-right-chat-box-mid-msg-me-text">
        다음에 다시 문의드리겠습니다.
      </div>
    </div>
    <div class="myfage-right-chat-box-mid-msg-other">
      <div class="myfage-right-chat-box-mid-msg-other-time">
        2023.09.25 12:28
      </div>
      <div class="myfage-right-chat-box-mid-msg-other-text">
        네 알겠습니다.
      </div>
    </div>
  </div>
  <div class="myfage-right-chat-box-bot">
    <input class="myfage-right-chat-box-bot-send-text" type="text" />
    <img
      class="myfage-right-chat-box-bot-send-attach"
      src="./css/icon/chat-attach.png"
    />
    <img
      class="myfage-right-chat-box-bot-send-btn"
      src="./css/icon/chat-send-btn.png"
    />
  </div>
  </div>`)
}

myfageChatRightAddTag()

myfageNavChat.addEventListener('click', function () {
    myfageChatRightAddTag()
})