import React, { Component } from "react";
import UserService from "./../Services/UserService";
import Header from "./../Layout/Header";

class Register extends Component {
  constructor(props) {
    super(props);

    this.state = {
      username: "",
      fullname: "",
      password: "",
      confirmpassword: "",
      accounttype: "Customer",
      address: "",
      phonenumber: "",
      abn: "",
    };

    this.changeUsernameHandler = this.changeUsernameHandler.bind(this);
    this.changeFullNameHandler = this.changeFullNameHandler.bind(this);
    this.changePasswordHandler = this.changePasswordHandler.bind(this);
    this.changeConfirmPasswordHandler =
      this.changeConfirmPasswordHandler.bind(this);
    this.changeAccountType = this.changeAccountTypeHandler.bind(this);
    this.changeAddress = this.changeAddressHandler.bind(this);
    this.changePhoneNumber = this.changePhoneNumberHandler.bind(this);
    this.changeAbn = this.changeAbnHandler.bind(this);
    this.createUser = this.createUser.bind(this);
  }

  async createUser(event) {
    event.preventDefault();
    let registeredUser;
    if (this.state.accounttype == "Customer") {
      registeredUser = {
        username: this.state.username,
        fullname: this.state.fullname,
        password: this.state.password,
        confirmpassword: this.state.confirmpassword,
        accounttype: this.state.accounttype,
        address: this.state.address,
        phonenumber: this.state.phonenumber,
        registerstatus: "registered",
      };
    } else {
      registeredUser = {
        username: this.state.username,
        fullname: this.state.fullname,
        password: this.state.password,
        confirmpassword: this.state.confirmpassword,
        accounttype: this.state.accounttype,
        address: this.state.address,
        phonenumber: this.state.phonenumber,
        registerstatus: "registered",
        abn: this.state.abn,
      };
    }

    try {
      let urlChange = "/register";
      await UserService.postUser(registeredUser, urlChange);
    } catch (error) {
      // console.log(error.response.data);
      document.getElementById("error").innerHTML =
        error.response.data.username +
        "<br/>" +
        error.response.data.password +
        "<br/>" +
        error.response.data.address +
        "<br/>" +
        error.response.data.phonenumber +
        "<br/>" +
        error.response.data.fullname +
        "<br/>" +
        error.response.data.abn;
    }

    let user = {
      username: this.state.username,
      password: this.state.password,
    };
    try {
      let urlChange = "/login";
      let resp = await UserService.postUser(user, urlChange);
      if (resp.data.success === true) {
        let userReceived = await UserService.patchUser(
          "/updateToken/" + resp.data.token + "/" + this.state.username
        );
        let pageChange = "/dashboard";
        if (userReceived.data.accounttype === "Admin") {
          pageChange = "/admin/home/" + this.state.username;
        } else if (userReceived.data.accounttype === "Customer") {
          pageChange = "/customer/home/" + this.state.username;
        } else if (userReceived.data.accounttype === "Publisher") {
          pageChange = "/publisher/home/" + this.state.username;
        } else if (userReceived.data.accounttype === "Shope Owner") {
          pageChange = "/shop-owner/home/" + this.state.username;
        }
        this.props.history.push({
          pathname: pageChange,
          state: { token: resp.data.token, username: this.state.username },
        });
      }
    } catch (error) {
      // document.getElementById("error").innerHTML = "Login Failed";
    }
  }

  changeUsernameHandler = (event) => {
    this.setState({ username: event.target.value });
  };

  changeFullNameHandler = (event) => {
    this.setState({ fullname: event.target.value });
  };

  changePasswordHandler = (event) => {
    this.setState({ password: event.target.value });
  };

  changeConfirmPasswordHandler = (event) => {
    this.setState({ confirmpassword: event.target.value });
  };

  changeAccountTypeHandler = (event) => {
    this.setState({ accounttype: event.target.value });
  };

  changeAddressHandler = (event) => {
    this.setState({ address: event.target.value });
  };

  changePhoneNumberHandler = (event) => {
    this.setState({ phonenumber: event.target.value });
  };

  changeAbnHandler = (event) => {
    this.setState({ abn: event.target.value });
  };

  render() {
    return (
      <div className="register">
        <Header />
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <h1 className="display-4 text-center">Sign Up</h1>
              <p className="lead text-center">Create your Account</p>
              <form>
                <div className="form-group">
                  <input
                    type="text"
                    className="form-control form-control-lg"
                    placeholder="Username"
                    name="username"
                    required
                    value={this.state.username}
                    onChange={this.changeUsernameHandler}
                  />
                </div>
                <div className="form-group">
                  <input
                    type="text"
                    className="form-control form-control-lg"
                    placeholder="Full Name"
                    name="fullName"
                    value={this.state.fullname}
                    onChange={this.changeFullNameHandler}
                  />
                </div>
                <div className="form-group">
                  <input
                    type="text"
                    className="form-control form-control-lg"
                    placeholder="Password"
                    name="password"
                    value={this.state.password}
                    onChange={this.changePasswordHandler}
                  />
                </div>
                <div className="form-group">
                  <input
                    type="text"
                    className="form-control form-control-lg"
                    placeholder="Confirm Password"
                    name="confirmPassword"
                    value={this.state.confirmpassword}
                    onChange={this.changeConfirmPasswordHandler}
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="sel1">Account Type:</label>
                  <select
                    name="accountType"
                    value={this.state.accounttype}
                    onChange={this.changeAccountTypeHandler}
                    className="form-control"
                    id="sel1"
                  >
                    <option>Customer</option>
                    <option>Publisher</option>
                    <option>Shop Owner</option>
                  </select>
                </div>
                <div className="form-group">
                  <input
                    type="text"
                    className="form-control form-control-lg"
                    placeholder="Address"
                    name="address"
                    value={this.state.address}
                    onChange={this.changeAddressHandler}
                  />
                </div>
                <div className="form-group">
                  <input
                    type="text"
                    className="form-control form-control-lg"
                    placeholder="Phone Number"
                    name="phoneNumber"
                    value={this.state.phonenumber}
                    onChange={this.changePhoneNumberHandler}
                  />
                </div>
                <div className="form-group">
                  <input
                    type="text"
                    className="form-control form-control-lg"
                    placeholder="ABN"
                    name="abn"
                    value={this.state.abn}
                    onChange={this.changeAbnHandler}
                  />
                </div>
                <input
                  type="submit"
                  className="btn btn-primary btn-block mt-4"
                  onClick={this.createUser}
                />
                <br></br>
                <p id="error" className="text-danger"></p>
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default Register;
