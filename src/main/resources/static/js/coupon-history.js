document.getElementById("search-btn").addEventListener("click", () => {
    // 필터 입력 값 가져오기
    const startDate = document.getElementById("start-date").value;
    const endDate = document.getElementById("end-date").value;
    const status = document.getElementById("filter-status").value;
    const keyword = document.getElementById("keyword").value.trim().toLowerCase();

    // 샘플 데이터
    const data = [
        { couponNo: "NHN01001", issueDate: "2024-12-01", expiryDate: "2024-12-31", discount: "35%(3만원 이상 구매시)", usage: "미사용", usageDate: "" },
        { couponNo: "NHN01002", issueDate: "2024-11-20", expiryDate: "2024-12-15", discount: "20%", usage: "사용 완료", usageDate: "2024-12-05" },
        { couponNo: "NHN01003", issueDate: "2024-10-01", expiryDate: "2024-11-01", discount: "10%", usage: "사용 완료", usageDate: "2024-10-10" }
    ];

    // 필터링 조건 적용
    const filteredData = data.filter(item => {
        // 날짜 필터 조건
        const isDateValid = (!startDate || new Date(item.issueDate) >= new Date(startDate)) &&
            (!endDate || new Date(item.issueDate) <= new Date(endDate));

        // 상태 필터 조건
        const isStatusValid = status === "all" ||
            (status === "unused" && item.usage === "미사용") ||
            (status === "used" && item.usage === "사용 완료");

        // 키워드 필터 조건
        const isKeywordValid = keyword === "" ||
            item.couponNo.toLowerCase().includes(keyword) ||
            item.discount.toLowerCase().includes(keyword);

        return isDateValid && isStatusValid && isKeywordValid;
    });

    // 발급 내역 업데이트
    const issuedCouponsTable = document.getElementById("issued-coupons");
    issuedCouponsTable.innerHTML = ""; // 기존 테이블 초기화

    const issuedCoupons = filteredData.filter(item => item.usage === "미사용" || item.usage === "사용 완료");
    if (issuedCoupons.length > 0) {
        issuedCoupons.forEach(item => {
            const row = `
                <tr>
                    <td>${item.couponNo}</td>
                    <td>${item.issueDate}</td>
                    <td>${item.expiryDate}</td>
                    <td>${item.discount}</td>
                    <td>${item.usage}</td>
                </tr>
            `;
            issuedCouponsTable.innerHTML += row;
        });
    } else {
        issuedCouponsTable.innerHTML = `<tr><td colspan="5">조건에 맞는 데이터가 없습니다.</td></tr>`;
    }

    // 사용 내역 업데이트
    const usedCouponsTable = document.getElementById("used-coupons");
    usedCouponsTable.innerHTML = ""; // 기존 테이블 초기화

    const usedCoupons = filteredData.filter(item => item.usage === "사용 완료");
    if (usedCoupons.length > 0) {
        usedCoupons.forEach(item => {
            const row = `
                <tr>
                    <td>${item.couponNo}</td>
                    <td>${item.issueDate}</td>
                    <td>${item.usageDate}</td>
                    <td>${item.usage}</td>
                </tr>
            `;
            usedCouponsTable.innerHTML += row;
        });
    } else {
        usedCouponsTable.innerHTML = `<tr><td colspan="4">조건에 맞는 데이터가 없습니다.</td></tr>`;
    }
});