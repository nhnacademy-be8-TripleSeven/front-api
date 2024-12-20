document.getElementById('register-btn').addEventListener('click', function () {
    // 입력 값 가져오기
    const couponName = document.getElementById('coupon-name').value;
    const minAmount = document.getElementById('min-amount').value;
    const maxAmount = document.getElementById('max-amount').value;
    const discountType = document.getElementById('discount-type').value;
    const discountValue = document.getElementById('discount-value').value;
    const validity = document.getElementById('validity').value;

    // 모든 필드가 입력되었는지 확인
    if (couponName && minAmount && maxAmount && discountValue && validity) {
        // 팝업에 값 채우기
        document.getElementById('popup-name').innerText = couponName;
        document.getElementById('popup-min').innerText = `${minAmount}원`;
        document.getElementById('popup-max').innerText = `${maxAmount}원`;
        document.getElementById('popup-discount').innerText = `${discountValue}${discountType === '할인율' ? '%' : '원'}`;
        document.getElementById('popup-validity').innerText = validity;

        // 팝업 표시
        document.getElementById('popup-overlay').style.display = 'flex';
    } else {
        alert('모든 필드를 입력해주세요.');
    }
});

// 팝업 닫기
document.getElementById('close-popup').addEventListener('click', function () {
    document.getElementById('popup-overlay').style.display = 'none';
});
