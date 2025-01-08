document.addEventListener("DOMContentLoaded", () => {
    const addTagButton = document.getElementById("addTagButton");
    const tagNameInput = document.getElementById("tagName");

    // 태그 추가 버튼 클릭 이벤트
    addTagButton.addEventListener("click", async () => {
        const tagName = tagNameInput.value.trim();

        if (!tagName) {
            alert("태그 이름을 입력하세요.");
            return;
        }

        try {
            // Axios를 사용해 POST 요청
            const response = await axios.post("/admin/tags", {
                name: tagName,
            });

            if (response.status === 201) {
                // 성공적으로 등록되면 메시지 표시
                alert("태그가 성공적으로 추가되었습니다.");
                // 입력 필드 초기화
                window.location.reload();
            }
        } catch (error) {
            console.error(error);
            if (error.response && error.response.status === 400) {
                alert("태그가 이미 존재합니다.");
            } else {
                alert("태그 추가에 실패했습니다. 다시 시도하세요.");
            }
        }
    });
});
