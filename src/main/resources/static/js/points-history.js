document.addEventListener("DOMContentLoaded", () => {
    const searchButton = document.getElementById("search-btn");
    const startDateInput = document.getElementById("start-date");
    const endDateInput = document.getElementById("end-date");
    const tableBody = document.getElementById("point-table-body");
    const paginationDiv = document.getElementById("pagination");

    /**
     * Fetch data from the server based on filters and pagination.
     * @param {number} page - Current page number (0-based index).
     * @param {number} size - Number of records per page.
     */
    const fetchData = async (page = 0, size = 10) => {
        const startDate = startDateInput.value || new Date(new Date().setFullYear(new Date().getFullYear() - 5)).toISOString().split("T")[0]; // 5년 전 날짜
        const endDate = endDateInput.value || new Date().toISOString().split("T")[0]; // 현재 날짜

        try {
            const response = await axios.get(`/api/frontend/points/history/data`, {
                params: { startDate, endDate, page, size }
            });

            const data = response.data;
            renderTable(data.content); // Render table with fetched data
            renderPagination(data); // Render pagination controls
        } catch (error) {
            console.error("Error fetching point histories:", error);
            tableBody.innerHTML = `<tr><td colspan="6">데이터를 불러오는 중 오류가 발생했습니다.</td></tr>`;
        }
    };

    /**
     * Render table rows with point history data.
     * @param {Array} histories - Array of point history objects.
     */
    const renderTable = (histories) => {
        tableBody.innerHTML = ""; // Clear existing table data
        if (!histories.length) {
            const row = document.createElement("tr");
            row.innerHTML = `<td colspan="6">조회된 데이터가 없습니다.</td>`;
            tableBody.appendChild(row);
            return;
        }

        histories.forEach(history => {
            const row = document.createElement("tr");
            const amount = history.types === "SPEND" ? `-${history.amount}` : history.amount;
            row.innerHTML = `
                <td>${history.pointHistoryId}</td>
                <td>${history.orderGroupId || "N/A"}</td>
                <td>${history.typesKoreanName}</td>
                <td>${amount} P</td>
                <td>${new Date(history.changedAt).toLocaleString()}</td>
                <td>${history.comment}</td>
            `;
            tableBody.appendChild(row);
        });
    };

    /**
     * Render pagination controls.
     * @param {Object} page - PageResponseDTO object containing pagination details.
     */
    const renderPagination = (page) => {
        paginationDiv.innerHTML = ""; // Clear existing pagination controls

        const currentPage = page.pageNumber ?? 0; // Use pageNumber instead of currentPage
        const totalPages = page.totalPages ?? 0; // Ensure totalPages fallback to 0 if undefined

        if (totalPages > 1) {
            if (currentPage > 0) { // Show "Previous" button if not on the first page
                const prev = document.createElement("button");
                prev.textContent = "이전";
                prev.addEventListener("click", () => fetchData(currentPage - 1));
                paginationDiv.appendChild(prev);
            }

            const current = document.createElement("span");
            current.textContent = `${currentPage + 1} / ${totalPages}`; // Display 1-based index
            paginationDiv.appendChild(current);

            if (currentPage + 1 < totalPages) { // Show "Next" button if not on the last page
                const next = document.createElement("button");
                next.textContent = "다음";
                next.addEventListener("click", () => fetchData(currentPage + 1));
                paginationDiv.appendChild(next);
            }
        } else {
            const noPages = document.createElement("span");
            noPages.textContent = "1 / 1"; // Default to 1 page if only 1 page exists
            paginationDiv.appendChild(noPages);
        }
    };


    searchButton.addEventListener("click", () => fetchData());


    fetchData();
});
