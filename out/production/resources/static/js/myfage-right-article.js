// ------------------------------ myfage-right-article ------------------------------

function myfageArticleRightAddTag() {
  myfageRight.insertAdjacentHTML('beforeend', `<div class="myfage-right-article-manage-box">
    <div class="myfage-right-article-manage-title">작품 등록하기</div>
    <div class="myfage-right-article-manage-category">
      <div class="myfage-right-article-manage-category-select-title">
        카테고리
      </div>
      <div class="myfage-right-article-manage-category-select-box">
        <div
          class="myfage-right-article-manage-category-select-box-illust" name="0">
          ILLUST
        </div>
        <div class="myfage-right-article-manage-category-select-box-live" name="0">
          LIVE2D•3D
        </div>
        <div
          class="myfage-right-article-manage-category-select-box-design" name="0">
          DESIGN
        </div>
        <div
          class="myfage-right-article-manage-category-select-box-video" name="0">
          VIDEO
        </div>
        <div
          class="myfage-right-article-manage-category-select-box-character" name="0">
          CHARACTER
        </div>
      </div>
      <div
        class="myfage-right-article-manage-category-select-box-caution"
      >
        <img
          class="myfage-right-article-manage-caution-img"
          src="./css/icon/error.png"
        />카테고리는 필수로 1개 선택하셔야합니다.
      </div>
    </div>
    <div class="myfage-right-article-manage-title-box">
      <div class="myfage-right-article-manage-title-box-label">
        작품제목
      </div>
      <textarea
        class="myfage-right-article-manage-title-box-value"
        cols="53"
        rows="1"
      ></textarea>
    </div>
    <div class="myfage-right-article-manage-intro-box">
      <div class="myfage-right-article-manage-intro-box-label">
        작품설명
      </div>
      <textarea
        class="myfage-right-article-manage-intro-box-value"
        cols="53"
        rows="10"
      ></textarea>
    </div>
    <div class="myfage-right-article-manage-file-box">
      <div class="myfage-right-article-manage-intro-file-label">
        파일첨부
      </div>
      <div class="myfage-right-article-manage-intro-file-img">
        <img
          class="myfage-right-article-manage-intro-file-icon"
          src="./css/icon/article-file-upload-icon.png"
        />
        <input
        class="myfage-right-article-manage-intro-file-value"
        type="file" multiple
      />
      </div>
      <div class="myfage-right-article-manage-intro-file-caution">
        <img
          class="myfage-right-article-manage-caution-img"
          src="./css/icon/error.png"
        />파일은 최소3개~최대5개까지 업로드 가능합니다.
      </div>
      <div class="myfage-right-article-manage-intro-file-caution">
        <img
          class="myfage-right-article-manage-caution-img"
          src="./css/icon/error.png"
        />가로길이는 850으로 맞춰주세요.
      </div>
    </div>
    <div class="myfage-right-article-manage-btn-box">
      <div class="myfage-right-article-manage-btn-box-show">미리보기</div>
      <div class="myfage-right-article-manage-btn-box-add">등록하기</div>
    </div>
    </div>
    <img
    class="myfage-right-article-back-img"
    src="./css/img/myfage-right-article.png"
    />`)

  // 카테고리 선택

  const myfageRightArticleManageCategorySelectBox = document.querySelector('.myfage-right-article-manage-category-select-box')
  let categoryLists = myfageRightArticleManageCategorySelectBox.childNodes

  const myfageRightArticleManageCategorySelectBoxIllust = document.querySelector('.myfage-right-article-manage-category-select-box-illust')
  const myfageRightArticleManageCategorySelectBoxLive = document.querySelector('.myfage-right-article-manage-category-select-box-live')
  const myfageRightArticleManageCategorySelectBoxDesign = document.querySelector('.myfage-right-article-manage-category-select-box-design')
  const myfageRightArticleManageCategorySelectBoxVideo = document.querySelector('.myfage-right-article-manage-category-select-box-video')
  const myfageRightArticleManageCategorySelectBoxCharacter = document.querySelector('.myfage-right-article-manage-category-select-box-character')

  function articleCategoryUnClick(clickTag) {

    clickTag.style.backgroundColor = 'rgba(41, 45, 50, 1)'
    clickTag.style.color = 'white'
    clickTag.style.fontWeight = '500'
    clickTag.style.fontSize = '0.85rem'
    clickTag.style.padding = '0.09rem 2rem'
    clickTag.style.border = 'solid 0.1rem white'
    clickTag.style.borderRadius = '1rem'
    clickTag.style.margin = '0.2rem'
    clickTag.style.cursor = 'pointer'
    clickTag.setAttribute('name', '0')
  }

  function articleCategoryMouseOut(clickTag) {

    if (clickTag.getAttribute('name') === '0') {
      clickTag.style.backgroundColor = 'rgba(41, 45, 50, 1)'
      clickTag.style.color = 'white'
      clickTag.style.fontWeight = '500'
      clickTag.style.fontSize = '0.85rem'
      clickTag.style.padding = '0.09rem 2rem'
      clickTag.style.border = 'solid 0.1rem white'
      clickTag.style.borderRadius = '1rem'
      clickTag.style.margin = '0.2rem'
      clickTag.style.cursor = 'pointer'
    }

  }

  function articleCategoryClick(unClickTag) {
    unClickTag.style.backgroundColor = 'white'
    unClickTag.style.color = 'rgba(41, 45, 50, 1)'
    unClickTag.style.fontWeight = '600'
    unClickTag.style.cursor = 'pointer'
    unClickTag.setAttribute('name', '1')
  }

  function articleCategoryMouseOver(unClickTag) {

    if (unClickTag.getAttribute('name') === '0') {
      unClickTag.style.backgroundColor = 'white'
      unClickTag.style.color = 'rgba(41, 45, 50, 1)'
      unClickTag.style.fontWeight = '600'
      unClickTag.style.cursor = 'pointer'
    }

  }

  function articleCategorySelect() {

    for (var i = 1; i < categoryLists.length; i += 2) {
      articleCategoryUnClick(categoryLists[i])
    }
  }

  myfageRightArticleManageCategorySelectBoxIllust.addEventListener('click', function () {
    articleCategorySelect()
    articleCategoryClick(myfageRightArticleManageCategorySelectBoxIllust)
    localStorage.setItem('artCategory', 'illust')
  })

  myfageRightArticleManageCategorySelectBoxIllust.addEventListener('mouseover', function () {
    articleCategoryMouseOver(myfageRightArticleManageCategorySelectBoxIllust)
  })

  myfageRightArticleManageCategorySelectBoxIllust.addEventListener('mouseout', function () {
    articleCategoryMouseOut(myfageRightArticleManageCategorySelectBoxIllust)
  })

  myfageRightArticleManageCategorySelectBoxLive.addEventListener('click', function () {
    articleCategorySelect()
    articleCategoryClick(myfageRightArticleManageCategorySelectBoxLive)
    localStorage.setItem('artCategory', 'live')
  })

  myfageRightArticleManageCategorySelectBoxLive.addEventListener('mouseover', function () {
    articleCategoryMouseOver(myfageRightArticleManageCategorySelectBoxLive)
  })

  myfageRightArticleManageCategorySelectBoxLive.addEventListener('mouseout', function () {
    articleCategoryMouseOut(myfageRightArticleManageCategorySelectBoxLive)
  })


  myfageRightArticleManageCategorySelectBoxDesign.addEventListener('click', function () {
    articleCategorySelect()
    articleCategoryClick(myfageRightArticleManageCategorySelectBoxDesign)
    localStorage.setItem('artCategory', 'design')
  })

  myfageRightArticleManageCategorySelectBoxDesign.addEventListener('mouseover', function () {
    articleCategoryMouseOver(myfageRightArticleManageCategorySelectBoxDesign)
  })

  myfageRightArticleManageCategorySelectBoxDesign.addEventListener('mouseout', function () {
    articleCategoryMouseOut(myfageRightArticleManageCategorySelectBoxDesign)
  })

  myfageRightArticleManageCategorySelectBoxVideo.addEventListener('click', function () {
    articleCategorySelect()
    articleCategoryClick(myfageRightArticleManageCategorySelectBoxVideo)
    localStorage.setItem('artCategory', 'video')
  })

  myfageRightArticleManageCategorySelectBoxVideo.addEventListener('mouseover', function () {
    articleCategoryMouseOver(myfageRightArticleManageCategorySelectBoxVideo)
  })

  myfageRightArticleManageCategorySelectBoxVideo.addEventListener('mouseout', function () {
    articleCategoryMouseOut(myfageRightArticleManageCategorySelectBoxVideo)
  })

  myfageRightArticleManageCategorySelectBoxCharacter.addEventListener('click', function () {
    articleCategorySelect()
    articleCategoryClick(myfageRightArticleManageCategorySelectBoxCharacter)
    localStorage.setItem('artCategory', 'character')
  })

  myfageRightArticleManageCategorySelectBoxCharacter.addEventListener('mouseover', function () {
    articleCategoryMouseOver(myfageRightArticleManageCategorySelectBoxCharacter)
  })

  myfageRightArticleManageCategorySelectBoxCharacter.addEventListener('mouseout', function () {
    articleCategoryMouseOut(myfageRightArticleManageCategorySelectBoxCharacter)
  })

  // 작품등록 파일 핸들링

  const myfageRightArticleManageIntroFileIcon = document.querySelector(`.myfage-right-article-manage-intro-file-icon`)
  const myfageRightArticleManageIntroFileValue = document.querySelector('.myfage-right-article-manage-intro-file-value')
  const myfageRightArticleManageIntroFileImg = document.querySelector('.myfage-right-article-manage-intro-file-img')

  let articleCount = 0;

  const articleFileDataMap = new Map()
  let articleFileDataMapCount = 1

  function myfageArticleFileUpload() {

    myfageRightArticleManageIntroFileValue.click()

    myfageRightArticleManageIntroFileValue.addEventListener('change', (e) => {

      const file = e.target.files[0]
      myfageRightArticleManageIntroFileValue.value = ''

      const articleFileReader = new FileReader();

      if (file !== undefined) {
        articleFileReader.readAsDataURL(file)
        articleFileDataMap.set(`${articleFileDataMapCount}`, file)
      }

      articleFileReader.onload = function (e) {

        myfageRightArticleManageIntroFileImg.insertAdjacentHTML('beforeend', `
            <div class="myfage-right-article-manage-intro-file-suc-box-${articleCount + 1}">
              <img
              class="myfage-right-article-manage-intro-file-suc"
              src="${e.target.result}"
              /><img class="myfage-right-article-manage-intro-file-del-${articleCount + 1}" src="./css/icon/article-file-delete.png" id="${articleFileDataMapCount}"/>
            </div>`)

        articleCount += 1
        articleFileDataMapCount += 1

        let myfageRightArticleManageIntroFileSucBox = document.querySelector(`.myfage-right-article-manage-intro-file-suc-box-${articleCount}`)
        let myfageRightArticleManageIntroFileDel = document.querySelector(`.myfage-right-article-manage-intro-file-del-${articleCount}`)

        myfageRightArticleManageIntroFileDel.addEventListener('click', function () {
          myfageRightArticleManageIntroFileSucBox.remove()
          articleCount -= 1

          articleFileDataMap.delete(myfageRightArticleManageIntroFileDel.getAttribute('id'))

          if (articleCount < 5) {
            myfageRightArticleManageIntroFileIcon.style.display = 'block'
          }
        })

        if (articleCount === 5) {
          myfageRightArticleManageIntroFileIcon.style.display = 'none'
        }

      }

    })
  }

  myfageRightArticleManageIntroFileIcon.addEventListener('click', function () {
    myfageArticleFileUpload()
  })


  // 작품 등록 에러 메세지

  const articleCategoryErrMsgTag = document.createElement('span')
  const articleTitleErrMsgTag = document.createElement('span')
  const articleDesErrMsgTag = document.createElement('span')
  const articleFileErrMsgTag = document.createElement('span')

  const myfageRightArticleManageCategorySelectTitle = document.querySelector('.myfage-right-article-manage-category-select-title')

  const myfageRightArticleManageTitleBoxLabel = document.querySelector('.myfage-right-article-manage-title-box-label')
  const myfageRightArticleManageTitleBoxValue = document.querySelector('.myfage-right-article-manage-title-box-value')

  const myfageRightArticleManageIntroBoxLabel = document.querySelector('.myfage-right-article-manage-intro-box-label')
  const myfageRightArticleManageIntroBoxValue = document.querySelector('.myfage-right-article-manage-intro-box-value')

  const myfageRightArticleManageIntroFileLabel = document.querySelector('.myfage-right-article-manage-intro-file-label')

  function articleAddErrMsg(articleLabelTag, articleErrTag, articleErrMsg) {
    articleErrTag.textContent = articleErrMsg
    articleErrTag.style.position = 'absolute'
    articleErrTag.style.color = 'rgba(255, 83, 83, 1)'
    articleErrTag.style.fontSize = '0.8rem'
    articleErrTag.style.marginTop = '-1.9rem'
    articleErrTag.style.marginLeft = '5rem'

    if (articleLabelTag === myfageRightArticleManageCategorySelectTitle) {
      articleErrTag.style.marginTop = '-1.5rem'
    }

    if (articleLabelTag === myfageRightArticleManageIntroFileLabel) {
      articleErrTag.style.marginTop = '-1.9rem'
    }

    articleLabelTag.after(articleErrTag)
    articleErrTag.style.display = 'none'
  }

  articleAddErrMsg(myfageRightArticleManageCategorySelectTitle, articleCategoryErrMsgTag, '카테고리를 선택해주세요.')
  articleAddErrMsg(myfageRightArticleManageTitleBoxLabel, articleTitleErrMsgTag, '제목을 입력해주세요.')
  articleAddErrMsg(myfageRightArticleManageIntroBoxLabel, articleDesErrMsgTag, '내용을 입력해주세요.')
  articleAddErrMsg(myfageRightArticleManageIntroFileLabel, articleFileErrMsgTag, '파일을 3개 이상 첨부해주세요.')

  // 작품 등록 에러 메세지 추가 구현
  function articleAddErr() {

    let checkStatus = true

    if (myfageRightArticleManageCategorySelectBoxIllust.getAttribute('name') === '0' &&
      myfageRightArticleManageCategorySelectBoxLive.getAttribute('name') === '0' &&
      myfageRightArticleManageCategorySelectBoxDesign.getAttribute('name') === '0' &&
      myfageRightArticleManageCategorySelectBoxVideo.getAttribute('name') === '0' &&
      myfageRightArticleManageCategorySelectBoxCharacter.getAttribute('name') === '0') {
      checkStatus = false
      articleCategoryErrMsgTag.style.display = 'block'
    } else {
      articleCategoryErrMsgTag.style.display = 'none'
    }

    if (myfageRightArticleManageTitleBoxValue.value === '') {
      checkStatus = false
      articleTitleErrMsgTag.style.display = 'block'
    } else {
      articleTitleErrMsgTag.style.display = 'none'
    }

    if (myfageRightArticleManageIntroBoxValue.value === '') {
      checkStatus = false
      articleDesErrMsgTag.style.display = 'block'
    } else {
      articleDesErrMsgTag.style.display = 'none'
    }

    const myfageRightArticleManageIntroFileSucs = document.querySelectorAll('.myfage-right-article-manage-intro-file-suc')

    if (myfageRightArticleManageIntroFileSucs.length < 3) {
      checkStatus = false
      articleFileErrMsgTag.style.display = 'block'
    } else {
      articleFileErrMsgTag.style.display = 'none'
    }

    return checkStatus;
  }

  // 작품등록 form 생성


  const myfageRightArticleManageBtnBoxAdd = document.querySelector('.myfage-right-article-manage-btn-box-add')

  myfageRightArticleManageBtnBoxAdd.addEventListener('click', function () {

    if (articleAddErr() !== false) {

      let articleAddStatus
      const articleAddFormData = new FormData()

      const myfageRightArticleManageTitleBoxValue = document.querySelector('.myfage-right-article-manage-title-box-value')
      const myfageRightArticleManageIntroBoxValue = document.querySelector('.myfage-right-article-manage-intro-box-value')


      articleAddFormData.append('id', localStorage.getItem('id'))
      articleAddFormData.append('category', localStorage.getItem('artCategory'))
      articleAddFormData.append('articleTitle', myfageRightArticleManageTitleBoxValue.value)
      articleAddFormData.append('articleDetail', myfageRightArticleManageIntroBoxValue.value)

      for (key of articleFileDataMap.keys()) {
        articleAddFormData.append('articleFile', articleFileDataMap.get(key), articleFileDataMap.get(key).name)
      }

      fetch(`${baseUrl}/mypage-articles-in`, {
        method: 'POST',
        body: articleAddFormData
      }).then(response => {
        articleAddStatus = response.status
      }).then(() => {
        if (articleAddStatus === 201) {
          alert('등록이 완료되었습니다.')
          location.reload()
        }
      })


    }
  })
}

