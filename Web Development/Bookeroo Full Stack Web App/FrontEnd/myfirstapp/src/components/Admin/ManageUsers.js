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
      users: [],
    };
    if (this.props.location.state !== undefined) {
      this.state.username = this.props.location.state.username;
      this.state.token = this.props.location.state.token;
    }
    //bind so these functions access to state
    this.delete = this.delete.bind(this);
  }

  componentDidMount() {
    const { id } = this.props.match.params;
    if (this.state.username !== id) {
      this.props.history.push({
        pathname: "/login",
      });
    } else {
      UserService.post("/allUsers").then((response) => {
        this.setState({ users: response.data });
      });
    }
  }

  async delete(selectedUsername) {
    UserService.delete("/deleteUser/" + selectedUsername).then((response1) => {
      document.getElementById("error").innerHTML = "User Deleted successfuly.";
      UserService.post("/allUsers").then((response2) => {
        this.setState({ users: response2.data });
      });
    });
  }

  render() {
    return (
      <div>
        <Header headerUsername={this.state.username} />
        <div className="container landing">
          <div className="row">
            <div className="col-md-12 text-center">
              <h1 className="display-3 mb-4">Manage Users</h1>
              <p className="lead">Welcome {this.state.username}</p>
              <hr />
              <Link
                to={{
                  pathname: "/admin/adduser/" + this.state.username,
                  state: {
                    adminToken: this.state.token,
                    adminUsername: this.state.username,
                  },
                }}
                style={{ fontSize: "14px" }}
                className="text-right"
              >
                add
              </Link>

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
                      <td></td>
                      <td></td>
                      <td>Username</td>
                      <td>Full Name</td>
                      <td>Type</td>
                      <td>Register Status</td>
                    </tr>
                  </thead>
                  <tbody>
                    {this.state.users.map((user) => (
                      <tr key={user.id}>
                        <td>
                          <Link
                            to={{
                              pathname:
                                "/admin/viewuser/" + this.state.username,
                              state: {
                                adminToken: this.state.token,
                                adminUsername: this.state.username,
                                user: user,
                              },
                            }}
                            style={{ fontSize: "11px" }}
                            className="text-right"
                          >
                            view
                          </Link>
                        </td>
                        <td>
                          <Link
                            to={{
                              pathname:
                                "/admin/edituser/" + this.state.username,
                              state: {
                                adminToken: this.state.token,
                                adminUsername: this.state.username,
                                user: user,
                              },
                            }}
                            style={{ fontSize: "11px" }}
                            className="text-right"
                          >
                            edit
                          </Link>
                        </td>
                        <td>
                          <Link
                            onClick={async () => this.delete(user.username)}
                            style={{ fontSize: "11px" }}
                          >
                            delete
                          </Link>
                        </td>
                        <td> {user.username}</td>
                        <td>{user.fullname}</td>
                        <td> {user.accounttype}</td>
                        <td> {user.registerstatus}</td>
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
