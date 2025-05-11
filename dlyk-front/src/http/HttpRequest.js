import axios from "axios";

// 定义后端接口地址的前缀
axios.defaults.baseURL = "http://localhost:8089";

export function doGet(url, params) {
    return axios({
        method: 'GET',
        url: url,
        params: params,
        dataType: "json"
    })
}

export function doPost(url, data) {
    return axios({
        method: 'POST',
        url: url,
        data: data,
        dataType: "json"
    })
}

export function doPut(url, data) {
    return  axios({
        method: 'PUT',
        url: url,
        data: data,
        dataType: "json"
    })
}

export function doDelete(url, params) {
    return axios({
        method: 'DELETE',
        url: url,
        params: params,
        dataType: "json"
    })
}