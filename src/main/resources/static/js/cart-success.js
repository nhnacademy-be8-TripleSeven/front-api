document.addEventListener("DOMContentLoaded", () => {
    const numberBox = document.getElementById("number-box");
    const deleteBtn = document.getElementById("delete-button");
    const clearCartBtn = document.getElementById("clear-cart-btn");
    // 수량 변경 시 유효성 체크 및 서버에 요청
    numberBox.addEventListener("input", () => {

        if (numberBox.value < numberBox.min) {
            numberBox.value = numberBox.min; // 최소값으로 고정
            return;
        }

        // 수량 값 변경 시 서버에 PUT 요청
        const quantity = numberBox.value; // 변경된 수량
        const bookId = numberBox.getAttribute("data-book-id");

        // Axios로 PUT 요청 보내기
        axios.put("/cart/book/quantity", {}, {
            params: {
                quantity: quantity,
                bookId : bookId
            }
        })
            .then(response => {
            })
            .catch(error => {
                window.location.reload();
            });
    });

    deleteBtn.addEventListener("click", () => {

        const bookId = deleteBtn.getAttribute("data-book-id");
        axios.delete("/cart/book", {
            params: {
                bookId: bookId
            }
        })
            .then(response => {
                window.location.reload();
            })
    })

    clearCartBtn.addEventListener("click", () => {
        // 장바구니 비우기 요청 (DELETE 요청)
        axios.delete("/cart")
            .then(response => {
                window.location.reload();  // 성공 시 장바구니 페이지 새로 고침
            })
            .catch(error => {
                console.error("장바구니 비우기 실패:", error);
                alert("장바구니를 비우는 데 실패했습니다.");
            });
    });

});