import axios from 'vue-axios'

let base = 'api/v1/';
export const postRequest = (url, params) => {
  return axios.post({
    url: `${base}${url}`,
    data: params
  })
}

export const getRequest = (url, params) => {
  return axios.get({
    url: `${base}${url}`,
    data: params
  })
}

// export const uploadFileRequest = (url, params) => {
//   return axios({
//     method: 'post',
//     url: `${base}${url}`,
//     data: params,
//     headers: {
//       'Content-Type': 'multipart/form-data'
//     }
//   });
// }
// export const putRequest = (url, params) => {
//   return axios({
//     method: 'put',
//     url: `${base}${url}`,
//     data: params,
//     transformRequest: [function (data) {
//       let ret = ''
//       for (let it in data) {
//         ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
//       }
//       return ret
//     }],
//     headers: {
//       'Content-Type': 'application/x-www-form-urlencoded'
//     }
//   });
// }
// export const deleteRequest = (url) => {
//   return axios({
//     method: 'delete',
//     url: `${base}${url}`
//   });
// }
// export const getRequest = (url,params) => {
//   return axios({
//     method: 'get',
//     data:params,
//     transformRequest: [function (data) {
//       let ret = ''
//       for (let it in data) {
//         ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
//       }
//       return ret
//     }],
//     headers: {
//       'Content-Type': 'application/x-www-form-urlencoded'
//     },
//     url: `${base}${url}`
//   });
// }
