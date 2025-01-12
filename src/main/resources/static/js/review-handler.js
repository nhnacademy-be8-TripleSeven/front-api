document.addEventListener("DOMContentLoaded", () => {
    const reviewForm = document.querySelector(".review-form form");
    const reviewRating = document.getElementById("review-rating");
    const reviewText = document.getElementById("review-text");
    const reviewFile = document.getElementById("file");
    const bookId = document.querySelector("input[name='bookId']").value;
    const userId = document.getElementById("userId").value;

    if (!reviewForm || !userId || !bookId) {
        console.error("폼, 사용자 ID 또는 도서 ID를 찾을 수 없습니다.");
        return;
    }

    reviewForm.addEventListener("submit", async (event) => {
        event.preventDefault();

        const rating = reviewRating.value;
        const text = reviewText.value;
        const file = reviewFile.files[0];

        try {
            const formData = new FormData();
            const reviewRequestDto = {
                bookId: parseInt(bookId, 10),
                rating: parseInt(rating, 10),
                text: text,
            };

            formData.append("reviewRequestDto", new Blob([JSON.stringify(reviewRequestDto)], { type: "application/json" }));
            if (file) {
                formData.append("file", file);
            }

            const response = await axios.post("/api/reviews", formData, {
                headers: {
                    "Content-Type": "multipart/form-data",
                    "X-USER": userId,
                },
            });
            alert("리뷰가 성공적으로 등록되었습니다!");
            const policyType = file ? "CONTENT_REVIEW" : "NO_CONTENT_REVIEW";

            // 4. DefaultPointPolicy 조회 (pointPolicyId를 얻기 위한 GET 요청)
            //    /orders/default-policy/point?type=CONTENT_REVIEW or NO_CONTENT_REVIEW
            const defaultPolicyResponse = await axios.get("/orders/default-policy/point", {
                params: { type: policyType },
            });

            // API 응답 예시:
            // {
            //   id: 2,
            //   type: "CONTENT_REVIEW",
            //   pointPolicyId: 10,
            //   name: "사진 리뷰",
            //   amount: 1000,
            //   rate: 0.05
            // }
            //
            // 여기서 pointPolicyId 추출
            const pointPolicyId = defaultPolicyResponse.data.pointPolicyId;

            // 5. 포인트 적립 API (POST /api/point-histories)
            //    - types: "EARN" 같은 열거형을 실제로 사용
            const pointRequestBody = {
                types: "EARN",
                pointPolicyId: pointPolicyId,
            };

            await axios.post("/api/point-histories", pointRequestBody, {
                headers: {
                    "X-USER": userId,
                },
            });
            alert("포인트가 적립되었습니다!");

            // 모든 작업이 끝났으므로 페이지 새로고침 (또는 다른 페이지 이동)
            window.location.reload();
        } catch (error) {
            console.error("리뷰 등록 실패:", error);
            alert("리뷰 등록 중 오류가 발생했습니다.");
        }
    });
});