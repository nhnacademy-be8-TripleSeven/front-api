document.addEventListener('DOMContentLoaded', function () {
    // 미리보기 버튼 이벤트
    document.getElementById('preview-btn').addEventListener('click', function () {
        const couponName = document.getElementById('coupon-policy-name').value;
        const minAmount = document.getElementById('min-amount').value;
        const maxAmount = document.getElementById('max-amount').value;
        const discountType = document.getElementById('discount-type').value;
        const discountValue = document.getElementById('discount-value').value;
        const validity = document.getElementById('validity').value;

        if (couponName && minAmount && maxAmount && discountValue && validity) {
            document.getElementById('popup-name').innerText = couponName;
            document.getElementById('popup-min').innerText = `${minAmount} 원`;
            document.getElementById('popup-max').innerText = `${maxAmount} 원`;
            document.getElementById('popup-discount').innerText = `${discountValue}${discountType === '율' ? '%' : ' 원'}`;
            document.getElementById('popup-validity').innerText = `${validity} 일`;

            document.getElementById('popup-overlay').style.display = 'flex';
        } else {
            alert('모든 필드를 입력해주세요.');
        }
    });

    // 등록 버튼 이벤트
    document.getElementById('register-btn').addEventListener('click', function () {
        const discountType = document.getElementById('discount-type').value;
        const discountValue = document.getElementById('discount-value').value;

        if (discountType === '금액') {
            document.getElementById('couponDiscountAmount').value = discountValue;
            document.getElementById('couponDiscountRate').value = '';
        } else if (discountType === '율') {
            document.getElementById('couponDiscountRate').value = discountValue;
            document.getElementById('couponDiscountAmount').value = '';
        }

        const form = document.getElementById('coupon-form');
        const data = new FormData(form);

        fetch('/admin/frontend/coupon-policy/register', {
            method: 'POST',
            body: data
        }).then(response => {
            if (response.ok) {
                return response.text();
            } else {
                throw new Error('쿠폰 정책 등록 실패');
            }
        }).then(message => {
            document.getElementById('popup-success-message').innerText = message;
            document.getElementById('popup-overlay').style.display = 'flex';
        }).catch(error => {
            console.error('Error:', error);
            alert('오류가 발생했습니다: ' + error.message);
        });
    });

    // 팝업 닫기 버튼 이벤트
    document.getElementById('close-popup').addEventListener('click', function () {
        document.getElementById('popup-overlay').style.display = 'none';
    });
});
