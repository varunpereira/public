import React, { Component } from "react";
import UserService from "./../Services/UserService";
import Header from "./../Layout/Header";
import { Link } from "react-router-dom";
import BookService from "../Services/SearchResultsService";

class AdminAddUsers extends Component {
  constructor(props) {
    super(props);

    this.state = {
      adminUsername: "",
      adminToken: "",
      book: [],
      isbn: "",
      name: "",
      category: "",
      author: "",
      seller: "",
      price: "",
      stocklevel: "",
      coverurl: "",
      contenturl: "",
    };

    if (this.props.location.state !== undefined) {
      this.state.adminUsername = this.props.location.state.adminUsername;
      this.state.adminToken = this.props.location.state.adminToken;
      this.state.isbn = this.props.location.state.book.isbn;
      this.state.name = this.props.location.state.book.name;
      this.state.category = this.props.location.state.book.category;
      this.state.author = this.props.location.state.book.author;
      this.state.seller = this.props.location.state.book.seller;
      this.state.price = this.props.location.state.book.price;
      this.state.stocklevel = this.props.location.state.book.stocklevel;
      this.state.coverurl = this.props.location.state.book.coverurl;
      this.state.contenturl = this.props.location.state.book.contenturl;
    }
    this.changeIsbn = this.changeIsbn.bind(this);
    this.changeName = this.changeName.bind(this);
    this.changeCategory = this.changeCategory.bind(this);
    this.changeAuthor = this.changeAuthor.bind(this);
    this.changeSeller = this.changeSeller.bind(this);
    this.changePrice = this.changePrice.bind(this);
    this.changeStockLevel = this.changeStockLevel.bind(this);
    this.changeCoverUrl = this.changeCoverUrl.bind(this);
    this.changeContentUrl = this.changeContentUrl.bind(this);
    this.updateBook = this.updateBook.bind(this);
  }

  componentDidMount() {
    const { id } = this.props.match.params;
    if (this.state.adminUsername !== id) {
      this.props.history.push({
        pathname: "/login",
      });
    }
  }

  async updateBook(event) {
    event.preventDefault();
    let updatedBook = {
      isbn: this.state.isbn,
      name: this.state.name,
      category: this.state.category,
      author: this.state.author,
      seller: this.state.seller,
      price: this.state.price,
      stocklevel: this.state.stocklevel,
      coverurl: this.state.coverurl,
      contenturl: this.state.contenturl,
    };

    try {
      await BookService.patch("/updateBook/" + this.state.isbn, updatedBook);
      document.getElementById("error").innerHTML = "Book Updated successfuly.";
    } catch (error) {
      console.log(error.response.data);
      document.getElementById("error").innerHTML =
        error.response.data.isbn +
        "<br/>" +
        error.response.data.name +
        "<br/>" +
        error.response.data.category +
        "<br/>" +
        error.response.data.author +
        "<br/>" +
        error.response.data.seller +
        "<br/>" +
        error.response.data.price +
        "<br/>" +
        error.response.data.stocklevel +
        "<br/>" +
        error.response.data.coverurl +
        "<br/>" +
        error.response.data.contenturl;
    }
  }

  changeIsbn = (event) => {
    this.setState({ isbn: event.target.value });
  };

  changeName = (event) => {
    this.setState({ name: event.target.value });
  };

  changeCategory = (event) => {
    this.setState({ category: event.target.value });
  };

  changeAuthor = (event) => {
    this.setState({ author: event.target.value });
  };
  changeSeller = (event) => {
    this.setState({ seller: event.target.value });
  };
  changePrice = (event) => {
    this.setState({ price: event.target.value });
  };
  changeStockLevel = (event) => {
    this.setState({ stocklevel: event.target.value });
  };

  changeCoverUrl = (event) => {
    this.setState({ coverurl: event.target.value });
  };

  changeContentUrl = (event) => {
    this.setState({ contenturl: event.target.value });
  };

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
                  <input
                    type="text"
                    className="form-control form-control-lg"
                    placeholder="ISBN"
                    name="username"
                    required
                    value={this.state.isbn}
                    onChange={this.changeIsbn}
                  />
                </div>
                <div className="form-group">
                  <input
                    type="text"
                    className="form-control form-control-lg"
                    placeholder="Name"
                    name="fullName"
                    value={this.state.name}
                    onChange={this.changeName}
                  />
                </div>
                <div className="form-group">
                  <input
                    type="text"
                    className="form-control form-control-lg"
                    placeholder="Category"
                    name="password"
                    value={this.state.category}
                    onChange={this.changeCategory}
                  />
                </div>
                <div className="form-group">
                  <input
                    type="text"
                    className="form-control form-control-lg"
                    placeholder="Author"
                    name="confirmPassword"
                    value={this.state.author}
                    onChange={this.changeAuthor}
                  />
                </div>
                <div className="form-group">
                  <input
                    type="text"
                    className="form-control form-control-lg"
                    placeholder="Seller"
                    name="address"
                    value={this.state.seller}
                    onChange={this.changeSeller}
                  />
                </div>
                <div className="form-group">
                  <input
                    type="text"
                    className="form-control form-control-lg"
                    placeholder="Price"
                    name="phoneNumber"
                    value={this.state.price}
                    onChange={this.changePrice}
                  />
                </div>
                <div className="form-group">
                  <input
                    type="text"
                    className="form-control form-control-lg"
                    placeholder="Stock Level"
                    name="abn"
                    value={this.state.stocklevel}
                    onChange={this.changeStockLevel}
                  />
                </div>
                <div className="form-group">
                  <input
                    type="text"
                    className="form-control form-control-lg"
                    placeholder="Cover URL"
                    name="abn"
                    value={this.state.coverurl}
                    onChange={this.changeCoverUrl}
                  />
                </div>
                <div className="form-group">
                  <input
                    type="text"
                    className="form-control form-control-lg"
                    placeholder="Content URL"
                    name="abn"
                    value={this.state.contenturl}
                    onChange={this.changeContentUrl}
                  />
                </div>
                <button
                  type="submit"
                  className="btn btn-primary btn-block mt-4"
                  onClick={this.updateBook}
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
