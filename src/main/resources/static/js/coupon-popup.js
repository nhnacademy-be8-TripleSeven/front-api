
const couponData = {
    name: "신규 가입 혜택 쿠폰",
    minAmount: "10,000원",
    maxAmount: "50,000원",
    discountRate: "20%",
    discountAmount: "5,000원",
    validPeriod: "30일"
};


document.getElementById("coupon-name").innerText = couponData.name;
document.getElementById("min-amount").innerText = couponData.minAmount;
document.getElementById("max-amount").innerText = couponData.maxAmount;
document.getElementById("discount-rate").innerText = couponData.discountRate;
document.getElementById("discount-amount").innerText = couponData.discountAmount;
document.getElementById("valid-period").innerText = couponData.validPeriod;


document.getElementById("confirm-btn").addEventListener("click", () => {
    alert("저장되었습니다.");

});