document.getElementById('register-btn').addEventListener('click', function () {

    const couponName = document.getElementById('coupon-name').value;
    const minAmount = document.getElementById('min-amount').value;
    const maxAmount = document.getElementById('max-amount').value;
    const discountType = document.getElementById('discount-type').value;
    const discountValue = document.getElementById('discount-value').value;
    const validity = document.getElementById('validity').value;


    if (couponName && minAmount && maxAmount && discountValue && validity) {
        document.getElementById('popup-name').innerText = couponName;
        document.getElementById('popup-min').innerText = `${minAmount}원`;
        document.getElementById('popup-max').innerText = `${maxAmount}원`;
        document.getElementById('popup-discount').innerText = `${discountValue}${discountType === '할인율' ? '%' : '원'}`;
        document.getElementById('popup-validity').innerText = validity;

        document.getElementById('popup-overlay').style.display = 'flex';
    } else {
        alert('모든 필드를 입력해주세요.');
    }
});

document.getElementById('close-popup').addEventListener('click', function () {
    document.getElementById('popup-overlay').style.display = 'none';
});
