const detailTopTitleValue = document.querySelector('.detail-top-title-value')
const detailTopArticleNumValue = document.querySelector('.detail-top-article-num-value')

const detailMidLeftProfileImgValue = document.querySelector('.detail-mid-left-profile-img-value')
const detailMidLeftProfileInfoNickname = document.querySelector('.detail-mid-left-profile-info-nickname')
const detailMidLeftProfileInfoIntro = document.querySelector('.detail-mid-left-profile-info-intro')

const detailBotIntroValue = document.querySelector('.detail-bot-intro-value')
const detailBotIntroLabel = document.querySelector('.detail-bot-intro-label')


const detailBotImgLists = document.querySelector('.detail-bot-img-lists')

const productChatRoom = document.querySelector('.product-chat-room')
productChatRoom.style.display = 'none'

productDetailShow()

// product 정보 get
function productDetailShow() {
    fetch(`/product/${sessionStorage.getItem('detailproduct')}`, {
        method: 'GET',
        headers: {
            'content-type': 'application/json'
        }
    }).then(response => {
        return response.json()
    }).then(data => {
        //console.log(data);
        detailTopArticleNumValue.textContent = `${sessionStorage.getItem('detailproduct')}`
        detailTopArticleNumValue.setAttribute('name', `${sessionStorage.getItem('detailproduct')}`)
        detailTopTitleValue.textContent = `${data.productDetail.title}`

        if (data.author.authorPofile == '') {
            detailMidLeftProfileImgValue.setAttribute('src', './css/icon/default-profile-img.png')
        } else {
            detailMidLeftProfileImgValue.setAttribute('src', `data:image/jpeg;base64,${data.author.authorPofile}`)
        }

        detailMidLeftProfileInfoNickname.textContent = `${data.author.authorNickname}`
        detailMidLeftProfileInfoNickname.setAttribute('value', `${data.author.authorNickname}`)
        detailMidLeftProfileInfoIntro.textContent = `${data.author.authorIntro}`
        detailMidLeftProfileInfoIntro.setAttribute('value', `${data.author.authorIntro}`)
        const detailBotIntroValues = data.productDetail.productDetail.split('\\n')

        for (var i = 0; i < detailBotIntroValues.length; i++) {
            let detailBotIntroText = document.createElement('div')
            detailBotIntroText.setAttribute('class', 'detail-bot-intro-value')
            detailBotIntroText.textContent = `${detailBotIntroValues[i]}`
            detailBotIntroLabel.after(detailBotIntroText)
        }

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


// chat 문의하기
const detailMidRightChatBtn = document.querySelector('.detail-mid-right-chat-btn')
// chat room 변수
const productChatRoomBoxMid = document.querySelector('.product-chat-room-box-mid')
sessionStorage.setItem('chatcurrenttag', `${productChatRoomBoxMid.getAttribute('class')}`)

// product id

detailMidRightChatBtn.addEventListener('click', function () {

    const detailTopArticleNumValue = document.querySelector('.detail-top-article-num-value')


    productChatRoom.style.display = 'block'

    const detailMidLeftProfileInfoNickname = document.querySelector('.detail-mid-left-profile-info-nickname')
    if (sessionStorage.getItem('id') === null) {
        alert('로그인 후 채팅 가능합니다.')
        productChatRoom.style.display = 'none'
    } else if (sessionStorage.getItem('nickname') === detailMidLeftProfileInfoNickname.getAttribute('value')) {
        alert('본인 작품에는 문의할 수 없습니다.')
        productChatRoom.style.display = 'none'
    } else {
        fetch(`/product`, {
            method: 'POST',
            headers: {
                'content-type': 'application/json'
            },
            body: JSON.stringify({
                askProduct: `${detailTopArticleNumValue.getAttribute('name')}`,
                askMember: `${sessionStorage.getItem('id')}`
            })
        }).then(response => {
            if (response.status === 200) {
                return response.json()
            }
        }).then(data => {
            chatRoomClick(productChatRoomBoxMid, data.chatRoomId, data.chatList)
        })
    }
})


function chatRoomClick(productChatRoomBoxMid, chatRoomId, chatList) {

    productChatRoomBoxMid.setAttribute('id', `${chatRoomId}`)

    if (chatList !== null) {
        for (var i = 0; i < chatList.length; i++) {
            chatMsgGet(chatList[i], detailMidLeftProfileInfoNickname.textContent)
        }
    }
    productChatRoomBoxMid.scrollTop = productChatRoomBoxMid.scrollHeight;


    // chat 파일 관련 태그 추가
    //const myfageChatRoomBoxBotSendAttach = document.querySelector('.myfage-right-chat-box-bot-send-attach')
    const ChatRoomBoxBotSendAttachFile = document.getElementById('fileMsg');
    let chatFileBox = document.querySelector("#fileBox");
    let chatTxtFrom = document.querySelector(".product-chat-room-box-bot-send-text")



    // chat send btn
    const productChatRoomBoxBotSendBtn = document.querySelector('.product-chat-room-box-bot-send-btn')

    // chat exit btn
    const productChatRoomBoxTopShowExit = document.querySelector('.product-chat-room-box-top-show-exit')

    let socket = new SockJS(`/stomp/chat`)
    stompClient = Stomp.over(socket)

    stompClient.connect({}, function () {

            stompClient.subscribe(`/sub/chat-room/get/${productChatRoomBoxMid.getAttribute('id')}`, (message) => {
                chatMsgGet(JSON.parse(message.body), detailMidLeftProfileInfoNickname.getAttribute('value'))
                productChatRoomBoxMid.scrollTop = productChatRoomBoxMid.scrollHeight;
            })


            //파일 업로드 구현
            ChatRoomBoxBotSendAttachFile.addEventListener('click', function () {
                console.log("클릭");
                ChatRoomBoxBotSendAttachFile.addEventListener('change',function(){
                    var fileList = ChatRoomBoxBotSendAttachFile.files ;
                    // 읽기
                    var reader = new FileReader();
                    reader.readAsDataURL(fileList[0]);
                    //로드 한 후
                    reader.onload = function  () {
                        let fileType = fileList.type;
                        if(fileList[0].type == "image/png" || fileList[0].type == "image/jpeg"){
                            chatFileBox.style.display = 'block';
                            chatTxtFrom.style = 'padding-left:3rem';
                            chatFileBox.src=  reader.result;
                        }
                    };
                });
            });




            productChatRoomBoxBotSendBtn.addEventListener('click', function () {
                let fileList = ChatRoomBoxBotSendAttachFile.files[0];

                const productChatRoomBoxBotSendText = document.querySelector('.product-chat-room-box-bot-send-text')

                if (productChatRoomBoxBotSendText.value === '') {
                    alert('메세지를 입력해주세요.')
                } else {
                    //파일이 있을경우
                    if (fileList != null) {
                        // 웹소켓에서 파일객체는 직렬화 해서 전달 받아야한다.
                        // 바이트 배열로 변환하여 전송
                        let reader = new FileReader();
                        reader.onload = function (event) {
                            let fileContent = event.target.result; // 파일 내용
                            stompClient.send(`/pub/chat-room/send`, {}, JSON.stringify({
                                sendChatRoomId: productChatRoomBoxMid.getAttribute('id'),
                                sendChatSender: sessionStorage.getItem('id'),
                                sendChatMsg: productChatRoomBoxBotSendText.value,
                                sendChatFile: {
                                    chatFileName: fileList.name,
                                    chatFileData:  Array.from(new Uint8Array(fileContent)),
                                    chatFileType: fileList.type
                                }
                            }));

                        };

                        reader.readAsArrayBuffer(fileList); // 파일을 바이너리로 읽기
                    }else{
                        // 일반 메시지
                        stompClient.send(`/pub/chat-room/send`, {}, JSON.stringify({
                            sendChatRoomId: productChatRoomBoxMid.getAttribute('id'),
                            sendChatSender: sessionStorage.getItem('id'),
                            sendChatMsg: productChatRoomBoxBotSendText.value,
                            sendChatFile: null
                        }));
                    }

                    //초기화
                    setTimeout(function () {
                        productChatRoomBoxBotSendText.value = '';
                        if(chatFileBox != null){
                            chatFileBox.style = "display:none";
                            productChatRoomBoxBotSendText.style = '';
                            ChatRoomBoxBotSendAttachFile.value ='';
                        }
                    },100);

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

// 카카오페이 결제 btn
const detailMidRightPaymentBtn = document.querySelector('.detail-mid-right-payment-btn');

detailMidRightPaymentBtn.addEventListener('click', function () {
    const deadline = document.querySelector('.detail-mid-right-deadline-btn').value;
    const amount = document.querySelector('.detail-mid-right-payment-cost').value;
    const deadlineDate = new Date(deadline);
    const amountValue = parseInt(amount);
    const requestData = {
        memberId: sessionStorage.getItem('id'),
        productId: sessionStorage.getItem('detailproduct'),
        nickname: sessionStorage.getItem('nickname'),
        productName: sessionStorage.getItem('detailproduct'),
        quantity: parseInt("1"),
        totalAmount: amountValue,
        deadline: deadlineDate.toISOString(),
        orderStatus: '결제 대기'
    };

    fetch('http://localhost:8070/ready', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestData)
    })
        .then(response => response.json())
        .then(data => {
            alert('결제 창으로 이동합니다.');

            const popupUrl = data.next_redirect_pc_url;
            const popupWindow = window.open(popupUrl, '_blank', 'width=600,height=600');
        })
        .catch(error => console.error('에러:', error));
});

