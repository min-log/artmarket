

// 작품 수정
function myfageModifyArticle(){
    let myfageModifyArticleBtns = document.querySelectorAll(".myfage-mid-article-box-bot-list-mod");
    let myfageArticleRightModifyBtn = document.querySelector(".myfage-right-article-manage-btn-box-modify");
    let myfageArticleRightRegisterBtn = document.querySelector(".myfage-right-article-manage-btn-box-add");


    const myfageRightArticleManageTitleBoxValue = document.querySelector('.myfage-right-article-manage-title-box-value')
    const myfageRightArticleManageIntroBoxValue = document.querySelector('.myfage-right-article-manage-intro-box-value')


    // 작품등록 파일 핸들링
    const myfageRightArticleManageIntroFileIcon = document.querySelector(`.myfage-right-article-manage-intro-file-icon`)
    const myfageRightArticleManageIntroFileImg = document.querySelector('.myfage-right-article-manage-intro-file-img')



    // 카테고리
    // illust,live,design,video,character
    let categoryName = new Map();
    categoryName.set(1,'illust');
    categoryName.set(2,'live');
    categoryName.set(3,'design');
    categoryName.set(4,'video');
    categoryName.set(5,'character');
    const categoryActiveStyle = "background-color: white; color: rgb(41, 45, 50); font-weight: 600; font-size: 0.85rem; padding: 0.09rem 2rem; border: 0.1rem solid white; border-radius: 1rem; margin: 0.2rem; cursor: pointer;";
    const myfageRightArticleManageCategorySelectBox = document.querySelector('.myfage-right-article-manage-category-select-box');
    const categoryIllust = document.querySelector('.myfage-right-article-manage-category-select-box-illust');
    const categoryLive = document.querySelector('.myfage-right-article-manage-category-select-box-live');
    const categoryDesign = document.querySelector('.myfage-right-article-manage-category-select-box-design');
    const categoryVideo = document.querySelector('.myfage-right-article-manage-category-select-box-video');
    const categoryCharacter = document.querySelector('.myfage-right-article-manage-category-select-box-character');

    let artModifyValue; // productId





    // 1. 수정 작품 가져오기
    myfageModifyArticleBtns.forEach((btn,index) => {
        btn.addEventListener('click',function () {
            artModifyValue = btn.dataset.art;
            console.log(artModifyValue);
            fetch("/product/"+ artModifyValue, {
                method: 'GET',
                headers: {
                    'content-type': 'application/json'
                }
            })
            .then(response => {
                return response.json()
            })
            .then(data => {
                console.log(data);
                // 초기화
                // 저장 버튼 숨김
                myfageArticleRightRegisterBtn.setAttribute("style","display:none;");
                myfageArticleRightModifyBtn.setAttribute("style","display:block;");

                articleCount = 0;
                articleFileDataMapCount = 1;
                articleFileDataMap.clear();
                myfageRightArticleManageIntroFileIcon.style.display = 'block';
                let filelistSet = myfageRightArticleManageIntroFileImg.querySelectorAll('div');
                filelistSet.forEach(function(item){
                    item.remove();
                });

                myfageRightArticleManageTitleBoxValue.value = data.productDetail.title;
                myfageRightArticleManageIntroBoxValue.value = data.productDetail.productDetail;

                // 카테고리
                let category = data.productDetail.category;
                // 카테고리 초기화
                categoryIllust.setAttribute("name","0");
                categoryIllust.setAttribute("style","");
                categoryLive.setAttribute("name","0");
                categoryLive.setAttribute("style","");
                categoryDesign.setAttribute("name","0");
                categoryDesign.setAttribute("style","");
                categoryVideo.setAttribute("name","0");
                categoryVideo.setAttribute("style","");
                categoryCharacter.setAttribute("name","0");
                categoryCharacter.setAttribute("style","");

                switch (category){
                    case categoryName.get(1) : {
                        categoryIllust.setAttribute("name","1");
                        categoryIllust.setAttribute("style",categoryActiveStyle);
                        break;
                    }
                    case categoryName.get(2) : {
                        categoryLive.setAttribute("name","1");
                        categoryLive.setAttribute("style",categoryActiveStyle);
                        break;
                    }
                    case categoryName.get(3) : {
                        categoryDesign.setAttribute("name","1");
                        categoryDesign.setAttribute("style",categoryActiveStyle);
                        break;
                    }
                    case categoryName.get(4) : {
                        categoryVideo.setAttribute("name","1");
                        categoryVideo.setAttribute("style",categoryActiveStyle);
                        break;
                    }
                    case categoryName.get(5) : {
                        categoryCharacter.setAttribute("name","1");
                        categoryCharacter.setAttribute("style",categoryActiveStyle);
                        break;
                    }
                }


                // 이미지 
                for (var i = 0; i < data.productDetail.productDetailImgs.length; i++) {
                    myfageRightArticleManageIntroFileImg.insertAdjacentHTML('beforeend', `
                        <div class="myfage-right-article-manage-intro-file-suc-box-${articleCount + 1}">
                            <img
                            class="myfage-right-article-manage-intro-file-suc"
                            src="data:image/jpeg;base64,${data.productDetail.productDetailImgs[i]}"
                          />
                          <img class="myfage-right-article-manage-intro-file-del myfage-right-article-manage-intro-file-del-${articleCount + 1}" src="./css/icon/article-file-delete.png" id="${articleFileDataMapCount}"/>
                      </div>
                  `);
                    console.log("articleCount : "+articleCount);
                    let file = base64toFile("data:image/jpeg;base64,"+`${data.productDetail.productDetailImgs[i]}`, artModifyValue +'_'+ articleCount +'.jpeg');
                    articleFileDataMap.set(`${articleFileDataMapCount}`, file);
                    articleCount += 1;
                    articleFileDataMapCount += 1;
                }

                if(data.productDetail.productDetailImgs.length == 5) {
                    myfageRightArticleManageIntroFileIcon.style.display = 'none';
                }
                myfageModifyArticleFilesDelte();
            });
       });
    });

    //파일 제거 로직
    function myfageModifyArticleFilesDelte(){
        let myfageRightArticleManageIntroFileDels = document.querySelectorAll(".myfage-right-article-manage-intro-file-del")
        myfageRightArticleManageIntroFileDels.forEach((item,index)=>{
            item.addEventListener('click', function () {
                let itemId = item.getAttribute("id");
                console.log(itemId);
                let myfageRightArticleManageIntroFileSucBox = document.querySelector(".myfage-right-article-manage-intro-file-suc-box-" + itemId);
                myfageRightArticleManageIntroFileSucBox.remove();
                articleCount -= 1;
                articleFileDataMapCount -= 1;
                articleFileDataMap.delete(itemId);
                if (articleCount < 5) {
                    myfageRightArticleManageIntroFileIcon.style.display = 'block'
                }
            });

        });
    }


    //작품 수정
    myfageArticleRightModifyBtn.addEventListener('click',function () {
        const articleAddFormData = new FormData();

        myfageRightArticleManageCategorySelectBox.querySelectorAll("div").forEach((item,index)=>{

           if(item.getAttribute("name") == "1"){
               switch (item.innerText){
                   case "ILLUST": {
                       articleAddFormData.append('articleModCategory', categoryName.get(1));
                       break;
                   }
                   case "LIVE2D•3D" : {
                       articleAddFormData.append('articleModCategory', categoryName.get(2));
                       break;
                   }
                   case "DESIGN" : {
                       articleAddFormData.append('articleModCategory', categoryName.get(3));
                       break;
                   }
                   case "VIDEO" : {
                       articleAddFormData.append('articleModCategory', categoryName.get(4));
                       break;
                   }
                   case "CHARACTER" : {
                       articleAddFormData.append('articleModCategory', categoryName.get(5));
                       break;
                   }
               }
           }
        });

        articleAddFormData.append('articleModProductId', artModifyValue);
        articleAddFormData.append('articleModTitle', myfageRightArticleManageTitleBoxValue.value)
        articleAddFormData.append('articleModDetail', myfageRightArticleManageIntroBoxValue.value )
        for (key of articleFileDataMap.keys()) {
            articleAddFormData.append('articleModImgs', articleFileDataMap.get(key), articleFileDataMap.get(key).name)
        }

        console.log(articleAddFormData);

        fetch(`${baseUrl}/mypage-articles-in`, {
            method: 'PATCH',
            body: articleAddFormData
        })
        .then(response => {
            if (response.status === 204) {
                alert("수정이 성공했습니다.");
                location.reload();
            } else if (response.status === 400) {
                alert("삭제가 실패했습니다.");
            }
        })
        
    });


    function base64toFile(base_data, filename) {
        var arr = base_data.split(','),
            mime = arr[0].match(/:(.*?);/)[1],
            bstr = atob(arr[1]),
            n = bstr.length,
            u8arr = new Uint8Array(n);

        while(n--){
            u8arr[n] = bstr.charCodeAt(n);
        }

        return new File([u8arr], filename, {type:mime});
    }


}
