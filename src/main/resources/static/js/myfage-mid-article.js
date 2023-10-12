// ------------------------------ myfage-mid-article ------------------------------

function myfageArticleMidAddTag() {

    if (myfageMid.childNodes) {
        const childNodesArray = Array.from(myfageMid.childNodes);
        for (const myfageMidTag of childNodesArray) {
            myfageMidTag.remove();
        }
    }

    myfageMid.insertAdjacentHTML('beforeend', `<div class="myfage-mid-article-box">
      <div class="myfage-mid-article-box-top">
<!--        <div class="myfage-mid-article-box-top-filter">
          <select class="myfage-mid-article-box-top-filter-category">
            <option >카테고리</option>
            <option value="ILLUST">ILLUST</option>
            <option value="LIVE">LIVE2D•3D</option>
            <option value="CHARACTER">CHARACTER</option>
            <option value="DESIGN">DESIGN</option>
            <option value="VIDEO">VIDEO</option>
          </select>
          <input type="date" date-placeholder="시작일" class="myfage-mid-article-box-top-start-date" />
          <input type="date" date-placeholder="종료일" class="myfage-mid-article-box-top-end-date" />
        </div>-->
<!--        <div class="myfage-mid-article-box-top-manage">-->
<!--          <div class="myfage-mid-article-box-top-manage-onOff">-->
<!--            <div class="myfage-mid-article-box-top-manage-onOff-label">의뢰</div>-->
<!--            <img class="myfage-mid-article-box-top-manage-onOff-icon" src="./css/icon/article-on.png">-->
<!--          </div>-->
<!--        </div>-->
      </div>
      <div class="myfage-mid-article-box-top-manage-onOff-label"><strong>등록 작품</strong></div>
      
      <div class="myfage-mid-article-box-bot"></div>
      </div>`)

    const myfageMidArticleBoxBot = document.querySelector('.myfage-mid-article-box-bot')
    const minWidth = '850px';
    myfageMidArticleBoxBot.style.minWidth = minWidth;
    fetch(`${baseUrl}/mypage-articles/${sessionStorage.getItem('id')}`, {
        method: 'GET',
        headers: {
            'content-type': 'application/json'
        }
    }).then(respone => {
        articleGetStatus = respone.status
        return respone.json()
    }).then((data) => {
        // 작품 데이터가 없는 경우
        if (data.length === 0) {
            myfageMidArticleBoxBot.insertAdjacentHTML('beforeend', `<div class="myfage-mid-article-box-bot-list">등록한 작품이 없습니다. 우측 작품 등록하기에서 작품을 등록해주세요.</div>`);
        } else {
            for (var i = 0; i < data.length; i++) {

                data[i].articleCategory = data[i].articleCategory.toUpperCase()

                if (data[i].articleCategory === 'LIVE') {
                    data[i].articleCategory = 'LIVE2D•3D'
                }

                myfageMidArticleBoxBot.insertAdjacentHTML('beforeend', `<div class="myfage-mid-article-box-bot-list" data-index="${i}" id="${data[i].articleDate}">
        <div class="myfage-mid-article-box-bot-list-thumb">
          <img class="myfage-mid-article-box-bot-list-thumb-img" src="data:image/jpeg;base64,${data[i].articleImgs[0]}"/>
          <img class="myfage-mid-article-box-bot-list-thumb-img" src="data:image/jpeg;base64,${data[i].articleImgs[1]}"/>
          <img class="myfage-mid-article-box-bot-list-thumb-img" src="data:image/jpeg;base64,${data[i].articleImgs[2]}"/>
        </div>
        <div class="myfage-mid-article-box-bot-list-content">
          <div class="myfage-mid-article-box-bot-list-right-top">
            <div class="myfage-mid-article-box-bot-list-right-top-art">
              <div class="myfage-mid-article-box-bot-list-right-top-art-label">작품번호</div>
              <div class="myfage-mid-article-box-bot-list-right-top-art-value">${data[i].articleId}</div>
            </div>
<!--            <div class="myfage-mid-article-box-bot-list-right-top-onOff">-->
<!--              <img class="myfage-mid-article-box-bot-list-right-top-onOff-icon" src="./css/icon/article-off.png">-->
<!--            </div>-->
          </div>
          <div class="myfage-mid-article-box-bot-list-category">
            <div class="myfage-mid-article-box-bot-list-category-label">카테고리</div>
            <div class="myfage-mid-article-box-bot-list-category-value">${data[i].articleCategory}</div>
          </div>
        </br>
          <div class="myfage-mid-article-box-bot-list-order">
            <div class="myfage-mid-article-box-bot-list-order-label">주문</div>
            <div class="myfage-mid-article-box-bot-list-order-value">${data[i].articleTotalOrder}</div>
          </div>
          <div class="myfage-mid-article-box-bot-list-mod-del">
            <div class="myfage-mid-article-box-bot-list-mod" data-art="${data[i].articleId}" data-index="${i}">수정하기</div>
            <div class="myfage-mid-article-box-bot-list-del" data-art="${data[i].articleId}">삭제하기</div>
          </div>
        </div>
    </div>`)
            }
        }
        myfageDeleteArticle();
        myfageModifyArticle();
    })

}
