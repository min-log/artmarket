const myfageMid = document.querySelector('.myfage-mid');
const myfageRight = document.querySelector('.myfage-right');
const memberId = sessionStorage.getItem("id");
let orderMamberNavList;
const orderMap = new Map();

function myfageOrderMidAddTag() {

    myfageRight.innerHTML = "";

    sessionStorage.setItem("orderNav","orderSends"); // orderSends(내가 주문 받은 내역),orderGetters(내가 주문한 내역)


    if (myfageMid.childNodes) {
        for (myfageMidTag of myfageMid.childNodes) {
            myfageMidTag.remove();
        }
    }

    if (myfageRight.childNodes) {
        for (myfageRightTag of myfageRight.childNodes) {
            myfageRightTag.remove();
        }
    }
    myfageMid.innerHTML = `
                <div class="myfage-mid-order-content">
                    <div class="myfage-mid-order-content-nav">
                    </div>
                    <div class="myfage-mid-order-content-status">
                        <div class="status-1">
                            <div class="status-1-labl status-name-1">의뢰 신청</div>
                            <div class="status-1-value">0건</div>
                        </div>
                        <div class="status-2">
                            <div class="status-2-label status-name-2">의뢰 진행중</div>
                            <div class="status-2-value">0건</div>
                        </div>
                        <div class="status-3">
                            <div class="status-3-label status-name-3">처리 완료</div>
                            <div class="status-3-value">0건</div>
                        </div>
                        <div class="status-4">
                            <div class="status-4-label status-name-4">작품 수령</div>
                            <div class="status-4-value">0건</div>
                        </div>
                        <div class=" status-5">
                            <div class="status-5-label  status-name-5">의뢰 취소</div>
                            <div class="status-5-value">0건</div>
                        </div>
                    </div>
                    <div class="myfage-mid-order-content-info"></div>
                    <div class="myfage-mid-order-content-filter">
                        <select class="myfage-mid-order-content-filter-current">
                            <option value="">전체 상태</option>
                            <option id="status-1" class="status-name-1" value="order">의뢰 신청</option>
                            <option id="status-2" class="status-name-2" value="ongoing">의뢰 진행중</option>
                            <option id="status-3" class="status-name-3" value="finish">처리 완료</option>
                            <option id="status-4" class="status-name-4" value="delivery">작품 수령</option>
                            <option id="status-5" class="status-name-5" value="cancel">의뢰 취소</option>
                        </select>
                        <input type="date" class="myfage-mid-order-content-filter-start" />
                        <input type="date" class="myfage-mid-order-content-filter-end" />
                        <input type="text" class="myfage-mid-order-content-filter-nickname" placeholder="닉네임을 입력해주세요." />
                        <button id="OrderSearchBtn">검색</button>
                    </div>
                   
                    <div class="myfage-mid-order-content-list-box">
                        <div class="myfage-mid-order-content-list-title">
                            <div class="myfage-mid-order-content-list-title-order-num">
                                의뢰번호
                            </div>
                            <div class="myfage-mid-order-content-list-title-order-nickname">
                                작가명
                            </div>
                            <div class="myfage-mid-order-content-list-title-order-cost">
                                결제금액
                            </div>
                            <div class="myfage-mid-order-content-list-title-order-date">
                                주문날짜
                            </div>
                            <div class="myfage-mid-order-content-list-title-order-deadline">
                                마감기한
                            </div>
                            <div class="myfage-mid-order-content-list-title-order-status">
                                주문상태
                            </div>
                        </div>
                        <!-- -->
                        <div class="myfage-mid-order-content-list-content">
                        
                        </div>
                        <!-- -->
                    </div>
                </div>
         `;

    // 네비게이션, 내용설명 회원에 따라 다르게 추가
    const myfageOrderNav = document.querySelector(".myfage-mid-order-content-nav");


    let myfageOrderNavContent ='<div class="myfage-mid-order-content-type-req active">의뢰관리</div>';
    if(sessionStorage.getItem('identity') == 'AUTHOR'){
        myfageOrderNavContent += '<div class="myfage-mid-order-content-type-art">작업관리</div>';
    }
    myfageOrderNav.innerHTML = myfageOrderNavContent;

    orderSetListContent();
    orderSearch();
    myOrderNavClick();
}


function orderSetListContent(){

    orderMap.set("statusOrder",0);
    orderMap.set("statusOngoing",0);
    orderMap.set("statusFinish",0);
    orderMap.set("statusDelivery",0);
    orderMap.set("statusCancel",0);

    

    const myfageOrderInfoTxt = document.querySelector(".myfage-mid-order-content-info");
    // 상태 명 변경 출력
    let orderTableName = document.querySelector(".myfage-mid-order-content-list-title-order-nickname");
    let nevName1 = document.querySelectorAll(".status-name-1");
    let nevName2 = document.querySelectorAll(".status-name-2");
    let nevName3 = document.querySelectorAll(".status-name-3");
    let nevName4 = document.querySelectorAll(".status-name-4");
    let nevName5 = document.querySelectorAll(".status-name-5");

    if(sessionStorage.getItem("orderNav") == "orderSends"){
        orderMamberNavList ="/mypage-orderMember/";

        orderTableName.innerHTML = "작가명";
        nevName1.forEach(item=>{
            item.innerHTML = "의뢰 신청";
        });
        nevName2.forEach(item=>{
            item.innerHTML = "의뢰 진행중";
        })
        nevName3.forEach(item=>{
            item.innerHTML = "처리 완료";
        })
        nevName4.forEach(item=>{
            item.innerHTML = "작품 수령";
        })
        nevName5.forEach(item=>{
            item.innerHTML = "의뢰 취소";
        });


        myfageOrderInfoTxt.innerHTML = '<img src="./css/icon/mypage-caution.png"> ' +
            '<p>의뢰 신청을 작가가 수락해야 작업이 진행됩니다.</br>'
            + '작업시작 전에는 의뢰 취소가 가능합니다.</p>'

    }else {
        orderMamberNavList =   "/mypage-orderAuthor/";

        orderTableName.innerHTML = "의뢰자명";
        nevName1.forEach(item=>{
            item.innerHTML = "주문 신청";
        });
        nevName2.forEach(item=>{
            item.innerHTML = "주문 진행중";
        })
        nevName3.forEach(item=>{
            item.innerHTML = "작업 완료";
        })
        nevName4.forEach(item=>{
            item.innerHTML = "작품 전달";
        })
        nevName5.forEach(item=>{
            item.innerHTML = "주문 취소";
        });

        myfageOrderInfoTxt.innerHTML = '<img src="./css/icon/mypage-caution.png"> ' +
            '<p>의뢰 신청을 수락해야 작업이 진행됩니다.</br>'
            + '의뢰 수락 전에는 의뢰자가 취소할 수 있습니다.</p>'

    }



    let myfageOrderList = document.querySelector(".myfage-mid-order-content-list-content");
    myfageOrderList.innerHTML = "";

    fetch(orderMamberNavList + memberId ,{
        method:'GET'
    }).then(response=>{
        return response.json();
    }).then(data=>{
        console.log(data);
        let listData = [];
        listData = data;
        
        myfageOrderList.innerHTML = orderList(listData);

        // count 추가
        let orderStatusCount1 = document.querySelector(".status-1-value");
        let orderStatusCount2 = document.querySelector(".status-2-value");
        let orderStatusCount3 = document.querySelector(".status-3-value");
        let orderStatusCount4 = document.querySelector(".status-4-value");
        let orderStatusCount5 = document.querySelector(".status-5-value");
        orderStatusCount1.innerText = orderMap.get("statusOrder") + "건";
        orderStatusCount2.innerText = orderMap.get("statusOngoing") + "건";
        orderStatusCount3.innerText = orderMap.get("statusFinish") + "건";
        orderStatusCount4.innerText = orderMap.get("statusDelivery") + "건";
        orderStatusCount5.innerText = orderMap.get("statusCancel") + "건";




    });


}



function orderList(listData){
    let myfageOrderListContet = "";
    for(let i = 0;i<listData.length;i++){
        myfageOrderListContet += '<div class="myfage-mid-order-content-list-value">' +
            '              <div class="myfage-mid-order-content-list-value-order-num">' + listData[i].orderId + '</div>' +
            '              <div class="myfage-mid-order-content-list-value-order-nickname">' +
            listData[i].nickname +
            '              </div>' +
            '              <div class="myfage-mid-order-content-list-value-order-cost">' +
            listData[i].totalAmount +
            '              </div>' +
            '              <div class="myfage-mid-order-content-list-value-order-date">' +
            listData[i].orderDate +
            '              </div>' +
            '              <div class="myfage-mid-order-content-list-value-order-deadline">' +
            listData[i].deadline +
            '              </div>' +
            '              <div class="myfage-mid-order-content-list-value-order-status">\n' +
            orderStatusList(listData[i].orderStatus,listData[i].orderId)
            +
            '              </div>' +
            '            </div>';

    }

    return myfageOrderListContet;

}

function orderSearch(){
    //검색기능

    let orderSearchSelect = document.querySelector(".myfage-mid-order-content-filter-current");
    let orderSearchStartDate = document.querySelector(".myfage-mid-order-content-filter-start");
    let orderSearchEndDate = document.querySelector(".myfage-mid-order-content-filter-end");
    let orderSearchNickName = document.querySelector(".myfage-mid-order-content-filter-nickname");
    let searchBtn = document.getElementById("OrderSearchBtn");

    searchBtn.addEventListener('click',function () {
        let orderSearchUrl = "/mypage-searchMember";
        if(sessionStorage.getItem("orderNav") == "orderSends"){
            orderSearchUrl = "/mypage-searchMember";
        }else {
            orderSearchUrl = "/mypage-searchAuthor";

        }


        let orderSearchUrlPram = "";
        let  orderSelectValue = orderSearchSelect.options[orderSearchSelect.selectedIndex].value;

        if(orderSelectValue != "") orderSearchUrlPram  += "&orderStatus=" + orderSelectValue;
        if(orderSearchStartDate.value != "") orderSearchUrlPram  += "&startDate=" + orderSearchStartDate.value;
        if(orderSearchEndDate.value != "") orderSearchUrlPram  += "&endDate=" + orderSearchEndDate.value;
        if(orderSearchNickName.value != "")orderSearchUrlPram  += "&nickname=" + orderSearchNickName.value;

        console.log(orderSearchUrl + "?memberId=" + sessionStorage.getItem("id") + orderSearchUrlPram);

        let myfageOrderList = document.querySelector(".myfage-mid-order-content-list-content");
        myfageOrderList.innerHTML = "";



        fetch(orderSearchUrl +
            "?memberId=" + sessionStorage.getItem("id")
            + orderSearchUrlPram
        ,{
            method : 'GET'
        }).then(response=>{
            return response.json();
        }).then(data=>{
            let dataList = [];
            dataList= data;

            myfageOrderList.innerHTML = orderList(dataList);
        });
    })
}



function myOrderNavClick(){

    let myOrderBtn = document.querySelector(".myfage-mid-order-content-type-req");
    let myWorkBtn = document.querySelector(".myfage-mid-order-content-type-art");

    myWorkBtn.addEventListener("click",function (){
        sessionStorage.setItem("orderNav","orderGetters");
        orderSetListContent();

        myWorkBtn.classList.add("active");
        myOrderBtn.classList.remove("active");
    });


    myOrderBtn.addEventListener("click",function (){
        sessionStorage.setItem("orderNav","orderSends");
        orderSetListContent();
        myOrderBtn.classList.add("active");
        myWorkBtn.classList.remove("active");
    })

}


// 의뢰인지 작업인지 구분되어야해서 전달 받는 값 추가 필요, (중단) => 스크립트로 카운트 처리 완료
// function orderCount(){
//     // private String orderStatus;
//     // private int count;
//
//     fetch("/count",{
//         method : 'GET'
//     }).then(response=>{
//         return response.json();
//     }).then(data=>{
//       console.log(data);
//     })
// }


function orderStatusList(orderStatus,orderid){
    //order,ongoing,finish,delivery,cancel
    if(orderStatus == "order"){
        orderMap.set("statusOrder",orderMap.get("statusOrder") + 1);
        if(sessionStorage.getItem("orderNav") == "orderSends"){
            return '의뢰 신청 <button type="button" onclick="orderStatusChange(\'cancel\',\''+orderid+'\')" > 취소 </button>'
        }else{
            return '작업 신청 <button type="button" onclick="orderStatusChange(\'ongoing\',\''+orderid+'\')" > 수락 </button><button type="button" onclick="orderStatusChange(\'cancel\',\''+orderid+'\')" > 취소 </button>'
        }
    }else if(orderStatus == "ongoing"){
        orderMap.set("statusOngoing",orderMap.get("statusOngoing") + 1);
        if(sessionStorage.getItem("orderNav") == "orderSends"){
            return '의뢰 진행중'
        }else{
            return '진행 중 <button type="button" onclick="orderStatusChange(\'finish\',\''+orderid+'\')" > 완료 </button>'
        }
    }else if(orderStatus == "finish"){
        orderMap.set("statusFinish",orderMap.get("statusFinish") + 1);
        if(sessionStorage.getItem("orderNav") == "orderSends"){
            return '처리 완료'
        }else{
            return '처리 완료 <button type="button" onclick="orderStatusChange(\'delivery\',\''+orderid+'\')"  > 전달 </button>'
        }
    }else if(orderStatus == "delivery"){
        orderMap.set("statusDelivery",orderMap.get("statusDelivery") + 1);
        if(sessionStorage.getItem("orderNav") == "orderSends"){
            return '작품 수령'
        }else{
            return '작품 전달'
        }
    }else if(orderStatus == "cancel"){
        orderMap.set("statusCancel",orderMap.get("statusCancel") + 1);
        if(sessionStorage.getItem("orderNav") == "orderSends"){
            return '의뢰 취소'
        }else{
            return '주문 취소'
        }
    }

}


function orderStatusChange(orderStatus,orderId){
    modal.style = "display:block";
    modalTitle.innerHTML = "주문상태 변경"


    let orderStatusMsg = '<p><b style="font-size: 0.8rem">';
    if(orderStatus == "order"){

    }else if(orderStatus == "ongoing"){
        orderStatusMsg += "작업을 시작하시겠습니까?";
    }else if(orderStatus == "finish"){
        orderStatusMsg += "작업이 완료되었습니까?";

    }else if(orderStatus == "delivery"){
        orderStatusMsg += "작업물을 전송하셨습니까?";
    }else if(orderStatus == "cancel"){
        orderStatusMsg += "의뢰 신청을 취소 하시겠습니까?";
    }
    orderStatusMsg += '</b></p>';
    orderStatusMsg += "<p>변경된 내역은 복구가 불가합니다.</p>";
    orderStatusMsg += "<p>진행하시려면 '확인'을 눌러주세요.</p>";
    orderStatusMsg += '<div class="btn-wrap" style="margin-top:1.5rem">';
    orderStatusMsg += '<button type="button" class="order-status-result-btn btn-type01">확인</button>';
    orderStatusMsg += '</div>';


    modalContent.innerHTML = orderStatusMsg;

    let orderStatusResultBtn = document.querySelector(".order-status-result-btn");

    orderStatusResultBtn.addEventListener('click',function () {

        if(orderStatus == "cancel"){
            // 취소일 경우 = 환불 절차
            fetch('/refund',{
                method:'POST',
                headers: {
                    'content-type': 'application/json'
                },
                body: JSON.stringify({
                    orderId: orderId
                })
            }).then(response=>{
                if (response.status == 200){
                    orderStatuseRe();
                }else {
                    alert("취소가 실패했습니다.");
                }

            });
        }else{
            // 이외 상태 변경
            orderStatuseRe();
        }
        function orderStatuseRe(){
            // 주문상태 변경
            fetch('/order-status',{
                method:'POST',
                headers: {
                    'content-type': 'application/json'
                },
                body: JSON.stringify({
                    orderStatus: orderStatus,
                    orderId: orderId
                })
            }).then(response=>{
                if (response.status == 200){
                    alert("상태가 변경되었습니다.");
                    location.reload();
                }else{
                    alert("변경이 실패했습니다.");
                }
            }).then(data=>{
               console.log(data);
            });
        }
    });

}



// myfageOrderMidAddTag(); // 페이지 로딩 후 바로 실행할 경우

