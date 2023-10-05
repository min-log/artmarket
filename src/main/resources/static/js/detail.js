const detailTopTitleValue = document.querySelector('.detail-top-title-value')
const detailTopArticleNumValue = document.querySelector('.detail-top-article-num-value')

const detailMidLeftProfileImgValue = document.querySelector('.detail-mid-left-profile-img-value')
const detailMidLeftProfileInfoNickname = document.querySelector('.detail-mid-left-profile-info-nickname')
const detailMidLeftProfileInfoIntro = document.querySelector('.detail-mid-left-profile-info-intro')

const detailBotIntroValue = document.querySelector('.detail-bot-intro-value')
const detailBotImgLists = document.querySelector('.detail-bot-img-lists')

const productId = 20

const productChatRoom = document.querySelector('.product-chat-room')
productChatRoom.style.display = 'none'

productDetailShow()

// product 정보 get
function productDetailShow() {
  fetch(`${baseUrl}/product/${productId}`, {
    method: 'GET',
    headers: {
      'content-type': 'application/json'
    }
  }).then(response => {
    return response.json()
  }).then(data => {
    detailTopArticleNumValue.textContent = `${productId}`
    detailTopArticleNumValue.setAttribute('name', `${productId}`)
    detailTopTitleValue.textContent = `${data.productDetail.title}`
    detailMidLeftProfileImgValue.setAttribute('src', `${data.author.authorPofile}`)
    detailMidLeftProfileInfoNickname.textContent = `${data.author.authorNickname}`
    detailBotIntroValue.textContent = `${data.productDetail.productDetail}`
    for (var i = 0; i < data.productDetail.productDetailImgs.length; i++) {
      detailBotImgLists.insertAdjacentHTML('beforeend', `
            <img
            class="detail-bot-img-lists-value"
            src="data:image/jpeg;base64,${data.productDetail.productDetailImgs[i]}"
          />
            `)
    }
  })
}

const detailMidRightPaymentBtn = document.querySelector('.detail-mid-right-payment-btn')
// chat 문의하기
const detailMidRightChatBtn = document.querySelector('.detail-mid-right-chat-btn')

// chat room 변수
const productChatRoomBoxMid = document.querySelector('.product-chat-room-box-mid')

detailMidRightChatBtn.addEventListener('click', function () {

  productChatRoom.style.display = 'block'

  fetch(`/product`, {
    method: 'POST',
    headers: {
      'content-type': 'application/json'
    },
    body: JSON.stringify({
      askProduct: `${productId}`,
      askMember: `${localStorage.getItem('id')}`
    })
  }).then(response => {
    if (response.status === 200) {
      return response.json()
    }
  }).then(data => {
    chatRoomClick(productChatRoomBoxMid, data.chatRoomId, data.chatList)
  })
})


function chatRoomClick(productChatRoomBoxMid, chatRoomId, chatList) {

  productChatRoomBoxMid.setAttribute('id', `${chatRoomId}`)

  if (chatList !== null) {
    for (var i = 0; i < chatList.length; i++) {
      chatMsgGet(productChatRoomBoxMid, chatList[i], detailMidLeftProfileInfoNickname.textContent)
    }
  }

  let socket = new SockJS(`/stomp/chat`)
  stompClient = Stomp.over(socket)

  connect(stompClient, productChatRoomBoxMid.getAttribute('id'))

  //파일 업로드 구현
  productChatRoomBoxBotSendAttach.addEventListener('click', function () {

    const productChatRoomBoxBotSendAttach = document.querySelector('.product-chat-room-box-bot-send-attach')
    const productChatRoomBoxBotSendAttachFile = document.createElement('input')
    productChatRoomBoxBotSendAttachFile.setAttribute('class', 'product-chat-room-box-bot-send-attach-file')
    productChatRoomBoxBotSendAttachFile.setAttribute('type', 'file')
    productChatRoomBoxBotSendAttachFile.style.display = 'none'
    productChatRoomBoxBotSendAttach.after(productChatRoomBoxBotSendAttachFile)

    productChatRoomBoxBotSendAttachFile.click()

    productChatRoomBoxBotSendAttachFile.addEventListener('change', (e) => {

      let chatFileName

      const file = e.target.files[0]
      chatFileName = file.name

      const chatFileReader = new FileReader();

      if (file !== undefined) {
        chatFileReader.onload = function (e) {
          const fileObject = e.target.result
          productChatRoomBoxBotSendAttachFile.setAttribute('id', `${Array.from(new Uint8Array(fileObject))}`)
          productChatRoomBoxBotSendAttachFile.setAttribute('name', `${chatFileName}`)
        }
      }
    })
  })

  // send 구현
  const productChatRoomBoxBotSendBtn = document.querySelector('.product-chat-room-box-bot-send-btn')
  productChatRoomBoxBotSendBtn.addEventListener('click', function () {

    const productChatRoomBoxBotSendText = document.querySelector('.product-chat-room-box-bot-send-text')

    if (productChatRoomBoxBotSendText.value === '') {
      alert('메세지를 입력해주세요.')
    } else {
      chatDataObject = {
        sendChatRoomId: productChatRoomBoxMid.getAttribute('id'),
        sendChatSender: localStorage.getItem('id'),
        sendChatMsg: productChatRoomBoxBotSendText.value,
        sendChatFile: null
      }

      if (productChatRoomBoxBotSendAttachFile.getAttribute('id') !== null) {

        const chatFileType = productChatRoomBoxBotSendAttachFile.getAttribute('name').split('.')
        chatDataObject.sendChatFile = {
          chatFileName: productChatRoomBoxBotSendAttachFile.getAttribute('name'),
          chatFileData: productChatRoomBoxBotSendAttachFile.getAttribute('id'),
          chatFileType: chatFileType[chatFileType.length - 1]
        }
      }

      send(chatDataObject)
    }
  })

  // disconnect 구현
  const productChatRoomBoxTopShowExit = document.querySelector('.product-chat-room-box-top-show-exit')

  productChatRoomBoxTopShowExit.addEventListener('click', function () {
    productChatRoom.style.display = 'none'
    stompClient.disconnect()
  })
}







