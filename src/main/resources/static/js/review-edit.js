document.addEventListener("DOMContentLoaded", () => {
    const editReviewForm = document.getElementById("editReviewForm");
    const updateReviewBtn = document.getElementById("updateReviewBtn");
    const cancelEditBtn = document.getElementById("cancelEditBtn");

    // 이벤트 위임 방식으로 editReviewBtn 처리
    document.addEventListener("click", (event) => {
        if (event.target && event.target.id === "editReviewBtn") {
            // 리뷰 정보 가져오기
            const currentReviewStars = document.getElementById("currentReviewStars");
            const currentReviewText = document.getElementById("currentReviewText").innerText;
            const ratingValue = currentReviewStars.getAttribute("data-rating");

            // 수정 폼에 값 설정
            document.getElementById("editRating").value = ratingValue;
            document.getElementById("editText").value = currentReviewText;

            // 수정 폼 표시
            editReviewForm.style.display = "block";
        }
    });

    // 리뷰 수정 버튼 클릭 이벤트
    if (updateReviewBtn) {
        updateReviewBtn.addEventListener("click", async () => {
            const bookId = document.getElementById("bookId").value;
            const userId = document.getElementById("currentUserId").innerText;
            const newRating = document.getElementById("editRating").value;
            const newText = document.getElementById("editText").value;
            const removeImageChecked = document.getElementById("removeImage").checked;
            const fileInput = document.getElementById("editFile");

            // DTO에 들어갈 데이터를 객체로 생성
            const reviewRequestDto = {
                bookId: bookId,
                text: newText,
                rating: parseInt(newRating, 10)
            };

            // FormData 생성
            const formData = new FormData();

            // reviewRequestDto를 JSON으로 변환해 FormData에 추가
            formData.append(
                "reviewRequestDto",
                new Blob([JSON.stringify(reviewRequestDto)], { type: "application/json" })
            );

            // 파일이 있는 경우 FormData에 추가
            if (fileInput.files.length > 0) {
                formData.append("file", fileInput.files[0]);
            }

            // removeImage -> 문자열로 변환 (중요!)
            formData.append("removeImage", removeImageChecked ? "true" : "false");

            try {
                // PUT 요청
                await axios.put("/api/reviews", formData, {
                    headers: {
                        "X-USER": userId,
                        "Content-Type": "multipart/form-data"
                    },
                });

                alert("리뷰 수정 완료!");
                window.location.reload();
            } catch (error) {
                console.error("Error response:", error.response);
                alert("리뷰 수정 중 오류가 발생했습니다.");
            }
        });
    }

    // 취소 버튼 이벤트 처리
    if (cancelEditBtn) {
        cancelEditBtn.addEventListener("click", () => {
            editReviewForm.style.display = "none";
        });
    }
});
