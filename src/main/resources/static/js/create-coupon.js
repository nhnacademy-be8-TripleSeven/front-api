document.addEventListener("DOMContentLoaded", function () {
    const couponForm = document.getElementById("coupon-form");

    // 등록 버튼 클릭 이벤트
    couponForm.addEventListener("submit", function (event) {
        event.preventDefault(); // 폼 제출 기본 동작 막기
        const couponName = document.getElementById("coupon-name").value;
        const couponPolicy = document.getElementById("coupon-policy").value;
        const recipientType = document.getElementById("recipient-type").value;
        const couponCategory = document.getElementById("coupon-category").value;

        if (couponName && couponPolicy && recipientType && couponCategory) {
            alert(
                `쿠폰이 등록되었습니다!\n` +
                `쿠폰 이름: ${couponName}\n` +
                `정책: ${couponPolicy}\n` +
                `대상: ${recipientType}\n` +
                `분류: ${couponCategory}`
            );
        } else {
            alert("모든 필드를 입력해주세요.");
        }
    });

    // 발급 대상 선택 시 팝업 열기
    const recipientTypeSelect = document.getElementById("recipient-type");
    recipientTypeSelect.addEventListener("change", function () {
        const value = recipientTypeSelect.value;

        if (value === "등급별") {
            openGradePopup();
        } else {
            const popupUrl = getPopupUrl(value);
            openPopup(popupUrl, `${value} 팝업`);
        }
    });

    // 분류 선택 시 팝업 열기
    const couponCategorySelect = document.getElementById("coupon-category");
    couponCategorySelect.addEventListener("change", function () {
        const value = couponCategorySelect.value;

        const popupUrl = getPopupUrl(value);
        openPopup(popupUrl, `${value} 팝업`);
    });

    // 팝업 URL 매핑 함수
    function getPopupUrl(value) {
        switch (value) {
            case "전체":
                return "/static/popup/popup-all.html";
            case "개인별":
                return "/static/popup/popup-individual.html";
            case "도서 쿠폰":
            case "카테고리 쿠폰":
            case "일반 쿠폰":
                return "/static/popup/popup-category.html";
            default:
                console.error("Invalid popup type selected.");
                return null;
        }
    }

    // 팝업 열기 함수
    function openPopup(url, title) {
        if (url) {
            window.open(
                url,
                title,
                "width=500,height=400,scrollbars=no,resizable=no,top=200,left=500"
            );
        } else {
            alert("팝업을 열 수 없습니다. URL이 잘못되었습니다.");
        }
    }

    // 등급별 팝업 열기 함수
    function openGradePopup() {
        const popup = window.open(
            "/static/popup/popup-grade.html",
            "등급별 발급",
            "width=500,height=400,scrollbars=no,resizable=no,top=200,left=500"
        );

        // 팝업에서 선택한 등급을 부모 창으로 반영
        popup.addEventListener("unload", function () {
            if (popup.selectedGrade) { // 팝업에서 설정된 전역 변수
                const newOption = new Option(popup.selectedGrade, popup.selectedGrade, true, true);
                recipientTypeSelect.options.length = 0; // 기존 옵션 제거
                recipientTypeSelect.add(newOption); // 새 옵션 추가
            }
        });
    }
});
