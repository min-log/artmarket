const commonNameReg = /^[ㄱ-힣]{2,5}$/
const commonIdReg = /^[a-z]+[a-z0-9]{5,19}$/
const commonNicknameReg = /^[a-zA-Z0-9가-힣_]{2,10}$/
const commonPassReg = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{8,16}$/
const commonPhoneReg = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/
const commonEmailReg = /^[a-zA-Z0-9+-\_.]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/
const commonTokenReg = /\d\d\d\d\d\d/i

const baseUrl = 'http://localhost:8070'

function fadeIn(fadeTag) {
    let fadeOpacity = 0;
    fadeTag.style.opacity = fadeOpacity

    let IntervalFade = setInterval(function () {
        if (fadeOpacity < 1) {
            fadeOpacity = fadeOpacity + 0.1
            fadeTag.style.opacity = fadeOpacity
        } else {
            clearInterval(IntervalFade)
        }
    }, 15)
}

function fadeOut(fadeTag) {
    let fadeOpacity = 1;
    fadeTag.style.opacity = fadeOpacity

    let IntervalFade = setInterval(function () {
        if (fadeOpacity < 0) {
            fadeOpacity = fadeOpacity - 0.1
            fadeTag.style.opacity = fadeOpacity
        } else {
            clearInterval(IntervalFade)
        }
    }, 40)
}