import React, { Component } from "react";
import { Link } from "react-router-dom";
import UserService from "../Services/UserService";

class Header extends Component {
  //uses logout button or closes window but wb changing url - publicpage/username
  // if window.close() || loggedIn == false ? this.state.loggedIn == false

  constructor(props) {
    super(props);
    this.state = {
      headerUsername: "",
      searchTerm: "",
      searchCategory: "all",
      accountType: "",
    };
    if (this.state.headerUsername !== null) {
      this.state.headerUsername = this.props.headerUsername;
    }

    this.changeSearchTermHandler = this.changeSearchTermHandler.bind(this);
    this.changeSearchCategoryHandler =
      this.changeSearchCategoryHandler.bind(this);
  }

  componentDidMount() {
    if (this.state.headerUsername !== null) {
      this.state.headerUsername = this.props.headerUsername;
      UserService.post("/findUser/" + this.state.headerUsername).then(
        (response) => {
          this.setState({ accountType: response.data.accounttype });
        }
      );
    }
  }

  changeSearchTermHandler = (event) => {
    this.setState({ searchTerm: event.target.value });
  };

  changeSearchCategoryHandler = (event) => {
    this.setState({ searchCategory: event.target.value });
  };

  render() {
    return (
      <div>
        <nav className="navbar navbar-expand-sm navbar-dark bg-dark mb-5">
          <div className="container">
            <Link
              to={{
                pathname: "/",
                state: {
                  username: this.props.headerUsername,
                },
              }}
              className="navbar-brand"
            >
              Bookeroo
            </Link>

            <button
              className="navbar-toggler"
              type="button"
              data-toggle="collapse"
              data-target="#mobile-nav"
            >
              <span className="navbar-toggler-icon" />
            </button>

            <div className="collapse navbar-collapse" id="mobile-nav">
              <ul className="navbar-nav mr-auto">
                <li className="nav-item">
                  <Link
                    to={{
                      pathname: "/about",
                      state: {
                        username: this.props.headerUsername,
                      },
                    }}
                    className="nav-link"
                  >
                    About
                  </Link>
                </li>
                <li className="nav-item">
                  <Link
                    to={{
                      pathname: "/contact",
                      state: {
                        username: this.props.headerUsername,
                      },
                    }}
                    className="nav-link"
                  >
                    Contact
                  </Link>
                </li>
              </ul>

              <form className="form-inline mr-auto ">
                <select
                  name="searchCategory"
                  value={this.state.searchCategory}
                  onChange={this.changeSearchCategoryHandler}
                  className="form-control"
                  style={{
                    borderRadius: "10px 0px 0px 10px",
                    width: "95px",
                  }}
                >
                  <option>all</option>
                  <option>name</option>
                  <option>author</option>
                  <option>isbn</option>
                  <option>category</option>
                </select>
                <input
                  type="text"
                  className="form-control"
                  placeholder="search"
                  value={this.state.searchTerm}
                  onChange={this.changeSearchTermHandler}
                  style={{
                    borderRadius: "0px 0px 0px 0px",
                    width: "300px",
                  }}
                ></input>
                <Link
                  to={{
                    pathname: "/search",
                    state: {
                      searchTerm: this.state.searchTerm,
                      searchType: this.state.searchCategory,
                      username: this.props.headerUsername,
                    },
                  }}
                >
                  <div className="input-group-append">
                    <button
                      className="btn btn-secondary"
                      type="submit"
                      style={{
                        borderRadius: "0px 10px 10px 0px",
                      }}
                    >
                      <i className="fa fa-search"></i>
                    </button>
                  </div>
                </Link>
              </form>

              {this.props.headerUsername == null ||
              this.props.headerUsername == "" ? (
                <ul className="navbar-nav ml-auto">
                  <li className="nav-item">
                    <a className="nav-link " href="/register">
                      Sign Up
                    </a>
                  </li>
                  <li className="nav-item">
                    <a className="nav-link" href="/login">
                      Login
                    </a>
                  </li>
                </ul>
              ) : this.state.accountType == "Admin" ? (
                <ul className="navbar-nav ml-auto">
                  <li className="nav-item">
                    <div className="dropdown show">
                      <div
                        className="btn navbar-dark text-light dropdown-toggle"
                        role="button"
                        id="dropdownMenuLink"
                        data-toggle="dropdown"
                        aria-haspopup="true"
                        aria-expanded="false"
                      >
                        Manage
                      </div>
                      <div
                        className="dropdown-menu "
                        aria-labelledby="dropdownMenuLink"
                      >
                        <Link
                          to={{
                            pathname:
                              "/admin/manageusers/" + this.props.headerUsername,
                            state: {
                              username: this.props.headerUsername,
                            },
                          }}
                          className="dropdown-item"
                        >
                          Users
                        </Link>
                        <Link
                          to={{
                            pathname:
                              "/admin/managebooks/" + this.props.headerUsername,
                            state: {
                              username: this.props.headerUsername,
                            },
                          }}
                          className="dropdown-item"
                        >
                          Books
                        </Link>
                        <Link
                          to={{
                            pathname:
                              "/admin/managepayments/" +
                              this.props.headerUsername,
                            state: {
                              username: this.props.headerUsername,
                            },
                          }}
                          className="dropdown-item"
                        >
                          Payments
                        </Link>
                        <Link
                          to={{
                            pathname:
                              "/admin/managereports/" +
                              this.props.headerUsername,
                            state: {
                              username: this.props.headerUsername,
                            },
                          }}
                          className="dropdown-item"
                        >
                          Reports
                        </Link>
                      </div>
                    </div>
                  </li>
                  <li className="nav-item">
                    <div className="dropdown show">
                      <div
                        className="btn navbar-dark text-light dropdown-toggle"
                        role="button"
                        id="dropdownMenuLink"
                        data-toggle="dropdown"
                        aria-haspopup="true"
                        aria-expanded="false"
                      >
                        {this.props.headerUsername}
                      </div>

                      <div
                        className="dropdown-menu"
                        aria-labelledby="dropdownMenuLink"
                      >
                        <Link
                          to={{
                            pathname: "/login",
                          }}
                          className="dropdown-item"
                        >
                          Logout
                        </Link>
                      </div>
                    </div>
                  </li>
                </ul>
              ) : (
                <ul className="navbar-nav ml-auto">
                     <li className="nav-item">
                    <Link
                      to={{
                        pathname:
                          "/book-sellbook/" + this.props.headerUsername,
                        state: {
                          username: this.props.headerUsername,
                        },
                      }}
                      className="nav-link"
                    >
                      Sell
                    </Link>
                  </li>
                  <li className="nav-item">
                    <Link
                      to={{
                        pathname:
                          "/book-currentorder/" + this.props.headerUsername,
                        state: {
                          username: this.props.headerUsername,
                        },
                      }}
                      className="nav-link"
                    >
                      Cart
                    </Link>
                  </li>
                  <li className="nav-item">
                    <div className="dropdown show">
                      <div
                        className="btn btn-dark dropdown-toggle"
                        role="button"
                        id="dropdownMenuLink"
                        data-toggle="dropdown"
                        aria-haspopup="true"
                        aria-expanded="false"
                      >
                        {this.props.headerUsername}
                      </div>

                      <div
                        className="dropdown-menu"
                        aria-labelledby="dropdownMenuLink"
                      >
                        <Link
                          to={{
                            pathname:
                              "/book-pastpayments/" + this.props.headerUsername,
                            state: {
                              username: this.props.headerUsername,
                            },
                          }}
                          className="dropdown-item"
                        >
                          Transactions
                        </Link>
                        <Link
                          to={{
                            pathname: "/login",
                          }}
                          className="dropdown-item"
                        >
                          Logout
                        </Link>
                      </div>
                    </div>
                  </li>
                </ul>
              )}
            </div>
          </div>
        </nav>
      </div>
    );
  }
}
export default Header;
