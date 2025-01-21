document.addEventListener('DOMContentLoaded', function () {
    const payButton = document.getElementById('pay-button');

    payButton.addEventListener('click', function (e) {
        e.preventDefault(); // 기본 동작(폼 제출) 막기

        // 필수 입력 필드 ID 및 이름 목록
        const requiredFields = [
            { id: 'name', name: '이름' },
            { id: 'roadAddress', name: '도로명 주소' },
            { id: 'detailAddress', name: '상세 주소' },
            { id: 'mobile-phone1', name: '휴대폰 앞자리' },
            { id: 'mobile-phone2', name: '휴대폰 중간자리' },
            { id: 'mobile-phone3', name: '휴대폰 끝자리' },
            { id: 'customer-name', name: '주문 고객 이름' }

        ];

        // 첫 번째로 입력되지 않은 필드 찾기
        for (let fieldInfo of requiredFields) {
            const field = document.getElementById(fieldInfo.id);
            console.log("field",field);
            if (!field.value.trim()) {
                alert(`"${fieldInfo.name}"를 입력해 주세요.`);
                field.focus(); // 해당 필드에 포커스
                field.classList.add('error'); // 에러 스타일 추가
                return; // 첫 번째 누락된 필드 발견 시 즉시 종료
            } else {
                field.classList.remove('error'); // 에러 스타일 제거
            }
        }

        // 결제 수단 선택 확인
        const selectedPayment = document.querySelector('input[name="payment-method"]:checked');
        if (!selectedPayment) {
            alert("결제 방식을 선택해 주세요.");
            return;
        }


        // 모든 필드가 올바르게 입력된 경우 폼 제출 함수 호출
        submitOrderForm();
    });
});