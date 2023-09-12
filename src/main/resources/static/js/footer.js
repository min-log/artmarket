const footer = document.createElement("footer")

container.appendChild(footer); // nav.js에 있는 container 변수 사용

// footer-left ******************************************

const footer_left = document.createElement("footer-left")
footer_left.className = `footer-left`

// 요소 추가

const foo_div1 = document.createElement("div")
foo_div1.textContent = `회사소개`

const foo_div2 = document.createElement("div")
foo_div2.textContent = `이용약관`

const foo_div3 = document.createElement("div")
foo_div3.textContent = `제휴제안`

const foo_div4 = document.createElement("div")
foo_div4.textContent = `개인정보처리방침`

footer_left.appendChild(foo_div1)
footer_left.appendChild(foo_div2)
footer_left.appendChild(foo_div3)
footer_left.appendChild(foo_div4)

footer.appendChild(footer_left)

// footer-right ******************************************

const footer_right = document.createElement("footer-right")
footer_right.className = `footer-right`

const foo_img1 = document.createElement("img")
foo_img1.setAttribute("src","../css/icon/facebook_1384005.png")

const foo_img2 = document.createElement("img")
foo_img2.setAttribute("src","../css/icon/instagram_1384031.png")

const foo_img3 = document.createElement("img")
foo_img3.setAttribute("src","../css/icon/youtube_1384060.png")


footer_right.appendChild(foo_img1)
footer_right.appendChild(foo_img2)
footer_right.appendChild(foo_img3)


footer.appendChild(footer_right)