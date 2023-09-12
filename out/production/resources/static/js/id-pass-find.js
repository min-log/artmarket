const find_select_content = [
  
  ["아이디 찾기",
  "가입시 기재하신 정보를 입력해 주세요.",
  "이름",
  "휴대폰 번호",
  "휴대폰 문자로 받기"],

  ["비밀번호 찾기",
  "가입 시 등록하신 이메일 주소를 입력해 주세요.",
  "이메일",
  "",
  "비밀번호 재설정 링크 받기"]

]

const find_select_id = document.querySelector('.find-select-id')
const find_select_password = document.querySelector('.find-select-password')

find_select_id.addEventListener("click",function(){
  find_form_show(find_select_id)
})

find_select_password.addEventListener("click", function(){
  find_form_show(find_select_password)
})



function find_form_show(select){

  select.setAttribute("style","border-bottom: solid 0.2rem");

  const select_class = select.className
  const class_check = select_class.slice(select_class.search('select') + ('select'.length + 1))

  let form_list = []
  form_list.push(document.querySelector('.find-title'))
  form_list.push(document.querySelector('.find-message'))
  form_list.push(document.querySelector('.find-input-list1'))
  form_list.push(document.querySelector('.find-input-list2'))
  form_list.push(document.querySelector('.find-submit'))

  const select_index = class_check === 'id' ? 0 : 1
  
  for(var i = 0; i < form_list.length-1; i++){
    form_list[i].textContent = find_select_content[select_index][i]
  }

  form_list[form_list.length-1].value = find_select_content[select_index][form_list.length-1]
  
  if(class_check === "password"){
    document.querySelector('.find-input-list2').style.display = "none"
    document.querySelector('.find-input-tel').style.display = "none"
    find_select_id.setAttribute("style","")
  }else{
    document.querySelector('.find-input-list2').style.display = ""
    document.querySelector('.find-input-tel').style.display = ""
    find_select_password.setAttribute("style","")
  }
}