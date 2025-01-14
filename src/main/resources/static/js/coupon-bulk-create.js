document.addEventListener("DOMContentLoaded", () => {
    const couponPolicySelect = document.getElementById("coupon-policy");
    const couponCategorySelect = document.getElementById("coupon-category");
    const bookSearchContainer = document.getElementById("book-search");
    const categorySearchContainer = document.getElementById("book-category-search");

    // 쿠폰 정책 검색 버튼 클릭 이벤트
    document.getElementById("search-policy-btn").addEventListener("click", () => {
        const query = document.getElementById("coupon-policy-query").value;
        axios.get(`/admin/frontend/coupon-policies/search?query=${query}`)
            .then(response => {
                couponPolicySelect.innerHTML = response.data
                    .map(policy => `<option value="${policy.id}">${policy.name}</option>`)
                    .join("");
            })
            .catch(error => {
                console.error("Error fetching coupon policies:", error);
                alert("쿠폰 정책을 검색하는 중 오류가 발생했습니다.");
            });
    });

    // 카테고리 또는 도서 검색 활성화
    couponCategorySelect.addEventListener("change", () => {
        const category = couponCategorySelect.value;
        bookSearchContainer.style.display = category === "도서 쿠폰" ? "block" : "none";
        categorySearchContainer.style.display = category === "카테고리 쿠폰" ? "block" : "none";
    });

    // 카테고리 검색
    document.getElementById("search-category-btn").addEventListener("click", () => {
        const query = document.getElementById("category-query").value;
        axios.get(`/admin/frontend/coupons/category-search?query=${query}`)
            .then(response => {
                const select = document.getElementById("category-select");
                select.innerHTML = response.data
                    .map(category => `<option value="${category.id}">${category.name}</option>`)
                    .join("");
            })
            .catch(error => {
                console.error("Error fetching categories:", error);
            });
    });

    // 도서 검색
    document.getElementById("search-book-btn").addEventListener("click", () => {
        const query = document.getElementById("book-query").value;
        axios.get(`/admin/frontend/coupons/book-search?query=${query}`)
            .then(response => {
                const select = document.getElementById("book-select");
                select.innerHTML = response.data
                    .map(book => `<option value="${book.id}">${book.title}</option>`)
                    .join("");
            })
            .catch(error => {
                console.error("Error fetching books:", error);
            });
    });

    // 폼 제출
    document.getElementById("bulk-coupon-form").addEventListener("submit", (event) => {
        event.preventDefault();

        const formData = {
            name: document.getElementById("coupon-name").value,
            couponPolicyId: document.getElementById("coupon-policy").value,
            quantity: document.getElementById("coupon-quantity").value,
            categoryId: document.getElementById("category-select").value || null,
            bookId: document.getElementById("book-select").value || null
        };

        axios.post("/admin/frontend/coupons/bulk", formData)
            .then(response => {
                const { success, createdCount } = response.data;
                if (success) {
                    alert(`쿠폰 생성 완료. 생성된 쿠폰 수: ${createdCount}`);
                } else {
                    alert("쿠폰 생성에 실패했습니다.");
                }
            })
            .catch(error => {
                console.error("Error creating coupons in bulk:", error);
                alert("쿠폰 생성 중 오류가 발생했습니다.");
            });
    });
});
