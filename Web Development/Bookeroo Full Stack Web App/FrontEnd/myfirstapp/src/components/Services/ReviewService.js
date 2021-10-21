import axios from 'axios';

const USERS_REST_API_URL = 'http://localhost:1003/api/review';

class SearchResultsService {

    postReview(review,page) {
        return axios.post(USERS_REST_API_URL + page, review);
    }

    getReviews(page){
        return axios.get(USERS_REST_API_URL + page);
    }

    
}

export default new SearchResultsService();