document.addEventListener('DOMContentLoaded', () => {
    const editPopup = document.getElementById('edit-popup');
    const saveBtn = document.getElementById('save-btn');
    const cancelBtn = document.getElementById('cancel-btn');

    // 수정 팝업 열기
    document.body.addEventListener('click', (event) => {
        if (event.target.classList.contains('edit-btn')) {
            const policyId = event.target.getAttribute('data-id');

            axios.get(`/admin/frontend/coupon-policy/update/${policyId}`)
                .then(response => {
                    const policy = response.data;
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
                .catch(error => {
                    console.error('Error fetching policy:', error);
                    alert(`오류: ${error.response?.data?.message || error.message}`);
                });
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

        axios.post(`/admin/frontend/coupon-policy/update/${policyId}`, data, {
            headers: { 'Content-Type': 'application/json' },
        })
            .then(() => {
                alert('정책이 성공적으로 수정되었습니다.');
                location.reload();
            })
            .catch(error => {
                console.error('Error updating policy:', error);
                alert(`오류: ${error.response?.data?.message || error.message}`);
            });
    });

    // 삭제 버튼 클릭 이벤트
    document.querySelectorAll('.delete-btn').forEach(button => {
        button.addEventListener('click', () => {
            const policyId = button.getAttribute('data-id');
            if (confirm('정말로 삭제하시겠습니까?')) {
                axios.post(`/admin/frontend/coupon-policy/delete/${policyId}`)
                    .then(() => {
                        alert('정책이 성공적으로 삭제되었습니다.');
                        location.reload();
                    })
                    .catch(error => {
                        console.error('Error deleting policy:', error);
                        alert(`오류: ${error.response?.data?.message || error.message}`);
                    });
            }
        });
    });

    // 팝업 닫기
    cancelBtn.addEventListener('click', () => {
        editPopup.style.display = 'none';
    });
});
