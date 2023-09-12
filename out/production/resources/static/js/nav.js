const container = document.querySelector('container')

const nav = document.createElement("nav")
container.insertBefore(nav,container.firstChild);


// nav-top ---------------------------------------------------
const nav_top = document.createElement("div")
nav_top.className = `nav-top`


// 요소 삽입
const top_div1 = document.createElement("div");
top_div1.setAttribute("class","nav-to-login")
top_div1.textContent = `로그인`

const top_div2 = document.createElement("div");
top_div2.setAttribute("class", "nav-to-myfage")
top_div2.textContent = `마이페이지`


const top_div3 = document.createElement("div");
top_div3.textContent = `고객센터`

nav_top.appendChild(top_div1)
nav_top.appendChild(top_div2)
nav_top.appendChild(top_div3)

nav.appendChild(nav_top)


// nav-mid ---------------------------------------------------

const nav_mid = document.createElement("div")
nav_mid.className = `nav-mid`

// 요소 삽입
const mid_img = document.createElement("img");
mid_img.setAttribute("src", "../css/icon/search-lg.png")

const mid_div1 = document.createElement("div");
mid_div1.textContent = `리뷰`

const mid_div2 = document.createElement("div");
mid_div2.textContent = `가이드`

const mid_div3 = document.createElement("div");
mid_div3.setAttribute("class","nav-to-index")
mid_div3.textContent = `홈`

nav_mid.appendChild(mid_img)
nav_mid.appendChild(mid_div1)
nav_mid.appendChild(mid_div2)
nav_mid.appendChild(mid_div3)

nav.appendChild(nav_mid)

// nav-logo ---------------------------------------------------

const nav_logo = document.createElement("img")
nav_logo.setAttribute("id","nav-logo")
nav_logo.setAttribute("class","nav-to-index")
nav_logo.setAttribute("src","../css/icon/ARTMARKET.png")
nav.appendChild(nav_logo)


// nav-bot ---------------------------------------------------
// 카테고리가 필요 없는 페이지에서 메뉴에서 제외
if(!(document.querySelector('#login-form') || document.querySelector('#join-form'))){

  const nav_bot = document.createElement("div")
  nav_bot.className = `nav-bot`

  // 요소 삽입

  const bot_div1 = document.createElement("div")
  bot_div1.textContent = `캐릭터`

  const bot_div2 = document.createElement("div")
  bot_div2.textContent = `일러스트`

  const bot_div3 = document.createElement("div")
  bot_div3.textContent = `버츄얼 · LIVE2D`

  const bot_div4 = document.createElement("div")
  bot_div4.textContent = `디자인`

  const bot_div5 = document.createElement("div")
  bot_div5.textContent = `웹툰 · 만화`

  nav_bot.appendChild(bot_div1)
  nav_bot.appendChild(bot_div2)
  nav_bot.appendChild(bot_div3)
  nav_bot.appendChild(bot_div4)
  nav_bot.appendChild(bot_div5)

  nav.appendChild(nav_bot)
}



// 이동 ---------------------------------------------------

// index -> login
click_move_page(top_div1)

// index -> myfage
click_move_page(top_div2)

// other page -> index
click_move_page(mid_div3)
click_move_page(nav_logo)
