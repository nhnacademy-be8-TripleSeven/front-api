document.addEventListener("DOMContentLoaded", () => {
    const selectAllCheckbox = document.getElementById("select-all");
    const rowCheckboxes = document.querySelectorAll(".row-checkbox");
    const cancelButtons = document.querySelectorAll(".cancel-btn");
    const searchInput = document.querySelector(".search-container input");
    const searchButton = document.querySelector(".search-container button");
    const orderList = document.getElementById("order-list");

    // 전체 선택 기능
    selectAllCheckbox.addEventListener("change", () => {
        rowCheckboxes.forEach((checkbox) => {
            checkbox.checked = selectAllCheckbox.checked;
        });
    });

    // 취소 버튼 클릭 이벤트
    cancelButtons.forEach((button) => {
        button.addEventListener("click", (e) => {
            const status = button.dataset.status;
            if (status === "배송 전") {
                alert("주문이 취소되었습니다.");
                button.disabled = true;
                button.textContent = "취소 완료";
            } else {
                alert("배송 완료된 주문은 취소할 수 없습니다.");
            }
        });
    });

    // 검색 기능
    searchButton.addEventListener("click", () => {
        const query = searchInput.value.trim().toLowerCase();
        const rows = orderList.querySelectorAll("tr");

        rows.forEach((row) => {
            const status = row.children[1]?.textContent.toLowerCase() || "";
            const productName = row.children[2]?.textContent.toLowerCase() || "";

            // 조건에 맞는 행만 표시
            if (status.includes(query) || productName.includes(query)) {
                row.style.display = "";
            } else {
                row.style.display = "none";
            }
        });

        // 검색 결과 없을 때 처리
        const visibleRows = Array.from(rows).filter((row) => row.style.display !== "none");
        if (visibleRows.length === 0) {
            orderList.innerHTML = `<tr><td colspan="6">조건에 맞는 데이터가 없습니다.</td></tr>`;
        }
    });
});