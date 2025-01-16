let originalAmount = 0;   // 상품 금액 (배송비 제외한 초기 금액)
let deliveryFee = 5000;   // 기본 택배비
let currentWrapperPrice = 0; // 현재 선택된 포장지 가격
let pointsUsed = 0;       // 현재 사용 중인 포인트
let isUsingPoints = false;
let deliveryMinPrice = 0;
document.addEventListener("DOMContentLoaded", () => {
    const finalAmountElems = document.querySelectorAll("#final-amount, #payment-info-final-amount");
    const deliveryFeeElem = document.getElementById("delivery-fee");
    const wrapperPriceDetailElem = document.getElementById("wrapper-price-detail");
    const pointUsedElem = document.getElementById("point-used");
    const availablePointsElem = document.getElementById("available-points");
    const deliveryMinPriceElem = document.getElementById("deliveryMinPrice");

    originalAmount = parseInt(finalAmountElems[0].textContent.replace(/[^0-9]/g, "")) || 0;
    let availablePoints = parseInt(availablePointsElem.textContent.replace(/[^0-9]/g, "")) || 0;
    deliveryMinPrice = parseInt(deliveryMinPriceElem.textContent.replace(/[^0-9]/g, "")) || 0;
    updateFinalAmount();

    // 포인트 사용 여부 처리
    document.getElementById("use-points-yes").addEventListener("change", () => {
        isUsingPoints = true;
        pointsUsed = Math.min(originalAmount, availablePoints);
        updateFinalAmount();
    });

    document.getElementById("use-points-no").addEventListener("change", () => {
        isUsingPoints = false;
        pointsUsed = 0;
        updateFinalAmount();
    });

    // 포장지 선택 처리
    document.querySelectorAll(".wrapper-item").forEach(item => {
        item.addEventListener("click", () => {
            const wrapperId = item.getAttribute("data-id");
            currentWrapperPrice = parseInt(item.getAttribute("data-price")) || 0;
            document.getElementById("wrapper-id").value = wrapperId;

            console.log(`포장지 선택됨: ID = ${wrapperId}, 가격 = ${currentWrapperPrice}원`);
            updateFinalAmount();
            document.getElementById("gift-wrap-modal").classList.add("hidden");
        });
    });

    // 최종 결제 금액 업데이트 함수
    function updateFinalAmount() {
        // 포인트와 포장지 가격을 적용한 총 금액 계산
        let tempAmount = originalAmount + currentWrapperPrice - pointsUsed;
        // 3만 원 미만일 경우 택배비 추가
        if (tempAmount < deliveryMinPrice) {
            deliveryFee = 5000;
        } else {
            deliveryFee = 0;
        }

        const totalAmount = tempAmount + deliveryFee;

        console.log(`현재 총 결제 금액: ${totalAmount}원, 택배비: ${deliveryFee}원, 포장비: ${currentWrapperPrice}원, 사용 포인트: ${pointsUsed}원`);

        // UI 업데이트
        wrapperPriceDetailElem.textContent = `${currentWrapperPrice.toLocaleString()} 원`;
        pointUsedElem.textContent = `${pointsUsed.toLocaleString()} 원`;
        deliveryFeeElem.textContent = `${deliveryFee.toLocaleString()} 원`;

        finalAmountElems.forEach(elem => {
            elem.textContent = `${totalAmount.toLocaleString()} 원`;
        });
    }
});