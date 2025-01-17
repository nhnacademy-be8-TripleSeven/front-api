
document.addEventListener("DOMContentLoaded", async () => {
    const userId = document.body.getAttribute("data-user-id");

    console.log("userId",userId);
    if (userId == null) {
        console.log("❌ 비회원은 쿠폰을 사용할 수 없습니다.");

        // 📌 모든 쿠폰 버튼 숨기기
        document.querySelectorAll(".apply-coupon-btn").forEach(btn => {
            btn.style.display = "none";
        });

        return; // 🚫 쿠폰 관련 로직 실행하지 않음
    }

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

    // "쿠폰 적용" 버튼 클릭 시 모달 표시
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

    // 📌 쿠폰 적용 버튼 클릭 시
    applyCouponModalBtn.addEventListener("click", async () => {
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

    // 모달 닫기 버튼
    closeCouponModal.addEventListener("click", () => {
        couponModal.classList.add("hidden");
    });

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
                headers: { "X-USER": userId },
                params: {
                    bookId: bookId, // ✅ 배열이 아니라 단일 값 전달
                    amount: totalAmount
                },
                paramsSerializer: params => {
                    return Object.keys(params)
                        .map(key => {
                            const value = Array.isArray(params[key])
                                ? params[key].map(val => `${encodeURIComponent(key)}[]=${encodeURIComponent(val)}`).join("&")
                                : `${encodeURIComponent(key)}=${encodeURIComponent(params[key])}`;
                            return value;
                        })
                        .join("&");
                }
            });

            return response.data;
        } catch (error) {
            console.error(`📌 ${bookId}번 책의 쿠폰 조회 실패:`, error);
            return [];
        }
    }
});