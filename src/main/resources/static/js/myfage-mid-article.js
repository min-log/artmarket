// ------------------------------ myfage-mid-article ------------------------------

function myfageArticleMidAddTag() {

    myfageMid.insertAdjacentHTML('beforeend', `<div class="myfage-mid-article-box">
      <div class="myfage-mid-article-box-top">
        <div class="myfage-mid-article-box-top-filter">
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
        </div>
        <div class="myfage-mid-article-box-top-manage">
          <div class="myfage-mid-article-box-top-manage-onOff">
            <div class="myfage-mid-article-box-top-manage-onOff-label">의뢰</div>
            <img class="myfage-mid-article-box-top-manage-onOff-icon" src="./css/icon/article-on.png">
          </div>
        </div>
      </div>
      <div class="myfage-mid-article-box-bot">
        <div class="myfage-mid-article-box-bot-list">
          <div class="myfage-mid-article-box-bot-list-thumb">
            <img class="myfage-mid-article-box-bot-list-thumb-img" src="./css/img/profile-test.gif"/>
            <img class="myfage-mid-article-box-bot-list-thumb-img" src="./css/img/profile-test.gif"/>
            <img class="myfage-mid-article-box-bot-list-thumb-img" src="./css/img/profile-test.gif"/>
          </div>
          <div class="myfage-mid-article-box-bot-list-content">
            <div class="myfage-mid-article-box-bot-list-right-top">
              <div class="myfage-mid-article-box-bot-list-right-top-art">
                <div class="myfage-mid-article-box-bot-list-right-top-art-label">작품번호</div>
                <div class="myfage-mid-article-box-bot-list-right-top-art-value"></div>
              </div>
              <div class="myfage-mid-article-box-bot-list-right-top-onOff">
                <img class="myfage-mid-article-box-bot-list-right-top-onOff-icon" src="./css/icon/article-off.png">
              </div>
            </div>
            <div class="myfage-mid-article-box-bot-list-category">
              <div class="myfage-mid-article-box-bot-list-category-label">카테고리</div>
              <div class="myfage-mid-article-box-bot-list-category-value"></div>
            </div>
          </br>
            <div class="myfage-mid-article-box-bot-list-order">
              <div class="myfage-mid-article-box-bot-list-order-label">주문</div>
              <div class="myfage-mid-article-box-bot-list-order-value"></div>
            </div>
            <div class="myfage-mid-article-box-bot-list-mod-del">
              <div class="myfage-mid-article-box-bot-list-mod">수정하기</div>
              <div class="myfage-mid-article-box-bot-list-del">삭제하기</div>
            </div>
          </div>
        </div>
      </div>
      </div>`)

}