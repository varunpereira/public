import React, { Component } from "react";
import UserService from "./../Services/UserService";
import Header from "./../Layout/Header";
import { Link } from "react-router-dom";

class AdminAddUsers extends Component {
  constructor(props) {
    super(props);

    this.state = {
      adminUsername: "",
      adminToken: "",
      user: [],
    };

    if (this.props.location.state !== undefined) {
      this.state.adminUsername = this.props.location.state.adminUsername;
      this.state.adminToken = this.props.location.state.adminToken;
      this.state.user = this.props.location.state.user;
    }
  }

  componentDidMount() {
    const { id } = this.props.match.params;
    if (this.state.adminUsername !== id) {
      this.props.history.push({
        pathname: "/login",
      });
    }
  }

  render() {
    return (
      <div className="register">
        <Header headerUsername={this.state.adminUsername} />
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <h1 className="display-4 text-center">View User</h1>
              <h1 className="display-4 text-center">
                admin:{this.state.adminUsername}
              </h1>

              <div className="">
                <p className="">Username: {this.state.user.username}</p>
                <p className="">Full Name: {this.state.user.fullname}</p>
                <p className="">Account Type: {this.state.user.accounttype}</p>
                <p className="">
                  Register Status: {this.state.user.registerstatus}
                </p>
                <p className="">Address: {this.state.user.address}</p>
                <p className="">Phone Number: {this.state.user.phonenumber}</p>
                <p className="">ABN: {this.state.user.abn}</p>
                <p className="">Updated At: {this.state.user.updateat}</p>
                <p className="">Ceated At: {this.state.user.createat}</p>
              </div>
              <br></br>
              <p id="error" className="text-danger"></p>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default AdminAddUsers;
