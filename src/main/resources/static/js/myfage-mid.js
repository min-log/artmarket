// ---------------------------------------------- myfage-chat
const myfageMid = document.querySelector('.myfage-mid')

function myfageChatMidAddTag() {

    let resStatusCode

    myfageMid.insertAdjacentHTML('afterend', `<div class="myfage-chat-box">
        <div class="myfage-chat-not-check">안 읽은 메세지만 보기</div>
        <div class="myfage-chat-box-default-title">현재 대화중인 채팅방이 없습니다.</div>
        <div class="myfage-chat-box-default-intro">다양한 작품을 구경하고 작가에게 문의해보세요!</div>
    </div>`)

const myfageNavProfileGreeting = document.querySelector('.myfage-nav-profile-greeting')
const myfageNavProfileImgTag = document.querySelector('.myfage-nav-profile-img-tag')

function myfageChatListBring(chatRoomId, chatRoomMsg, chatRoomLastDate, chatSender, chatSenderIdtity, chatSenderProfile){
    const myfageChatBox = document.querySelector('.myfage-chat-box')
    
    myfageChatBox.insertAdjacentHTML('beforeend',`<div class="myfage-chat-list" id="${chatRoomId}" name="${chatSender}">
    <div class="myfage-chat-content">
        <div class="myfage-chat-profile">
            <div class="myfage-chat-profile-identity">${chatSenderIdtity}</div>
            <div class="myfage-chat-profile-img">
                <img class="myfage-chat-profile-img-tag" src="${baseUrl}${chatSenderProfile}" />
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
}

    fetch(`${baseUrl}/myfage/${localStorage.getItem('id')}`,{
        method: 'POST',
        headers: {
            'content-type':'application/json'
        }
    }).then(response =>{
        resStatusCode = response.status
        return response.json()
    }).then(data =>{
        if(resStatusCode === 200){
            myfageNavProfileGreeting.textContent = `안녕하세요.<br />${data.nickname}님`
            localStorage.setItem('nickname',`${data.nickname}`)
            localStorage.setItem('intro',`${data.intro}`)
            myfageNavProfileImgTag.setAttribute('src',`${baseUrl}${data.profileImg}`)
            for(var i = 0; i < data.myChatRooms.length; i++){
                myfageChatListBring(data.myChatRooms[i].chatRoomId,
                    data.myChatRooms[i].chatMsg,
                    data.myChatRooms[i].chatDate,
                    data.myChatRooms[i].chatSender,
                    data.myChatRooms[i].chatSenderIdtity,
                    data.myChatRooms[i].chatSenderProfile)
            }
        }else if(resStatusCode === 401){
            alert('참여한 대화방이 없습니다.')
        }else if(resStatusCode === 400){
            alert('알수 없는 오류로 대화방을 읽어들일 수 없습니다.')
        }
    })
}

// myfageChatMidAddTag()

myfageNavChat.addEventListener('click', function () {
    myfageChatMidAddTag()
})



// ---------------------------------------------- myfage-article

myfageNavArticle.addEventListener('click',function(){
    alert('준비중입니다.')
})