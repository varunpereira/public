import React, { Component } from "react";
import { Link } from "react-router-dom";
import Header from "../Layout/Header";
import SearchResultsService from "../Services/SearchResultsService";

class Landing extends Component {
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
  }

  componentDidMount() {
    SearchResultsService.getSearchResults(
      "/search/allBooks"
    ).then((response) => {
      this.setState({ books: response.data });
    });
  }

  render() {
    const booksToRender = this.state.books.filter((book) => book.name);
    const numRows = booksToRender.length;
    return (
      <div>
        <Header headerUsername={this.state.username} />
        <div className="container landing">
          <div className="row">
            <div className="col-md-8 m-auto">
              <h1 className="display-4 text-center">Browse All Books</h1>
              <h1 className="display-5 text-center">
                {numRows} results username: {this.state.username}
              </h1>
              <div>
                <table className="table table-striped">
                  <thead>
                    <tr>
                      <td> Book Name</td>
                      <td> Book Author</td>
                      <td> Book ISBN</td>
                      <td> Book Category</td>
                    </tr>
                  </thead>
                  <tbody>
                    {this.state.books.map((book) => (
                      <tr key={book.name}>
                        <td>
                          <Link
                            to={{
                              pathname: "/book-details",
                              state: {
                                username: this.state.username,
                                bookName: book.name,
                                bookIsbn: book.isbn,
                                bookAuthor: book.author,
                                bookCategory: book.category,
                                bookPrice: book.price,
                                bookSeller: book.seller,
                              },
                            }}
                          >
                            {book.name}
                          </Link>
                        </td>

                        <td> {book.author}</td>
                        <td> {book.isbn}</td>
                        <td> {book.category}</td>
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

export default Landing;
