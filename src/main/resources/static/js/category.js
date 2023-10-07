const categorySelectKeyword = sessionStorage.getItem('selectcategory')
const categoryTitle = document.querySelector('.category-title')

categoryTitle.textContent = categorySelectKeyword.toUpperCase()

if (categorySelectKeyword === 'live') {
  categoryTitle.textContent = 'LIVE2D•3D'
}

fetch(`/category/list/${categorySelectKeyword}`, {
  method: 'GET',
  headers: {
    'content-type': 'application/json'
  }
}).then(response => {
  return response.json()
}).then(results => {
  const categoryContent = document.querySelector('.category-content')
  for (var i = 0; i < results.products.length; i++) {


    categoryContent.insertAdjacentHTML('beforeend', `
        <div class="category-list-box">
        <div class="category-list-box-img-lists">
          <div class="category-list-box-img-list">
            <img
              class="category-list-box-img-value"
              src="data:image/jpeg;base64,${results.products[i].productImgs[0]}"
            />
          </div>
          <div class="category-list-box-img-list">
            <img
              class="category-list-box-img-value"
              src="data:image/jpeg;base64,${results.products[i].productImgs[1]}"
            />
          </div>
          <div class="category-list-box-img-list">
            <img
              class="category-list-box-img-value"
              src="data:image/jpeg;base64,${results.products[i].productImgs[2]}"
            />
          </div>
        </div>
        <div class="category-list-box-title">
          ${results.products[i].title}
        </div>
        <div class="category-list-box-etc">
          <div class="category-list-box-nickname">${results.products[i].proNickname}</div>
          <input
            type="button"
            class="category-list-box-go-detail"
            value="상세페이지 보러가기"
            id="${results.products[i].productId}"
          />
        </div>
      </div>`)
  }


  const categoryGoDetailBtn = document.querySelectorAll('.category-list-box-go-detail')

  for (const categoryGoDetail of categoryGoDetailBtn) {
    categoryGoDetail.addEventListener('click', function () {
      sessionStorage.setItem('detailproduct', categoryGoDetail.getAttribute('id'))
      location.href = 'detail.html'
    })
  }

})

