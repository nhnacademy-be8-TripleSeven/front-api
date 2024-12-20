// 나중에 데이터를 불러와서 동적으로 설정할 수 있는 부분
const couponData = {
    name: "신규 가입 혜택 쿠폰",
    minAmount: "10,000원",
    maxAmount: "50,000원",
    discountRate: "20%",
    discountAmount: "5,000원",
    validPeriod: "30일"
};

// 데이터를 팝업에 반영하기
document.getElementById("coupon-name").innerText = couponData.name;
document.getElementById("min-amount").innerText = couponData.minAmount;
document.getElementById("max-amount").innerText = couponData.maxAmount;
document.getElementById("discount-rate").innerText = couponData.discountRate;
document.getElementById("discount-amount").innerText = couponData.discountAmount;
document.getElementById("valid-period").innerText = couponData.validPeriod;

// 확인- 이벤트 처리
document.getElementById("confirm-btn").addEventListener("click", () => {
    alert("저장되었습니다.");
    // 팝업 닫기 or 다른 작업 수행 가능
});