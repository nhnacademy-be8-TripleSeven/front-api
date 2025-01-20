document.addEventListener("DOMContentLoaded", async () => {
    const userId = document.body.getAttribute("data-user-id");
    const couponUsed = document.getElementById("coupon-used");
    let appliedCoupons = {};
    window.appliedCoupons = appliedCoupons;
    if (!userId || userId === "null" || userId.trim() === "") {
        console.warn("⚠️ 비회원이므로 쿠폰 기능을 숨깁니다.");
        document.querySelectorAll(".coupon-select").forEach(select => {
            select.style.display = "none"; // 비회원이면 드롭다운 숨김
        });
        return;
    }
    let couponUsedAmount;

    // ✅ 책별 쿠폰 저장 (한 번만 조회)
    let couponData = {};
    let selectedCoupon = null;  // 현재 적용된 쿠폰 정보
    document.querySelectorAll(".product-table tbody tr").forEach((row, index) => {
        const bookTitle = row.querySelector("a").textContent.trim(); // 책 제목
        const bookId = row.querySelector("a").getAttribute("href").split("/").pop(); // 책 ID
        const quantity = row.querySelector("td:nth-child(3)").textContent.trim(); // 수량
        const price = row.querySelector("td:nth-child(4)").textContent.replace(/[^0-9]/g, ""); // 가격 숫자만 추출
        const deliveryDate = row.querySelector("td:nth-child(5)").textContent.trim(); // 배송일

        console.log(`📌 책 제목: ${bookTitle}`);
        console.log(`📌 책 ID: ${bookId}`);
        console.log(`📌 수량: ${quantity}`);
        console.log(`📌 가격: ${price} 원`);
        console.log(`📌 배송일: ${deliveryDate}`);
    });

    // ✅ 페이지 로드 시 모든 책의 쿠폰 미리 조회
    for (const select of document.querySelectorAll(".coupon-select")) {
        const bookId = select.getAttribute("data-book-id");
        const totalPrice = select.getAttribute("data-book-totalPrice");

        couponData[bookId] = await fetchCouponsForBook(bookId, totalPrice);
        // ✅ 쿠폰 옵션 업데이트
        updateCouponDropdown(select, couponData[bookId]);
    }

    // ✅ 쿠폰 선택 이벤트 (쿠폰 적용 & 해제)
    document.querySelectorAll(".coupon-select").forEach((select) => {
        select.addEventListener("change", async (event) => {
            const selectedOption = event.target.options[event.target.selectedIndex];
            const bookId = event.target.getAttribute("data-book-id");
            const totalPrice = event.target.getAttribute("data-book-totalPrice"); // ✅ totalPrice 가져오기

            // ✅ "쿠폰 선택"을 다시 선택하면 적용 취소
            if (!selectedOption.value) {
                console.log(`📌 ${bookId}번 책의 쿠폰 취소됨`);
                selectedCoupon = null;
                couponUsed.textContent = "0원";
                appliedCoupons[bookId] = null;
                updateFinalAmount();  // 📌 `order.js`에 있는 가격 업데이트 함수 호출
                return;
            }
            Object.keys(appliedCoupons).forEach(key => delete appliedCoupons[key]); // 모든 쿠폰 초기화
            appliedCoupons[bookId] = selectedOption.value;
            console.log("appliedCoupons[bookId]",appliedCoupons[bookId]);
            selectedCoupon = {
                id: selectedOption.value,
                discount: parseInt(selectedOption.getAttribute("data-discount")) || 0,
                rate: parseFloat(selectedOption.getAttribute("data-rate")) || 0 //할인률이어서 소수점 표현해야함
            };
            console.log("bookId",bookId);
            console.log("appliedCoupons",appliedCoupons);
            couponUsedAmount = await fetchCouponPrice(totalPrice, (selectedCoupon.id))

            couponUsed.textContent = `${couponUsedAmount.toLocaleString()} 원`;
            console.log(`📌 ${bookId}번 책의 쿠폰 적용됨:`, selectedCoupon);

            updateFinalAmount();  // 📌 `order.js`에 있는 가격 업데이트 함수 호출
        });
    });

    // 📌 개별 책에 대한 쿠폰 조회 함수
    async function fetchCouponsForBook(bookId, totalPrice) {
        try {
            console.log(`📌 ${bookId}번 책의 쿠폰 조회 시작...`);
            const response = await axios.get("/api/coupons/available", {
                params: {
                    bookId: bookId,
                    amount: totalPrice
                }
            });
            console.log(`📌 ${bookId}번 책의 쿠폰 조회 완료...`);


            return response.data;
        } catch (error) {
            console.error(`📌 ${bookId}번 책의 쿠폰 조회 실패:`, error);
            return [];
        }
    }

    async function fetchCouponPrice(totalPrice, couponId) {
        try {
            const response = await axios.get("/api/coupons/apply", {
                params: {
                    couponId: couponId,
                    paymentAmount: totalPrice
                }
            });

            return response.data;
        } catch (error) {
            console.error(`📌 ${totalPrice}원 에 대한 ${couponId}번 쿠폰의 할인량 조회 실패`,error)
        }
    }

    // 📌 드롭다운 업데이트 함수 (쿠폰 옵션 추가)
    function updateCouponDropdown(select, coupons) {
        select.innerHTML = `<option value="">쿠폰 선택</option>`; // 기본값 추가
        coupons.forEach(coupon => {
            let option = document.createElement("option");
            option.value = coupon.couponId;
            option.setAttribute("data-discount", coupon.discountAmount);
            option.setAttribute("data-rate", coupon.discountRate);
            option.textContent = `[${coupon.couponName}] ${coupon.discountRate ? coupon.discountRate * 100 + "% 할인" : coupon.discountAmount + "원 할인"}`;
            select.appendChild(option);
        });
    }
});