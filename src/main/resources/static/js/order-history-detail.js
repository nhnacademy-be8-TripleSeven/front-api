document.querySelectorAll(".delivery-info-button").forEach(button => {
    button.addEventListener("click", function () {
        const orderId = this.getAttribute("id");

        axios.post(`/orders/tracking/${orderId}`)
            .catch(error => console.error("Error sending request:", error));
    });
});
document.querySelectorAll(".submit-button").forEach(button => {
    button.addEventListener("click", function (event) {
        event.preventDefault(); // 기본 제출 동작 방지
        const form = document.getElementById("orderForm");
        const formData = new FormData(form);
        const actionUrl = this.getAttribute("data-action"); // 버튼의 data-action 속성에서 URL 가져옴
        const orderId = this.getAttribute("id");
        const orderIds = this.getAttribute("orderIds");
        axios.post(actionUrl, formData)
            .then(response => {
                console.log("Update successful:", response.data); // 성공 시 메시지 출력
                alert("요청이 성공적으로 처리되었습니다.");
                // 요청 성공 시 리다이렉트
                window.location.href = `/frontend/orders/history/${orderId}`;
            })
            .catch(error => {
                console.error("Error during update:", error);
                alert("업데이트 중 오류가 발생했습니다.");
            });
    });
});
