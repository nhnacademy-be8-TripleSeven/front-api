document.addEventListener('DOMContentLoaded', function () {
  const updateBookBtn = document.getElementById('book-update-btn');
  if (updateBookBtn) {
    updateBookBtn.addEventListener('click', handleUpdateBook);
  }
});

// [1] 업데이트 처리 함수
function handleUpdateBook(event) {
  event.preventDefault();

  // [2] JSON에 담을 데이터 수집
  const bookData = {
    id: document.getElementById('book-id').value,
    title: document.getElementById('book-title').value,
    isbn: document.getElementById('book-isbn').value,
    publishedDate: document.getElementById('published-date').value,
    description: document.getElementById('description').value,
    regularPrice: document.getElementById('regular-price').value,
    salePrice: document.getElementById('sale-price').value,
    index: document.getElementById('index').value,
    page: document.getElementById('page').value,
    stock: document.getElementById('stock').value,

    // 중첩 필드들
    categories: categoryNestedInputValues('categories'),
    bookTypes: TypeCollectNestedInputValues('bookTypes'),
    authors: collectNestedInputValues('authors'),
  };

  // FormData 생성
  const formData = new FormData();

// JSON 데이터 추가
  formData.append('bookUpdateDTO', new Blob([JSON.stringify(bookData)], { type: 'application/json' }));

// 파일 데이터 추가
  const coverFiles = document.getElementById('cover-images').files;
  const detailFiles = document.getElementById('detail-images').files;

  // 표지 이미지 추가
  if (coverFiles.length > 0) {
    Array.from(coverFiles).forEach(file => formData.append('coverImage', file));
  } else {
    formData.append('coverImages', new Blob([], { type: 'application/octet-stream' }), ''); // 빈 파일 추가
  }

  // 상세 이미지 추가
  if (detailFiles.length > 0) {
    Array.from(detailFiles).forEach(file => formData.append('detailImage', file));
  } else {
    formData.append('detailImages', new Blob([], { type: 'application/octet-stream' }), ''); // 빈 파일 추가
  }

  // [4] Axios 요청
  axios.post('/admin/books/updateBook', formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
  .then(response => {
    console.log('도서 수정 성공:', response);
    alert('도서가 성공적으로 수정되었습니다.');
    // 수정 후 이동할 경로
    window.location.href = '/admin/frontend/books';
  })
  .catch(error => {
    console.error('도서 수정 실패:', error);
    alert('도서 수정 중 오류가 발생했습니다.');
  });
}

// [5] categories, bookTypes, authors와 같은 중첩 필드 수집 함수
// "categories[0].name" 등의 인풋들을 { name: ... } 객체 배열로 만들기
// 카테고리, 도서 타입, 저자 등 중첩 필드 수집 함수
function collectNestedInputValues(fieldName) {
  const inputs = document.querySelectorAll(`[name^="${fieldName}"]`);
  const values = [];

  inputs.forEach(input => {
    const match = input.name.match(/\[(\d+)\]\.(.+)/);
    if (match) {
      const index = parseInt(match[1], 10);
      const key = match[2];

      if (!values[index]) {
        values[index] = {};
      }

      values[index][key] = input.value.trim();
    }
  });

  return values;
}

// [6] tags 같은 단순 배열( "tags[0]", "tags[1]" ) 수집 함수
function collectInputValues(fieldName) {
  const inputs = document.querySelectorAll(`input[name^="${fieldName}"]`);
  return Array.from(inputs).map(input => input.value.trim());
}

function TypeCollectNestedInputValues(fieldName) {
  const inputs = document.querySelectorAll(`input[name^="${fieldName}"]`);
  const values = [];

  console.log('Found inputs:', inputs);  // inputs를 확인

  inputs.forEach(input => {
    console.log('Input name:', input.name); // name 속성 확인

    const match = input.name.match(/\[(\d+)\]\.(\w+)/); // 정규식 확인
    console.log('Match result:', match); // 매칭 결과 확인

    if (match) {
      const index = parseInt(match[1], 10);  // [0], [1], [2] 의 숫자 추출
      const key = match[2];  // 'type' 또는 'ranks' 추출

      if (!values[index]) {
        values[index] = {};  // 해당 인덱스에 객체 할당
      }

      values[index][key] = input.value.trim();  // 값 할당
    }
  });

  console.log('Final values:', values);  // 최종 값 확인
  return values;
}



function categoryNestedInputValues(fieldName) {
  const inputs = document.querySelectorAll(`[name^="${fieldName}"]`);
  const values = [];

  // 레벨마다 값을 처리
  inputs.forEach((input) => {
    const match = input.name.match(/\.(.+)/); // "categories.level1", "categories.level2"에서 level1, level2 추출
    if (match) {
      const level = match[1]; // level1, level2 등의 키 이름 추출
      const numericLevel = parseInt(level.replace(/[^\d]/g, ''), 10); // level1 -> 1, level2 -> 2 등으로 변환

      const selectedOption = input.options[input.selectedIndex];
      if (selectedOption && selectedOption.value) {
        const categoryName = selectedOption.text.trim(); // 선택된 옵션의 텍스트 값
        values.push({ level: numericLevel, name: categoryName });
      }
    }
  });

  return values;
}
document.addEventListener("DOMContentLoaded", function () {
  const categorySelectors = document.querySelectorAll('[name^="categories.level"]');

  // 각 셀렉트 박스에 이벤트 리스너 추가
  categorySelectors.forEach((selector, index) => {
    selector.addEventListener("change", function () {
      const nextLevel = categorySelectors[index + 1];

      if (nextLevel) {
        if (this.value) {
          nextLevel.disabled = false; // 현재 단계 선택 시 다음 단계 활성화
        } else {
          resetCategoryLevels(index + 1, categorySelectors); // 선택 해제 시 이후 단계 초기화
        }
      }
    });
  });

  // 초기 상태: 첫 번째 셀렉트만 활성화, 나머지는 비활성화
  resetCategoryLevels(1, categorySelectors);
});

function resetCategoryLevels(startIndex, categorySelectors) {
  for (let i = startIndex; i < categorySelectors.length; i++) {
    categorySelectors[i].value = ""; // 값 초기화
    categorySelectors[i].disabled = true; // 비활성화
  }
}
