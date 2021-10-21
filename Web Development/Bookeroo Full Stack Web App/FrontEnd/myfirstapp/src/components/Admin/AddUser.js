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
      username: "",
      fullname: "",
      password: "",
      confirmpassword: "",
      accounttype: "Customer",
      address: "",
      phonenumber: "",
      abn: "",
    };

    if (this.props.location.state !== undefined) {
      this.state.adminUsername = this.props.location.state.adminUsername;
      this.state.adminToken = this.props.location.state.adminToken;
    }

    this.changeUsernameHandler = this.changeUsernameHandler.bind(this);
    this.changeFullNameHandler = this.changeFullNameHandler.bind(this);
    this.changePasswordHandler = this.changePasswordHandler.bind(this);
    this.changeConfirmPasswordHandler =
      this.changeConfirmPasswordHandler.bind(this);
    this.changeAccountTypeHandler = this.changeAccountTypeHandler.bind(this);
    this.changeAddressHandler = this.changeAddressHandler.bind(this);
    this.changePhoneNumberHandler = this.changePhoneNumberHandler.bind(this);
    this.changeAbnHandler = this.changeAbnHandler.bind(this);
    this.createUser = this.createUser.bind(this);
  }

  componentDidMount() {
    const { id } = this.props.match.params;
    if (this.state.adminUsername !== id) {
      this.props.history.push({
        pathname: "/login",
      });
    }
  }

  async createUser(event) {
    event.preventDefault();
    let registeredUser;
    if (
      this.state.accounttype == "Customer" ||
      this.state.accounttype == "Admin"
    ) {
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
      await UserService.post("/register", registeredUser);
      document.getElementById("error").innerHTML = "User Added successfuly.";
      this.state.username = "";
      this.state.fullname = "";
      this.state.password = "";
      this.state.confirmpassword = "";
      this.state.accounttype = "Customer";
      this.state.address = "";
      this.state.phonenumber = "";
      this.state.abn = "";
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
        <Header headerUsername={this.state.adminUsername} />
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <h1 className="display-4 text-center">Add User</h1>
              <h1 className="display-4 text-center">
                admin:{this.state.adminUsername}
              </h1>
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
                    <option>Admin</option>
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

export default AdminAddUsers;
