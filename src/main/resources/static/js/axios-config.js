
// JWT 토큰 관리 함수
export function getToken() {
    return localStorage.getItem('jwt_token');
}

export function setToken(token) {
    localStorage.setItem('jwt_token', token);
}

// Axios 요청 인터셉터
axios.interceptors.request.use(
    function(config) {
        console.log("test")
        const token = getToken(); // JWT 토큰 가져오기
        if (token) {
            config.headers['Authorization'] = `Bearer ${token}`; // Authorization 헤더에 토큰 추가
        }
        return config;
    },
    function(error) {
        return Promise.reject(error);
    }
);

// Axios 응답 인터셉터
let isRefreshing = false; // 토큰 갱신 상태
let failedQueue = []; // 실패한 요청 대기열

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

        console.log(error)

        console.log("test")
        const originalRequest = error.config;

        // 토큰 만료 처리
        if (error.response && error.response.data.status === 401 &&
            error.response.data.message.includes("TOKEN_EXPIRED") && !originalRequest._retry) {
            if (isRefreshing) {
                return new Promise((resolve, reject) => {
                    failedQueue.push({ resolve, reject });
                })
                    .then(token => {
                        originalRequest.headers['Authorization'] = `Bearer ${token}`;
                        return axios(originalRequest);
                    })
                    .catch(err => Promise.reject(err));
            }

            originalRequest._retry = true;
            isRefreshing = true;

            return new Promise((resolve, reject) => {
                axios.post('/backend/auth/re-issue', {})
                    .then(({ data }) => {
                        const newToken = data.accessToken;
                        setToken(newToken);
                        processQueue(null, newToken);
                        originalRequest.headers['Authorization'] = `Bearer ${newToken}`;
                        resolve(axios(originalRequest));
                    })
                    .catch(err => {
                        processQueue(err, null);
                        reject(err);
                    })
                    .finally(() => {
                        isRefreshing = false;
                    });
            });
        }

        // 기타 에러 처리
        if (error.response.data.status === 401) {
            if (getToken()) {
                alert("다시 로그인 해주세요.");
            } else {
                alert("로그인이 필요한 서비스입니다.");
            }
            setToken(null);
            window.location.href = "/frontend/login";
        } else {
            alert(error.response.data.message);
        }

        return Promise.reject(error);
    }
);

