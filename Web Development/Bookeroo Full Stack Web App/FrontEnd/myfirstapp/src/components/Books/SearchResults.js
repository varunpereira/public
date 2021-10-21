import React, { Component } from "react";
import SearchResultsService from "../Services/SearchResultsService";
import Header from "../Layout/Header";
import { Link } from "react-router-dom";

class SearchResults extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      token: "",
      searchTerm: "",
      searchType: "",
      books: [],
    };
    if (this.props.location.state !== undefined) {
      this.state.username = this.props.location.state.username;
      this.state.token = this.props.location.state.token;
      this.state.searchTerm = this.props.location.state.searchTerm;
      this.state.searchType = this.props.location.state.searchType;
    }
  }

  componentDidMount() {
    SearchResultsService.getSearchResults(
      "/search/" + this.state.searchType + "/" + this.state.searchTerm
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
              <h1 className="display-5 text-center">
                {numRows} results in {this.state.searchType} for "
                {this.state.searchTerm}" <br></br>username:{" "}
                {this.state.username}
              </h1>
              <div>
                <table className="table table-striped">
                  <thead>
                    <tr>
                      <td> Book Name</td>
                      <td> Book Seller</td>
                      <td> Book ISBN</td>
                      <td> Book Category</td>
                      <td> New Book</td>
                      {/* <td> Loaned Book</td> */}
                    </tr>
                  </thead>
                  <tbody>
                    {this.state.books.map((book) => (
                      <tr key={book.name}>
                        {console.log(book.newbook)}
                        <td>
                          <Link
                            to={{
                              pathname: "/book-details",
                              state: {
                                username: this.state.username,
                                bookIsbn: book.isbn,
                              },
                            }}
                          >
                            {book.name}
                          </Link>
                        </td>
                        <td> {book.seller}</td>
                        <td> {book.isbn}</td>
                        <td> {book.category}</td>
                        <td> {String(book.newbook)}</td>
                        {/* <td> {String(book.loanedbook)}</td> */}
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

export default SearchResults;
