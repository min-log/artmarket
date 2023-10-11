// addEndpoint("/stomp/chat")

function chatMsgGet(chatData, chatOtherType) {
  console.log(chatData);
  let chatWho = (chatData.chatSender == sessionStorage.getItem('id') ? 'me' : 'other')
  let chatWhoType = (chatData.chatSender == sessionStorage.getItem('id') ? sessionStorage.getItem('nickname') : chatOtherType)

  let curentBoxTag = sessionStorage.getItem('chatcurrenttag')

  let curChatParentTag = (sessionStorage.getItem('chatcurrenttag') === 'myfage-right-chat-box-mid' ? 'myfage-right-chat-box-mid' : 'product-chat-room-box-mid')
  const chatParentBoxTag = document.querySelector(`.${curChatParentTag}`)


  if (chatData.chatType === 'TALK') {
    myfageRightChatTalk(curentBoxTag, chatWho, chatData.chatMsg, chatData.chatDate)
  } else if (chatData.chatType === 'FILE') {
    myfageRightChatFile(curentBoxTag, chatWho, chatData.chatFile, chatData.chatFileName, chatData.chatFileDownload,chatData.chatMsg, chatData.chatDate)
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
  function myfageRightChatFile(curentBoxTag, chatWhoTag, chatFile, chatFileName, chatFileDownload,chatMsg,chatDate) {
    console.log("myfageRightChatFile " + curentBoxTag + "/ "+chatWhoTag )
    console.log( chatFileName + "/" + chatFileDownload)
    console.log( chatFile )
    let fileExtention =  getExtensionOfFilename(chatFileName);

    // chatParentBoxTag.insertAdjacentHTML('beforeend', `
    // <div class="${curentBoxTag}-msg-${chatWhoTag}"></div>`);

    const productChatRoomBoxMid = document.querySelector(`.${curentBoxTag}-msg-${chatWhoTag}`)

    if (fileExtention === '.png' || fileExtention === '.jpg' || fileExtention === '.gif') {
      chatParentBoxTag.insertAdjacentHTML('beforeend', `
         <div class="${curentBoxTag}-msg-${chatWhoTag}">
            <div class="${curentBoxTag}-msg-${chatWhoTag}-time">${chatDate}</div>
            <div class="${curentBoxTag}-msg-${chatWhoTag}-text">${chatMsg}</div>
            <img class="myfage-right-chat-box-mid-img" src="${chatFile}">
            <a class="myfage-right-chat-box-mid-download" href="/file/download/${chatFileDownload}" download="${chatFileName}" target='_blank'><img src="../css/img/icon-downloads.png">${chatFileName}</a>
         </div>
      `)
    } else {
      chatParentBoxTag.insertAdjacentHTML('beforeend', `
          <div class="${curentBoxTag}-msg-${chatWhoTag}">
            <div class="${curentBoxTag}-msg-${chatWhoTag}-time">${chatDate}</div>
            <div class="${curentBoxTag}-msg-${chatWhoTag}-text">${chatMsg}</div>
            <a class="myfage-right-chat-box-mid-download" href="/file/download/${chatFileDownload}" download="${chatFileName}" target='_blank'><img src="../css/img/icon-downloads.png">${chatFileName}</a>
          </div>
      `)
    }
  }

  // 채팅 타입에 따른 함수(ENTER, LEAVE)
  function myfageRightChatEnterLeave(curentBoxTag, chatWhoTag, chatWhoType, chatEnterLeaveMsg) {
    chatParentBoxTag.insertAdjacentHTML('beforeend', `
  <div class="${curentBoxTag}-msg-${chatWhoTag}">${chatWhoType}이 ${chatEnterLeaveMsg}하셨습니다.</div>`)
  }

}











