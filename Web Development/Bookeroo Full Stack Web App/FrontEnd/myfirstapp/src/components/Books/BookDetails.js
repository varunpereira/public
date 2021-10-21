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
      book: [],
      reviews: [],
      quantity: "1",
    };
    if (this.props.location.state !== undefined) {
      this.state.username = this.props.location.state.username;
      this.state.token = this.props.location.state.token;
      this.state.bookIsbn = this.props.location.state.bookIsbn;
    }
    //allows you to access state variables in other functions
    this.addItemToCart = this.addItemToCart.bind(this);
    this.changeQuantity = this.changeQuantityHandler.bind(this);
  }

  componentDidMount() {
    if (this.state.bookIsbn != undefined) {
      BookService.get("/search/isbn/" + this.state.bookIsbn).then((resp) => {
        this.setState({ book: resp.data });
        ReviewService.getReviews("/findReviews/" + this.state.book.name).then(
          (resp) => {
            this.setState({ reviews: resp.data });
          }
        );
      });
    }
  }

  changeQuantityHandler = (event) => {
    this.setState({ quantity: event.target.value });
  };

  async addItemToCart(event) {
    event.preventDefault();
    try {
      let checkStockResponse = await BookService.post(
        "/checkStock/" + this.state.book.isbn + "/" + this.state.quantity
      );
      console.log(checkStockResponse.data);

      if (checkStockResponse.data == true) {
        console.log(this.state.username);
        let currentOrderStatusResponse = await OrderService.post(
          "/isOrderCurrent/" + this.state.username
        );
        console.log(currentOrderStatusResponse.data);
        let orderRequest = {
          isbn: this.state.book.isbn,
          quantity: this.state.quantity,
          itemprice: this.state.book.price,
          buyerusername: this.state.username,
          ordergroup: 0,
          sellerusername: this.state.book.seller,
          currentorder: true,
          orderprice: 0,
          newbook: Boolean(this.state.book.newbook),
          loanedbook: Boolean(this.state.book.newbook),
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
        document.getElementById("error").innerHTML =
          "Order Item successfully added to Cart.";

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
              <p className="text-center">Hi "{this.state.username}"</p>
              <h1 className="display-4 text-center">{this.state.book.name}</h1>
            </div>
          </div>
          <div className="row">
            <div className="col-md-8 m-auto">
              <iframe
                src={this.state.book.coverurl}
                width="100%"
                height="1000px"
              ></iframe>
              <a href={this.state.book.contenturl} target="_blank">
                view table of contents
              </a>
            </div>
          </div>

          <div className="row">
            <div className="col-md-8 m-auto">
              <div className="form-group">
                <label htmlFor="sel1">Quantity:</label>
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
                <a className="text-center" onClick={this.addItemToCart}>
                  add to cart
                </a>
                // </Link>
              )}
              <p id="error" className="text-danger"></p>
            </div>{" "}
          </div>

          <div className="row">
            <div className="col-md-8 m-auto">
              <h2 className="display-5 text-center">Details</h2>
              <p className="display-5 text-center">
                Name: {this.state.book.name}
              </p>
              <p className="display-5 text-center">
                Author: {this.state.book.author}
              </p>
              <p className="display-5 text-center">
                Category: {this.state.book.category}
              </p>
              <p className="display-5 text-center">
                Price: {this.state.book.price}
              </p>
              <p className="display-5 text-center">
                ISBN: {this.state.book.isbn}
              </p>
              <p className="display-5 text-center">
                Seller: {this.state.book.seller}
              </p>
              <p className="display-5 text-center">
                New Book: {String(this.state.book.newbook)}
              </p>
              {/* <p className="display-5 text-center">
                Loaned Book: {String(this.state.book.loanedbook)}
              </p> */}
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
                      bookName: this.state.book.name,
                      bookIsbn: this.state.book.isbn,
                      bookAuthor: this.state.book.author,
                      bookCategory: this.state.book.category,
                      bookPrice: this.state.book.price,
                      bookSeller: this.state.book.seller,
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
