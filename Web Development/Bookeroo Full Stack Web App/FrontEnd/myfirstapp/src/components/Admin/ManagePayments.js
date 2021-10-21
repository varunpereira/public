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
      payments: [],
    };
    if (this.props.location.state !== undefined) {
      this.state.username = this.props.location.state.username;
      this.state.token = this.props.location.state.token;
    }
  }

  componentDidMount() {
    const { id } = this.props.match.params;
    if (this.state.username !== id) {
      this.props.history.push({
        pathname: "/login",
      });
    } else {
      PaymentService.post("/findAllTranactions").then((response) => {
        this.setState({ payments: response.data });
        console.log(this.state.payments);
      });
    }
  }

  render() {
    return (
      <div>
        <Header headerUsername={this.state.username} />
        <div className="container landing">
          <div className="row">
            <div className="col-md-12 text-center">
              <h1 className="display-3 mb-4">Manage Payments</h1>
              <p className="lead">Welcome {this.state.username}</p>
              <hr />
              <p id="error" className="text-danger"></p>
            </div>
          </div>
          <div className="row">
            <div className="col-md-12 text-center" style={{ fontSize: "14px" }}>
              <div>
                <table className="table table-striped">
                  <thead>
                    <tr>
                      <td></td>
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
                                "/admin/viewpaymentorders/" +
                                this.state.username,
                              state: {
                                adminToken: this.state.token,
                                adminUsername: this.state.username,
                                ordergroup: payment.ordergroup,
                              },
                            }}
                            style={{ fontSize: "11px" }}
                            className="text-right"
                          >
                            view
                          </Link>
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

export default AdminUsers;
