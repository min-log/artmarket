

// myfageInfoRightAddTag()

function myfageInfoRightAddTag() {
  if (myfageRight.childNodes) {
    for (myfageRightTag of myfageRight.childNodes) {
      myfageRightTag.remove()
    }
  }

  myfageRight.insertAdjacentHTML('beforeend', `<div class="myfage-right-info-content">
        <div class="myfage-right-info-content-title">
          회원 전환을<br />하고 싶으신가요?
        </div>
        <div class="myfage-right-info-content-author-to-general">
          <div class="myfage-right-info-content-author-to-general-title">
            작가 -> 일반
          </div>
          <ul class="myfage-right-info-content-author-to-general-list">
            <li>완료되지 않은 주문건을 모두 완료하셔야합니다.</li>
            <li>다시 작가로 전환 시 작가 인증을 하셔야합니다.</li>
            <li>등록한 작품은 자동으로 삭제처리 됩니다.</li>
            <li>전환 이후 이전 주문관리 내역은 볼 수 없습니다.</li>
            <li>전환 이후 모든 정보들은 원복 불가능합니다.</li>
          </ul>
          </div>
          <div class="myfage-right-info-content-general-to-author">
            <div class="myfage-right-info-content-general-to-author-title">
              일반 -> 작가
            </div>
            <ul class="myfage-right-info-content-general-to-author-list">
              <li>사전에 계좌정보를 필수로 등록해주셔야합니다.</li>
              <li>신청 이후 내부적인 심사를 거친 후 전환 가능합니다.</li>
              <li>자격에 맞지 않는 경우 전환이 불가능합니다.</li>
              <li>전환 이후 이전 주문관리 내역은 볼 수 없습니다.</li>
              <li>전환 이후 1주일 이내로 작품이 등록되지 않는 경우<br/>
                일반회원으로 자동으로 변경됩니다.</li>
            </ul>
          </div>
          <div class="myfage-right-info-content-bot">
            <div class="myfage-right-info-content-btn-intro">신청 후 철회는 불가, 처리기간 영업일기준 3일~10일</div>
            <input class="myfage-right-info-content-btn" type="button" value="전환 신청하러 가기">
          </div>
        </div>`)
}