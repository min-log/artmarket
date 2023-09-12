// 버튼 클릭시 다른 페이지로 이동 -------------------------------------------------------

const move_page = new Map();

function click_move_page(type){
  
  const move_page_key = type.className
  const current_check = move_page_key.slice(0,(move_page_key.search("to"))-1)
  const move_page_value = `${move_page_key.slice(move_page_key.search("to")+'to'.length+1)}`

  if(!move_page.get(move_page_key)){
    move_page.set(`${move_page_key}`,`${move_page_key.slice((move_page_key.search("to")+3))}`)
  }
  
  let move_href = `./${move_page.get(move_page_key)}.html`

  // js로 html 구현한 nav, footer의 href 경로 설정
  if(current_check === 'nav' || current_check === 'footer'){
    if(move_page_value === 'index'){
      move_href = '.' + move_href
    }else{
      move_href = `../html/${move_page_value}.html`
    }
  }
  
  type.addEventListener("click", function(){
    location.href= move_href
  })
}
 
//유효성 검사  -------------------------------------------------------
const emailRegExp = /[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]$/i
const passwordRegExp = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$/

// join.html 항목 유효성 검사 데이터
let info_input_list = []
info_input_list = document.querySelectorAll('.info-input-list')

  for(var i = 0 ; i < info_input_list.length; i++){
    info_input_list[i].setAttribute("onfocusout","check(this)")
  }

// 공통 구현 (login, join에서 구현 못한 유효성 검사 항목이 있습니다.)
const reg_map = new Map()
reg_map.set("email", emailRegExp)
reg_map.set("password", passwordRegExp)
reg_map.set("")

const war_text_map = new Map()
war_text_map.set("email", "이메일 주소를 다시 확인해주세요.")
war_text_map.set("password", "영문, 숫자, 특수문자 조합 8~16자로 입력해주세요.")

let war_message_tag = new Map()

let title
let war_message

function check(input){

  if((reg_map.get(input.type)) === undefined){
    
    input.setAttribute("onfocusout","")

  }else if(!(reg_map.get(input.type)).test(input.value)){

    title = document.querySelector(`#join-${input.type}`)
    war_message = document.createElement("span")
    war_message.className = `${input.type}_war_message`
    war_message.textContent = war_text_map.get(`${input.type}`)

    war_message_tag.set(`${input.type}`,war_message)

    if(!(document.querySelector(`.${input.type}_war_message`))){
      if(title === null){
        return
      }else{
        title.appendChild(war_message_tag.get(`${input.type}`))
      }
    }

  }else if((reg_map.get(input.type)).test(input.value)){
    document.querySelector(`.${input.type}_war_message`).remove()
  }
}

// input placeholder 제어
if(document.querySelectorAll('.info-input-list')){

  const none_placeholder = document.querySelectorAll('.info-input-list')

  none_placeholder.forEach((s)=>{
    let message = s.getAttribute("placeholder")
    s.setAttribute("onfocus","this.placeholder=``")
    s.setAttribute("onblur",`this.placeholder="${message}"`)
  })

}


