function filterCategories() {
  const inputVal = document.getElementById("categorySearchInput").value.toLowerCase();
  const categoryItems = document.querySelectorAll("#categoryList > li");

  let visibleCount = 0; // 현재 표시된 카테고리 수

  categoryItems.forEach(item => {
    const catName = item.getAttribute("data-category")?.toLowerCase() || "";

    // 입력값이 없을 경우 전체 숨김
    if (inputVal === "") {
      item.style.display = "none";
    }
    // 앞글자 일치 + 최대 3개
    else if (catName.startsWith(inputVal) && visibleCount < 3) {
      item.style.display = "list-item";
      visibleCount++;
    } else {
      item.style.display = "none";
    }
  });
}
