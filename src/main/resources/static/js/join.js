 // user-author-check 상태 변경
 if(document.querySelector('.user') && document.querySelector('.author')){
  const user = document.querySelector('.user')
  const author = document.querySelector('.author')
  
  const before_back = `rgba(142, 142, 142, 1)`
  const after_back = `rgba(39, 39, 39, 1)`
  
  user.addEventListener("click",function(){
    user.style.backgroundColor = after_back;
    author.style.backgroundColor = before_back;
  })
  
  author.addEventListener("click",function(){
    author.style.backgroundColor = after_back;
    user.style.backgroundColor = before_back;
  })
}

// 약관 모두 동의 (구현 안함)
const join_check_all = document.querySelector('.join-check-all')
let bool_check = false;
const join_check_list = 3; // 약관 동의 갯수

let check_list = []
for(var i = 1; i <= join_check_list; i++){
  check_list.push(document.querySelector(`.join_check_list${i}`))
}