import React, { Component } from "react";
import Header from "../Layout/Header";
import OrderService from "../Services/OrderService";
import PaymentService from "../Services/PaymentService";
import BookService from "../Services/SearchResultsService";
import UserService from "../Services/UserService";
import ReviewService from "../Services/ReviewService";
import { Link } from "react-router-dom";

class AdminUsers extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      token: "",
      books: [],
    };
    if (this.props.location.state !== undefined) {
      this.state.username = this.props.location.state.username;
      this.state.token = this.props.location.state.token;
    }
    //bind so these functions access to state
    this.downloadBookSummary = this.downloadBookSummary.bind(this);
    this.downloadBookDetails = this.downloadBookDetails.bind(this);
    this.downloadPaymentSummary = this.downloadPaymentSummary.bind(this);
    this.downloadPaymentDetails = this.downloadPaymentDetails.bind(this);
  }

  componentDidMount() {
    const { id } = this.props.match.params;
    if (this.state.username !== id) {
      this.props.history.push({
        pathname: "/login",
      });
    }
  }

  async downloadBookSummary() {
    try {
      window
        .open("http://localhost:1002/api/book/bookListBasic", "_blank")
        .focus();
    } catch (e) {
      document.getElementById("error").innerHTML =
        "Error downloading Book List Summary";
    }
  }

  async downloadBookDetails() {
    try {
      window
        .open("http://localhost:1002/api/book/bookListDetailed", "_blank")
        .focus();
    } catch (e) {
      document.getElementById("error").innerHTML =
        "Error downloading Book List Details";
    }
  }

  async downloadPaymentSummary() {
    try {
      window
        .open(
          "http://localhost:1008/api/payment/transactionListBasic",
          "_blank"
        )
        .focus();
    } catch (e) {
      document.getElementById("error").innerHTML =
        "Error downloading Payment List Summary";
    }
  }

  async downloadPaymentDetails() {
    try {
      window
        .open(
          "http://localhost:1008/api/payment/transactionListDetailed",
          "_blank"
        )
        .focus();
    } catch (e) {
      document.getElementById("error").innerHTML =
        "Error downloading Payment List Details";
    }
  }

  render() {
    return (
      <div>
        <Header headerUsername={this.state.username} />
        <div className="container landing">
          <div className="row">
            <div className="col-md-12 text-center">
              <h1 className="display-3 mb-4">Manage Reports</h1>
              <p className="lead">Welcome {this.state.username}</p>
              <hr />
              <button
                type="submit"
                className="btn btn-primary btn-block mt-4"
                onClick={async () => this.downloadBookSummary()}
              >
                Download Book Summary
              </button>
              <button
                type="submit"
                className="btn btn-primary btn-block mt-4"
                onClick={async () => this.downloadBookDetails()}
              >
                Download Book Details
              </button>
              <button
                type="submit"
                className="btn btn-primary btn-block mt-4"
                onClick={async () => this.downloadPaymentSummary()}
              >
                Download Payment Summary
              </button>

              <button
                type="submit"
                className="btn btn-primary btn-block mt-4"
                onClick={async () => this.downloadPaymentDetails()}
              >
                Download Payment Details
              </button>

              <p id="error" className="text-danger"></p>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default AdminUsers;
