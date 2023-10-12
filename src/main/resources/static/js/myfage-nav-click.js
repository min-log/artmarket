//처음 시작할 페이지
myfageChatMidAddTag()
myfageChatRightAddTag()

myfageNavChat.addEventListener('click', function () {
    myfageChatMidAddTag()
    myfageChatRightAddTag()
})


myfageNavArticle.addEventListener('click', function () {
    myfageArticleMidAddTag()
    myfageArticleRightAddTag()
})

myfageNavInfo.addEventListener('click', function () {
    myfageInfoMidAddTag()

    function myfageInfoRightAddTag() {
        if (myfageRight.childNodes) {
            const childNodesArray = Array.from(myfageRight.childNodes);
            for (const myfageRightTag of childNodesArray) {
                myfageRightTag.remove();
            }
        }
    }

    myfageInfoRightAddTag()
})

myfageNavOrder.addEventListener('click', function () {
    myfageOrderMidAddTag()
})
