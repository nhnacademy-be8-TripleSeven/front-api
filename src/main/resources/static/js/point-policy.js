document.querySelectorAll(".point-edit-btn").forEach(button => {
    button.addEventListener("click", function () {
        const form = this.closest(".point-edit-form"); // 현재 버튼이 포함된 폼 선택
        const id = form.querySelector('input[name="id"]').value;
        const name = form.querySelector('input[name="name"]').value;
        const rate = form.querySelector('input[name="rate"]').value;
        const amount = form.querySelector('input[name="amount"]').value;

        // Axios PUT 요청
        axios.put(`/admin/orders/point-policies/${id}`, {
            name: name,
            amount: amount,
            rate: rate
        })
            .then(response => {
                console.log("Update successful:", response.data);
                alert("수정이 성공적으로 완료되었습니다.");
                // 필요 시 페이지를 새로고침하거나 다른 페이지로 이동
                window.location.href = "/admin/frontend/policies/point";
            })
            .catch(error => {
                console.error("Error during update:", error);
                alert("수정 중 오류가 발생했습니다.");
            });
    });
});