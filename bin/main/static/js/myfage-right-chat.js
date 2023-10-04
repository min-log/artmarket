const myfageRight = document.querySelector('.myfage-right')
myfageChatRightAddTag()

// ------------------------------ myfage-right-chat ------------------------------
function myfageChatRightAddTag() {
    myfageRight.insertAdjacentHTML('afterbegin', `<div class="myfage-right-chat-not-default">
      <div class="myfage-right-chat-not-select">선택된 대화방이 없습니다.</div>
      <div class="myfage-right-chat-not-select-advice">대화방을 선택해주세요!</div>
      <img class="myfage-right-chat-not-select-icon" src="./css/icon/chat-not-select-icon.png"/>
    </div>`)
}

function myfageChatListClick(chatRoomId, chatProudct) {

    const myfageRightChatNotDefault = document.querySelector('.myfage-right-chat-not-default')
    myfageRightChatNotDefault.remove()

    myfageRight.insertAdjacentHTML('beforeend', `
    <div class="myfage-right-chat-box" id="${chatRoomId}">
    <div class="myfage-right-chat-box-top">
    <div class="myfage-right-chat-box-top-profile">
      <div class="myafge-right-chat-box-top-nickname"></div>
      <div class="myafge-right-chat-box-top-show-article" id="${chatProudct}">
        작품 보러가기
      </div>
    </div>
    <input
      class="myafge-right-chat-box-top-show-exit"
      type="button"
      value="X"
    />
  </div>
  <div class="myfage-right-chat-box-mid"></div>
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

    // 채팅방 파일 핸들링
    const myfageRightChatBoxBotSendAttach = document.querySelector('.myfage-right-chat-box-bot-send-attach')

    const myfageChatAttachInputTag = document.createElement('input')
    myfageChatAttachInputTag.setAttribute('type', 'file')
    myfageChatAttachInputTag.style.display = 'none'


    myfageRightChatBoxBotSendAttach.addEventListener('click', function () {
        myfageChatAttachInputTag.click()
    })

    // 채팅 타입에 따른 함수(TALK)
    const myfageRightChatBoxMid = document.querySelector('.myfage-right-chat-box-mid')

    function myfageRightChatTalk(chatWhoTag, chatMsg, chatDate) {
        myfageRightChatBoxMid.insertAdjacentHTML('beforeend', `
  <div class="myfage-right-chat-box-mid-msg-${chatWhoTag}">
    <div class="myfage-right-chat-box-mid-msg-${chatWhoTag}-time">${chatDate}</div>
    <div class="myfage-right-chat-box-mid-msg-${chatWhoTag}-text">${chatMsg}</div>
  </div>`)
    }

    // 채팅 타입에 따른 함수(FILE)
    function myfageRightChatFile(chatWhoTag, chatFile, chatFileName, chatFileDownload) {

        let fileExtention = chatFileName.split('.')

        myfageRightChatBoxMid.insertAdjacentHTML('beforeend', `
  <div class="myfage-right-chat-box-mid-msg-${chatWhoTag}"></div>`)

        const myfageRightChatBoxMidMsg = document.querySelector(`.myfage-right-chat-box-mid-msg-${chatWhoTag}`)

        if (fileExtention[fileExtention.length - 1] === 'txt') {
            myfageRightChatBoxMidMsg.insertAdjacentHTML('beforeend', `
      <a href="${chatFile}" download="${chatFileDownload}">
    `)
        } else {
            myfageRightChatBoxMidMsg.insertAdjacentHTML('beforeend', `
      <img src="${baseUrl}${chatFile}">
      <a href="${baseUrl}${chatFile}" download="${chatFileDownload}">
    `)
        }
    }

    // 채팅 타입에 따른 함수(ENTER, LEAVE)
    function myfageRightChatEnterLeave(chatWhoTag, chatWhoType, chatEnterLeaveMsg) {
        myfageRightChatBoxMid.insertAdjacentHTML('beforeend', `
  <div class="myfage-right-chat-box-mid-${chatWhoTag}">${chatWhoType}이 ${chatEnterLeaveMsg}하셨습니다.</div>`)
    }

    const myfageChatLists = document.querySelectorAll('.myfage-chat-list')
    for (const myfageChatList of myfageChatLists) {

        let chatListStatus
        myfageChatList.addEventListener('click', function () {
            fetch(`${baseUrl}/myfage`, {
                method: 'POST',
                headers: {
                    'content-type': 'applicaiton/json'
                },
                body: JSON.stringify({
                    clickChatId: `${myfageChatList.getAttribute('id')}`,
                    clickMember: `${localStorage.getItem('id')}`
                })
            }).then(response => {
                chatListStatus = response.status
                return response.json()
            }).then(data => {
                if (chatListStatus === 201) {
                    const myafgeRightChatBoxTopNickname = document.querySelector('.myafge-right-chat-box-top-nickname')
                    myafgeRightChatBoxTopNickname.textContent = myfageChatList.getAttribute('name')

                    myfageChatListClick(data.chatRoomId, data.chatProudct)

                    const chatClickLists = data.chatList
                    let chatWhoTag
                    let chatWhoType

                    for (chatClickList of chatClickLists) {

                        chatWhoTag = chatClickList.chatSender === localStorage.getItem('id') ? 'me' : 'other'
                        chatWhoType = chatClickList.chatSender === localStorage.getItem('id') ? localStorage.getItem('nickname') : myfageChatList.getAttribute('name')

                        if (chatClickList.chatType === 'TALK') {
                            myfageRightChatTalk(chatWhoTag, chatMsg, chatDate)
                        } else if (chatClickList.chatType === 'FILE') {
                            myfageRightChatFile(chatWhoTag, chatClickList.chatFile, chatClickList.chatFileName, chatClickList.chatFileDownload)
                        } else if (chatClickList.chatType === 'ENTER') {
                            myfageRightChatEnterLeave(chatWhoTag, chatWhoType, '입장')
                        } else if (chatClickList.chatType === 'LEAVE') {
                            myfageRightChatEnterLeave(chatWhoTag, chatWhoType, '퇴장')
                        }
                    }
                } else if (chatListStatus === 400) {
                    alert('알수 없는 오류로 채팅내용을 불러오지 못했습니다.')
                }
            })
        })
    }
}

const myfageRightChatNotDefault = document.querySelector('.myfage-right-chat-not-default')
const myfageRightChatBox = document.querySelector('.myfage-right-chat-box')