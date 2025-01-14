document.addEventListener("DOMContentLoaded", () => {
    const giftWrapInclude = document.getElementById("gift-wrap-include");
    const giftWrapExclude = document.getElementById("gift-wrap-exclude");
    const selectGiftWrapLink = document.getElementById("select-gift-wrap");
    const giftWrapModal = document.getElementById("gift-wrap-modal");
    const closeModalButton = document.getElementById("close-modal");
    const wrapperIdInput = document.getElementById("wrapper-id");
    const wrapperPriceDetailElem = document.getElementById("wrapper-price-detail");
    const deliveryFeeElem = document.getElementById("delivery-fee");
    const finalAmountElems = document.querySelectorAll("#final-amount, #payment-info-final-amount");

    const originalAmount = parseInt(finalAmountElems[0].textContent.replace(/[^0-9]/g, "")) || 0;
    const deliveryFee = parseInt(deliveryFeeElem.textContent.replace(/[^0-9]/g, "")) || 0;
    let currentWrapperPrice = 0;
    let isUsingPoints = false;

    console.log(`초기 금액: ${originalAmount}원, 배송비: ${deliveryFee}원`);

    // 포장지 선택 가능 여부에 따른 링크 활성화
    giftWrapInclude.addEventListener("change", () => {
        if (giftWrapInclude.checked) {
            selectGiftWrapLink.style.pointerEvents = "auto";
            selectGiftWrapLink.style.color = "#007bff";
        }
    });

    // 포장 안 함 선택 시 처리
    giftWrapExclude.addEventListener("change", () => {
        currentWrapperPrice = 0; // 포장지 가격 초기화
        wrapperIdInput.value = 0; // 포장지 ID 초기화
        console.log("포장지 선택 해제됨");
        updateFinalAmount();
    });

    // 포장지 선택 모달 열기
    selectGiftWrapLink.addEventListener("click", (event) => {
        event.preventDefault();
        if (giftWrapInclude.checked) {
            giftWrapModal.classList.remove("hidden");
        }
    });

    // 포장지 선택 모달 닫기
    closeModalButton.addEventListener("click", () => {
        giftWrapModal.classList.add("hidden");
    });

    // 포장지 리스트에서 클릭 이벤트 처리
    document.querySelectorAll(".wrapper-item").forEach(item => {
        item.addEventListener("click", () => {
            const wrapperId = item.getAttribute("data-id");
            const wrapperPrice = parseInt(item.getAttribute("data-price")) || 0;

            currentWrapperPrice = wrapperPrice;
            wrapperIdInput.value = wrapperId; // 숨겨진 필드에 포장지 ID 설정
            console.log(`포장지 선택됨: ID = ${wrapperId}, 가격 = ${wrapperPrice}원`);

            updateFinalAmount();

            giftWrapModal.classList.add("hidden"); // 모달 닫기
        });
    });

    // 최종 결제 금액 업데이트 함수
    function updateFinalAmount() {
        const totalAmount = originalAmount + deliveryFee + currentWrapperPrice;
        console.log(`현재 총 결제 금액: ${totalAmount}원`);

        // 포장지 금액 상세 업데이트
        wrapperPriceDetailElem.textContent = `${currentWrapperPrice.toLocaleString()} 원`;

        // 최종 결제 금액 업데이트 (두 위치 동기화)
        finalAmountElems.forEach(elem => {
            elem.textContent = `${totalAmount.toLocaleString()} 원`;
        });
    }
});