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


// ------------------------------ myfage-right-article ------------------------------

function myfageArticleRightAddTag() {
  myfageRight.insertAdjacentHTML('beforeend', `<div class="myfage-right-article-manage-box">
  <div class="myfage-right-article-manage-title">작품 등록하기</div>
  <div class="myfage-right-article-manage-category">
    <div class="myfage-right-article-manage-category-select-title">
      카테고리
    </div>
    <div class="myfage-right-article-manage-category-select-box">
      <div
        class="myfage-right-article-manage-category-select-box-illust" name="0">
        ILLUST
      </div>
      <div class="myfage-right-article-manage-category-select-box-live" name="0">
        LIVE2D•3D
      </div>
      <div
        class="myfage-right-article-manage-category-select-box-design" name="0">
        DESIGN
      </div>
      <div
        class="myfage-right-article-manage-category-select-box-video" name="0">
        VIDEO
      </div>
      <div
        class="myfage-right-article-manage-category-select-box-character" name="0">
        CHARACTER
      </div>
    </div>
    <div
      class="myfage-right-article-manage-category-select-box-caution"
    >
      <img
        class="myfage-right-article-manage-caution-img"
        src="./css/icon/error.png"
      />카테고리는 필수로 1개 선택하셔야합니다.
    </div>
  </div>
  <div class="myfage-right-article-manage-title-box">
    <div class="myfage-right-article-manage-title-box-label">
      작품 제목
    </div>
    <textarea
      class="myfage-right-article-manage-title-box-value"
      cols="53"
      rows="1"
    ></textarea>
  </div>
  <div class="myfage-right-article-manage-intro-box">
    <div class="myfage-right-article-manage-intro-box-label">
      작품 설명
    </div>
    <textarea
      class="myfage-right-article-manage-intro-box-value"
      cols="53"
      rows="10"
    ></textarea>
  </div>
  <div class="myfage-right-article-manage-file-box">
    <div class="myfage-right-article-manage-intro-file-label">
      파일 첨부
    </div>
    <div class="myfage-right-article-manage-intro-file-img">
      <img
        class="myfage-right-article-manage-intro-file-icon"
        src="./css/icon/article-file-upload-icon.png"
      />
      <input
      class="myfage-right-article-manage-intro-file-value"
      type="file" multiple
    />
    </div>
    <div class="myfage-right-article-manage-intro-file-caution">
      <img
        class="myfage-right-article-manage-caution-img"
        src="./css/icon/error.png"
      />파일은 최소3개~최대5개까지 업로드 가능합니다.
    </div>
    <div class="myfage-right-article-manage-intro-file-caution">
      <img
        class="myfage-right-article-manage-caution-img"
        src="./css/icon/error.png"
      />가로길이는 850으로 맞춰주세요.
    </div>
  </div>
  <div class="myfage-right-article-manage-btn-box">
    <div class="myfage-right-article-manage-btn-box-show">미리보기</div>
    <div class="myfage-right-article-manage-btn-box-add">등록하기</div>
  </div>
  </div>
  <img
  class="myfage-right-article-back-img"
  src="./css/img/myfage-right-article.png"
  />`)

  // 카테고리 선택

  const myfageRightArticleManageCategorySelectBox = document.querySelector('.myfage-right-article-manage-category-select-box')
  let categoryLists = myfageRightArticleManageCategorySelectBox.childNodes

  const myfageRightArticleManageCategorySelectBoxIllust = document.querySelector('.myfage-right-article-manage-category-select-box-illust')
  const myfageRightArticleManageCategorySelectBoxLive = document.querySelector('.myfage-right-article-manage-category-select-box-live')
  const myfageRightArticleManageCategorySelectBoxDesign = document.querySelector('.myfage-right-article-manage-category-select-box-design')
  const myfageRightArticleManageCategorySelectBoxVideo = document.querySelector('.myfage-right-article-manage-category-select-box-video')
  const myfageRightArticleManageCategorySelectBoxCharacter = document.querySelector('.myfage-right-article-manage-category-select-box-character')


  function articleCategoryUnClick(clickTag) {

    clickTag.style.backgroundColor = 'rgba(41, 45, 50, 1)'
    clickTag.style.color = 'white'
    clickTag.style.fontWeight = '500'
    clickTag.style.fontSize = '0.85rem'
    clickTag.style.padding = '0.09rem 2rem'
    clickTag.style.border = 'solid 0.1rem white'
    clickTag.style.borderRadius = '1rem'
    clickTag.style.margin = '0.2rem'
    clickTag.style.cursor = 'pointer'
    clickTag.setAttribute('name', '0')
  }

  function articleCategoryMouseOut(clickTag) {

    if (clickTag.getAttribute('name') === '0') {
      clickTag.style.backgroundColor = 'rgba(41, 45, 50, 1)'
      clickTag.style.color = 'white'
      clickTag.style.fontWeight = '500'
      clickTag.style.fontSize = '0.85rem'
      clickTag.style.padding = '0.09rem 2rem'
      clickTag.style.border = 'solid 0.1rem white'
      clickTag.style.borderRadius = '1rem'
      clickTag.style.margin = '0.2rem'
      clickTag.style.cursor = 'pointer'
    }

  }

  function articleCategoryClick(unClickTag) {
    unClickTag.style.backgroundColor = 'white'
    unClickTag.style.color = 'rgba(41, 45, 50, 1)'
    unClickTag.style.fontWeight = '600'
    unClickTag.style.cursor = 'pointer'
    unClickTag.setAttribute('name', '1')
  }

  function articleCategoryMouseOver(unClickTag) {

    if (unClickTag.getAttribute('name') === '0') {
      unClickTag.style.backgroundColor = 'white'
      unClickTag.style.color = 'rgba(41, 45, 50, 1)'
      unClickTag.style.fontWeight = '600'
      unClickTag.style.cursor = 'pointer'
    }

  }

  function articleCategorySelect() {

    for (var i = 1; i < categoryLists.length; i += 2) {
      articleCategoryUnClick(categoryLists[i])
    }

  }

  myfageRightArticleManageCategorySelectBoxIllust.addEventListener('click', function () {
    articleCategorySelect()
    articleCategoryClick(myfageRightArticleManageCategorySelectBoxIllust)
  })

  myfageRightArticleManageCategorySelectBoxIllust.addEventListener('mouseover', function () {
    articleCategoryMouseOver(myfageRightArticleManageCategorySelectBoxIllust)
  })

  myfageRightArticleManageCategorySelectBoxIllust.addEventListener('mouseout', function () {
    articleCategoryMouseOut(myfageRightArticleManageCategorySelectBoxIllust)
  })

  myfageRightArticleManageCategorySelectBoxLive.addEventListener('click', function () {
    articleCategorySelect()
    articleCategoryClick(myfageRightArticleManageCategorySelectBoxLive)
  })

  myfageRightArticleManageCategorySelectBoxLive.addEventListener('mouseover', function () {
    articleCategoryMouseOver(myfageRightArticleManageCategorySelectBoxLive)
  })

  myfageRightArticleManageCategorySelectBoxLive.addEventListener('mouseout', function () {
    articleCategoryMouseOut(myfageRightArticleManageCategorySelectBoxLive)
  })


  myfageRightArticleManageCategorySelectBoxDesign.addEventListener('click', function () {
    articleCategorySelect()
    articleCategoryClick(myfageRightArticleManageCategorySelectBoxDesign)
  })

  myfageRightArticleManageCategorySelectBoxDesign.addEventListener('mouseover', function () {
    articleCategoryMouseOver(myfageRightArticleManageCategorySelectBoxDesign)
  })

  myfageRightArticleManageCategorySelectBoxDesign.addEventListener('mouseout', function () {
    articleCategoryMouseOut(myfageRightArticleManageCategorySelectBoxDesign)
  })

  myfageRightArticleManageCategorySelectBoxVideo.addEventListener('click', function () {
    articleCategorySelect()
    articleCategoryClick(myfageRightArticleManageCategorySelectBoxVideo)
  })

  myfageRightArticleManageCategorySelectBoxVideo.addEventListener('mouseover', function () {
    articleCategoryMouseOver(myfageRightArticleManageCategorySelectBoxVideo)
  })

  myfageRightArticleManageCategorySelectBoxVideo.addEventListener('mouseout', function () {
    articleCategoryMouseOut(myfageRightArticleManageCategorySelectBoxVideo)
  })

  myfageRightArticleManageCategorySelectBoxCharacter.addEventListener('click', function () {
    articleCategorySelect()
    articleCategoryClick(myfageRightArticleManageCategorySelectBoxCharacter)
  })

  myfageRightArticleManageCategorySelectBoxCharacter.addEventListener('mouseover', function () {
    articleCategoryMouseOver(myfageRightArticleManageCategorySelectBoxCharacter)
  })

  myfageRightArticleManageCategorySelectBoxCharacter.addEventListener('mouseout', function () {
    articleCategoryMouseOut(myfageRightArticleManageCategorySelectBoxCharacter)
  })

  // 작품등록 파일 핸들링

  const myfageRightArticleManageIntroFileIcon = document.querySelector(`.myfage-right-article-manage-intro-file-icon`)
  const myfageRightArticleManageIntroFileValue = document.querySelector('.myfage-right-article-manage-intro-file-value')
  const myfageRightArticleManageIntroFileImg = document.querySelector('.myfage-right-article-manage-intro-file-img')

  let articleCount = 0;

  function myfageArticleFileUpload() {

    myfageRightArticleManageIntroFileValue.click()

    myfageRightArticleManageIntroFileValue.addEventListener('change', (e) => {

      const file = e.target.files[0]
      myfageRightArticleManageIntroFileValue.value = ''

      const articleFileReader = new FileReader();

      if (file !== undefined) {
        articleFileReader.readAsDataURL(file)
      }

      articleFileReader.onload = function (e) {

        myfageRightArticleManageIntroFileImg.insertAdjacentHTML('beforeend', `
          <div class="myfage-right-article-manage-intro-file-suc-box-${articleCount + 1}">
            <img
            class="myfage-right-article-manage-intro-file-suc"
            src="${e.target.result}"
            /><img class="myfage-right-article-manage-intro-file-del-${articleCount + 1}" src="./css/icon/article-file-delete.png"/>
          </div>`)

        articleCount += 1

        let myfageRightArticleManageIntroFileSucBox = document.querySelector(`.myfage-right-article-manage-intro-file-suc-box-${articleCount}`)
        let myfageRightArticleManageIntroFileDel = document.querySelector(`.myfage-right-article-manage-intro-file-del-${articleCount}`)

        myfageRightArticleManageIntroFileDel.addEventListener('click', function () {
          myfageRightArticleManageIntroFileSucBox.remove()
          articleCount -= 1

          if (articleCount < 5) {
            myfageRightArticleManageIntroFileIcon.style.display = 'block'
          }
        })

        if (articleCount === 5) {
          myfageRightArticleManageIntroFileIcon.style.display = 'none'
        }

      }

    })
  }

  myfageRightArticleManageIntroFileIcon.addEventListener('click', function () {
    myfageArticleFileUpload()
  })


}

