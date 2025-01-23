// document.addEventListener("DOMContentLoaded", () => {
//     const selectAllCheckbox = document.getElementById("select-all");
//     const rowCheckboxes = document.querySelectorAll(".row-checkbox");
//     const cancelButtons = document.querySelectorAll(".cancel-btn");
//     const searchInput = document.querySelector(".search-container input");
//     const searchButton = document.querySelector(".search-container button");
//     const orderList = document.getElementById("order-list");
//
//     // 전체 선택 기능
//     selectAllCheckbox.addEventListener("change", () => {
//         rowCheckboxes.forEach((checkbox) => {
//             checkbox.checked = selectAllCheckbox.checked;
//         });
//     });
//
//     // 취소 버튼 클릭 이벤트
//     cancelButtons.forEach((button) => {
//         button.addEventListener("click", (e) => {
//             const status = button.dataset.status;
//             if (status === "배송 전") {
//                 alert("주문이 취소되었습니다.");
//                 button.disabled = true;
//                 button.textContent = "취소 완료";
//             } else {
//                 alert("배송 완료된 주문은 취소할 수 없습니다.");
//             }
//         });
//     });
//
//     // 검색 기능
//     searchButton.addEventListener("click", () => {
//         const query = searchInput.value.trim().toLowerCase();
//         const rows = orderList.querySelectorAll("tr");
//
//         rows.forEach((row) => {
//             const status = row.children[1]?.textContent.toLowerCase() || "";
//             const productName = row.children[2]?.textContent.toLowerCase() || "";
//
//             // 조건에 맞는 행만 표시
//             if (status.includes(query) || productName.includes(query)) {
//                 row.style.display = "";
//             } else {
//                 row.style.display = "none";
//             }
//         });
//
//         // 검색 결과 없을 때 처리
//         const visibleRows = Array.from(rows).filter((row) => row.style.display !== "none");
//         if (visibleRows.length === 0) {
//             orderList.innerHTML = `<tr><td colspan="6">조건에 맞는 데이터가 없습니다.</td></tr>`;
//         }
//     });
// });

document.addEventListener("DOMContentLoaded", () => {
    const orderList = document.getElementById("order-list");
    const orderDetailList = document.getElementById("order-detail-list");
    const modal = document.getElementById("order-detail-modal");
    const closeModalButton = document.getElementById("close-modal");

    const statusKoreanMap = {
        payment_pending: "결제대기",
        payment_completed: "결제완료",
        shipping: "배송중",
        delivered: "배송완료",
        returned: "반품",
        order_canceled: "주문취소",
        error: "오류",
    };

    const fetchGuestOrders = async (phone) => {
        try {
            const response = await axios.get("/frontend/guest/order-histories/list", {
                params: { phone }
            });

            const orders = response.data;
            renderOrderList(orders);
        } catch (error) {
            console.error("Error fetching guest orders:", error);
            orderList.innerHTML = `<tr><td colspan="6">주문 데이터를 불러오지 못했습니다.</td></tr>`;
        }
    };

    const renderOrderList = (orders) => {
        orderList.innerHTML = "";

        if (orders.length === 0) {
            orderList.innerHTML = `<tr><td colspan="6">조회된 주문이 없습니다.</td></tr>`;
            return;
        }

        const sortedOrders = orders.sort((a, b) => new Date(b.orderedAt) - new Date(a.orderedAt));

        sortedOrders.forEach(order => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${order.id}</td>
                <td>${order.recipientName}</td>
                <td>${order.address}</td>
                <td>${order.deliveryPrice.toLocaleString()}원</td>
                <td>${new Date(order.orderedAt).toLocaleDateString()}</td>
                <td><button class="detail-btn" data-group-id="${order.id}">상세조회</button></td>
            `;
            orderList.appendChild(row);
        });

        const detailButtons = document.querySelectorAll(".detail-btn");
        detailButtons.forEach(button => {
            button.addEventListener("click", (e) => {
                const orderGroupId = e.target.dataset.groupId;
                fetchOrderDetails(orderGroupId);
            });
        });
    };

    const fetchOrderDetails = async (orderGroupId) => {
        try {
            const response = await axios.get(`/frontend/guest/order-details/${orderGroupId}`);
            const details = response.data;
            renderOrderDetailList(details);
            openModal();
        } catch (error) {
            console.error("Error fetching order details:", error);
            alert("주문 상세 정보를 불러오지 못했습니다.");
        }
    };

    const renderOrderDetailList = (details) => {
        orderDetailList.innerHTML = "";

        if (details.length === 0) {
            orderDetailList.innerHTML = `<tr><td colspan="5">해당 주문 그룹에 대한 상세 정보가 없습니다.</td></tr>`;
            return;
        }

        details.forEach(detail => {
            const row = document.createElement("tr");
            const koreanStatus = statusKoreanMap[detail.orderStatus.toLowerCase()] || "알 수 없음";
            row.innerHTML = `
                <td>${detail.bookId}</td>
                <td>${detail.quantity}</td>
                <td>${koreanStatus}</td>
                <td>${detail.primePrice.toLocaleString()}원</td>
                <td>${detail.discountPrice.toLocaleString()}원</td>
            `;
            orderDetailList.appendChild(row);
        });
    };

    const openModal = () => {
        modal.style.display = "flex";
    };

    const closeModal = () => {
        modal.style.display = "none";
    };

    closeModalButton.addEventListener("click", closeModal);
    modal.addEventListener("click", (event) => {
        if (event.target === modal) {
            closeModal();
        }
    });

    if (typeof phone !== "undefined" && phone) {
        fetchGuestOrders(phone);
    } else {
        console.error("Phone number is missing");
    }
});

