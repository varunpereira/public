import React, { Component } from "react";
import OrderService from "../Services/OrderService";
import PaymentService from "../Services/PaymentService";
import Header from "../Layout/Header";
import { Link } from "react-router-dom";

class BookCurrentOrder extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      token: "",
      currentOrder: [],
      orderPrice: "",
      currentOrderGroup: "",
    };
    if (this.props.location.state !== undefined) {
      this.state.username = this.props.location.state.username;
      this.state.token = this.props.location.state.token;
    }
    //allows you to access state variables in other functions
    this.pay = this.pay.bind(this);
    this.cancel = this.cancel.bind(this);
  }

  componentDidMount() {
    // url trying to be accessed
    const { id } = this.props.match.params;
    // if logged in username does not equal url username then bad
    if (this.state.username !== id) {
      this.props.history.push({
        pathname: "/login",
      });
    } else {
      OrderService.post("/findOrders/" + this.state.username).then(
        (response) => {
          this.setState({ currentOrder: response.data });
        }
      );

      OrderService.post("/isOrderCurrent/" + this.state.username).then(
        (response) => {
          this.setState({ currentOrderGroup: response.data });
          if (response.data == false) {
            this.setState({ orderPrice: 0 });
          } else {
            OrderService.post(
              "/getOrderPrice/" +
                this.state.username +
                "/" +
                this.state.currentOrderGroup
            ).then((response) => {
              this.setState({ orderPrice: response.data });
            });
          }
        }
      );
    }
  }

  async pay(event) {
    event.preventDefault();
    if (this.state.orderPrice > 0) {
      let updatedOrderPriceResponse = OrderService.patch(
        "/updateOrderPrice/" +
          this.state.currentOrderGroup +
          "/" +
          this.state.username +
          "/" +
          this.state.orderPrice
      );

      //if (updatedOrderPriceResponse.data == true) {}
      this.props.history.push({
        pathname: "/paymentdetails/" + this.state.username,
        state: {
          token: this.state.token,
          username: this.state.username,
          group: this.state.currentOrderGroup,
          price: this.state.orderPrice,
        },
      });
    } else {
      document.getElementById("error").innerHTML = "Cart Empty";
    }
  }

  async cancel(orderitemid) {
    document.getElementById("error").innerHTML = "Item Cancelled";
    OrderService.delete("/cancelOrderItem/" + orderitemid).then((response1) => {
      OrderService.post("/findOrders/" + this.state.username).then(
        (response2) => {
          this.setState({ currentOrder: response2.data });
        }
      );
    });
  }

  render() {
    return (
      <div>
        <Header headerUsername={this.state.username} />
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <h1 className="display-6 text-center">Your Current Order</h1>
              <p className="text-center">
                username: {this.state.username} <br></br> order group:
                {this.state.currentOrderGroup} <br></br> order price: $
                {this.state.orderPrice}
              </p>
            </div>
          </div>
          <div className="row">
            <div className="col-md-8 m-auto">
              <table className="table table-striped">
                <thead>
                  <tr>
                    <td> ISBN</td>
                    <td> Quantity</td>
                    <td> Item Price</td>
                    <td> New Book</td>
                    {/* <td> Loaned Book</td> */}
                    <td> </td>
                  </tr>
                </thead>
                <tbody>
                  {this.state.currentOrder.map((item) => (
                    <tr key={item.orderid}>
                      <td> {item.isbn}</td>
                      <td> {item.quantity}</td>
                      <td> {item.itemprice}</td>
                      <td> {String(item.newbook)}</td>
                      {/* <td> {String(item.loanedbook)}</td> */}
                      <td>
                        <Link onClick={async () => this.cancel(item.orderid)}>
                          cancel
                        </Link>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
          <div className="row">
            <div className="col-md-8 m-auto">
              <button
                type="submit"
                className="btn btn-primary btn-block mb-5 mt-5"
                onClick={this.pay}
              >
                Procceed to Payment
              </button>
              <p id="error" className="text-danger"></p>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default BookCurrentOrder;
