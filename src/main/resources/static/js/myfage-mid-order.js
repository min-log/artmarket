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
                            <div class="status-1-label">의뢰 신청</div>
                            <div class="status-1-value">0건</div>
                        </div>
                        <div class="status-2">
                            <div class="status-2-label">의뢰 진행중</div>
                            <div class="status-2-value">0건</div>
                        </div>
                        <div class="status-3">
                            <div class="status-3-label">처리 완료</div>
                            <div class="status-3-value">0건</div>
                        </div>
                        <div class="status-4">
                            <div class="status-4-label">작품 수령</div>
                            <div class="status-4-value">0건</div>
                        </div>
                        <div class="status-5">
                            <div class="status-5-label">의뢰 취소</div>
                            <div class="status-5-value">0건</div>
                        </div>
                    </div>
<!--                    <div class="myfage-mid-order-content-filter">-->
<!--                        <select class="myfage-mid-order-content-filter-current">-->
<!--                            <option>전체 상태</option>-->
<!--                            <option id="status-1"></option>-->
<!--                            <option id="status-2"></option>-->
<!--                            <option id="status-3"></option>-->
<!--                            <option id="status-4"></option>-->
<!--                            <option id="status-5"></option>-->
<!--                        </select>-->
<!--                        <input type="date" class="myfage-mid-order-content-filter-start" />-->
<!--                        <input type="date" class="myfage-mid-order-content-filter-end" />-->
<!--                    </div>-->
                    <div class="myfage-mid-order-content-info"></div>
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








    orderListContent();
    myOrderNavClick();


    


}


function orderListContent(){

    orderMap.set("statusOrder",0);
    orderMap.set("statusOngoing",0);
    orderMap.set("statusFinish",0);
    orderMap.set("statusDelivery",0);
    orderMap.set("statusCancel",0);



    const myfageOrderInfoTxt = document.querySelector(".myfage-mid-order-content-info");
    // 리스트 출력
    if(sessionStorage.getItem("orderNav") == "orderSends"){
        orderMamberNavList =   "/mypage-orderAuthor/";

        myfageOrderInfoTxt.innerHTML = '<img src="./css/icon/mypage-caution.png"> ' +
            '<p>의뢰 신청을 수락해야 작업이 진행됩니다.</br>'
            + '의뢰 수락 전에는 의뢰자가 취소할 수 있습니다.</p>'

    }else {
        orderMamberNavList ="/mypage-orderMember/";

        myfageOrderInfoTxt.innerHTML = '<img src="./css/icon/mypage-caution.png"> ' +
            '<p>의뢰 신청을 작가가 수락해야 작업이 진행됩니다.</br>'
            + '작업시작 전에는 의뢰 취소가 가능합니다.</p>'

    }



    const myfageOrderList = document.querySelector(".myfage-mid-order-content-list-content");
    myfageOrderList.innerHTML = "";
    let myfageOrderListContet = "";

    fetch(orderMamberNavList + memberId ,{
        method:'GET'
    }).then(response=>{
        return response.json();
    }).then(data=>{
        console.log(data);
        let listData = [];
        listData = data;

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


        myfageOrderList.innerHTML = myfageOrderListContet;


        // count 추가

        //
        // orderMap.set("statusOrder",0);
        // orderMap.set("statusOngoing",0);
        // orderMap.set("statusFinish",0);
        // orderMap.set("statusDelivery",0);
        // orderMap.set("statusCancel",0);
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





function myOrderNavClick(){

    let myOrderBtn = document.querySelector(".myfage-mid-order-content-type-req");
    let myWorkBtn = document.querySelector(".myfage-mid-order-content-type-art");

    myWorkBtn.addEventListener("click",function (){
        sessionStorage.setItem("orderNav","orderGetters");
        orderListContent();

        myWorkBtn.classList.add("active");
        myOrderBtn.classList.remove("active");
    });


    myOrderBtn.addEventListener("click",function (){
        sessionStorage.setItem("orderNav","orderSends");
        orderListContent();
        myOrderBtn.classList.add("active");
        myWorkBtn.classList.remove("active");
    })

}


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
            return '의뢰 신청 <button type="button" onclick="orderStatusCange(\'cancel\',\''+orderid+'\')" > 취소 </button>'
        }else{
            return '작업 신청 <button type="button" onclick="orderStatusCange(\'ongoing\',\''+orderid+'\')" > 수락 </button>'
        }
    }else if(orderStatus == "ongoing"){
        orderMap.set("statusOngoing",orderMap.get("statusOngoing") + 1);
        if(sessionStorage.getItem("orderNav") == "orderSends"){
            return '의뢰 진행중'
        }else{
            return '진행 중 <button type="button" onclick="orderStatusCange(\'finish\',\''+orderid+'\')" > 완료 </button>'
        }
    }else if(orderStatus == "finish"){
        orderMap.set("statusFinish",orderMap.get("statusFinish") + 1);
        if(sessionStorage.getItem("orderNav") == "orderSends"){
            return '처리 완료'
        }else{
            return '처리 완료 <button type="button" onclick="orderStatusCange(\'delivery\',\''+orderid+'\')"  > 전달 </button>'
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


function orderStatusCange(orderStatus,orderId){
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
            }
        }).then(data=>{
           console.log(data);
        });
    });
}



// myfageOrderMidAddTag(); // 페이지 로딩 후 바로 실행할 경우

