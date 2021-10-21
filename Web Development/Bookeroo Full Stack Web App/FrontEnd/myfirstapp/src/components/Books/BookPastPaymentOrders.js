import React, { Component } from "react";
import OrderService from "./../Services/OrderService";
import PaymentService from "./../Services/PaymentService";
import Header from "./../Layout/Header";
import { Link } from "react-router-dom";

class AdminAddUsers extends Component {
  constructor(props) {
    super(props);

    this.state = {
      username: "",
      token: "",
      ordergroup: "",
      payment: [],
      orders: [],
    };

    if (this.props.location.state !== undefined) {
      this.state.username = this.props.location.state.username;
      this.state.token = this.props.location.state.token;
      this.state.ordergroup = this.props.location.state.ordergroup;
    }
  }

  componentDidMount() {
    const { id } = this.props.match.params;
    if (this.state.username !== id) {
      this.props.history.push({
        pathname: "/login",
      });
    } else {
      PaymentService.post(
        "/findTranactionsByOrderGroup/" + this.state.ordergroup
      ).then((response) => {
        this.setState({ payment: response.data });
      });

      OrderService.post("/findOrdersByGroup/" + this.state.ordergroup).then(
        (response) => {
          this.setState({ orders: response.data });
        }
      );
    }
  }

  render() {
    return (
      <div className="register">
        <Header headerUsername={this.state.username} />
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <h1 className="display-4 text-center">View Payment's Orders</h1>
              <p className="text-center">Welcome: {this.state.username}</p>
              <div className="">
                <p className="">
                  Payment Order Group: {this.state.payment.ordergroup}
                </p>
                <p className="">
                  Payment Buyer Username: {this.state.payment.buyerusername}
                </p>
                <p className="">
                  Payment Shipping Address: {this.state.payment.shippingaddress}
                </p>
                <p className="">
                  Payment Date Time: {this.state.payment.datetime}
                </p>
              </div>
              <br></br>
              <p id="error" className="text-danger"></p>
            </div>
          </div>
        </div>
        <div className="row">
          <div className="col-md-8 m-auto">
            <table className="table table-striped">
              <thead>
                <tr>
                  <td> ISBN</td>
                  <td>Seller</td>
                  <td> Quantity</td>
                  <td> Item Price</td>
                  <td> New Book</td>
                  <td> Loaned Book</td>
                </tr>
              </thead>
              <tbody>
                {this.state.orders.map((item) => (
                  <tr key={item.orderid}>
                    <td> {item.isbn}</td>
                    <td> {item.sellerusername}</td>
                    <td> {item.quantity}</td>
                    <td> {item.itemprice}</td>
                    <td> {String(item.newbook)}</td>
                    <td> {String(item.loanedbook)}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    );
  }
}

export default AdminAddUsers;
