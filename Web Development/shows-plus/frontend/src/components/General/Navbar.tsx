import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";

function Component() {
  const [searchTerm, setSearchTerm] = useState("");
  const [searchType, setSearchType] = useState("all");
  const navigate = useNavigate();

  const handleSubmit = (evt: React.FormEvent<HTMLButtonElement>) => {
    evt.preventDefault();
    navigate(`/search?${searchTerm}`, { state: { searchTerm: searchTerm } });
    window.location.reload();
  };

  const logout = (event: React.FormEvent<HTMLElement>) => {
    event.preventDefault();
    (window as any).localStorage.removeItem("username");
    navigate("/login");
  };

  return (
    <div className="container bg-dark ps-0 pe-0">
      <nav className="navbar navbar-expand-sm">
        <Link
          to={{
            pathname: "/",
          }}
        >
          <button
            type="button"
            className="btn btn-warning"
            style={{ fontFamily: "Impact", outline: "none", boxShadow: "none" }}
          >
            <i className="fas fa-couch me-1"></i>
            Zenix
          </button>
        </Link>

        <button
          className="navbar-toggler btn"
          type="button"
          data-toggle="collapse"
          data-target="#mobile-nav"
          style={{ fontFamily: "Impact" }}
        >
          <i className="fas fa-bars text-light"></i>
        </button>

        <div className="collapse navbar-collapse" id="mobile-nav">
          <ul className="navbar-nav me-auto">
            <li className="nav-item nav-link text-light">
              <Link
                to={{
                  pathname: "/trending",
                }}
                style={{ textDecoration: "none" }}
                className="text-light"
              >
                <i className="fas fa-chart-line ms-1 me-1"></i>
                Trending
              </Link>
            </li>
            <li className="nav-item nav-link text-light">
              <Link
                to={{
                  pathname: "/show/all",
                }}
                style={{ textDecoration: "none" }}
                className="text-light"
              >
                <i className="fas fa-film me-1"></i>
                All
              </Link>
            </li>
          </ul>

          <form className="d-flex col-7">
            <input
              type="search"
              placeholder="Search"
              aria-label="Search"
              className="form-control w-50"
              value={searchTerm}
              onChange={(event) => setSearchTerm(event.target.value)}
              style={{
                outline: "none",
                boxShadow: "none",
              }}
            />
            <button
              onClick={handleSubmit}
              className="btn btn-outline-light"
              type="submit"
              style={{ outline: "none", boxShadow: "none" }}
            >
              <i className="fa fa-search"></i>
            </button>
          </form>

          {/* not logged in */}
          {(window as any).localStorage.getItem("username") === "" ||
          (window as any).localStorage.getItem("username") === null ? (
            <ul className="navbar-nav">
              <li>
                <Link
                  to={{
                    pathname: "/signup",
                  }}
                  className="nav-item nav-link text-light"
                >
                  Sign Up
                </Link>
              </li>
              <li className="nav-item">
                <Link
                  to={{
                    pathname: "/login",
                  }}
                  className="nav-item nav-link text-light"
                >
                  Sign In
                </Link>
              </li>
            </ul>
          ) : (
            // Logged In
            <ul className="navbar-nav ml-auto">
              <li className="nav-item">
                <Link
                  to={{
                    pathname: "/cart",
                  }}
                  className="nav-item nav-link text-light"
                >
                  <i className="fas fa-shopping-cart me-1"></i>
                  Cart
                </Link>
              </li>
              <li className="nav-item ">
                <div className="dropdown show">
                  <div
                    className="btn dropdown-toggle text-light"
                    role="button"
                    id="dropdownMenuLink"
                    data-toggle="dropdown"
                    aria-haspopup="true"
                    aria-expanded="false"
                  >
                    <i className="fas fa-user me-1"></i>
                    {(window as any).localStorage.getItem("username")}
                  </div>

                  <div
                    className="dropdown-menu pt-0 pb-0 bg-dark"
                    aria-labelledby="dropdownMenuLink"
                    style={{ maxWidth: "10px" }}
                  >
                    <Link
                      to={{
                        pathname: "/account",
                      }}
                      className="ps-2 text-white text-decoration-none"
                      style={{ margin: "0px" }}
                    >
                      Account
                    </Link>
                    <br />
                    <a
                      href=""
                      className="ps-2 text-white text-decoration-none"
                      onClick={logout}
                      style={{ margin: "0px" }}
                    >
                      Sign Out
                    </a>
                  </div>
                </div>
              </li>
            </ul>
          )}
        </div>
      </nav>
    </div>
  );
}

export default Component;
