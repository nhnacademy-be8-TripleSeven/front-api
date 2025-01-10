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
            // 3) 사진 여부에 따른 pointPolicyId 분기
            // const pointPolicyId = file ? 2 : 1;  // 사진이 있으면 2, 없으면 1
            //
            // // 4) 포인트 적립 API (POST /api/point-histories)
            // const pointRequestBody = {
            //     types: "EARN",       // 예: 리뷰 적립 타입
            //     pointPolicyId: pointPolicyId
            // };
            // await axios.post("/api/point-histories", pointRequestBody, {
            //     headers: {
            //         "X-USER": userId,
            //     },
            // });

            window.location.reload();
        } catch (error) {
            console.error("리뷰 등록 실패:", error);
            alert("리뷰 등록 중 오류가 발생했습니다.");
        }
    });
});