let index = 1;

const rclick = document.querySelector('.icon-right')
const lclick = document.querySelector('.icon-left')
const back = document.querySelector('.background')

function auto_right_image(){
  index += 1
  back.style.backgroundImage = `url('../css/image/view${index%2}.png')`
}

function left_image(){
  index -= 1;
  back.style.backgroundImage = `url('../css/image/view${Math.abs(index)%2}.png')`
}

// 아이콘 클릭 시 전환
rclick.addEventListener('click',auto_right_image)
lclick.addEventListener('click',left_image)

// 이미지 자동 전환 
setInterval(auto_right_image,1500)