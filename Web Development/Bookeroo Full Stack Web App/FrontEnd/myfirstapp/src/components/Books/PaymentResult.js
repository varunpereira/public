import React, { Component } from "react";
import PaymentService from "../Services/PaymentService";
import OrderService from "../Services/OrderService";
import Header from "../Layout/Header";

class PaymentResult extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      token: "",
    };
  }

  componentDidMount() {
    //send a LoginRequest Object instead with username and token
    PaymentService.get("/success" + window.location.search).then((response) => {
      this.setState({ username: response.data });
      const { id } = this.props.match.params;
      if (this.state.username != id) {
        this.props.history.push({
          pathname: "/login",
        });
      } else {
        OrderService.patch("/updateConfirmStatus/" + this.state.username).then(
          (response2) => {
            if (response2.data != true) {
              this.props.history.push({
                pathname: "/login",
              });
            }
          }
        );
      }
    });
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
              <p className="display-6 text-center">Payment Successful.</p>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default PaymentResult;
