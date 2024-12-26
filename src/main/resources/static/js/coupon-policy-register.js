document.getElementById('preview-btn').addEventListener('click', function () {
    const couponName = document.getElementById('coupon-name').value;
    const minAmount = document.getElementById('min-amount').value;
    const maxAmount = document.getElementById('max-amount').value;
    const discountType = document.getElementById('discount-type').value;
    const discountValue = document.getElementById('discount-value').value;
    const validity = document.getElementById('validity').value;

    if (couponName && minAmount && maxAmount && discountValue && validity) {
        document.getElementById('popup-name').innerText = couponName;
        document.getElementById('popup-min').innerText = `${minAmount} 원`;
        document.getElementById('popup-max').innerText = `${maxAmount} 원`;
        document.getElementById('popup-discount').innerText = `${discountValue}${discountType === 'RATE' ? '%' : ' 원'}`;
        document.getElementById('popup-validity').innerText = `${validity} 일`;

        document.getElementById('popup-overlay').style.display = 'flex';
    } else {
        alert('모든 필드를 입력해주세요.');
    }
});

document.getElementById('close-popup').addEventListener('click', function () {
    document.getElementById('popup-overlay').style.display = 'none';
});

document.getElementById('confirm-btn').addEventListener('click', function () {
    const form = document.getElementById('coupon-form');
    const data = new FormData(form);

    fetch('/admin/frontend/coupon-policy/register', {
        method: 'POST',
        body: data
    }).then(response => {
        if (response.ok) {
            alert('쿠폰 정책이 등록되었습니다.');
            window.location.href = '/admin/frontend/check-coupon-policy';
        } else {
            alert('등록에 실패했습니다.');
        }
    }).catch(error => {
        console.error('Error:', error);
        alert('오류가 발생했습니다.');
    });
});
