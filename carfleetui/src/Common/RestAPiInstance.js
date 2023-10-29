import axios from 'axios';
import APIs from './API';

const BASE_URL = '/'

let defautRequestConfig = {
  baseURL: '/'
}

const defaultRestApi = axios.create(defautRequestConfig);

defaultRestApi.interceptors.request.use(function (config) {
    config.params = {
      locale: "en_US",
      '_': new Date().getTime(),
      withCredentials: true
      };
    return config;
  }, function (error) {
    // Do something with request error
    return Promise.reject(error);
  });

  // defaultRestApi.interceptors.response.use(response => {
  //   return response;
  // }, async error => {
  //   const originalRequest = error.config;
  //   if (error.response.status === 403 && !originalRequest._retry) {
  //     originalRequest._retry = true;
      
  //     // Fetch a new token using the refresh token API.
  //     const token = await refreshToken();
  //     // Set the new token for the original request and re-try it.
  //     originalRequest.headers['Authorization'] = 'Bearer ' + token;
  //     return await defaultRestApi(originalRequest);
  //   }
  
  //   return Promise.reject(error);
  // });

  // async function refreshToken() {

  //   const response = await axios.post(APIs.getRefreshToken(), {
  //     refreshToken: localStorage.getItem('refreshToken')
  //   });
  //   const newToken = response.data.accessToken;
  //   console.log("New token accessed " + newToken)
  //   localStorage.setItem("token", newToken);
  //   return newToken;
  // }

export default defaultRestApi;