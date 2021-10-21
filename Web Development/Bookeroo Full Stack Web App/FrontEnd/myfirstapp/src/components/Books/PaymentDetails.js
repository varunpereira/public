import React, { Component } from "react";
import PaymentService from "../Services/PaymentService";
import Header from "../Layout/Header";

class PaymentDetails extends Component {
  constructor(props) {
    super(props);
    this.state = {
      token: "",
      username: "",
      group: "",
      price: "",
      shippingAddress: "",
    };
    if (this.props.location.state !== undefined) {
      this.state.token = this.props.location.state.token;
      this.state.username = this.props.location.state.username;
      this.state.group = this.props.location.state.group;
      this.state.price = this.props.location.state.price;
    }
    this.changeShippingAddressHandler =
      this.changeShippingAddressHandler.bind(this);
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

  changeShippingAddressHandler = (event) => {
    this.setState({ shippingAddress: event.target.value });
  };

  async pay(event) {
    event.preventDefault();
    if (this.state.shippingAddress == "") {
      document.getElementById("error").innerHTML = "Shipping Address Required";
    } else {
      this.props.history.push({
        pathname: "/paymentconfirm/" + this.state.username,
        state: {
          token: this.state.token,
          username: this.state.username,
          group: this.state.group,
          price: this.state.price,
          shippingAddress: this.state.shippingAddress,
        },
      });
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
              <form>
                <div className="form-group">
                  <input
                    type="text"
                    className="form-control form-control-lg"
                    placeholder="Shipping Address"
                    name="username"
                    value={this.state.shippingAddress}
                    onChange={this.changeShippingAddressHandler}
                  />
                </div>
                <button
                  type="submit"
                  className="btn btn-primary btn-block mb-5 mt-5"
                  onClick={this.pay}
                >
                  Procceed to Confirm Payment
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

export default PaymentDetails;
