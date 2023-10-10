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

//탭 버튼 구현
const tabBtnWrap = document.getElementById("tab-btn-wrap");
if (tabBtnWrap != null) {
	const tabBtnList = tabBtnWrap.querySelectorAll(".tab-btn");
	const tabContWrap = document.getElementById("tab-content-wrap");
	const tabContList = tabContWrap.querySelectorAll(".tab-content");
	tabBtnList.forEach((tab, index) => {
		tab.addEventListener("click", function (e) {
			e.preventDefault();
			for (var j = 0; j < tabBtnList.length; j++) {
				tabBtnList[j].classList.remove('tab-active');
				tabContList[j].classList.remove('tab-active');
				// 컨텐츠 내부 폼이 존재하면 내용 리셋
				if (tabContList[j].querySelector("form") != null) {
					tabContList[j].querySelector("form").reset();
				}
			}
			tab.classList.add("tab-active");
			tabContList[index].classList.add("tab-active");
		});
	});
}