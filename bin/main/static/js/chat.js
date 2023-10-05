// addEndpoint("/stomp/chat")

function chatMsgGet(currentHtmlTag, chatData, chatOtherType) {
    let chatWho = chatData.chatSender === localStorage.getItem('id') ? 'me' : 'other'
    let chatWhoType = chatData.chatSender === localStorage.getItem('id') ? localStorage.getItem('nickname') : chatOtherType

    if (chatData.chatType === 'TALK') {
        myfageRightChatTalk(currentHtmlTag, chatWho, chatList[i].chatMsg, chatList[i].chatDate)
    } else if (chatData.chatType === 'FILE') {
        myfageRightChatFile(currentHtmlTag, chatWho, chatList[i].chatFile, chatList[i].chatFileName, chatList[i].chatFileDownload)
    } else if (chatData.chatType === 'ENTER') {
        myfageRightChatEnterLeave(currentHtmlTag, chatWho, chatWhoType, '입장')
    }
}

// 채팅 타입에 따른 함수(TALK)

function myfageRightChatTalk(currentHtmlTag, chatWhoTag, chatMsg, chatDate) {
    productChatRoomBoxMid.insertAdjacentHTML('beforeend', `
  <div class="${currentHtmlTag}-msg-${chatWhoTag}">
    <div class="${currentHtmlTag}-msg-${chatWhoTag}-time">${chatDate}</div>
    <div class="${currentHtmlTag}-msg-${chatWhoTag}-text">${chatMsg}</div>
  </div>`)
}

// 채팅 타입에 따른 함수(FILE)
function myfageRightChatFile(currentHtmlTag, chatWhoTag, chatFile, chatFileName, chatFileDownload) {

    let fileExtention = chatFileName.split('.')

    productChatRoomBoxMid.insertAdjacentHTML('beforeend', `
  <div class="${currentHtmlTag}-msg-${chatWhoTag}"></div>`)

    const productChatRoomBoxMid = document.querySelector(`.${currentHtmlTag}-msg-${chatWhoTag}`)

    if (fileExtention[fileExtention.length - 1] === 'txt') {
        productChatRoomBoxMid.insertAdjacentHTML('beforeend', `
      <a href="data:image/jpeg;base64,${chatFile}" download="${chatFileDownload}">
    `)
    } else {
        productChatRoomBoxMid.insertAdjacentHTML('beforeend', `
      <img src="data:image/jpeg;base64,${chatFile}">
      <a href="data:image/jpeg;base64,${chatFile}" download="${chatFileDownload}">
    `)
    }
}

// 채팅 타입에 따른 함수(ENTER, LEAVE)
function myfageRightChatEnterLeave(currentHtmlTag, chatWhoTag, chatWhoType, chatEnterLeaveMsg) {
    productChatRoomBoxMid.insertAdjacentHTML('beforeend', `
  <div class="${currentHtmlTag}-msg-${chatWhoTag}">${chatWhoType}이 ${chatEnterLeaveMsg}하셨습니다.</div>`)
}

function connect(stompClient, chatRoomId) {
    stompClient.connect({}, function () {
        stompClient.subscribe(`/chat-room/get/${chatRoomId}`, (data) => {
            chatMsgGet(productChatRoomBoxMid, data, detailMidLeftProfileInfoNickname.textContent)
        })
    },
        function () {
            alert('연결에 실패했습니다.')
        })
}

function send(sendData) {
    stompClient.send('/chat-room/send', {}, JSON.stringify(sendData))
}