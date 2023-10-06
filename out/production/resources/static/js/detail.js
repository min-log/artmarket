const detailTopTitleValue = document.querySelector('.detail-top-title-value')
const detailTopArticleNumValue = document.querySelector('.detail-top-article-num-value')

const detailMidLeftProfileImgValue = document.querySelector('.detail-mid-left-profile-img-value')
const detailMidLeftProfileInfoNickname = document.querySelector('.detail-mid-left-profile-info-nickname')
const detailMidLeftProfileInfoIntro = document.querySelector('.detail-mid-left-profile-info-intro')

const detailBotIntroValue = document.querySelector('.detail-bot-intro-value')
const detailBotImgLists = document.querySelector('.detail-bot-img-lists')

const productId = 1

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
    detailMidLeftProfileInfoNickname.setAttribute('value', `${data.author.authorNickname}`)
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


// 카카오페이 결제 btn
const detailMidRightPaymentBtn = document.querySelector('.detail-mid-right-payment-btn')

// chat 문의하기
const detailMidRightChatBtn = document.querySelector('.detail-mid-right-chat-btn')
// chat room 변수
const productChatRoomBoxMid = document.querySelector('.product-chat-room-box-mid')
sessionStorage.setItem('chatcurrenttag', `${productChatRoomBoxMid.getAttribute('class')}`)

detailMidRightChatBtn.addEventListener('click', function () {

  productChatRoom.style.display = 'block'

  fetch(`/product`, {
    method: 'POST',
    headers: {
      'content-type': 'application/json'
    },
    body: JSON.stringify({
      askProduct: `${productId}`,
      askMember: `${sessionStorage.getItem('id')}`
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
      chatMsgGet(chatList[i], detailMidLeftProfileInfoNickname.textContent)
    }
  }


  // chat 파일 관련 태그 추가
  const productChatRoomBoxBotSendAttach = document.querySelector('.product-chat-room-box-bot-send-attach')
  const productChatRoomBoxBotSendAttachFile = document.createElement('input')
  productChatRoomBoxBotSendAttachFile.setAttribute('class', 'product-chat-room-box-bot-send-attach-file')
  productChatRoomBoxBotSendAttachFile.setAttribute('type', 'file')
  productChatRoomBoxBotSendAttachFile.style.display = 'none'
  productChatRoomBoxBotSendAttach.after(productChatRoomBoxBotSendAttachFile)

  // chat send btn
  const productChatRoomBoxBotSendBtn = document.querySelector('.product-chat-room-box-bot-send-btn')

  // chat exit btn
  const productChatRoomBoxTopShowExit = document.querySelector('.product-chat-room-box-top-show-exit')

  let socket = new SockJS(`/stomp/chat`)
  stompClient = Stomp.over(socket)

  stompClient.connect({}, function () {

    stompClient.subscribe(`/sub/chat-room/get/${productChatRoomBoxMid.getAttribute('id')}`, (message) => {
      chatMsgGet(JSON.parse(message.body), detailMidLeftProfileInfoNickname.getAttribute('value'))
    })

    //파일 업로드 구현
    productChatRoomBoxBotSendAttach.addEventListener('click', function () {

      productChatRoomBoxBotSendAttachFile.click()

      productChatRoomBoxBotSendAttachFile.addEventListener('change', (e) => {

        let chatFileName

        const file = e.target.files[0]
        chatFileName = file.name

        const chatFileReader = new FileReader();

        if (file !== undefined) {
          chatFileReader.readAsArrayBuffer(file)
        }

        chatFileReader.onload = function (e) {

          const fileObject = e.target.result;
          productChatRoomBoxBotSendAttachFile.setAttribute('id', `${Array.from(new Uint8Array(fileObject))}`)
          productChatRoomBoxBotSendAttachFile.setAttribute('name', `${chatFileName}`)
        }

      })
    })

    productChatRoomBoxBotSendBtn.addEventListener('click', function () {

      const productChatRoomBoxBotSendText = document.querySelector('.product-chat-room-box-bot-send-text')

      if (productChatRoomBoxBotSendText.value === '') {
        alert('메세지를 입력해주세요.')
      } else {
        chatDataObject = {
          sendChatRoomId: productChatRoomBoxMid.getAttribute('id'),
          sendChatSender: sessionStorage.getItem('id'),
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

        stompClient.send(`/pub/chat-room/send`, {}, JSON.stringify(chatDataObject))
        productChatRoomBoxBotSendText.value = ''
      }
    })

    // disconnect 구현
    productChatRoomBoxTopShowExit.addEventListener('click', function () {
      productChatRoom.style.display = 'none'
      stompClient.disconnect()
    })

  },
    function () {
      alert('연결에 실패했습니다.')
    })

}