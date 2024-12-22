document.getElementById("search-btn").addEventListener("click", () => {
    // 입력값 가져오기
    const startDate = document.getElementById("start-date").value;
    const endDate = document.getElementById("end-date").value;
    const keyword = document.getElementById("keyword").value.toLowerCase();

    // 테스트 데이터
    const data = [
        { id: "1", bookId: "nhnbook01", bookName: "도서 1", memberId: "User1", likeDate: "2024-06-01" },
        { id: "2", bookId: "nhnbook02", bookName: "도서 2", memberId: "User2", likeDate: "2024-05-15" },
        { id: "3", bookId: "nhnbook03", bookName: "좋은 책", memberId: "User3", likeDate: "2024-04-10" }
    ];

    // 필터링 조건 적용
    const filteredData = data.filter(item => {
        const matchesStartDate = !startDate || new Date(item.likeDate) >= new Date(startDate);
        const matchesEndDate = !endDate || new Date(item.likeDate) <= new Date(endDate);
        const matchesKeyword = !keyword || item.bookName.toLowerCase().includes(keyword);

        // 조건 중 하나라도 충족하면 true
        return matchesStartDate && matchesEndDate && matchesKeyword;
    });

    // 테이블 업데이트
    const tableBody = document.getElementById("like-table-body");
    tableBody.innerHTML = ""; // 기존 데이터 초기화

    if (filteredData.length > 0) {
        filteredData.forEach(item => {
            const row = `
                <tr>
                    <td>${item.id}</td>
                    <td>${item.bookId}</td>
                    <td>${item.bookName}</td>
                    <td>${item.memberId}</td>
                    <td>${item.likeDate}</td>
                </tr>
            `;
            tableBody.innerHTML += row;
        });
    } else {
        tableBody.innerHTML = `<tr><td colspan="5">조건에 맞는 데이터가 없습니다.</td></tr>`;
    }
});