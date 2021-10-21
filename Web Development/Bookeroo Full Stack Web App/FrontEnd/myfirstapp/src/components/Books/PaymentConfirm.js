import React, { Component } from "react";
import PaymentService from "../Services/PaymentService";
import Header from "../Layout/Header";

class PaymentConfirm extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      token: "",
      group: "",
      price: "",
      shippingAddress: "",
    };
    if (this.props.location.state !== undefined) {
      this.state.username = this.props.location.state.username;
      this.state.token = this.props.location.state.token;
      this.state.group = this.props.location.state.group;
      this.state.price = this.props.location.state.price;
      this.state.shippingAddress = this.props.location.state.shippingAddress;
    }
    this.pay = this.pay.bind(this);
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

  async pay(event) {
    event.preventDefault();
    let paymentModel = {
      buyerusername: this.state.username,
      ordergroup: this.state.group,
      price: this.state.price,
      shippingaddress: this.state.shippingAddress,
    };
    let addPaymentResponse = PaymentService.post("/addPayment", paymentModel);
    let paymentid = (await addPaymentResponse).data.paymentid;
    let paymentUsername = (await addPaymentResponse).data.buyerusername;
    console.log(paymentid);

    let paypalResponse = PaymentService.post("/pay/" + paymentid + "/" + paymentUsername);
    let paypalLink = (await paypalResponse).data;
    window.location.replace(paypalLink);
    //window.open(paypalLink, '_blank').focus();
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
              <p>Order Group: {this.state.group} </p>
              <p>Order Price: {this.state.price}</p>
              <p>Order Shipping Address: {this.state.shippingAddress}</p>
              <button
                type="submit"
                className="btn btn-primary btn-block mb-5 mt-5"
                onClick={this.pay}
              >
                Procceed to PayPal
              </button>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default PaymentConfirm;
