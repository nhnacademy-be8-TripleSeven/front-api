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
        const formData = new FormData(form);

        axios.post('/admin/frontend/coupon-policies/register', formData)
            .then(response => {
                console.log('Request successful:', response.data);
                showSuccessPopup(response.data);
            })
            .catch(error => {
                console.error('Request failed:', error.response || error);
            });
    });

    // 성공 팝업 표시 함수
    function showSuccessPopup(message) {
        const successPopup = document.getElementById('success-popup-overlay');
        document.getElementById('success-message').innerText = message;
        successPopup.style.display = 'flex';
    }

    // 성공 팝업 닫기 이벤트
    document.getElementById('close-success-popup').addEventListener('click', function () {
        document.getElementById('success-popup-overlay').style.display = 'none';
        resetPopupContent(); // 팝업 내용 초기화
    });

    // 팝업 닫기 버튼 이벤트
    document.getElementById('close-popup').addEventListener('click', function () {
        document.getElementById('popup-overlay').style.display = 'none';
        resetPopupContent(); // 팝업 내용 초기화
    });

    // 팝업 내용 초기화 함수
    function resetPopupContent() {
        document.getElementById('popup-name').innerText = '';
        document.getElementById('popup-min').innerText = '';
        document.getElementById('popup-max').innerText = '';
        document.getElementById('popup-discount').innerText = '';
        document.getElementById('popup-validity').innerText = '';
    }
});
