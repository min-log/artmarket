// addEndpoint("/stomp/chat")

function chatMsgGet(chatData, chatOtherType) {
  let chatWho = (chatData.chatSender == sessionStorage.getItem('id') ? 'me' : 'other')
  let chatWhoType = (chatData.chatSender == sessionStorage.getItem('id') ? sessionStorage.getItem('nickname') : chatOtherType)

  let curentBoxTag = sessionStorage.getItem('chatcurrenttag')

  let curChatParentTag = (sessionStorage.getItem('chatcurrenttag') === 'myfage-right-chat-box-mid' ? 'myfage-right-chat-box-mid' : 'product-chat-room-box-mid')
  const chatParentBoxTag = document.querySelector(`.${curChatParentTag}`)


  if (chatData.chatType === 'TALK') {
    myfageRightChatTalk(curentBoxTag, chatWho, chatData.chatMsg, chatData.chatDate)
  } else if (chatData.chatType === 'FILE') {
    myfageRightChatFile(curentBoxTag, chatWho, chatData.chatFile, chatData.chatFileName, chatData.chatFileDownload)
  } else if (chatData.chatType === 'ENTER') {
    myfageRightChatEnterLeave(curentTag, chatWho, chatWhoType, '입장')
  }

  // 채팅 타입에 따른 함수(TALK)
  function myfageRightChatTalk(curentBoxTag, chatWhoTag, chatMsg, chatDate) {


    chatParentBoxTag.insertAdjacentHTML('beforeend', `
    <div class="${curentBoxTag}-msg-${chatWhoTag}">
      <div class="${curentBoxTag}-msg-${chatWhoTag}-time">${chatDate}</div>
      <div class="${curentBoxTag}-msg-${chatWhoTag}-text">${chatMsg}</div>
    </div>`)
  }

  // 채팅 타입에 따른 함수(FILE)
  function myfageRightChatFile(curentBoxTag, chatWhoTag, chatFile, chatFileName, chatFileDownload) {

    let fileExtention = chatFileName.split('.')

    chatParentBoxTag.insertAdjacentHTML('beforeend', `
    <div class="${curentBoxTag}-msg-${chatWhoTag}"></div>`)

    const productChatRoomBoxMid = document.querySelector(`.${curentBoxTag}-msg-${chatWhoTag}`)

    if (fileExtention[fileExtention.length - 1] === 'txt') {
      chatParentBoxTag.insertAdjacentHTML('beforeend', `
        <a href="data:image/jpeg;base64,${chatFile}" download="${chatFileDownload}">
      `)
    } else {
      chatParentBoxTag.insertAdjacentHTML('beforeend', `
        <img src="data:image/jpeg;base64,${chatFile}">
        <a href="data:image/jpeg;base64,${chatFile}" download="${chatFileDownload}">
      `)
    }
  }

  // 채팅 타입에 따른 함수(ENTER, LEAVE)
  function myfageRightChatEnterLeave(curentBoxTag, chatWhoTag, chatWhoType, chatEnterLeaveMsg) {
    chatParentBoxTag.insertAdjacentHTML('beforeend', `
  <div class="${curentBoxTag}-msg-${chatWhoTag}">${chatWhoType}이 ${chatEnterLeaveMsg}하셨습니다.</div>`)
  }

}







