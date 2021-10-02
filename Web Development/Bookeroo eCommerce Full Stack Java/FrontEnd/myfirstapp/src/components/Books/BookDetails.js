import React, { Component } from "react";
import ReviewService from "../Services/ReviewService";
import OrderService from "../Services/OrderService";
import BookService from "../Services/SearchResultsService";
import Header from "../Layout/Header";
import { Link } from "react-router-dom";

class BookDetails extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      token: "",
      bookIsbn: "",
      bookName: "",
      bookAuthor: "",
      bookCategory: "",
      bookPrice: "",
      bookSeller: "",
      reviews: [],
      quantity: "1",
    };
    if (this.props.location.state !== undefined) {
      this.state.username = this.props.location.state.username;
      this.state.token = this.props.location.state.token;
      this.state.bookIsbn = this.props.location.state.bookIsbn;
      this.state.bookName = this.props.location.state.bookName;
      this.state.bookAuthor = this.props.location.state.bookAuthor;
      this.state.bookCategory = this.props.location.state.bookCategory;
      this.state.bookPrice = this.props.location.state.bookPrice;
      this.state.bookSeller = this.props.location.state.bookSeller;
    }
    //allows you to access state variables in other functions
    this.addItemToCart = this.addItemToCart.bind(this);
    // this.changeQuantity = this.changeQuantityHandler.bind(this);
  }

  componentDidMount() {
    ReviewService.getReviews("/findReviews/" + this.state.bookName).then(
      (resp) => {
        this.setState({ reviews: resp.data });
      }
    );
  }

  changeQuantityHandler = (event) => {
    this.setState({ quantity: event.target.value });
  };

  async addItemToCart(event) {
    event.preventDefault();
    try {
      let checkStockResponse = await BookService.post(
        "/checkStock/" + this.state.bookIsbn + "/" + this.state.quantity
      );
      console.log(checkStockResponse.data);

      if (checkStockResponse.data == true) {
        console.log(this.state.username);
        let currentOrderStatusResponse = await OrderService.post(
          "/isOrderCurrent/" + this.state.username
        );
        console.log(currentOrderStatusResponse.data);

        let orderRequest = {
          isbn: this.state.bookIsbn,
          quantity: this.state.quantity,
          itemprice: this.state.bookPrice,
          buyerusername: this.state.username,
          ordergroup: 0,
          sellerusername: this.state.bookSeller,
          currentorder: true,
          orderprice: 0,
        };
        //need new group value
        if (currentOrderStatusResponse.data == false) {
          //random number between 1 and 1000 for new order identification
          orderRequest.ordergroup = Math.floor(Math.random() * 1000000);
        }
        //get old group value from db
        else {
          orderRequest.ordergroup = currentOrderStatusResponse.data;
        }
        console.log(orderRequest.ordergroup);

        console.log(orderRequest);
        let addItemOrderResponse = await OrderService.post(
          "/addOrder",
          orderRequest
        );
        console.log(addItemOrderResponse.data);
        document.getElementById("error").innerHTML = "Order Item successfully added to Cart.";

        let decreaseStockResponse = await BookService.patch(
          "/decreaseStock/" + this.state.bookIsbn + "/" + this.state.quantity
        );
        if (decreaseStockResponse.data == true) {
          console.log("SUCCESS");
        } else {
          console.log("FAILURE");
        }
      } else {
        document.getElementById("error").innerHTML = "Not enough stock.";
      }
    } catch (error) {
      // console.log(error.response.data);
    }
  }

  render() {
    return (
      <div>
        <Header headerUsername={this.state.username} />
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <h1 className="display-6 text-center">
                Hi "{this.state.username}"
              </h1>
              <h1 className="display-4 text-center">
                "{this.state.bookName}" Info
              </h1>

              <div className="form-group">
                <label htmlFor="sel1">Quantity: {this.state.quantity}</label>
                <select
                  name="accountType"
                  value={this.state.quantity}
                  onChange={this.changeQuantityHandler}
                  className="form-control"
                  id="sel1"
                >
                  <option>1</option>
                  <option>2</option>
                  <option>3</option>
                  <option>4</option>
                  <option>5</option>
                </select>
              </div>

              {this.state.username == null || this.state.username == "" ? (
                <Link
                  to={{
                    pathname: "/login",
                    state: {
                      username: null,
                    },
                  }}
                >
                  <p className="text-center">add to cart</p>
                </Link>
              ) : (
                // <Link
                //   to={{
                //     pathname: "/book-currentorder/" + this.state.username,
                //     state: {
                //       username: this.state.username,
                //       bookName: this.state.bookName,
                //       quantity: this.state.quantity,
                //     },
                //   }}
                // >

                <a className="text-center" onClick={this.addItemToCart}>
                  add to cart
                </a>
                // </Link>
              )}
              <p id="error" className="text-danger"></p>
            </div>
          </div>
          <div className="row">
            <div className="col-md-8 m-auto">
              <h2 className="display-5 text-center">Details</h2>
              <p className="display-5 text-center">
                Name: {this.state.bookName}
              </p>
              <p className="display-5 text-center">
                Author: {this.state.bookAuthor}
              </p>
              <p className="display-5 text-center">
                Category: {this.state.bookCategory}
              </p>
              <p className="display-5 text-center">
                Price: {this.state.bookPrice}
              </p>
              <p className="display-5 text-center">
                ISBN: {this.state.bookIsbn}
              </p>
              <p className="display-5 text-center">
                Seller: {this.state.bookSeller}
              </p>
            </div>
          </div>
          <div className="row">
            <div className="col-md-8 m-auto">
              <h2 className="display-5 text-center">Reviews</h2>
              {this.state.username == null || this.state.username == "" ? (
                <Link
                  to={{
                    pathname: "/login",
                    state: {
                      username: "",
                    },
                  }}
                >
                  <p className="text-center">new review</p>
                </Link>
              ) : (
                <Link
                  to={{
                    pathname: "/book-reviews/" + this.state.username,
                    state: {
                      username: this.state.username,
                      bookName: this.state.bookName,
                      bookIsbn: this.state.bookIsbn,
                      bookAuthor: this.state.bookAuthor,
                      bookCategory: this.state.bookCategory,
                      bookPrice: this.state.bookPrice,
                      bookSeller: this.state.bookSeller,
                    },
                  }}
                >
                  <p className="text-center">new review</p>
                </Link>
              )}
            </div>
          </div>
          <div className="row">
            <div className="col-md-8 m-auto">
              <table className="table table-striped">
                <thead>
                  <tr>
                    <td> Rating</td>
                    <td> Description</td>
                    <td> By</td>
                  </tr>
                </thead>
                <tbody>
                  {this.state.reviews.map((review) => (
                    <tr key={review.reviewid}>
                      <td> {review.rating}</td>
                      <td> {review.description}</td>
                      <td> {review.user}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default BookDetails;
