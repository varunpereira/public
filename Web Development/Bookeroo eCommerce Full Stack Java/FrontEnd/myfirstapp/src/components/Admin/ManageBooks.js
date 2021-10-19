import React, { Component } from "react";
import Header from "../Layout/Header";
import OrderService from "../Services/OrderService";
import PaymentService from "../Services/PaymentService";
import BookService from "../Services/SearchResultsService";
import UserService from "../Services/UserService";
import ReviewService from "../Services/ReviewService";
import { Link } from "react-router-dom";

class AdminUsers extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      token: "",
      books: [],
    };
    if (this.props.location.state !== undefined) {
      this.state.username = this.props.location.state.username;
      this.state.token = this.props.location.state.token;
    }
    //bind so these functions access to state
    this.delete = this.delete.bind(this);
  }

  componentDidMount() {
    const { id } = this.props.match.params;
    if (this.state.username !== id) {
      this.props.history.push({
        pathname: "/login",
      });
    } else {
      BookService.get("/search/allBooks").then((response) => {
        this.setState({ books: response.data });
      });
    }
  }

  async delete(selectedIsbn) {
    BookService.delete("/deleteBook/" + selectedIsbn).then((response1) => {
      document.getElementById("error").innerHTML = "Book Deleted successfuly.";
      BookService.get("/search/allBooks").then((response) => {
        this.setState({ books: response.data });
      });
    });
  }

  render() {
    return (
      <div>
        <Header headerUsername={this.state.username} />
        <div className="container landing">
          <div className="row">
            <div className="col-md-12 text-center">
              <h1 className="display-3 mb-4">Manage Books</h1>
              <p className="lead">Welcome {this.state.username}</p>
              <hr />
              <Link
                to={{
                  pathname: "/admin/addbook/" + this.state.username,
                  state: {
                    adminToken: this.state.token,
                    adminUsername: this.state.username,
                  },
                }}
                style={{ fontSize: "14px" }}
                className="text-right"
              >
                add
              </Link>

              <p id="error" className="text-danger"></p>
            </div>
          </div>
          <div className="row">
            <div className="col-md-12 text-center" style={{ fontSize: "14px" }}>
              <div>
                <table className="table table-striped">
                  <thead>
                    <tr>
                      <td></td>
                      <td></td>
                      <td></td>
                      <td>ISBN</td>
                      <td>Name</td>
                      <td>Category</td>
                      <td>Seller</td>
                    </tr>
                  </thead>
                  <tbody>
                    {this.state.books.map((book) => (
                      <tr key={book.id}>
                        <td>
                          <Link
                            to={{
                              pathname:
                                "/admin/viewbook/" + this.state.username,
                              state: {
                                adminToken: this.state.token,
                                adminUsername: this.state.username,
                                book: book,
                              },
                            }}
                            style={{ fontSize: "11px" }}
                            className="text-right"
                          >
                            view
                          </Link>
                        </td>
                        <td>
                          <Link
                            to={{
                              pathname:
                                "/admin/editbook/" + this.state.username,
                              state: {
                                adminToken: this.state.token,
                                adminUsername: this.state.username,
                                book: book,
                              },
                            }}
                            style={{ fontSize: "11px" }}
                            className="text-right"
                          >
                            edit
                          </Link>
                        </td>
                        <td>
                          <Link
                            onClick={async () => this.delete(book.isbn)}
                            style={{ fontSize: "11px" }}
                          >
                            delete
                          </Link>
                        </td>
                        <td> {book.isbn}</td>
                        <td>{book.name}</td>
                        <td> {book.author}</td>
                        <td> {book.seller}</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default AdminUsers;
