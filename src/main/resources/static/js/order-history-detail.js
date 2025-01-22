document.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll(".delivery-info-button").forEach(button => {
        button.addEventListener("click", function (event) {
            event.preventDefault();

            const orderId = this.getAttribute("id");

            axios.post(`/api/orders/tracking/${orderId}`)
                .catch(error => console.error("Error sending request:", error));
        });
    });
    document.querySelectorAll(".submit-button").forEach(button => {
        button.addEventListener("click", function (event) {
            event.preventDefault(); // 기본 제출 동작 방지
            const path = window.location.pathname; // 현재 경로를 가져옴: "/orders/12345"
            const segments = path.split('/'); // 슬래시로 분리
            const id = segments[segments.length - 1]; // 마지막 요소가 ID

            const orderIds = Array.from(document.querySelectorAll('input[name="orderIds"]:checked'))
                .map(checkbox => checkbox.value);

            const requestData = {
                orderIds: orderIds,
                orderStatus: this.getAttribute("data-status") // 버튼의 상태 값
            };

            axios.put("/api/orders/order-details/status", requestData)
                .then(response => {
                    console.log("Update successful:", response.data); // 성공 시 메시지 출력
                    alert("요청이 성공적으로 처리되었습니다.");
                    // 요청 성공 시 리다이렉트
                    window.location.href = `/frontend/orders/history/${id}`;
                })
                .catch(error => {
                    console.error("Error during update:", error);
                    alert("업데이트 중 오류가 발생했습니다.");
                });
        });
    });
});
