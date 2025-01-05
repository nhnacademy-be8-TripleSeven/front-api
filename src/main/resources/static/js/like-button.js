document.addEventListener("DOMContentLoaded", () => {
    const likeIcon = document.getElementById("likeIcon");
    const isLoggedIn = likeIcon.dataset.isLoggedIn === "true";
    let isLiked = likeIcon.dataset.isLiked === "true";
    const bookId = likeIcon.dataset.bookId;

    // 초기 이미지 설정
    likeIcon.src = isLiked
        ? "/image/likes/filled-heart.png"
        : "/image/likes/empty-heart.png";

    // 클릭 이벤트 추가
    likeIcon.addEventListener("click", async () => {
        if (!isLoggedIn) {
            alert("로그인이 필요합니다.");
            return;
        }

        try {
            const response = await fetch(`/frontend/likes/${bookId}`, {
                method: isLiked ? "DELETE" : "POST",
                headers: {
                    "X-USER": likeIcon.dataset.userId,
                },
            });

            if (response.ok) {
                isLiked = !isLiked;
                likeIcon.src = isLiked
                    ? "/image/likes/filled-heart.png"
                    : "/image/likes/empty-heart.png";
            } else {
                alert("좋아요 처리 중 문제가 발생했습니다.");
            }
        } catch (error) {
            alert("네트워크 오류가 발생했습니다.");
        }
    });
});
