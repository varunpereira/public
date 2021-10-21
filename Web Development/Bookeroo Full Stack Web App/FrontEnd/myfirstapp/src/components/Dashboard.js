import React, { Component } from "react";
import Person from "./Persons/Person";
import CreatePersonButton from "./Persons/CreatePersonButton";
import Header from "./Layout/Header";

class Dashboard extends Component {
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
      <div className="Persons">
        <Header headerUsername={this.state.username} />
        <div className="container">
          <div className="row">
            <div className="col-md-12">
              <h1 className="display-4 text-center">Persons</h1>
              <h4> </h4>
              {/* as long as not accessed beforehand else undefined error */}
              <h4>{this.state.username}</h4>

              <br />
              <CreatePersonButton />
              <br />
              <hr />
              <Person />
            </div>
          </div>
        </div>
      </div>
    );
  }
}
export default Dashboard;
