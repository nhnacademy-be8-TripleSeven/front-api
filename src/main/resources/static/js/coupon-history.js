// document.querySelector('.filter-btn').addEventListener('click', (event) => {
//     event.preventDefault();
//     const form = event.target.closest('form');
//     const params = new URLSearchParams(new FormData(form)).toString();
//
//     fetch(`/coupon-history?${params}`)
//         .then(response => response.text())
//         .then(html => {
//             const parser = new DOMParser();
//             const doc = parser.parseFromString(html, 'text/html');
//             document.querySelector('.coupon-history').innerHTML = doc.querySelector('.coupon-history').innerHTML;
//         })
//         .catch(error => console.error('Error fetching coupons:', error));
// });
