import axios from "axios";

const PAYMENT_REST_API_URL = "http://localhost:1008/api/payment";

class PaymentService {
  post(backendLocationUrl, backendDataBody) {
    return axios.post(PAYMENT_REST_API_URL + backendLocationUrl, backendDataBody);
  }

  get(backendLocationUrl) {
    return axios.get(PAYMENT_REST_API_URL + backendLocationUrl);
  }

  patch(backendLocationUrl, backendDataBody) {
    return axios.patch(PAYMENT_REST_API_URL + backendLocationUrl, backendDataBody);
  }
}

export default new PaymentService();
