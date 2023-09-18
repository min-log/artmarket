const commonNameReg = /^[ㄱ-힣]{2,5}$/
const commonNicknameReg = /^[a-zA-Z0-9가-힣_]{2,10}$/
const commonIdReg = /^[a-z]+[a-z0-9]{5,19}$/
const commonPassReg = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{8,16}$/
const commonPhoneReg = /^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$/