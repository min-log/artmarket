// 에러 관련
const findUserMapErr = new Map();
const findErrTxt = "visibility:block;margin-left: 6.5rem; font-size: 0.8rem;color: red;";


// form input list 빈값 채크
function findInfoCheck(formInputList) {
	findUserMapErr.clear();
	let findUserInfoStatus = 0; // total 3
	let findUserInfoTotal = formInputList.querySelectorAll("input").length; // total 3
	formInputList.querySelectorAll("input").forEach((item, index) => {
		if (!item.value) {
			findUserInfoStatus -= 1; // total 3
			findUserMapErr.set(index, item);
		} else {
			findUserInfoStatus += 1; // total 3
			findUserMapErr.delete(index);
		}
	});

	if (findUserInfoStatus == findUserInfoTotal) {
		return true;
	} else {

		for (let [key, value] of findUserMapErr) {
			value.parentElement.querySelector("div").textContent = "값을 입력해주세요.";
			value.parentElement.querySelector("div").style = findErrTxt;
		}
		return false;
	}
}




// 아이디 찾기
const findIdBtn = document.getElementById("find-info-id-btn");
const findUserInfo = document.getElementById("find-id-info");
const findUserName = document.getElementById("find-id-name");
const findUserPhone = document.getElementById("find-id-phone");
const findUserEmail = document.getElementById("find-id-email");



findUserInfo.querySelectorAll("input").forEach((item, index) => {
	item.addEventListener('change', function () {
		item.parentElement.querySelector("div").style = "visibility:hidden;margin-left: 6.5rem; font-size: 0.8rem;color: red;";
	})
});


findIdBtn.addEventListener('click', function (event) {
	if (findInfoCheck(findUserInfo) == false) {
		// 동일한 DOM에 걸린 이벤트를 막습니다.
		event.stopPropagation();
		event.stopImmediatePropagation();
		event.preventDefault();
		return false;
	} else {
		console.log(findUserName.value);
		let errCk = false;

		fetch("/find-info", {
			method: 'POST',
			body: JSON.stringify({
				findIdName: findUserName.value,
				findIdPhone: findUserPhone.value,
				findIdEmail: findUserEmail.value
			}),
			headers: {
				'content-type': 'application/json'
			}
		}).then(response => {
			if (response.status == 400) {
				errCk = true;
			} else if (response.status == 401) {
				errCk = true;
			} else if (response.status == 200) {
				console.log("성공");
			}
			return response.json();
		}).then(data => {
			// err start
			if (errCk == true) {
				if (data.dontFindIdMsg == null) {
					let errArray = [];
					errArray = data;
					console.log(errArray);
					for (let i = 0; i < errArray.length; i++) {
						switch (errArray[i].findIdErrorParam) {
							case "findIdName": {
								findUserName.parentElement.querySelector("div").innerText = errArray[i].findIdErrorMsg;
								findUserName.parentElement.querySelector("div").style = findErrTxt;
								break;
							}
							case "findIdPhone": {
								findUserPhone.parentElement.querySelector("div").innerText = errArray[i].findIdErrorMsg;
								findUserPhone.parentElement.querySelector("div").style = findErrTxt;
								break;
							}
							case "findIdEmail": {
								findUserEmail.parentElement.querySelector("div").innerText = errArray[i].findIdErrorMsg;
								findUserEmail.parentElement.querySelector("div").style = findErrTxt;
								break;
							}
						}
					}
				} else {
					alert(data.dontFindIdMsg);
				}
				// err end
			} else {
				// 성공 시 // 로그인 아이디 화면 노출
				console.log(data);
				let successId = data.findLoginId;
				let successContent = '<div class="con-title">회원 아이디</div>';
				successContent += '<div class="" style="text-align: center">아이디 찾기가 성공했습니다.</div>';
				successContent += '<label style="margin: 2rem 0 0;display: block;\n">아 이 디 <input value="' + successId + '" type="text" style="margin-left: 2.5rem; padding: 0.6rem 0.6rem 0.6rem 0.5rem; border-radius: 0.5rem; outline: none; border: 0.1rem solid rgba(41, 45, 50, 0.3);box-sizing:border-box;width:12rem" readonly></label>';
				successContent += '<div style="margin-top:3rem;display: flex;justify-content:center;"><a class="btn-type01 " href="/login.html">로그인하러 가기</a></div>';
				findUserInfo.innerHTML = successContent;

			}


		});
	}
})



// 비밀번호 찾기
const findPwBtn = document.getElementById("find-info-pw-btn");
const findUserPwInfo = document.getElementById("find-pw-info");
const findUserPwName = document.getElementById("find-pw-name");
const findUserPwId = document.getElementById("find-pw-id");
const findUserPwEmail = document.getElementById("find-pw-email");

findUserPwInfo.querySelectorAll("input").forEach((item, index) => {
	item.addEventListener('change', function () {
		item.parentElement.querySelector("div").style = "visibility:hidden;margin-left: 6.5rem; font-size: 0.8rem;color: red;";
	})
});

findPwBtn.addEventListener('click', function (event) {
	if (findInfoCheck(findUserPwInfo) == false) {
		// 동일한 DOM에 걸린 이벤트를 막습니다.
		event.stopPropagation();
		event.stopImmediatePropagation();
		event.preventDefault();
		return false;
	} else {
		console.log(findUserPwName.value);
		let errCk = false;
		fetch("/find-info", {
			method: 'POST',
			body: JSON.stringify({
				findPassName: findUserPwName.value,
				findPassLoginId: findUserPwId.value,
				findPassEmail: findUserPwEmail.value
			}),
			headers: {
				'content-type': 'application/json'
			}
		}).then(response => {
			if (response.status == 400) {
				errCk = true;
				return response.json();
			} else if (response.status == 401) {
				errCk = true;
				return response.json();
			} else if (response.status == 200) {
				console.log("성공");
			}
		}).then(data => {
			// err start
			if (errCk == true) {
				if (data.dontFindPassMsg == null) {
					let errArray = [];
					errArray = data;
					console.log(errArray);
					for (let i = 0; i < errArray.length; i++) {
						switch (errArray[i].findIdErrorParam) {
							case "findPassName": {
								findUserPwName.parentElement.querySelector("div").innerText = errArray[i].findIdErrorMsg;
								findUserPwName.parentElement.querySelector("div").style = findErrTxt;
								break;
							}
							case "findPassLoginId": {
								findUserPwId.parentElement.querySelector("div").innerText = errArray[i].findIdErrorMsg;
								findUserPwId.parentElement.querySelector("div").style = findErrTxt;
								break;
							}
							case "findPassEmail": {
								findUserPwEmail.parentElement.querySelector("div").innerText = errArray[i].findIdErrorMsg;
								findUserPwEmail.parentElement.querySelector("div").style = findErrTxt;
								break;
							}
							default: {
								alert(errArray[i].findPassErrorParam);
								break;
							}
						}
					}
				} else {
					alert(data.dontFindPassMsg);
				}
				// err end
			} else {
				// 성공 시 암호 확인
				console.log("/ 성공 시 암호 확인\n");
				console.log(data);
				let successContent = '<div class="con-title">회원 비밀번호 재설정</div>';
				successContent += '<div class="" style="text-align: center">인증번호가 메일로 발송 되었습니다.<br />인증을 완료해주세요.</div>';
				successContent += '<label style="margin: 2rem 0 0;display: block;">아 이 디 <input id="find-new-id"  value="' + findUserPwId.value + '" type="text" style="margin-left: 2.5rem; padding: 0.6rem 0.6rem 0.6rem 0.5rem; border-radius: 0.5rem; outline: none; border: 0.1rem solid rgba(41, 45, 50, 0.3);box-sizing:border-box;width:12rem" readonly></label>';
				successContent += '<label style="margin: 2rem 0 0;display: block;">인증번호 <input id="find-new-pw-code" type="text" style="margin-left: 2.5rem; padding: 0.6rem 0.6rem 0.6rem 0.5rem; border-radius: 0.5rem; outline: none; border: 0.1rem solid rgba(41, 45, 50, 0.3);box-sizing:border-box;width:12rem"></label>';
				successContent += '<div style="margin-top:3rem;display: flex;justify-content:center;"><button id="find-new-pw-btn" class="btn-type01">다음</button></div>';
				findUserPwInfo.innerHTML = successContent;
				findNewPw(findUserPwId.value, data.memberId);
			}
		});
	}
});



// 비밀번호 찾기 - 인증
function findNewPw(pwId, userId) {
	console.log("비밀번호 찾기 인증");
	const findNewPwBtn = document.getElementById("find-new-pw-btn");
	const findNewPwCode = document.getElementById("find-new-pw-code");

	findNewPwBtn.addEventListener('click', function () {
		console.log(findNewPwCode.value);

		fetch("/find-info", {
			method: 'POST',
			body: JSON.stringify({
				updateId: userId,
				updateToken: findNewPwCode.value
			}),
			headers: {
				'content-type': 'application/json'
			}
		}).then(response => {
			if (response.status == 400) {
				errCk = true;
				return response.json();
			} else if (response.status == 401) {
				errCk = true;
				return response.json();
			} else if (response.status == 200) {
				console.log("인증 성공");
			}
		}).then(data => {
			// err start
			if (errCk == true) {
				if (data.dontFindPassMsg == null) {
					let errArray = [];
					errArray = data;
					console.log(errArray);
					for (let i = 0; i < errArray.length; i++) {
						switch (errArray[i].findIdErrorParam) {
							default: {
								alert(errArray[i].findPassErrorParam);
								break;
							}
						}
					}
				} else {
					alert(data.dontFindPassMsg);
				}
				// err end
			} else {
				// 성공 시 암호 확인
				let successContent = '<div class="con-title">회원 비밀번호 재설정</div>';
				successContent += '<div class="" style="text-align: center">새로운 비밀번호를 입력하세요.</div>';
				successContent += '<label style="margin: 2rem 0 0;display: block;">아 이 디 <input id="pw-new-id"  value="' + pwId + '" type="text" style="margin-left: 2.5rem; padding: 0.6rem 0.6rem 0.6rem 0.5rem; border-radius: 0.5rem; outline: none; border: 0.1rem solid rgba(41, 45, 50, 0.3);box-sizing:border-box;width:12rem" readonly></label>';
				successContent += '<label style="margin: 2rem 0 0;display: block;">비밀번호 <input id="pw-new" type="text" style="margin-left: 2.5rem; padding: 0.6rem 0.6rem 0.6rem 0.5rem; border-radius: 0.5rem; outline: none; border: 0.1rem solid rgba(41, 45, 50, 0.3);box-sizing:border-box;width:12rem"></label>';
				successContent += '<div style="margin-top:3rem;display: flex;justify-content:center;"><button id="pw-new-btn" class="btn-type01">비밀번호 변경하기</button></div>';
				findUserPwInfo.innerHTML = successContent;
				ReplacePw(pwId, userId);
			}
		});
	});

}


//비밀번호 변경
function ReplacePw(pwId, userId) {
	console.log("비밀번호 변경");
	const newPwBtn = document.getElementById("pw-new-btn");
	const newPw = document.getElementById("pw-new");
	fetch("/update-password", {
		method: 'PATCH',
		body: JSON.stringify({
			updatePassId: userId,
			updatePassword: newPw.value
		}),
		headers: {
			'content-type': 'application/json'
		}
	}).then(response => {
		if (response.status == 400) {
			errCk = true;
			return response.json();
		} else if (response.status == 401) {
			errCk = true;
			return response.json();
		} else if (response.status == 200) {
			console.log("변경성공");
		}
	}).then(data => {
		// err start
		if (errCk == true) {
			if (data.passwordMatchMsg == null) {
				let errArray = [];
				errArray = data;
				console.log(errArray);
				for (let i = 0; i < errArray.length; i++) {
					switch (errArray[i].findIdErrorParam) {
						default: {
							alert(errArray[i].findPassErrorParam);
							break;
						}
					}
				}
			} else {
				alert(data.passwordMatchMsg);
			}
			// err end
		} else {
			//성공시 로그인페이지 이동
			alert("비밀번호 변경 성공");

		}
	});

}
