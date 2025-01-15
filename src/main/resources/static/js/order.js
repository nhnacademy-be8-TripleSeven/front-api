document.addEventListener("DOMContentLoaded", () => {
    let originalAmount = 0;
    let deliveryFee = 5000;
    let currentWrapperPrice = 0;
    let pointsUsed = 0;
    let availablePoints = 0;
    let isUsingPoints = false;
    let defaultDeliveryPrice = 0;

    const finalAmountElems = document.querySelectorAll("#final-amount, #payment-info-final-amount");
    const deliveryFeeElem = document.getElementById("delivery-fee");
    const wrapperPriceDetailElem = document.getElementById("wrapper-price-detail");
    const pointUsedElem = document.getElementById("point-used");
    const pointsFinalAmountElem = document.getElementById("points-final-amount");
    const availablePointsElem = document.getElementById("available-points");
    const wrapperIdInput = document.getElementById("wrapper-id");
    const defaultDeliveryPriceElem = document.getElementById("defaultDeliveryPrice");
    const giftWrapInclude = document.getElementById("gift-wrap-include");
    const giftWrapExclude = document.getElementById("gift-wrap-exclude");
    const selectGiftWrapLink = document.getElementById("select-gift-wrap");
    const giftWrapModal = document.getElementById("gift-wrap-modal");
    const closeModalButton = document.getElementById("close-modal");
    const deliveryDateCell = document.querySelector(".product-table tbody tr td:nth-child(4)");
    const ordererNameInput = document.getElementById("customer-name");

    // 초기값 설정
    originalAmount = parseInt(finalAmountElems[0].textContent.replace(/[^0-9]/g, "")) || 0;
    availablePoints = parseInt(availablePointsElem.textContent.replace(/[^0-9]/g, "")) || 0;
    defaultDeliveryPrice = parseInt(defaultDeliveryPriceElem.textContent.replace(/[^0-9]/g, "")) || 0;

    updateFinalAmount();

    // 포인트 사용 여부 처리
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

    // 포장지 선택 처리
    giftWrapInclude.addEventListener("change", () => {
        if (giftWrapInclude.checked) {
            selectGiftWrapLink.style.pointerEvents = "auto";
            selectGiftWrapLink.style.color = "#007bff";
        }
    });

    giftWrapExclude.addEventListener("change", () => {
        currentWrapperPrice = 0;
        wrapperIdInput.value = 0;
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

    // 배송 날짜 선택 이벤트
    document.querySelectorAll(".delivery-date-container .date").forEach(dateElement => {
        dateElement.addEventListener("click", () => {
            document.querySelectorAll(".delivery-date-container .date").forEach(elem => {
                elem.classList.remove("active");
            });

            dateElement.classList.add("active");

            const selectedDate = dateElement.getAttribute("data-date");
            deliveryDateCell.textContent = selectedDate;
        });
    });

    // 최종 결제 금액 업데이트 함수
    function updateFinalAmount() {
        let tempAmount = originalAmount + currentWrapperPrice - pointsUsed;
        if (tempAmount < defaultDeliveryPrice) {
            deliveryFee = 5000;
        } else {
            deliveryFee = 0;
        }

        const totalAmount = tempAmount + deliveryFee;
        const remainingPoints = availablePoints - pointsUsed;

        wrapperPriceDetailElem.textContent = `${currentWrapperPrice.toLocaleString()} 원`;
        pointUsedElem.textContent = `${pointsUsed.toLocaleString()} 원`;
        pointsFinalAmountElem.textContent = `${(totalAmount + pointsUsed).toLocaleString()} 원`;
        availablePointsElem.textContent = `${remainingPoints.toLocaleString()} PT`;
        deliveryFeeElem.textContent = `${deliveryFee.toLocaleString()} 원`;

        finalAmountElems.forEach(elem => {
            elem.textContent = `${totalAmount.toLocaleString()} 원`;
        });
    }

    // 폼 제출 처리
    const payForm = document.getElementById("pay-button");
    payForm.addEventListener("click", (event) => {
        event.preventDefault();
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
            const price = parseInt(row.querySelector("td:nth-child(5) span").textContent.replace(/[^0-9]/g, "")); // 쿠폰 할인 적용된 가격 사용
            const quantity = parseInt(row.querySelector("td:nth-child(2)").textContent);

            addHiddenField(`bookOrderDetails[${index}].bookId`, bookId);
            addHiddenField(`bookOrderDetails[${index}].title`, title);
            addHiddenField(`bookOrderDetails[${index}].price`, price);
            addHiddenField(`bookOrderDetails[${index}].quantity`, quantity);
        });

        addHiddenField("recipientInfo.recipientName", document.getElementById("name").value);
        addHiddenField("recipientInfo.recipientPhone", `${document.getElementById("mobile-phone1").value}${document.getElementById("mobile-phone2").value}${document.getElementById("mobile-phone3").value}`);
        addHiddenField("recipientInfo.recipientLandline", `${document.getElementById("landline-phone1").value}${document.getElementById("landline-phone2").value}${document.getElementById("landline-phone3").value}`);
        addHiddenField("addressInfo.roadAddress", document.getElementById("road-address").value);
        addHiddenField("addressInfo.zoneAddress", document.getElementById("zone-address").value);
        addHiddenField("addressInfo.detailAddress", document.getElementById("detail-address").value);
        addHiddenField("wrapperId", wrapperIdInput.value);
        // addHiddenField("couponId", couponSelectElem.value || 0);
        addHiddenField("point", pointsUsed);
        addHiddenField("totalAmount", parseInt(document.querySelector("#final-amount").textContent.replace(/[^0-9]/g, "")));

        const selectedDateElement = document.querySelector(".delivery-date-container .date.active");
        if (selectedDateElement) {
            const deliveryDate = convertToDate(selectedDateElement.getAttribute("data-date"));
            addHiddenField("deliveryDate", deliveryDate);
        } else {
            alert("배송 날짜를 선택해주세요.");
            return;
        }

        addHiddenField("ordererName", ordererNameInput.value);

        document.body.appendChild(form);
        form.submit();
    });

    // 날짜 형식 변환 함수 ("01/19(일)" -> "2025-01-19")
    function convertToDate(rawDate) {
        const [month, dayWithRest] = rawDate.split("/");
        const day = dayWithRest.split("(")[0];
        const year = new Date().getFullYear();
        return `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`;
    }
});