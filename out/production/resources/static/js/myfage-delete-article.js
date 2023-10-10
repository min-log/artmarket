
function myfageDeleteArticle(){
    // 상품 삭제 기능
    let myfageDeleteArticleBtns = document.querySelectorAll(".myfage-mid-article-box-bot-list-del");
     myfageDeleteArticleBtns.forEach((btn,index) => {
        btn.addEventListener('click',function () {
            let artDeleteValue = btn.dataset.art;
            let parentNode = btn.parentNode.parentNode.parentNode; // 부모요소
            fetch(`${baseUrl}`+"/mypage-articles/"+artDeleteValue, {
                method: 'Delete',
                headers: {
                    'content-type': 'application/json'
                }
            }).then(response => {
                if (response.status === 204) {
                    alert("삭제가 성공했습니다.");
                    parentNode.remove();
                } else if (response.status === 400) {
                    alert("삭제가 실패했습니다.");
                }
            });
        });
    });
}
