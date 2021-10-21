import React, { Component } from "react";
import UserService from "../Services/UserService";
import Header from "../Layout/Header";

class Login extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      token: "",
      password: "",
      users: "",
      error: "",
    };
    this.changeUsernameHandler = this.changeUsernameHandler.bind(this);
    this.changePasswordHandler = this.changePasswordHandler.bind(this);
    this.loginUser = this.loginUser.bind(this);
  }

  async loginUser(event) {
    event.preventDefault();
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
        } else if (userReceived.data.accounttype === "Shop Owner") {
          pageChange = "/shop-owner/home/" + this.state.username;
        }

        this.props.history.push({
          pathname: pageChange,
          state: { token: resp.data.token, username: this.state.username },
        });
      }
    } catch (error) {
      document.getElementById("error").innerHTML =
        error.response.data.username + "<br/>" + error.response.data.password;
    }
  }

  changeUsernameHandler = (event) => {
    this.setState({ username: event.target.value });
  };

  changePasswordHandler = (event) => {
    this.setState({ password: event.target.value });
  };

  render() {
    return (
      <div>
        <Header />
        <div className="container landing">
          <div className="row">
            <div className="col-md-8 m-auto">
              <h1 className="display-4 text-center">Log In</h1>
              <form>
                <div className="form-group">
                  <input
                    type="text"
                    className="form-control form-control-lg"
                    placeholder="Username"
                    name="username"
                    value={this.state.username}
                    onChange={this.changeUsernameHandler}
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
                <input
                  type="submit"
                  className="btn btn-primary
                btn-block mt-4"
                  onClick={this.loginUser}
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

export default Login;
