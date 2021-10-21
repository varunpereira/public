import React, { Component } from "react";
import ReviewService from "../Services/ReviewService";
import Header from "../Layout/Header";
import { Link } from "react-router-dom";

class BookReviews extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      token: "",
      rating: "5",
      description: "",
      bookName: "",
      bookIsbn: "",
      bookAuthor: "",
      bookCategory: "",
      bookPrice: "",
      bookSeller: "",
    };
    if (this.props.location.state !== undefined) {
      this.state.username = this.props.location.state.username;
      this.state.token = this.props.location.state.token;
      this.state.bookName = this.props.location.state.bookName;
      this.state.bookIsbn = this.props.location.state.bookIsbn;
      this.state.bookAuthor = this.props.location.state.bookAuthor;
      this.state.bookCategory = this.props.location.state.bookCategory;
      this.state.bookPrice = this.props.location.state.bookPrice;
      this.state.bookSeller = this.props.location.state.bookSeller;
    }
    this.changeRating = this.changeRatingHandler.bind(this);
    this.changeDescription = this.changeDescriptionHandler.bind(this);
    this.createReview = this.createReview.bind(this);
  }

  componentDidMount() {
    // url trying to be accessed
    const { id } = this.props.match.params;
    // if logged in username does not equal url username then bad
    if (this.state.username !== id) {
      this.props.history.push({
        pathname: "/login",
      });
    }
  }

  changeRatingHandler = (event) => {
    this.setState({ rating: event.target.value });
  };

  changeDescriptionHandler = (event) => {
    this.setState({ description: event.target.value });
  };

  async createReview(event) {
    event.preventDefault();
    let review = {
      user: this.state.username,
      rating: this.state.rating,
      description: this.state.description,
      bookname: this.state.bookName,
    };
    try {
      let backendURL = "/addReview";
      await ReviewService.postReview(review, backendURL);
      let frontendURL = "/book-details";
      this.props.history.push({
        pathname: frontendURL,
        state: {
          username: this.state.username,
          bookName: this.state.bookName,
          bookIsbn: this.state.bookIsbn,
          bookAuthor:this.state.bookAuthor,
          bookCategory: this.state.bookCategory,
          bookPrice: this.state.bookPrice,
          bookSeller: this.state.bookSeller,
        },
      });
    } catch (error) {
      // document.getElementById("error").innerHTML =
      // error.response.data.username + "<br/>" + error.response.data.password;
      console.log(error.response.data);
    }
  }

  render() {
    return (
      <div>
        <Header headerUsername={this.state.username} />
        <div className="container landing">
          <div className="row">
            <div className="col-md-8 m-auto">
              <h1 className="display-4 text-center">
                New Review for "{this.state.bookName}" 
              </h1>
            </div>
          </div>
          <div className="row">
            <div className="col-md-8 m-auto">
              <form>
                <div className="form-group">
                  <label htmlFor="exampleFormControlSelect1">
                    Rating (out of 5)
                  </label>
                  <select
                    className="form-control"
                    id="exampleFormControlSelect1"
                    value={this.state.rating}
                    onChange={this.changeRatingHandler}
                  >
                    <option>1</option>
                    <option>2</option>
                    <option>3</option>
                    <option>4</option>
                    <option>5</option>
                  </select>
                </div>
                <div className="form-group">
                  <label htmlFor="exampleFormControlTextarea1">Response</label>
                  <textarea
                    className="form-control"
                    id="exampleFormControlTextarea1"
                    rows="3"
                    value={this.state.description}
                    onChange={this.changeDescriptionHandler}
                  ></textarea>
                </div>
                <button
                  type="button"
                  className="btn btn-primary"
                  onClick={this.createReview}
                >
                  Submit
                </button>
                <p id="error" className="text-danger"></p>
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default BookReviews;
