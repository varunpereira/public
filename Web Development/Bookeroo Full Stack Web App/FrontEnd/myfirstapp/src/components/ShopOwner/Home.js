import React, { Component } from "react";
import Header from "../Layout/Header";

class HomeShopOwner extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      token: "",
    };
    if (this.props.location.state !== undefined) {
      this.state.username = this.props.location.state.username;
      this.state.token = this.props.location.state.token;
    }
  }

  render() {
    return (
      <div>
        <Header headerUsername={this.state.username} />

        <div className="container landing">
          <div className="row">
            <div className="col-md-12 text-center">
              <h1 className="display-3 mb-4">Home</h1>
              <p className="lead">Welcome {this.state.username}</p>
              <hr />
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default HomeShopOwner;
