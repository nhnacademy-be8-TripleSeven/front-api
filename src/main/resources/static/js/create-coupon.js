document.addEventListener("DOMContentLoaded", () => {
    const recipientType = document.getElementById("recipient-type");
    const couponCategory = document.getElementById("coupon-category");

    const gradeContainer = document.getElementById("recipient-grade-container");
    const memberContainer = document.getElementById("recipient-member-container");
    const bookSearchContainer = document.getElementById("book-search");
    const categorySearchContainer = document.getElementById("book-category-search");

    // 수신자 유형 변경 이벤트
    recipientType.addEventListener("change", (e) => {
        gradeContainer.style.display = "none";
        memberContainer.style.display = "none";
        const type = e.target.value;
        if (type === "등급별") {
            gradeContainer.style.display = "block";
        } else if (type === "개인별") {
            memberContainer.style.display = "block";
        }
    });

    // 쿠폰 카테고리 변경 이벤트
    couponCategory.addEventListener("change", (e) => {
        bookSearchContainer.style.display = "none";
        categorySearchContainer.style.display = "none";
        const type = e.target.value;
        if (type === "카테고리 쿠폰") {
            categorySearchContainer.style.display = "block";
        } else if (type === "도서 쿠폰") {
            bookSearchContainer.style.display = "block";
        }
    });

    // 쿠폰 정책 검색
    document.getElementById("search-policy-btn").addEventListener("click", () => {
        const query = document.getElementById("coupon-policy-query").value;
        axios
            .get(`/admin/frontend/coupon-policies/search?query=${query}`)
            .then((response) => {
                const select = document.getElementById("coupon-policy");
                select.innerHTML = response.data
                    .map((policy) => `<option value="${policy.id}">${policy.name}</option>`)
                    .join("");
            })
            .catch((error) => console.error("Error fetching coupon policies:", error));
    });

    // 카테고리 검색
    document.getElementById("search-category-btn").addEventListener("click", () => {
        const query = document.getElementById("category-query").value;
        axios
            .get(`/admin/frontend/search/category?query=${query}`)
            .then((response) => {
                const select = document.getElementById("category-select");
                select.innerHTML = response.data
                    .map((category) => `<option value="${category.id}">${category.name}</option>`)
                    .join("");
            })
            .catch((error) => console.error("Error fetching categories:", error));
    });

    // 도서 검색
    document.getElementById("search-book-btn").addEventListener("click", () => {
        const query = document.getElementById("book-query").value;
        axios
            .get(`/admin/frontend/search/book?query=${query}`)
            .then((response) => {
                const select = document.getElementById("book-select");
                select.innerHTML = response.data
                    .map((book) => `<option value="${book.id}">${book.title}</option>`)
                    .join("");
            })
            .catch((error) => console.error("Error fetching books:", error));
    });

    // 양식 제출
    document.getElementById("coupon-form").addEventListener("submit", (e) => {
        e.preventDefault();

        const formData = {
            name: document.getElementById("coupon-name").value,
            couponPolicyId: document.getElementById("coupon-policy").value,
            recipientType: document.getElementById("recipient-type").value,
            grade: document.getElementById("recipient-grade").value,
            memberIds: document.getElementById("recipient-member")
                .value.split(",")
                .map(Number),
            categoryId: document.getElementById("category-select").value || null,
            bookId: document.getElementById("book-select").value || null,
        };

        axios
            .post("/admin/frontend/coupons/create-and-assign", formData)
            .then((response) => {
                alert("쿠폰이 성공적으로 생성 및 발급되었습니다.");
                console.log(response.data);
            })
            .catch((error) => {
                console.error("Error creating and assigning coupon:", error);
                alert(error.response?.data?.message || "쿠폰 생성 중 오류가 발생했습니다.");
            });
    });
});
