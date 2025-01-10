document.addEventListener("DOMContentLoaded", () => {

    document.getElementById("point-register-btn").addEventListener("click", function () {
        const name = document.getElementById("point-policy-name").value; // 정책 이름
        const discountType = document.getElementById("discount-type").value; // 선택된 옵션 값
        const pointValue = document.getElementById("point-value").value; // 입력된 값

        // 입력값 검증
        if (!name.trim() || !pointValue) {
            alert("모든 필드를 입력해주세요.");
            return;
        }

        // amount와 rate 초기화
        let amount = null;
        let rate = null;

        // 적립액 또는 적립률에 따라 값 설정
        if (discountType === "적립액") {
            amount = pointValue;
        } else if (discountType === "적립률") {
            rate = pointValue;
        }

        // 서버로 데이터 전송
        axios.post("/admin/orders/point-policies", {
            name: name,
            amount: amount ? parseInt(amount) : 0,
            rate: rate ? parseFloat(rate) : 0,
        })
            .then(response => {
                alert("정책이 성공적으로 등록되었습니다.");
                console.log("Response data:", response.data);
                // 필요 시 페이지 새로고침
                window.location.href = "/admin/frontend/policies/point";
            })
            .catch(error => {
                console.error("Error during registration:", error);
                alert("정책 등록 중 오류가 발생했습니다.");
            });
    });
});