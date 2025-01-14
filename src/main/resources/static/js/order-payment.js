document.addEventListener("DOMContentLoaded", () => {
    const pointsYes = document.getElementById("use-points-yes");
    const pointsNo = document.getElementById("use-points-no");
    const availablePointsElem = document.getElementById("available-points");
    const pointUsedElem = document.getElementById("point-used");
    const wrapperIdInput = document.getElementById("wrapper-id");
    const deliveryFeeElem = document.getElementById("delivery-fee");
    const wrapperPriceDetailElem = document.getElementById("wrapper-price-detail");
    const finalAmountElems = document.querySelectorAll("#final-amount, #payment-info-final-amount");
    const deliveryDateCell = document.querySelector(".product-table tbody tr td:nth-child(4)");

    let availablePoints = parseInt(availablePointsElem.textContent.replace(/[^0-9]/g, "")) || 0;
    let originalAmount = parseInt(finalAmountElems[0].textContent.replace(/[^0-9]/g, "")) || 0;
    let deliveryFee = parseInt(deliveryFeeElem.textContent.replace(/[^0-9]/g, "")) || 0;
    let currentWrapperPrice = 0;
    let isUsingPoints = false;
    let pointsUsed = 0;

    console.log(`초기 금액: ${originalAmount}원, 배송비: ${deliveryFee}원`);

    // 포인트 사용 여부 처리
    pointsYes.addEventListener("change", () => {
        isUsingPoints = true;
        pointsUsed = Math.min(originalAmount, availablePoints);
        updateFinalAmount();
    });

    pointsNo.addEventListener("change", () => {
        isUsingPoints = false;
        pointsUsed = 0;
        updateFinalAmount();
    });

    // 포장지 선택 처리
    document.querySelectorAll(".wrapper-item").forEach(item => {
        item.addEventListener("click", () => {
            const wrapperId = item.getAttribute("data-id");
            const wrapperPrice = parseInt(item.getAttribute("data-price")) || 0;

            currentWrapperPrice = wrapperPrice;
            wrapperIdInput.value = wrapperId;
            console.log(`포장지 선택됨: ID = ${wrapperId}, 가격 = ${wrapperPrice}원`);

            updateFinalAmount();
            document.getElementById("gift-wrap-modal").classList.add("hidden");
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
            const formattedDate = convertToDate(selectedDate);
            deliveryDateCell.innerHTML = `${selectedDate}<br>`;

            console.log(`선택된 배송 날짜: ${selectedDate} -> 서버에 전달할 날짜: ${formattedDate}`);
        });
    });

    // 날짜 형식 변환 함수 ("01/19(일)" -> "2025-01-19")
    function convertToDate(rawDate) {
        const [month, dayWithRest] = rawDate.split("/");
        const day = dayWithRest.split("(")[0]; // "(일)" 제거
        const year = new Date().getFullYear();
        return `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`;
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
            const price = parseInt(row.querySelector("td:nth-child(3)").textContent.replace(/[^0-9]/g, ""));
            const quantity = parseInt(row.querySelector("td:nth-child(2)").textContent);
            const couponSalePrice = parseInt(row.querySelector("td:nth-child(5) span").textContent.replace(/[^0-9]/g, ""));

            addHiddenField(`bookOrderDetails[${index}].bookId`, bookId);
            addHiddenField(`bookOrderDetails[${index}].title`, title);
            addHiddenField(`bookOrderDetails[${index}].price`, price);
            addHiddenField(`bookOrderDetails[${index}].quantity`, quantity);
            addHiddenField(`bookOrderDetails[${index}].couponSalePrice`, couponSalePrice);
        });

        addHiddenField("recipientInfo.recipientName", document.getElementById("name").value);
        addHiddenField("recipientInfo.recipientPhone", `${document.getElementById("mobile-phone1").value}-${document.getElementById("mobile-phone2").value}-${document.getElementById("mobile-phone3").value}`);
        addHiddenField("recipientInfo.recipientLandline", `${document.getElementById("landline-phone1").value}-${document.getElementById("landline-phone2").value}-${document.getElementById("landline-phone3").value}`);
        addHiddenField("addressInfo.roadAddress", document.getElementById("road-address").value);
        addHiddenField("addressInfo.zoneAddress", document.getElementById("zone-address").value);
        addHiddenField("addressInfo.detailAddress", document.getElementById("detail-address").value);
        addHiddenField("wrapperId", wrapperIdInput.value);
        addHiddenField("couponId", document.getElementById("apply-coupon").value || 0);
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

        document.body.appendChild(form);
        form.submit();
    });

    function updateFinalAmount() {
        const totalAmount = originalAmount + currentWrapperPrice - pointsUsed;
        console.log(`현재 총 결제 금액: ${totalAmount}원, 포장비: ${currentWrapperPrice}원, 사용 포인트: ${pointsUsed}원`);

        wrapperPriceDetailElem.textContent = `${currentWrapperPrice.toLocaleString()} 원`;
        pointUsedElem.textContent = `${pointsUsed.toLocaleString()} 원`;

        finalAmountElems.forEach(elem => {
            elem.textContent = `${totalAmount.toLocaleString()} 원`;
        });
    }
});