import React, { Component } from "react";
import BookService from "../Services/SearchResultsService";
import PaymentService from "../Services/PaymentService";
import Header from "../Layout/Header";
import { Link } from "react-router-dom";

class BookPastOrders extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      token: "",
      payments: [],
    };
    if (this.props.location.state !== undefined) {
      this.state.username = this.props.location.state.username;
      this.state.token = this.props.location.state.token;
    }
    this.refund = this.refund.bind(this);
  }

  componentDidMount() {
    const { id } = this.props.match.params;
    if (this.state.username !== id) {
      this.props.history.push({
        pathname: "/login",
      });
    } else {
      PaymentService.post("/findTranactions/" + this.state.username).then(
        (response) => {
          this.setState({ payments: response.data });
          console.log(this.state.payments);
        }
      );
    }
  }

  async refund(ordergroup, saleid, price, paymentid) {
    PaymentService.post("/findTranactionsByOrderGroup/" + ordergroup).then(
      (response) => {
        if (response.data.refund == true) {
          document.getElementById("error").innerHTML =
            "Order Group " + ordergroup + " already refunded!";
        } else {
          PaymentService.post("/refundWithinTimeRange/" + paymentid).then(
            (response) => {
              if (response.data == true) {
                PaymentService.post("/refund/" + saleid + "/" + price).then(
                  (response) => {
                    if (response.data == true) {
                      PaymentService.patch("/updateRefund/" + ordergroup).then(
                        (response) => {
                          if (response.data == true) {
                            //too much work to decrease stock for each record in order table
                            //BookService.patch("/increaseStock/{isbn}/{amount}")
                            PaymentService.post(
                              "/findTranactions/" + this.state.username
                            ).then((response) => {
                              this.setState({ payments: response.data });
                              document.getElementById("error").innerHTML =
                                "Refund Success for order group " + ordergroup;
                            });
                          } else {
                            document.getElementById("error").innerHTML =
                              "Refund Failure.";
                          }
                        }
                      );
                    } else {
                      document.getElementById("error").innerHTML =
                        "Payment doesn't exist!";
                    }
                  }
                );
              } else {
                document.getElementById("error").innerHTML =
                  "Refund not within 2 hours time limit of purchase.";
              }
            }
          );
        }
      }
    );
  }

  render() {
    return (
      <div>
        <Header headerUsername={this.state.username} />
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <h1 className="display-6 text-center">Your Past Payments</h1>
              <p className="text-center">username: {this.state.username}</p>
            </div>
          </div>
          <div className="row">
            <div className="col-md-12 text-center" style={{ fontSize: "14px" }}>
              <p id="error" className="text-danger"></p>
              <div>
                <table className="table table-striped">
                  <thead>
                    <tr>
                      <td></td>
                      <td>Refund/Return Status</td>
                      <td>Order Group</td>
                      <td>Buyer Username</td>
                      <td>Date Time</td>
                    </tr>
                  </thead>
                  <tbody>
                    {this.state.payments.map((payment) => (
                      <tr key={payment.paymentid}>
                        <td>
                          <Link
                            to={{
                              pathname:
                                "/book-pastpaymentorders/" +
                                this.state.username,
                              state: {
                                token: this.state.token,
                                username: this.state.username,
                                ordergroup: payment.ordergroup,
                              },
                            }}
                            className="text-right"
                          >
                            view
                          </Link>
                        </td>

                        <td>
                          {payment.refund.toString() == "false" ? (
                            <Link
                              onClick={async () =>
                                this.refund(
                                  payment.ordergroup,
                                  payment.saleid,
                                  payment.price,
                                  payment.paymentid
                                )
                              }
                            >
                              refund/return
                            </Link>
                          ) : (
                            <p>already refunded</p>
                          )}
                        </td>

                        <td> {payment.ordergroup}</td>
                        <td>{payment.buyerusername}</td>
                        <td>{payment.datetime}</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default BookPastOrders;
