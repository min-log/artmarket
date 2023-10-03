const myfageMid = document.querySelector('.myfage-mid')
myfageChatMidAddTag()

// ------------------------------ myfage-mid-chat ------------------------------

function myfageChatMidAddTag() {

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

    fetch(`${baseUrl}/myfage/${localStorage.getItem('id')}`, {
        method: 'GET',
        headers: {
            'content-type': 'application/json'
        }
    }).then(response => {
        resStatusCode = response.status
        return response.json()
    }).then(data => {
        if (resStatusCode === 200) {
            for (var i = 0; i < data.myChatRooms.length; i++) {
                myfageChatListBring(data.myChatRooms[i].chatRoomId,
                    data.myChatRooms[i].chatMsg,
                    data.myChatRooms[i].chatDate,
                    data.myChatRooms[i].chatSender,
                    data.myChatRooms[i].chatSenderIdtity,
                    data.myChatRooms[i].chatSenderProfile)
            }
        } else if (resStatusCode === 401) {
            alert('참여한 대화방이 없습니다.')
        } else if (resStatusCode === 400) {
            alert('알수 없는 오류로 대화방을 읽어들일 수 없습니다.')
        }
    })
}

// ------------------------------ myfage-mid-article ------------------------------

function myfageArticleMidAddTag() {

    myfageMid.insertAdjacentHTML('beforeend', `<div class="myfage-mid-article-box">
    <div class="myfage-mid-article-box-top">
      <div class="myfage-mid-article-box-top-filter">
        <select class="myfage-mid-article-box-top-filter-category">
          <option >카테고리</option>
          <option value="ILLUST">ILLUST</option>
          <option value="LIVE">LIVE2D•3D</option>
          <option value="CHARACTER">CHARACTER</option>
          <option value="DESIGN">DESIGN</option>
          <option value="VIDEO">VIDEO</option>
        </select>
        <input type="date" date-placeholder="시작일" class="myfage-mid-article-box-top-start-date" />
        <input type="date" date-placeholder="종료일" class="myfage-mid-article-box-top-end-date" />
      </div>
      <div class="myfage-mid-article-box-top-manage">
        <div class="myfage-mid-article-box-top-manage-onOff">
          <div class="myfage-mid-article-box-top-manage-onOff-label">의뢰</div>
          <img class="myfage-mid-article-box-top-manage-onOff-icon" src="./css/icon/article-on.png">
        </div>
      </div>
    </div>
    <div class="myfage-mid-article-box-bot">
      <div class="myfage-mid-article-box-bot-list">
        <div class="myfage-mid-article-box-bot-list-thumb">
          <img class="myfage-mid-article-box-bot-list-thumb-img" src="./css/img/profile-test.gif"/>
          <img class="myfage-mid-article-box-bot-list-thumb-img" src="./css/img/profile-test.gif"/>
          <img class="myfage-mid-article-box-bot-list-thumb-img" src="./css/img/profile-test.gif"/>
        </div>
        <div class="myfage-mid-article-box-bot-list-content">
          <div class="myfage-mid-article-box-bot-list-right-top">
            <div class="myfage-mid-article-box-bot-list-right-top-art">
              <div class="myfage-mid-article-box-bot-list-right-top-art-label">작품번호</div>
              <div class="myfage-mid-article-box-bot-list-right-top-art-value"></div>
            </div>
            <div class="myfage-mid-article-box-bot-list-right-top-onOff">
              <img class="myfage-mid-article-box-bot-list-right-top-onOff-icon" src="./css/icon/article-off.png">
            </div>
          </div>
          <div class="myfage-mid-article-box-bot-list-category">
            <div class="myfage-mid-article-box-bot-list-category-label">카테고리</div>
            <div class="myfage-mid-article-box-bot-list-category-value"></div>
          </div>
        </br>
          <div class="myfage-mid-article-box-bot-list-order">
            <div class="myfage-mid-article-box-bot-list-order-label">주문</div>
            <div class="myfage-mid-article-box-bot-list-order-value"></div>
          </div>
          <div class="myfage-mid-article-box-bot-list-mod-del">
            <div class="myfage-mid-article-box-bot-list-mod">수정하기</div>
            <div class="myfage-mid-article-box-bot-list-del">삭제하기</div>
          </div>
        </div>
      </div>
    </div>
    </div>`)

}