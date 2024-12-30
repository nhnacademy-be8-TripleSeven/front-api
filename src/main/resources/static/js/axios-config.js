// JWT 토큰을 가져오는 함수
function getToken() {
    return localStorage.getItem('jwt_token');
}

// JWT 토큰을 저장하는 함수
function setToken(token) {
    localStorage.setItem('jwt_token', token);
}

// Axios 전역 설정 (인터셉터)
axios.interceptors.request.use(
    function(config) {
        const token = getToken(); // JWT 토큰을 가져옵니다.
        if (token) {
            config.headers['Authorization'] = `Bearer ${token}`; // 요청에 토큰을 포함시킴
        }
        return config;
    },
    function(error) {
        return Promise.reject(error);
    }
);

// Axios 응답 인터셉터 설정 (에러 처리)
let isRefreshing = false; // 토큰 갱신 중인지 확인
let failedQueue = []; // 실패한 요청을 저장

function processQueue(error, token = null) {
    failedQueue.forEach(promise => {
        if (error) {
            promise.reject(error);
        } else {
            promise.resolve(token);
        }
    });
    failedQueue = [];
}

axios.interceptors.response.use(
    function(response) {
        return response;
    },
    function(error) {
        const originalRequest = error.config;

        // 토큰 만료 처리
        if (error.response && error.response.status === 401 &&
            error.response.data.message.contain("TOKEN_EXPIRED") && !originalRequest._retry ) {
            if (isRefreshing) {
                // 토큰 갱신 중인 경우 요청 대기
                return new Promise((resolve, reject) => {
                    failedQueue.push({ resolve, reject });
                })
                    .then(token => {
                        originalRequest.headers['Authorization'] = `Bearer ${token}`;
                        return axios(originalRequest);
                    })
                    .catch(err => Promise.reject(err));
            }

            originalRequest._retry = true; // 요청 재시도 표시
            isRefreshing = true;

            // 토큰 갱신 요청
            return new Promise((resolve, reject) => {
                axios
                    .post('/backend/auth/re-issue', {})
                    .then(({ data }) => {
                        const newToken = data.accessToken; // 새 토큰
                        setToken(newToken); // localStorage에 저장
                        processQueue(null, newToken); // 대기 중인 요청 처리
                        originalRequest.headers['Authorization'] = `Bearer ${newToken}`;
                        resolve(axios(originalRequest)); // 원래 요청 재시도
                    })
                    .catch(err => {
                        processQueue(err, null); // 실패한 요청 처리
                        reject(err); // Promise 거절
                    })
                    .finally(() => {
                        isRefreshing = false; // 갱신 상태 초기화
                    });
            });
        }

        // 기타 에러 처리

        else if (error.response.status === 401) {

            if (getToken() != null) {
                alert("다시 로그인 해주세요");
            } else {
                alert("로그인이 필요한 서비스입니다.");
            }
            setToken(null)
            window.location.href = "/login";
        } else {
            alert(error.response.data.message);
        }

        return Promise.reject(error);
    }
);
