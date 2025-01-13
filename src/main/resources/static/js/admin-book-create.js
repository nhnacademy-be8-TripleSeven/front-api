document.addEventListener('DOMContentLoaded', function () {
  const createBookBtn = document.getElementById('book-create-btn');
  if (createBookBtn) {
    createBookBtn.addEventListener('click', handleCreateBook);
  }
});

function handleCreateBook(event) {
  event.preventDefault(); // 기본 제출 동작 방지

  // 입력 필드 값 수집
  const bookData = {
    title: document.getElementById('book-name').value,
    isbn: document.getElementById('isbn').value,
    publisherName: document.getElementById('publisher-name').value, // 추가

    // categories도 collectNestedInputValues로 수정
    categories: categoryNestedInputValues('categories'),
    bookTypes: TypeCollectNestedInputValues('bookTypes'),
    authors: collectNestedInputValues('authors'),
    publishedDate: document.getElementById('published-date').value,
    description: document.getElementById('description').value,
    regularPrice: document.getElementById('regular-price').value,
    salePrice: document.getElementById('sale-price').value,
    index: document.getElementById('index').value,
    page: document.getElementById('page').value,
    stock: document.getElementById('stock').value,
  };

  // FormData 생성
  const formData = new FormData();

// JSON 데이터 추가
  formData.append('bookCreatDTO', new Blob([JSON.stringify(bookData)], { type: 'application/json' }));

// 파일 데이터 추가
  const coverFiles = document.getElementById('cover-images').files;
  const detailFiles = document.getElementById('detail-images').files;

  // 표지 이미지 추가
  if (coverFiles.length > 0) {
    Array.from(coverFiles).forEach(file => formData.append('coverImages', file));
  } else {
    formData.append('coverImages', new Blob([], { type: 'application/octet-stream' }), ''); // 빈 파일 추가
  }

  // 상세 이미지 추가
  if (detailFiles.length > 0) {
    Array.from(detailFiles).forEach(file => formData.append('detailImages', file));
  } else {
    formData.append('detailImages', new Blob([], { type: 'application/octet-stream' }), ''); // 빈 파일 추가
  }

  // Axios 요청
  axios.post('/admin/books/createBook', formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
  .then(response => {
    console.log('도서 생성 성공:', response);
    alert('도서가 성공적으로 생성되었습니다.');
    window.location.href = '/admin/frontend/books/create'; // 성공 시 리다이렉트
  })
  .catch(error => {
    console.error('도서 생성 실패:', error);
    alert('도서 생성 중 오류가 발생했습니다.');
  });
}

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
document.addEventListener('DOMContentLoaded', function () {
  const categorySelectors = document.querySelectorAll('[name^="categories.level"]');

  // 각 카테고리 셀렉터에 이벤트 리스너 추가
  categorySelectors.forEach((selector, index) => {
    selector.addEventListener('change', function () {
      const selectedValue = this.value; // 현재 선택된 값
      const nextLevel = categorySelectors[index + 1]; // 다음 단계 셀렉터

      if (nextLevel) {
        if (selectedValue) {
          // 다음 단계 카테고리 요청
          fetchCategoriesForLevel(selectedValue, index + 2).then((options) => {
            nextLevel.innerHTML = '<option value="" disabled selected>선택</option>';
            options.forEach((option) => {
              nextLevel.innerHTML += `<option value="${option.id}">${option.name}</option>`;
            });
            nextLevel.disabled = false; // 다음 단계 활성화
          });
        } else {
          // 선택값이 없으면 하위 단계 초기화
          resetCategoryLevels(index + 1, categorySelectors);
        }
      }
    });
  });

  // 초기화
  resetCategoryLevels(1, categorySelectors);
});

// 하위 카테고리 초기화 함수
function resetCategoryLevels(startIndex, categorySelectors) {
  for (let i = startIndex; i < categorySelectors.length; i++) {
    categorySelectors[i].innerHTML = '<option value="" disabled selected>선택</option>';
    categorySelectors[i].disabled = true; // 비활성화
  }
}

// 카테고리 데이터 요청 함수
async function fetchCategoriesForLevel(parentCategoryId, level) {
  try {
    const response = await axios.get(`/admin/books/categoriesParentList`, {
      params: {
        parent: parentCategoryId,
        level: level
      }
    });
    return response.data;
  } catch (error) {
    console.error('Error fetching categories:', error);
    return [];
  }
}