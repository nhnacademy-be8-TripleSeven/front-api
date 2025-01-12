document.addEventListener("DOMContentLoaded", () => {
    const tagContainer = document.querySelector(".tag-card-container");
    const prevPageButton = document.getElementById("prevPage");
    const nextPageButton = document.getElementById("nextPage");
    const pageInfo = document.getElementById("pageInfo");

    let currentPage = 0;
    const pageSize = 24;

    // 태그 데이터를 불러와 업데이트하는 함수
    async function fetchTags(page) {
        try {
            const response = await axios.get(`/admin/tags`, {
                params: {
                    page: page,
                    size: pageSize,
                    sortField: "name",
                    sortDirection: "asc",
                },
            });

            const { content, last, first, number } = response.data;

            // 태그 목록 업데이트
            tagContainer.innerHTML = content
                .map(
                    (tag) =>
                        `<div class="tag-item" data-tag-id="${tag.id}">
                        <!-- tagId도 표시 -->
                        <span class="tag-id">#${tag.id}</span>
                        <span class="tag-name">${tag.tagName}</span>
                        <button class="delete-tag-button">삭제</button>
                     </div>`
                )
                .join("");

            attachDeleteListeners();
            // 페이징 버튼 상태 업데이트
            pageInfo.textContent = number + 1; // number는 0부터 시작하므로 +1

            // '이전' 버튼 활성/비활성
            prevPageButton.disabled = first;
            // '다음' 버튼 활성/비활성
            nextPageButton.disabled = last;

            currentPage = number;
        } catch (error) {
            console.error("태그 데이터를 불러오는 중 오류 발생:", error);
            alert("태그 데이터를 불러오는 중 문제가 발생했습니다.");
        }
    }

    // 태그 삭제 요청 함수
    async function deleteTag(tagId) {
        try {
            await axios.delete(`/admin/tags/${tagId}`);
            alert("태그가 성공적으로 삭제되었습니다.");
            fetchTags(currentPage); // 현재 페이지를 다시 로드
        } catch (error) {
            console.error("태그 삭제 중 오류 발생:", error);
            if (error.response && error.response.status === 404) {
                alert("삭제하려는 태그를 찾을 수 없습니다.");
            } else {
                alert("태그 삭제에 실패했습니다. 다시 시도하세요.");
            }
        }
    }

    // 삭제 버튼 이벤트 리스너 추가 함수
    function attachDeleteListeners() {
        const deleteButtons = document.querySelectorAll(".delete-tag-button");
        deleteButtons.forEach((button) => {
            button.addEventListener("click", (event) => {
                const tagCard = event.target.closest(".tag-item");
                const tagId = tagCard.dataset.tagId;

                if (confirm("정말로 이 태그를 삭제하시겠습니까?")) {
                    deleteTag(tagId);
                }
            });
        });
    }

    // 초기 데이터 로드
    fetchTags(currentPage);

    // 이전 페이지 버튼 클릭
    prevPageButton.addEventListener("click", () => {
        if (currentPage > 0) {
            fetchTags(currentPage - 1);
        }
    });

    // 다음 페이지 버튼 클릭
    nextPageButton.addEventListener("click", () => {
        fetchTags(currentPage + 1);
    });
});
