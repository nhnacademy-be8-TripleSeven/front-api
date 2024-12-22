document.getElementById("search-btn").addEventListener("click", () => {
    // 필터 입력 값 가져오기
    const startDate = document.getElementById("start-date").value;
    const endDate = document.getElementById("end-date").value;
    const status = document.getElementById("filter-status").value;

    // 샘플 데이터
    const data = [
        { orderNo: "Y032208960", date: "2024-12-15", details: "도서명: 고래", amount: "18,900원", status: "결제 완료", receiver: "User1" },
        { orderNo: "Y032208961", date: "2024-11-10", details: "도서명: 나무", amount: "15,000원", status: "결제 대기", receiver: "User2" },
        { orderNo: "Y032208962", date: "2024-10-01", details: "도서명: 바다", amount: "12,000원", status: "취소 완료", receiver: "User3" }
    ];

    // 필터링 조건 적용
    const filteredData = data.filter(item => {
        const isDateValid =
            (!startDate || new Date(item.date) >= new Date(startDate)) &&
            (!endDate || new Date(item.date) <= new Date(endDate));

        const isStatusValid =
            status === "all" || item.status === status;

        return isDateValid && isStatusValid; // 날짜와 상태 조건 모두 충족
    });

    // 테이블 업데이트
    const tableBody = document.getElementById("order-table-body");
    tableBody.innerHTML = ""; // 기존 테이블 초기화

    if (filteredData.length > 0) {
        filteredData.forEach(item => {
            const row = `
                <tr>
                    <td>${item.orderNo}</td>
                    <td>${item.date}</td>
                    <td>${item.details}</td>
                    <td>${item.amount}</td>
                    <td>${item.status}</td>
                    <td>${item.receiver}</td>
                </tr>
            `;
            tableBody.innerHTML += row;
        });
    } else {
        tableBody.innerHTML = `<tr><td colspan="6">조건에 맞는 데이터가 없습니다.</td></tr>`;
    }
});

// 정렬 기능 추가
document.getElementById("sort-order").addEventListener("change", (e) => {
    const sortBy = e.target.value;

    const rows = Array.from(document.querySelectorAll("#order-table-body tr"));
    rows.sort((a, b) => {
        const colA = a.cells[0].textContent.trim(); // 첫 번째 열 기준
        const colB = b.cells[0].textContent.trim();

        if (sortBy === "desc") {
            return colA < colB ? 1 : -1;
        }
        return colA > colB ? 1 : -1;
    });

    // 정렬된 데이터 반영
    const tableBody = document.getElementById("order-table-body");
    tableBody.innerHTML = "";
    rows.forEach(row => tableBody.appendChild(row));
});