document.addEventListener("DOMContentLoaded", async () => {
    const userId = document.body.getAttribute("data-user-id");


    let selectedCoupon = null;
    let selectedBookId = null;
    let selectedIndex = null;
    let discountAmount = 0;

    const applyCouponBtns = document.querySelectorAll(".apply-coupon-btn");
    const couponModal = document.getElementById("coupon-modal");
    const couponSelect = document.getElementById("coupon-select");
    const applyCouponModalBtn = document.getElementById("apply-coupon-modal-btn");
    const closeCouponModal = document.getElementById("close-coupon-modal");
    const discountAmountElem = document.getElementById("coupon-final-amount");
    const finalAmountElem = document.getElementById("final-amount");


    // ❌ 비회원이면 쿠폰 버튼 숨기기
    if (!userId || userId === "null" || userId.trim() === "") {
        console.warn("⚠️ 비회원이므로 쿠폰 기능을 숨깁니다.");

        applyCouponBtns.forEach(btn => {
            btn.style.display = "none"; // 버튼 숨기기
        });

        if (applyCouponModalBtn) {
            applyCouponModalBtn.style.display = "none"; // 모달 적용 버튼 숨기기
        }
    } else {
        console.log("✅ 회원입니다. 쿠폰 기능 활성화");

        // ✅ 회원일 경우만 "쿠폰 적용" 버튼 이벤트 리스너 추가
        applyCouponBtns.forEach(button => {
            button.addEventListener("click", async () => {
                selectedBookId = button.getAttribute("data-book-id");
                selectedIndex = button.getAttribute("data-index");
                console.log(`📌 ${selectedBookId}번 책의 쿠폰 조회 시작...`);

                let availableCoupons = await fetchCouponsForBook(selectedBookId); // ✅ 개별 조회

                // 📌 쿠폰 선택 옵션 업데이트
                couponSelect.innerHTML = "<option value=''>쿠폰 선택</option>";
                availableCoupons.forEach(coupon => {
                    let option = document.createElement("option");
                    option.value = coupon.couponId;
                    option.setAttribute("data-discount", coupon.discountAmount);
                    option.setAttribute("data-rate", coupon.discountRate);
                    option.textContent = `[${coupon.couponName}] ${coupon.discountRate ? coupon.discountRate + "% 할인" : coupon.discountAmount + "원 할인"}`;
                    couponSelect.appendChild(option);
                });

                couponModal.classList.remove("hidden");
            });
        });
    }

    console.log("📌 쿠폰 관련 UI 활성화 완료");

    // ✅ applyCouponModalBtn이 존재하고, 회원일 경우만 이벤트 리스너 추가
    if (applyCouponModalBtn && userId) {
        applyCouponModalBtn.addEventListener("click", async () => {
            console.log("✅ 쿠폰 적용 버튼 클릭됨");

            const selectedOption = couponSelect.options[couponSelect.selectedIndex];

            if (!selectedOption.value) {
                alert("쿠폰을 선택해주세요.");
                return;
            }

            selectedCoupon = {
                id: selectedOption.value,
                discount: parseInt(selectedOption.getAttribute("data-discount")) || 0,
                rate: parseInt(selectedOption.getAttribute("data-rate")) || 0
            };

            discountAmount = selectedCoupon.discount;

            // 📌 UI 업데이트
            document.getElementById(`applied-coupon-${selectedIndex}`).textContent = `쿠폰 적용: ${selectedOption.text}`;
            discountAmountElem.textContent = `-${discountAmount.toLocaleString()} 원`;
            updateFinalAmount();
            couponModal.classList.add("hidden");
        });
    } else {
        console.warn("⚠️ applyCouponModalBtn 요소가 없거나, 비회원입니다. 쿠폰 적용 기능은 실행되지 않습니다.");
    }

    // ✅ 모달 닫기 버튼은 회원/비회원 모두 이벤트 리스너 추가
    if (closeCouponModal) {
        closeCouponModal.addEventListener("click", () => {
            couponModal.classList.add("hidden");
        });
    } else {
        console.warn("⚠️ closeCouponModal 요소가 존재하지 않습니다.");
    }

    function updateFinalAmount() {
        let originalAmount = parseInt(finalAmountElem.textContent.replace(/[^0-9]/g, ""));
        let newFinalAmount = originalAmount - discountAmount;
        finalAmountElem.textContent = `${newFinalAmount.toLocaleString()} 원`;
    }

    // 📌 개별 책에 대한 쿠폰 조회 함수
    async function fetchCouponsForBook(bookId) {
        try {
            console.log(`📌 ${bookId}번 책의 쿠폰 조회 시작...`);

            const totalAmount = parseInt(finalAmountElem.textContent.replace(/[^0-9]/g, ""), 10);

            console.log("📌 userId:", userId);
            console.log("📌 bookId:", bookId);
            console.log("📌 totalAmount:", totalAmount);
            console.log(`📌 API 요청 URL: /api/coupons/available?bookId=${bookId}&amount=${totalAmount}`);

            const response = await axios.get("/api/coupons/available", {
                params: {
                    bookId: bookId, // ✅ 배열이 아니라 단일 값 전달
                    amount: totalAmount
                }
            });

            return response.data;
        } catch (error) {
            console.error(`📌 ${bookId}번 책의 쿠폰 조회 실패:`, error);
            return [];
        }
    }
});