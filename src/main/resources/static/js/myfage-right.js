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

function myfageChatListClick(chatRoomId, chatProudct){

    myfageRightChatBox.insertAdjacentHTML('afterend',`
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
}

const myfageRightChatBoxMid = document.querySelector('.myfage-right-chat-box-mid')

function myfageRightChatMsgs(chatWhoTag, chatWhoType, chatMsg, chatDate, chatType, chatFile, chatFileName, chatFileDownload){
  myfageRightChatBoxMid.insertAdjacentHTML('beforeend',`
  <div class="myfage-right-chat-box-mid-msg-${chatWhoTag}">
    <div class="myfage-right-chat-box-mid-msg-${chatWhoTag}-time">${chatDate}</div>
    <div class="myfage-right-chat-box-mid-msg-${chatWhoTag}-text">${chatMsg}</div>
  </div>`)

  const myfageRightChatBoxMidMsg = document.querySelector(`.myfage-right-chat-box-mid-msg-${chatWhoTag}`)
  const myfageRightChatBoxMidMsgTime = document.querySelector(`.myfage-right-chat-box-mid-msg-${chatWhoTag}-time`)
  const myfageRightChatBoxMidMsgText = document.querySelector(`.myfage-right-chat-box-mid-msg-${chatWhoTag}-text`)

  if(chatType === 'ENTER'){
    myfageRightChatBoxMidMsg.insertAdjacentHTML('afterbegin',`
    <div class="myfage-right-chat-box-mid-enter">${chatWhoType}이 입장하셨습니다.</div>`)
  }else if(chatType === 'LEAVE'){
    myfageRightChatBoxMidMsgTime.remove()
    myfageRightChatBoxMidMsgText.remove()
    myfageRightChatBoxMidMsg.insertAdjacentHTML('afterbegin',`
    <div class="myfage-right-chat-box-mid-leave">${chatWhoType}이 퇴장하셨습니다.</div>`)
  }

  if(chatFile !== null){
    let fileExtention = chatFileName.split('.')
    
    if(fileExtention[fileExtention.length-1] === 'txt'){

      myfageRightChatBoxMidMsg.insertAdjacentHTML('beforeend',`{
        <a href="${chatFile}" download="${chatFileDownload}">
      }`)
    }else{
      myfageRightChatBoxMidMsg.insertAdjacentHTML('beforeend',`
        <img src="${baseUrl}${chatFile}">
        <a href="${baseUrl}${chatFile}" download="${chatFileDownload}">
      `)
    }
  }
}

myfageChatRightAddTag()

const myfageChatLists = document.querySelectorAll('.myfage-chat-list')
for(const myfageChatList of myfageChatLists){

    let chatListStatus
    myfageChatList.addEventListener('click',function(){
        fetch(`${baseUrl}/myfage`,{
            method: 'POST',
            headers: {
                'content-type' : 'applicaiton/json'
            },
            body: JSON.stringify({
                clickChatId : `${myfageChatList.getAttribute('id')}`,
                clickMember : `${localStorage.getItem('id')}`
            })
        }).then(response=> {
            chatListStatus = response.status
            return response.json()
        }).then(data => {
          if(chatListStatus === 201){
            const myafgeRightChatBoxTopNickname = document.querySelector('.myafge-right-chat-box-top-nickname')
            myafgeRightChatBoxTopNickname.textContent = myfageChatList.getAttribute('name')
            myfageChatListClick(data.chatRoomId, data.chatProudct)
            for(var i = 0; i < data.chatList.length; i++){
              if(data.chatList[i].chatSender === localStorage.getItem('id')){
                myfageRightChatMsgs('me',
                  localStorage.getItem('nickname'), 
                  data.chatList[i].chatMsg, 
                  data.chatList[i].chatDate,
                  data.chatList[i].chatType,
                  data.chatList[i].chatFile,
                  data.chatList[i].chatFileName,
                  data.chatList[i].chatFileDownload)
              }else{
                myfageRightChatMsgs('other',
                  myfageChatList.getAttribute('name'),
                  data.chatList[i].chatMsg, 
                  data.chatList[i].chatDate,
                  data.chatList[i].chatType,
                  data.chatList[i].chatFile,
                  data.chatList[i].chatFileName,
                  data.chatList[i].chatFileDownload)
              }
            }
          }else if(chatListStatus === 400){
            alert('알수 없는 오류로 채팅내용을 불러오지 못했습니다.')
          }
        })
    })
}

myfageNavChat.addEventListener('click', function () {
    myfageChatRightAddTag()
})