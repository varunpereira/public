import axios from "axios";

const USERS_REST_API_URL = "http://localhost:1001/api/users";

class UserService {
  //sends data from current page to java class to h2 db and receives data
  post(backendLocationUrl, backendDataBody) {
    return axios.post(USERS_REST_API_URL + backendLocationUrl, backendDataBody);
  }

  delete(backendLocationUrl, backendDataBody) {
    return axios.delete(
      USERS_REST_API_URL + backendLocationUrl,
      backendDataBody
    );
  }

  patch(backendLocationUrl, backendDataBody) {
    return axios.patch(
      USERS_REST_API_URL + backendLocationUrl,
      backendDataBody
    );
  }

  postUser(user, page) {
    return axios.post(USERS_REST_API_URL + page, user);
  }

  patchUser(page) {
    return axios.patch(USERS_REST_API_URL + page);
  }

  //gets user from data
  getUser(page) {
    return axios.get(USERS_REST_API_URL + page);
  }
}

export default new UserService();

// import axios from "axios";
// import {GET_ERRORS, SET_CURRENT_USER} from "./types";
// import setJWTToken from "../securityUtils/setJWTToken";
// import jwt_decode from "jwt-decode";

// export const createNewUser = (newUser) => async dispatch => {

//     try{

//         await axios.post("/api/users/register", newUser);
//         //history.push("/login");
//         dispatch({
//             type: GET_ERRORS,
//             payload: {}
//         });
//     }
//     catch (err){
//         dispatch ({
//             type: GET_ERRORS,
//             payload: err.response.data
//         });

//     }

// };

// export const login = LoginRequest => async dispatch => {
//     try {

//         //post => login request

//         //extract token from res.data

//         //set our token in the local storage

//         // set our token in header

//         //decode the token on React

//         // dispatch to our securityReducer

//     }
//     catch (err)
//     {

//     }

// }
