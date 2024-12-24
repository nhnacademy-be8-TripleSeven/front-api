document.addEventListener("DOMContentLoaded", () => {
    const button = document.querySelector(".filter-btn");
    const tbody = document.querySelector("table tbody");

    // 예제 데이터
    const exampleData = [
        {
            orderNumber: "Y032208960",
            orderDate: "2024-12-15",
            orderDetail: "도서명: 고래, 알렉스 그림책",
            orderPrice: "18,900원 / 1개",
            orderStatus: "반품 완료",
            receiver: "User1",
        },
        {
            orderNumber: "Y032208961",
            orderDate: "2024-12-16",
            orderDetail: "도서명: 코딩의 세계",
            orderPrice: "25,000원 / 2개",
            orderStatus: "반품 처리 신청",
            receiver: "User2",
        },
        {
            orderNumber: "Y032208962",
            orderDate: "2024-12-17",
            orderDetail: "도서명: JavaScript의 기초",
            orderPrice: "15,000원 / 1개",
            orderStatus: "교환 처리 신청",
            receiver: "User3",
        },
        {
            orderNumber: "Y032208963",
            orderDate: "2024-12-18",
            orderDetail: "도서명: HTML의 정석",
            orderPrice: "30,000원 / 1개",
            orderStatus: "교환 완료",
            receiver: "User4",
        },
    ];

    // 조회 버튼 클릭 이벤트
    button.addEventListener("click", () => {
        const startDateInput = document.getElementById("start-date").value;
        const endDateInput = document.getElementById("end-date").value;
        const statusInput = document.getElementById("filter-options").value;

        const startDate = startDateInput ? new Date(startDateInput) : null;
        const endDate = endDateInput ? new Date(endDateInput) : null;

        // 필터링 로직
        const filteredData = exampleData.filter((data) => {
            const orderDate = new Date(data.orderDate);

            // 날짜 필터 조건
            const isWithinDateRange =
                (!startDate || orderDate >= startDate) &&
                (!endDate || orderDate <= endDate);

            // 상태 필터 조건
            const isStatusMatch =
                statusInput === "all" || data.orderStatus === statusInput;

            return isWithinDateRange && isStatusMatch;
        });

        // 테이블 초기화
        tbody.innerHTML = "";

        // 필터링 결과가 있으면 테이블에 추가
        if (filteredData.length > 0) {
            filteredData.forEach((data) => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${data.orderNumber}</td>
                    <td>${data.orderDate}</td>
                    <td>${data.orderDetail}</td>
                    <td>${data.orderPrice}</td>
                    <td>${data.orderStatus}</td>
                    <td>${data.receiver}</td>
                `;
                tbody.appendChild(row);
            });
        } else {
            // 결과가 없을 때 메시지 표시
            const noDataRow = document.createElement("tr");
            noDataRow.innerHTML = `
                <td colspan="6" class="info-box">조건에 맞는 데이터가 없습니다.</td>
            `;
            tbody.appendChild(noDataRow);
        }

        // 항상 추가되는 메시지
        const infoRow = document.createElement("tr");
        infoRow.innerHTML = `
            <td colspan="6" class="info-box">리뷰 작성 시 포인트가 지급됩니다.</td>
        `;
        tbody.appendChild(infoRow);
    });
});