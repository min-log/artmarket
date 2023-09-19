let joinFile = document.querySelector('#join-file')
const joinBtn = document.querySelector('.join-btn')
let joinFileResult

let joinFileReader = new FileReader()

joinBtn.addEventListener("click", function () {

    console.log(joinFile.files[0])
    joinFileReader.readAsDataURL(joinFile.files[0])

    joinFileReader.onload = function (e) {
        joinFileResult = e.target.result
        console.log(joinFileResult)
        console.log(joinFileResult.length)
    }
})