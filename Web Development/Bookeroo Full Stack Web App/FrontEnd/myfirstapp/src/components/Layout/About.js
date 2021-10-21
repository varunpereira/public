import React, { Component } from "react";
import Header from "../Layout/Header";

class About extends Component {
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
                <h1 className="display-3 mb-4">
                  About Us
                </h1>
                <p className="lead">
                  Service with a smile! 
                </p>
                <hr />
                
              </div>
            </div>
          </div>
      </div>
    );
  }
}

export default About;