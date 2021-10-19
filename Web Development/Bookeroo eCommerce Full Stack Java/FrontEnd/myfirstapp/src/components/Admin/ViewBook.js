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
      book: [],
    };

    if (this.props.location.state !== undefined) {
      this.state.adminUsername = this.props.location.state.adminUsername;
      this.state.adminToken = this.props.location.state.adminToken;
      this.state.book = this.props.location.state.book;
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
              <h1 className="display-4 text-center">View Book</h1>
              <h1 className="display-4 text-center">
                admin:{this.state.adminUsername}
              </h1>

              <div className="">
                <p className="">ISBN: {this.state.book.isbn}</p>
                <p className="">Name: {this.state.book.name}</p>
                <p className="">Category: {this.state.book.category}</p>
                <p className="">Author: {this.state.book.author}</p>
                <p className="">Seller: {this.state.book.seller}</p>
                <p className="">Price: {this.state.book.price}</p>
                <p className="">Stock Level: {this.state.book.stocklevel}</p>
                <p className="">Cover URL: {this.state.book.coverurl}</p>
                <p className="">Content URL: {this.state.book.contenturl}</p>
                <p className="">New Book: {String(this.state.book.newbook)}</p>
                <p className="">
                  Loaned Book: {String(this.state.book.loanedbook)}
                </p>
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
