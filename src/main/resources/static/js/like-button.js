document.addEventListener("DOMContentLoaded", function() {
    const likeIcon = document.getElementById("likeIcon");
    const bookId = likeIcon.dataset.bookId;    // data-book-id 값
    const userId = likeIcon.dataset.userId;    // data-user-id 값 (로그인 안 됐으면 "" 일 수도)

    // 1) 페이지 로드시, 로그인 상태인지 확인 -> 아이콘 초기 상태 설정
    if (!userId) {
        // (1) 로그인 X: 그냥 empty-heart로 두고, 클릭 시 alert만
        likeIcon.src = "/image/likes/empty-heart.png";
    } else {
        // (2) 로그인 O: 서버에 이미 좋아요가 되어있는지 조회
        fetch(`/api/likes/${bookId}/status`, {
            method: "GET",
            headers: {
                "X-USER": userId, // 로그인한 유저의 ID를 헤더로 전달
            }
        })
            .then(response => {
                if (!response.ok) {
                    // API에서 예외가 나면 false 처리
                    console.error("좋아요 상태 조회 실패");
                    return false;
                }
                return response.json(); // Boolean(true or false)
            })
            .then(isLiked => {
                if (isLiked) {
                    likeIcon.src = "/image/likes/filled-heart.png";
                } else {
                    likeIcon.src = "/image/likes/empty-heart.png";
                }
            })
            .catch(err => {
                console.error("좋아요 상태 조회 중 오류: ", err);
            });
    }

    // 2) 좋아요 버튼(하트) 클릭 시 동작
    likeIcon.addEventListener("click", function() {
        // (A) 로그인 안 된 상태 -> 알림 후 종료
        if (!userId) {
            alert("로그인이 필요합니다.");
            return;
        }

        // (B) 로그인 된 상태 -> 현재 아이콘이 어떤지 확인 후 요청
        const isHeartFilled = likeIcon.src.includes("filled-heart.png");
        // 이미 눌러져 있으면(filled-heart) -> DELETE, 아니면 POST
        const method = isHeartFilled ? "DELETE" : "POST";

        fetch(`/api/likes/${bookId}`, {
            method: method,
            headers: {
                "X-USER": userId
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("좋아요 API 호출 실패");
                }
                // 성공 시 아이콘 전환
                likeIcon.src = isHeartFilled
                    ? "/image/likes/empty-heart.png"
                    : "/image/likes/filled-heart.png";
            })
            .catch(err => {
                console.error(err);
                alert("좋아요 처리 중 오류가 발생했습니다.");
            });
    });
});
