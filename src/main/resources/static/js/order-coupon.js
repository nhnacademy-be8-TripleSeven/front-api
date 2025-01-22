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
    let selectedCoupon = null;  // 현재 적용된 쿠폰 정보
    let couponData = {}; // ✅ 모든 책의 쿠폰 저장

    document.querySelectorAll(".product-table tbody tr").forEach((row, index) => {
        const bookTitle = row.querySelector("a").textContent.trim();
        const bookId = row.querySelector("a").getAttribute("href").split("/").pop();
        console.log(`📌 책 ID: ${bookId}, 제목: ${bookTitle}`);
    });

    // ✅ 페이지 로드 시 모든 책의 쿠폰 미리 조회
    for (const select of document.querySelectorAll(".coupon-select")) {
        const bookId = select.getAttribute("data-book-id");
        const totalPrice = select.getAttribute("data-book-totalPrice");

        couponData[bookId] = await fetchCouponsForBook(bookId, totalPrice);
        updateCouponDropdown(select, couponData[bookId]);
    }

    // ✅ 쿠폰 선택 이벤트 (하나만 적용 가능하도록 변경)
    document.querySelectorAll(".coupon-select").forEach((select) => {
        select.addEventListener("change", async (event) => {
            const selectedOption = event.target.options[event.target.selectedIndex];
            const bookId = event.target.getAttribute("data-book-id");
            const totalPrice = event.target.getAttribute("data-book-totalPrice");

            // ✅ "쿠폰 선택"을 다시 선택하면 적용 취소
            if (!selectedOption.value) {
                console.log(`📌 ${bookId}번 책의 쿠폰 취소됨`);
                selectedCoupon = null;
                couponUsed.textContent = "0원";
                appliedCoupons = {}; // ✅ 모든 쿠폰 초기화
                window.appliedCoupons = appliedCoupons;
                updateFinalAmount();
                return;
            }

            // ✅ 기존에 적용된 쿠폰 해제 (다른 책의 선택 취소)
            Object.keys(appliedCoupons).forEach((key) => {
                appliedCoupons[key] = null;
            });

            // ✅ 모든 `coupon-select` 요소에서 선택 해제
            document.querySelectorAll(".coupon-select").forEach(otherSelect => {
                if (otherSelect !== select) {
                    otherSelect.selectedIndex = 0; // 기본값(쿠폰 선택)으로 변경
                }
            });

            appliedCoupons[bookId] = selectedOption.value;
            selectedCoupon = {
                id: selectedOption.value,
                discount: parseInt(selectedOption.getAttribute("data-discount")) || 0,
                rate: parseFloat(selectedOption.getAttribute("data-rate")) || 0
            };

            couponUsedAmount = await fetchCouponPrice(totalPrice, selectedCoupon.id);
            couponUsed.textContent = `${couponUsedAmount.toLocaleString()} 원`;

            console.log(`📌 ${bookId}번 책의 쿠폰 적용됨:`, selectedCoupon);
            updateFinalAmount();
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
            console.error(`📌 ${totalPrice}원 에 대한 ${couponId}번 쿠폰의 할인량 조회 실패`, error);
            return 0;
        }
    }

    // 📌 드롭다운 업데이트 함수 (쿠폰 옵션 추가)
    function updateCouponDropdown(select, coupons) {
        select.innerHTML = `<option value="">쿠폰 선택</option>`;
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