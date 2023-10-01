const myfageMid = document.querySelector('.myfage-mid')

function myfageChatMidAddTag() {
    myfageMid.insertAdjacentHTML('afterend', `<div class="myfage-chat-box">
    <div class="myfage-chat-not-check">안 읽은 메세지만 보기</div>
    <div class="myfage-chat-list">
        <div class="myfage-chat-content">
            <div class="myfage-chat-profile">
                <div class="myfage-chat-profile-identity">작가</div>
                <div class="myfage-chat-profile-img">
                    <img class="myfage-chat-profile-img-tag" src="./css/icon/myfage-profile-default.png" />
                </div>
            </div>
            <div class="myafge-chat-info">
                <div class="myfage-chat-info-top">
                    <div class="myfage-chat-info-top-nickname"></div>
                    <div class="myfage-chat-info-top-send-time"></div>
                </div>
                <div class="myfage-chat-info-msg"></div>
            </div>
        </div>
        <div class="myfage-chat-delete">
            <img class="myfage-chat-delete-img" src="./css/icon/chat-exit.png" />
        </div>
    </div>
</div>`)
}

myfageChatMidAddTag()

myfageNavChat.addEventListener('click', function () {
    myfageChatMidAddTag()
})