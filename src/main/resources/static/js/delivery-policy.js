document.querySelectorAll(".delivery-edit-btn").forEach(button => {
    button.addEventListener("click", function () {
        const form = this.closest(".delivery-edit-form"); // 현재 버튼이 포함된 폼 선택
        const id = form.querySelector('input[name="id"]').value;
        const name = form.querySelector('input[name="name"]').value;
        const price = form.querySelector('input[name="price"]').value;

        // Axios PUT 요청
        axios.put(`/admin/orders/delivery-policies/${id}`, {
            name: name,
            price: price
        })
            .then(response => {
                console.log("Update successful:", response.data);
                alert("수정이 성공적으로 완료되었습니다.");
                // 필요 시 페이지를 새로고침하거나 다른 페이지로 이동
                window.location.href = "/admin/frontend/policies/delivery";
            })
            .catch(error => {
                console.error("Error during update:", error);
                alert("수정 중 오류가 발생했습니다.");
            });
    });
});

document.querySelectorAll(".delivery-update-btn").forEach(button => {
    button.addEventListener("click", function () {
        const form = this.closest(".delivery-edit-form"); // 현재 버튼이 포함된 폼 선택
        const id = form.querySelector('input[name="id"]').value;
        const type = form.querySelector('input[name="type"]').value;

        // Axios PUT 요청
        axios.put(`/admin/orders/default-policy/delivery`, {
            deliveryPolicyId: id,
            type: type
        })
            .then(response => {
                console.log("Update successful:", response.data);
                alert("수정이 성공적으로 완료되었습니다.");
                // 필요 시 페이지를 새로고침하거나 다른 페이지로 이동
                window.location.href = "/admin/frontend/policies/default";
            })
            .catch(error => {
                console.error("Error during update:", error);
                alert("수정 중 오류가 발생했습니다.");
            });
    });
});