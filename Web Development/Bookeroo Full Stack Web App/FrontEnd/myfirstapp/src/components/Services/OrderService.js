import axios from "axios";

const ORDER_REST_API_URL = "http://localhost:1004/api/order";

class OrderService {
  post(backendLocationUrl, backendDataBody) {
    return axios.post(ORDER_REST_API_URL + backendLocationUrl, backendDataBody);
  }

  get(backendLocationUrl) {
    return axios.get(ORDER_REST_API_URL + backendLocationUrl);
  }

  patch(backendLocationUrl, backendDataBody) {
    return axios.patch(ORDER_REST_API_URL + backendLocationUrl, backendDataBody);
  }

  delete(backendLocationUrl, backendDataBody) {
    return axios.delete(ORDER_REST_API_URL + backendLocationUrl, backendDataBody);
  }

}

export default new OrderService();
