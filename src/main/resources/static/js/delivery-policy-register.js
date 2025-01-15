document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("delivery-register-btn").addEventListener("click", function () {
        const name = document.getElementById("delivery-policy-name").value;// 정책 이름
        const minPrice = document.getElementById("delivery-value-min-price").value;
        const price = document.getElementById("delivery-value").value; // 입력된 값

        // 입력값 검증
        if (!name.trim() || !price.trim()) {
            alert("모든 필드를 입력해주세요.");
            return;
        }

        // 서버로 데이터 전송
        axios.post("/admin/orders/delivery-policies", {
            name: name,
            minPrice: minPrice,
            price: price
        })
            .then(response => {
                alert("정책이 성공적으로 등록되었습니다.");
                console.log("Response data:", response.data);
                // 필요 시 페이지 새로고침
                window.location.href = "/admin/frontend/policies/delivery";
            })
            .catch(error => {
                console.error("Error during registration:", error);
                alert("정책 등록 중 오류가 발생했습니다.");
            });
    });
});