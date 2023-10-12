const categorySearchBtn = document.querySelector(".cateogry-search-btn");
const categorySearchInput = document.querySelector(".cateogry-keyword-search-box");
const categorySearchContent = document.querySelector('.category-content');
const categorySelect = document.querySelector(".category-filter-pop-cur");


categorySearchBtn.addEventListener('click',function (){
    let categorySearchTxt =categorySearchInput.value;
    if (categorySearchTxt == ""){
        alert("검색 내용을 입력해주세요.");
    }else{
        fetch('/category/keyword/' + categorySearchTxt,{
            method:'GET'
        }).then(response=>{
            return response.json();
        }).then(results=>{
            categorySearchContent.innerHTML = "";
            categorySearchInput.value = "";
            if(results.products.length != 0){
                for (var i = 0; i < results.products.length; i++) {
                    categorySearchContent.insertAdjacentHTML('beforeend', `
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
            }else{
                categorySearchContent.insertAdjacentHTML('beforeend',"<div class='category-list-none'>"
                    + "<img src='../css/img/icon-info.png' alt='정보' />"
                    + "<p class='category-list-none-title'>\"<b>" + categorySearchTxt + "</b>\" 검색 내역이 없습니다.</p>"
                    + "<p>올바른 내용을 검색해주세요</p>"
                    + "</div>");
            }
        });
    }

})


function categorySelectChange(){

    let categorySelectValue = categorySelect.options[categorySelect.selectedIndex].value;
    console.log(categorySelectValue);

    fetch('/category/array/' + categorySelectValue,{
        method:'GET'
    }).then(response=>{
        return response.json();
    }).then(results=>{
        categorySearchContent.innerHTML = "";
        for (var i = 0; i < results.products.length; i++) {
            categorySearchContent.insertAdjacentHTML('beforeend', `
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
    });

}