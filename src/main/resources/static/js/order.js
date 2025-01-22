document.addEventListener("DOMContentLoaded", () => {
    let originalAmount = 0;
    let deliveryFee = 0;
    let currentWrapperPrice = 0;
    let pointsUsed = 0;
    let availablePoints = 0;
    let isUsingPoints = false;
    let defaultDeliveryPrice = 0;
    let selectedPayType = null; // 선택된 결제 방식 저장
    let deliveryMinPrice = 0;
    let couponUsed = 0;

    const userId = document.body.getAttribute("data-user-id");
    const finalAmountElems = document.querySelectorAll("#final-amount, #payment-info-final-amount");
    const deliveryFeeElem = document.getElementById("delivery-fee");
    const wrapperPriceDetailElem = document.getElementById("wrapper-price-detail");
    const pointUsedElem = document.getElementById("point-used");
    const couponUsedElem = document.getElementById("coupon-used");
    const pointsFinalAmountElem = document.getElementById("points-final-amount");
    const availablePointsElem = document.getElementById("available-points");
    const wrapperIdInput = document.getElementById("wrapper-id");
    const defaultDeliveryPriceElem = document.getElementById("defaultDeliveryPrice");
    const giftWrapInclude = document.getElementById("gift-wrap-include");
    const giftWrapExclude = document.getElementById("gift-wrap-exclude");
    const selectGiftWrapLink = document.getElementById("select-gift-wrap");
    const giftWrapModal = document.getElementById("gift-wrap-modal");
    const closeModalButton = document.getElementById("close-modal");
    const ordererNameInput = document.getElementById("customer-name");
    const deliveryMinPriceElem = document.getElementById("deliveryMinPrice");
    const progressBarFill = document.querySelector(".progress-bar");


    // ✅ 배송 날짜 관련 요소 가져오기
    const deliveryDateColumnIndex = userId != null ? 5 : 4; // userId 여부에 따라 컬럼 인덱스 결정
    const deliveryDateCells = document.querySelectorAll(`.product-table tbody tr td:nth-child(${deliveryDateColumnIndex})`);

    const deliveryDateOptions = document.querySelectorAll(".delivery-date-container .date");
    // 초기값 설정

    originalAmount = parseInt(finalAmountElems[0].textContent.replace(/[^0-9]/g, "")) || 0;

    if (userId != null) {
        availablePoints = parseInt(availablePointsElem.textContent.replace(/[^0-9]/g, "")) || 0;
        couponUsed = parseInt(couponUsedElem.textContent.replace(/[^0-9]/g, "")) || 0;
    }

    defaultDeliveryPrice = parseInt(defaultDeliveryPriceElem.textContent.replace(/[^0-9]/g, "")) || 0;
    deliveryMinPrice = parseInt(deliveryMinPriceElem.textContent.replace(/[^0-9]/g, "")) || 0;

    updateFinalAmount();

    // 포인트 사용 여부 처리
    if (userId != null) {
        document.getElementById("use-points-yes").addEventListener("change", () => {
            isUsingPoints = true;
            pointsUsed = Math.min(originalAmount + currentWrapperPrice + deliveryFee, availablePoints);
            updateFinalAmount();
        });

        document.getElementById("use-points-no").addEventListener("change", () => {
            isUsingPoints = false;
            pointsUsed = 0;
            updateFinalAmount();
        });
    }




    // 포장지 선택 처리
    giftWrapInclude.addEventListener("change", () => {
        if (giftWrapInclude.checked) {
            selectGiftWrapLink.style.pointerEvents = "auto";
            selectGiftWrapLink.style.color = "#007bff";
        }
    });

    giftWrapExclude.addEventListener("change", () => {
        currentWrapperPrice = 0;
        wrapperIdInput.value = null;
        console.log("포장지 선택 해제됨");
        updateFinalAmount();
    });

    selectGiftWrapLink.addEventListener("click", (event) => {
        event.preventDefault();
        if (giftWrapInclude.checked) {
            giftWrapModal.classList.remove("hidden");
        }
    });

    closeModalButton.addEventListener("click", () => {
        giftWrapModal.classList.add("hidden");
    });

    document.querySelectorAll(".wrapper-item").forEach(item => {
        item.addEventListener("click", () => {
            const wrapperId = item.getAttribute("data-id");
            currentWrapperPrice = parseInt(item.getAttribute("data-price")) || 0;
            wrapperIdInput.value = wrapperId;
            console.log(`포장지 선택됨: ID = ${wrapperId}, 가격 = ${currentWrapperPrice}원`);
            updateFinalAmount();
            giftWrapModal.classList.add("hidden");
        });
    });

    // ✅ 배송 날짜 선택 이벤트 (회원/비회원 모두 가능)
    deliveryDateOptions.forEach(dateElement => {
        dateElement.addEventListener("click", () => {
            // 기존 활성화 상태 제거
            deliveryDateOptions.forEach(elem => elem.classList.remove("active"));

            // 새로 선택한 날짜 활성화
            dateElement.classList.add("active");

            // 선택한 날짜를 모든 상품의 배송일 셀에 적용
            const selectedDate = dateElement.getAttribute("data-date");
            deliveryDateCells.forEach(cell => {
                cell.textContent = `${selectedDate}`;
            });

            console.log(`📌 선택된 배송 날짜: ${selectedDate}`);
        });
    });

    // 최종 결제 금액 업데이트 함수
    function updateFinalAmount() {

        let tempAmount;
        if (userId != null) {
            // const couponUsedElem = document.getElementById("coupon-used");
            couponUsed = parseInt(couponUsedElem.textContent.replace(/[^0-9]/g, "")) || 0;
            tempAmount = originalAmount + currentWrapperPrice - pointsUsed - couponUsed;
        } else {
            tempAmount = originalAmount + currentWrapperPrice;

        }
        console.log("originalAmount", originalAmount);
        console.log("currentWrapperPrice", currentWrapperPrice);
        console.log("pointUsed", pointsUsed);
        console.log("couponUsed", couponUsed);
        console.log("tempAmount", tempAmount);
        if (tempAmount < deliveryMinPrice) {
            deliveryFee = defaultDeliveryPrice;
        } else {
            deliveryFee = 0;
        }
        console.log("deliveryFee", deliveryFee);

        const totalAmount = tempAmount + deliveryFee;
        const remainingPoints = availablePoints - pointsUsed;

        wrapperPriceDetailElem.textContent = `${currentWrapperPrice.toLocaleString()} 원`;
        if (userId != null) {
            pointUsedElem.textContent = `${pointsUsed.toLocaleString()} 원`;
            pointsFinalAmountElem.textContent = `${(totalAmount - remainingPoints).toLocaleString()} 원`;

            availablePointsElem.textContent = `${remainingPoints.toLocaleString()} PT`;
        }
        deliveryFeeElem.textContent = `${deliveryFee.toLocaleString()} 원`;

        finalAmountElems.forEach(elem => {
            elem.textContent = `${totalAmount.toLocaleString()} 원`;
        });

        let progressWidth = Math.min((tempAmount / deliveryMinPrice) * 100, 100);
        progressBarFill.style.width = `${progressWidth}%`;
        if (progressWidth >= 100) {
            progressBarFill.style.backgroundColor = "#4CAF50"; // 무료 배송 도달 시 초록색
        } else {
            progressBarFill.style.backgroundColor = "#6e8cba"; // 기본 색상
        }

    }


// 폼 제출 함수 수정
    function submitOrderForm() {
        const form = document.createElement("form");
        form.method = "post";
        form.action = "/frontend/payment";
        form.enctype = "application/x-www-form-urlencoded";

        const addHiddenField = (name, value) => {
            if (value !== null && value !== "") {
                const input = document.createElement("input");
                input.type = "hidden";
                input.name = name;
                input.value = value;
                form.appendChild(input);
            }
        };

        document.querySelectorAll(".product-table tbody tr").forEach((row, index) => {
            const bookId = parseInt(row.querySelector("a").getAttribute("href").split("/").pop());
            const title = row.querySelector("a").textContent;

            if (userId != null) {
                const price = document.getElementById("discountedPrice").textContent.replace(/[^0-9]/g, "") || 0;
                console.log("price = ",price);
                addHiddenField(`bookOrderDetails[${index}].price`, price);
                const quantity = parseInt(row.querySelector("td:nth-child(3)").textContent.trim());
                addHiddenField(`bookOrderDetails[${index}].quantity`, quantity);
                const couponSalePrice = parseInt(couponUsedElem.textContent.replace(/[^0-9]/g, "")) || 0;
                addHiddenField(`bookOrderDetails[${index}].couponSalePrice`, couponSalePrice)
                const couponId = appliedCoupons[bookId] ? appliedCoupons[bookId] : null;
                addHiddenField(`bookOrderDetails[${index}].couponId`, couponId);
            } else {
                const price = parseInt(row.querySelector("td:nth-child(5)").textContent.replace(/[^0-9]/g, ""));
                addHiddenField(`bookOrderDetails[${index}].price`, price);
                const quantity = parseInt(row.querySelector("td:nth-child(2)").textContent.trim());
                addHiddenField(`bookOrderDetails[${index}].quantity`, quantity);

            }
            addHiddenField(`bookOrderDetails[${index}].bookId`, bookId);
            addHiddenField(`bookOrderDetails[${index}].title`, title);


        });

        addHiddenField("recipientInfo.recipientName", document.getElementById("name").value);
        addHiddenField("recipientInfo.recipientPhone", `${document.getElementById("mobile-phone1").value}${document.getElementById("mobile-phone2").value}${document.getElementById("mobile-phone3").value}`);
        addHiddenField("recipientInfo.recipientLandline", `${document.getElementById("landline-phone1").value}${document.getElementById("landline-phone2").value}${document.getElementById("landline-phone3").value}`);
        addHiddenField("addressInfo.roadAddress", document.getElementById("roadAddress").value);
        addHiddenField("addressInfo.zoneAddress", document.getElementById("jibunAddress").value);
        addHiddenField("addressInfo.detailAddress", document.getElementById("detailAddress").value);
        addHiddenField("wrapperId", wrapperIdInput.value);
        addHiddenField("point", pointsUsed);
        addHiddenField("totalAmount", parseInt(document.querySelector("#final-amount").textContent.replace(/[^0-9]/g, "")));
        addHiddenField("deliveryFee", deliveryFee);
        const selectedDateElement = document.querySelector(".delivery-date-container .date.active");
        if (selectedDateElement) {
            const deliveryDate = convertToDate(selectedDateElement.getAttribute("data-date"));
            addHiddenField("deliveryDate", deliveryDate);
        } else {
            alert("배송 날짜를 선택해주세요.");
            return;
        }

        addHiddenField("ordererName", ordererNameInput.value);

        // 선택된 결제 방식을 추가
        if (!selectedPayType) {
            alert("결제 방식을 선택해주세요.");
            return;
        }
        addHiddenField("payType", selectedPayType); // payType 추가

        document.body.appendChild(form);
        form.submit();
    }

    // 날짜 형식 변환 함수 ("01/19(일)" -> "2025-01-19")
    function convertToDate(rawDate) {
        const [month, dayWithRest] = rawDate.split("/");
        const day = dayWithRest.split("(")[0];
        const year = new Date().getFullYear();
        return `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`;
    }

    // 결제 방식 선택 시 호출되는 함수
    function selectPayment(method) {
        alert(`${method} 결제가 선택되었습니다.`);
        selectedPayType = method;
    }

    function showUnsupportedMessage() {
        alert("현재는 토스 결제만 지원됩니다.");
    }

    window.submitOrderForm = submitOrderForm;
    window.selectPayment = selectPayment; // 전역으로 노출
    window.updateFinalAmount = updateFinalAmount;
    window.showUnsupportedMessage = showUnsupportedMessage; // 전역으로 노출
});