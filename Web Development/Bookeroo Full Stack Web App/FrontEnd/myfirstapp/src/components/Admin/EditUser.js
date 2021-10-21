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
      username: "",
      fullname: "",
      accounttype: "Customer",
      registerstatus: "registered",
      address: "",
      phonenumber: "",
      abn: "",
    };

    if (this.props.location.state !== undefined) {
      this.state.adminUsername = this.props.location.state.adminUsername;
      this.state.adminToken = this.props.location.state.adminToken;
      this.state.user = this.props.location.state.user;
      this.state.username = this.props.location.state.user.username;
      this.state.fullname = this.props.location.state.user.fullname;
      this.state.accounttype = this.props.location.state.user.accounttype;
      this.state.registerstatus = this.props.location.state.user.registerstatus;
      this.state.address = this.props.location.state.user.address;
      this.state.phonenumber = this.props.location.state.user.phonenumber;
      this.state.abn = this.props.location.state.user.abn;
    }

    this.changeUsernameHandler = this.changeUsernameHandler.bind(this);
    this.changeFullNameHandler = this.changeFullNameHandler.bind(this);
    this.changeAccountTypeHandler = this.changeAccountTypeHandler.bind(this);
    this.changeRegisterStatusHandler =
      this.changeRegisterStatusHandler.bind(this);
    this.changeAddressHandler = this.changeAddressHandler.bind(this);
    this.changePhoneNumberHandler = this.changePhoneNumberHandler.bind(this);
    this.changeAbnHandler = this.changeAbnHandler.bind(this);
    this.updateUser = this.updateUser.bind(this);
  }

  componentDidMount() {
    const { id } = this.props.match.params;
    if (this.state.adminUsername !== id) {
      this.props.history.push({
        pathname: "/login",
      });
    }
  }

  async updateUser(event) {
    event.preventDefault();
    let updatedUser = {
      username: this.state.username,
      fullname: this.state.fullname,
      password: this.state.user.password,
      confirmpassword: this.state.user.confirmpassword,
      accounttype: this.state.accounttype,
      address: this.state.address,
      phonenumber: this.state.phonenumber,
      registerstatus: this.state.registerstatus,
      abn: this.state.abn,
    };

    try {
      await UserService.patch("/updateUser/" + this.state.username, updatedUser);
      document.getElementById("error").innerHTML = "User Updated successfuly.";
    } catch (error) {
      console.log(error.response.data);
      document.getElementById("error").innerHTML =
        error.response.data.username +
        "<br/>" +
        error.response.data.fullname +
        "<br/>" +
        error.response.data.address +
        "<br/>" +
        error.response.data.phonenumber +
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

  changeRegisterStatusHandler = (event) => {
    this.setState({ registerstatus: event.target.value });
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
              <h1 className="display-4 text-center">Edit User</h1>
              <h1 className="display-4 text-center">
                admin:{this.state.adminUsername}
              </h1>
              <form>
                <div className="form-group">
                  Username:
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
                  Full Name:
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
                  <label htmlFor="sel1">Register Status:</label>
                  <select
                    name="accountType"
                    value={this.state.registerstatus}
                    onChange={this.changeRegisterStatusHandler}
                    className="form-control"
                    id="sel1"
                  >
                    <option>registered</option>
                    <option>pending</option>
                  </select>
                </div>
                <div className="form-group">
                  Address:
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
                  Phone Number:
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
                  ABN:
                  <input
                    type="text"
                    className="form-control form-control-lg"
                    placeholder="ABN"
                    name="abn"
                    value={this.state.abn}
                    onChange={this.changeAbnHandler}
                  />
                </div>
                <button
                  type="submit"
                  className="btn btn-primary btn-block mt-4"
                  onClick={this.updateUser}
                >
                  Update
                </button>
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
