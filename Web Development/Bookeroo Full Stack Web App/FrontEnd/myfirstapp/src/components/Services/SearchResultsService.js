import axios from 'axios';

const BOOK_REST_API_URL = 'http://localhost:1002/api/book';

class SearchResultsService {

    getSearchResults(url) {
        return axios.get(BOOK_REST_API_URL + url);

    }

    postUser(book,page) {
        return axios.post(BOOK_REST_API_URL + page, book);
    }

    post(backendLocationUrl, backendDataBody){
        return axios.post(BOOK_REST_API_URL + backendLocationUrl, backendDataBody);
    }

    patch(backendLocationUrl, backendDataBody){
        return axios.patch(BOOK_REST_API_URL + backendLocationUrl, backendDataBody);
    }

    get(backendLocationUrl, backendDataBody){
        return axios.get(BOOK_REST_API_URL + backendLocationUrl, backendDataBody);
    }

    
}

export default new SearchResultsService();