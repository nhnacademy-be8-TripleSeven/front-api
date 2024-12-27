document.addEventListener('DOMContentLoaded', () => {
    const editPopup = document.getElementById('edit-popup');
    const saveBtn = document.getElementById('save-btn');
    const cancelBtn = document.getElementById('cancel-btn');

    // 수정 팝업 열기
    document.body.addEventListener('click', (event) => {
        if (event.target.classList.contains('edit-btn')) {
            const policyId = event.target.getAttribute('data-id');
            fetch(`/admin/frontend/coupon-policy/update/${policyId}`, {
                headers: { 'Content-Type': 'application/json' },
                method: 'GET',
            })
                .then(response => {
                    if (!response.ok) throw new Error('정책 정보를 불러오지 못했습니다.');
                    return response.json();
                })
                .then(policy => {
                    document.getElementById('policy-id').value = policy.id;
                    document.getElementById('policy-name').value = policy.name;
                    document.getElementById('min-amount').value = policy.couponMinAmount;
                    document.getElementById('max-amount').value = policy.couponMaxAmount;
                    document.getElementById('discount-value').value = policy.couponDiscountRate
                        ? policy.couponDiscountRate * 100
                        : policy.couponDiscountAmount;
                    document.getElementById('discount-type').value = policy.couponDiscountRate ? 'rate' : 'amount';
                    document.getElementById('validity').value = policy.couponValidTime;
                    editPopup.style.display = 'flex';
                })
                .catch(error => alert(`오류: ${error.message}`));
        }
    });


    // 수정 데이터 저장
    saveBtn.addEventListener('click', () => {
        const policyId = document.getElementById('policy-id').value;
        const discountType = document.getElementById('discount-type').value;
        const discountValue = parseFloat(document.getElementById('discount-value').value);

        const data = {
            name: document.getElementById('policy-name').value,
            couponMinAmount: parseFloat(document.getElementById('min-amount').value),
            couponMaxAmount: parseFloat(document.getElementById('max-amount').value),
            couponValidTime: parseInt(document.getElementById('validity').value, 10),
        };

        if (discountType === 'rate') {
            data.couponDiscountRate = (discountValue / 100).toFixed(2);
            data.couponDiscountAmount = null;
        } else if (discountType === 'amount') {
            data.couponDiscountRate = null;
            data.couponDiscountAmount = discountValue;
        }

        fetch(`/admin/frontend/coupon-policy/update/${policyId}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data),
        })
            .then(response => {
                if (!response.ok) throw new Error('정책 수정 실패');
                alert('정책이 성공적으로 수정되었습니다.');
                location.reload();
            })
            .catch(error => alert(`오류: ${error.message}`));
    });

    // 삭제 버튼 클릭 이벤트
    document.querySelectorAll('.delete-btn').forEach(button => {
        button.addEventListener('click', () => {
            const policyId = button.getAttribute('data-id');
            if (confirm('정말로 삭제하시겠습니까?')) {
                fetch(`/admin/frontend/coupon-policy/delete/${policyId}`, {
                    method: 'POST',
                })
                    .then(response => {
                        if (!response.ok) throw new Error('정책 삭제 실패');
                        alert('정책이 성공적으로 삭제되었습니다.');
                        location.reload();
                    })
                    .catch(error => alert(`오류: ${error.message}`));
            }
        });
    });

    // 팝업 닫기
    cancelBtn.addEventListener('click', () => {
        editPopup.style.display = 'none';
    });
});
