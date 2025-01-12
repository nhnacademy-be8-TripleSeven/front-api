document.addEventListener("DOMContentLoaded", () => {
    // 기존 추가 태그 로직
    const addTagIdInput = document.getElementById("addTagId");
    const addTagToBookButton = document.getElementById("addTagToBookButton");

    addTagToBookButton.addEventListener("click", async () => {
        const bookId = document.getElementById("searchBookId").value.trim();
        const tagId = addTagIdInput.value.trim();

        if (!bookId || !tagId) {
            alert("도서 ID와 태그 ID를 모두 입력하세요.");
            return;
        }

        try {
            await axios.post("/admin/book-tags", {
                bookId: parseInt(bookId, 10),
                tagId: parseInt(tagId, 10),
            });

            alert("태그가 도서에 성공적으로 추가되었습니다.");
            addTagIdInput.value = "";

            // 목록 새로고침
            window.location = `/admin/frontend/book_tags?bookId=${bookId}`;
        } catch (error) {
            console.error(error);
            if (error.response && error.response.status === 409) {
                alert("이미 등록된 태그입니다.");
            } else {
                alert("태그 추가 중 오류가 발생했습니다.");
            }
        }
    });


    // ========================
    // 여기서부터 '삭제' 버튼 로직
    // ========================
    // .delete-tag-button class를 가진 요소들을 모두 찾음
    const deleteButtons = document.querySelectorAll(".delete-tag-button");

    deleteButtons.forEach((btn) => {
        btn.addEventListener("click", async (event) => {
            // 현재 도서 ID와, 삭제하려는 태그 ID를 구한다.
            const bookId = document.getElementById("searchBookId").value.trim();
            const tagId = event.target.getAttribute("data-tag-id");

            if (!bookId || !tagId) {
                alert("도서 ID 또는 태그 ID를 찾을 수 없습니다.");
                return;
            }

            // DELETE 요청 보낼 때, axios는 data(payload)를 다음과 같이 넣어야 함
            try {
                await axios.delete("/admin/book-tags", {
                    data: {
                        bookId: parseInt(bookId, 10),
                        tagId: parseInt(tagId, 10)
                    },
                });

                alert("태그가 성공적으로 삭제되었습니다.");

                // 삭제 후 목록 새로고침
                window.location = `/admin/frontend/book_tags?bookId=${bookId}`;
            } catch (error) {
                console.error(error);
                if (error.response && error.response.status === 404) {
                    alert("이미 삭제되었거나 존재하지 않는 태그입니다.");
                } else {
                    alert("태그 삭제 중 오류가 발생했습니다.");
                }
            }
        });
    });
});

