const myfageMid = document.querySelector('.myfage-mid')
const myfageRight = document.querySelector('.myfage-right')

myfageOrderMidAddTag()


function myfageOrderMidAddTag() {
    if (myfageMid.childNodes) {
        for (myfageMidTag of myfageMid.childNodes) {
            myfageMidTag.remove()
        }
    }

    if (myfageRight.childNodes) {
        for (myfageRightTag of myfageRight.childNodes) {
            myfageRightTag.remove()
        }
    }


}