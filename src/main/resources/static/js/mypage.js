document.querySelector("#search-button").addEventListener("click", function () {

    const dateValue = document.querySelector('input[type="date"]').value;
    const periodValue = document.querySelector('select[name="period"]').value;
    const statusValue = document.querySelector('select[name="status"]').value;
    const searchValue = document.querySelector('input[type="text"]').value;


    fetch('/api/orders', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            date: dateValue,
            period: periodValue,
            status: statusValue,
            search: searchValue,
        }),
    })
        .then((response) => response.json())
        .then((data) => {
            const tableBody = document.querySelector("#order-table-body");
            tableBody.innerHTML = "";

            data.forEach((order) => {
                const row = document.createElement("tr");

                row.innerHTML = `
          <td>${order.date}</td>
          <td>${order.orderNumber}</td>
          <td>${order.receiver}</td>
          <td>${order.product}</td>
          <td><button class="view-button" data-id="${order.id}">조회</button></td>
          <td>${order.note || "N/A"}</td>
        `;

                tableBody.appendChild(row);
            });
        })
        .catch((error) => {
            console.error("Error fetching orders:", error);
        });
});