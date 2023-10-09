// myfageChatMidAddTag()

// ------------------------------ myfage-mid-chat ------------------------------

function myfageChatMidAddTag() {

    if (myfageMid.childNodes) {
        const childNodesArray = Array.from(myfageMid.childNodes);
        for (const myfageMidTag of childNodesArray) {
            myfageMidTag.remove();
        }
    }

    let resStatusCode

    myfageMid.insertAdjacentHTML('afterbegin', `<div class="myfage-chat-box">
        <div class="myfage-chat-not-check">안 읽은 메세지만 보기</div>
        <div class="myfage-chat-box-default-title">현재 대화중인 채팅방이 없습니다.</div>
        <div class="myfage-chat-box-default-intro">다양한 작품을 구경하고 작가에게 문의해보세요!</div>
    </div>`)

    function myfageChatListBring(chatRoomId, chatRoomMsg, chatRoomLastDate, chatSender, chatSenderIdtity, chatSenderProfile) {
        const myfageChatBox = document.querySelector('.myfage-chat-box')

        myfageChatBox.insertAdjacentHTML('beforeend', `<div class="myfage-chat-list" id="${chatRoomId}" name="${chatSender}">
      <div class="myfage-chat-content">
          <div class="myfage-chat-profile">
              <div class="myfage-chat-profile-identity">${chatSenderIdtity}</div>
              <div class="myfage-chat-profile-img">
                  <img class="myfage-chat-profile-img-tag" src="${chatSenderProfile}" />
              </div>
          </div>
          <div class="myafge-chat-info">
              <div class="myfage-chat-info-top">
                  <div class="myfage-chat-info-top-nickname" id="${chatSender}">${chatSender}</div>
                  <div class="myfage-chat-info-top-send-time">${chatRoomLastDate}</div>
              </div>
              <div class="myfage-chat-info-msg">${chatRoomMsg}</div>
          </div>
      </div>
      <div class="myfage-chat-delete">
          <img class="myfage-chat-delete-img" src="./css/icon/chat-exit.png" />
      </div>
  </div>`)

        const myfageChatLists = document.querySelectorAll('.myfage-chat-list')

        for (const myfageChatList of myfageChatLists) {
            myfageChatList.addEventListener('click', function () {
                myfageChatListClick(myfageChatList.getAttribute('id'), myfageChatList.getAttribute('name'))
            })
        }
    }

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

            let chatListCheck = true
            for (var i = 0; i < data.myChatRooms.length; i++) {
                if (data.myChatRooms[i].chatMsg !== null) {
                    let defaultProfile = (data.myChatRooms[i].chatSenderProfile === null ? './css/icon/default-profile-img.png' : `data:image/jpeg;base64,${data.myChatRooms[i].chatSenderProfile}`)
                    myfageChatListBring(data.myChatRooms[i].chatRoomId,
                        data.myChatRooms[i].chatMsg,
                        data.myChatRooms[i].chatDate,
                        data.myChatRooms[i].chatSender,
                        data.myChatRooms[i].chatSenderIdtity,
                        defaultProfile)

                    chatListCheck = false
                }
            }

            if (!chatListCheck) {
                const myfageChatBoxDefaultTitle = document.querySelector('.myfage-chat-box-default-title')
                const myfageChatBoxDefaultIntro = document.querySelector('.myfage-chat-box-default-intro')

                myfageChatBoxDefaultTitle.remove()
                myfageChatBoxDefaultIntro.remove()
            }
        } else if (resStatusCode === 401) {
            alert('참여한 대화방이 없습니다.')
        } else if (resStatusCode === 400) {
            alert('알수 없는 오류로 대화방을 읽어들일 수 없습니다.')
        }
    })
}

// ------------------------------ myfage-right-chat ------------------------------

function myfageChatListClick(chatRoomId, chatSender) {

    const myfageRightChatNotDefault = document.querySelector('.myfage-right-chat-not-default')
    myfageRightChatNotDefault.remove()

    myfageRight.insertAdjacentHTML('beforeend', `
    <div class="myfage-right-chat-box" id="${chatRoomId}">
    <div class="myfage-right-chat-box-top">
    <div class="myfage-right-chat-box-top-profile">
      <div class="myfage-right-chat-box-top-nickname">${chatSender}</div>
      <div class="myfage-right-chat-box-top-show-article">
        작품 보러가기
      </div>
    </div>
    <input
      class="myfage-right-chat-box-top-show-exit"
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

    sessionStorage.setItem('chatcurrenttag', `myfage-right-chat-box-mid`)

    fetch(`${baseUrl}/myfage`, {
        method: 'POST',
        headers: {
            'content-type': 'application/json'
        },
        body: JSON.stringify({
            clickChatId: `${chatRoomId}`,
            clickMember: `${sessionStorage.getItem('id')}`
        })
    }).then(response => {
        return response.json()
    }).then(data => {
        const myfageRightChatBoxMid = document.querySelector('.myfage-right-chat-box-mid')

        const myfageRightChatBoxTopShowArticle = document.querySelector('.myfage-right-chat-box-top-show-article')
        myfageRightChatBoxTopShowArticle.setAttribute('id', `${data.chatProudct}`)

        for (chatList of data.chatList) {

            let chatWhoTag = (chatList.chatSender == sessionStorage.getItem('id') ? 'me' : 'other')

            myfageRightChatBoxMid.insertAdjacentHTML('beforeend', `
        <div class="${sessionStorage.getItem('chatcurrenttag')}-msg-${chatWhoTag}">
          <div class="${sessionStorage.getItem('chatcurrenttag')}-msg-${chatWhoTag}-time">${chatList.chatDate}</div>
          <div class="${sessionStorage.getItem('chatcurrenttag')}-msg-${chatWhoTag}-text">${chatList.chatMsg}</div>
        </div>`)
        }
    })

    const myfageRightChatBoxTopShowArticle = document.querySelector('.myfage-right-chat-box-top-show-article')


    myfageRightChatBoxTopShowArticle.addEventListener('click', function () {
        sessionStorage.setItem('detailproduct', `${myfageRightChatBoxTopShowArticle.getAttribute('id')}`)
        location.href = "detail.html"
    })


    const myfageChatRoomBoxBotSendAttach = document.querySelector('.myfage-right-chat-box-bot-send-attach')
    const myfageChatRoomBoxBotSendAttachFile = document.createElement('input')
    myfageChatRoomBoxBotSendAttachFile.setAttribute('class', 'myfage-right-chat-box-bot-send-attach-file')
    myfageChatRoomBoxBotSendAttachFile.setAttribute('type', 'file')
    myfageChatRoomBoxBotSendAttachFile.style.display = 'none'
    myfageChatRoomBoxBotSendAttach.after(myfageChatRoomBoxBotSendAttachFile)

    // chat send btn
    const myfageRightChatBoxBotSendBtn = document.querySelector('.myfage-right-chat-box-bot-send-btn')

    // chat exit btn
    const myfageRightChatBoxTopShowExit = document.querySelector('.myfage-right-chat-box-top-show-exit')

    let socket = new SockJS(`/stomp/chat`)
    stompClient = Stomp.over(socket)

    stompClient.connect({}, function () {

            stompClient.subscribe(`/sub/chat-room/get/${chatRoomId}`, (message) => {
                chatMsgGet(JSON.parse(message.body), `${chatSender}`)
            })

            //파일 업로드 구현
            myfageChatRoomBoxBotSendAttach.addEventListener('click', function () {

                myfageChatRoomBoxBotSendAttachFile.click()

                myfageChatRoomBoxBotSendAttachFile.addEventListener('change', (e) => {

                    let chatFileName

                    const file = e.target.files[0]
                    chatFileName = file.name

                    const chatFileReader = new FileReader();

                    if (file !== undefined) {
                        chatFileReader.readAsArrayBuffer(file)
                    }

                    chatFileReader.onload = function (e) {

                        const fileObject = e.target.result
                        myfageChatRoomBoxBotSendAttachFile.setAttribute('id', `${Array.from(new Uint8Array(fileObject))}`)
                        myfageChatRoomBoxBotSendAttachFile.setAttribute('name', `${chatFileName}`)
                    }

                })
            })

            myfageRightChatBoxBotSendBtn.addEventListener('click', function () {

                const myfageRightChatBoxBotSendText = document.querySelector('.myfage-right-chat-box-bot-send-text')

                if (myfageRightChatBoxBotSendText.value === '') {
                    alert('메세지를 입력해주세요.')
                } else {
                    chatDataObject = {
                        sendChatRoomId: `${chatRoomId}`,
                        sendChatSender: sessionStorage.getItem('id'),
                        sendChatMsg: myfageRightChatBoxBotSendText.value,
                        sendChatFile: null
                    }

                    if (myfageChatRoomBoxBotSendAttachFile.getAttribute('id') !== null) {

                        const chatFileType = myfageChatRoomBoxBotSendAttachFile.getAttribute('name').split('.')
                        chatDataObject.sendChatFile = {
                            chatFileName: myfageChatRoomBoxBotSendAttachFile.getAttribute('name'),
                            chatFileData: myfageChatRoomBoxBotSendAttachFile.getAttribute('id'),
                            chatFileType: chatFileType[chatFileType.length - 1]
                        }
                    }

                    stompClient.send(`/pub/chat-room/send`, {}, JSON.stringify(chatDataObject))
                    myfageRightChatBoxBotSendText.value = ''
                }
            })

            const myfageRightChatBox = document.querySelector('.myfage-right-chat-box')
            // disconnect 구현
            myfageRightChatBoxTopShowExit.addEventListener('click', function () {
                myfageRightChatBox.style.display = 'none'
                myfageChatRightAddTag()
                stompClient.disconnect()
            })

        },
        function () {
            alert('연결에 실패했습니다.')
        })
}
