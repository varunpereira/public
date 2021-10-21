import React, { Component } from "react";

class Footer extends Component {
  render() {
    return (
      <div>
        <footer className="navbar navbar-expand-sm navbar-dark bg-dark mt-0 mb-0 pt-4 pb-4">
          <div className="container">
            <a className="navbar-brand">
              Bookeroo
            </a>
            <button
              className="navbar-toggler"
              type="button"
              data-toggle="collapse"
              data-target="#mobile-nav"
            >
              <span className="navbar-toggler-icon" />
            </button>

            <div className="text-center p-4 text-light ml-auto">
              © 2021 Bookeroo
            </div>

            <div className="collapse navbar-collapse" id="mobile-nav">
              <ul className="navbar-nav ml-auto">
                <li className="nav-item">
                  <a className="nav-link">
                    Home
                  </a>
                </li>
                <li className="nav-item">
                  <a className="nav-link">
                    About
                  </a>
                </li>
                <li className="nav-item">
                  <a className="nav-link">
                    Contact
                  </a>
                </li>
                <li className="nav-item">
                  <a className="nav-link">
                    Sign Up
                  </a>
                </li>
                <li className="nav-item">
                  <a className="nav-link">
                    Login
                  </a>
                </li>
                <li className="nav-item">
                  <a className="nav-link">
                    Search
                  </a>
                </li>
              </ul>
            </div>
          </div>
        </footer>
      </div>
    );
  }
}
export default Footer;
