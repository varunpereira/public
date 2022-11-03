import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";

function Component() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    document.title = `Login`;
  });

  const login = (event: React.FormEvent<HTMLButtonElement>) => {
    event.preventDefault();
    const loginModel = {
      username: username,
      password: password,
    };
    axios
      .post(
        `${(window as any).localStorage.getItem(
          "backend")}/api/user/signin`,
        loginModel
      )
      .then((response) => {
        console.log(response.data);
        if (response.data == true) {
          (window as any).localStorage.setItem("username", username);
          navigate("/");
        } else {
          console.log(response.data.error);
          setError(response.data.error);
        }
      })
      .catch((error) => {
        console.log(error.data);
        // document.getElementById("error").innerHTML = error.response.data;
      });
  };

  return (
    <div
      className="container bg-light pl-0 pr-0 text-body w-50"
      style={{ borderRadius: "10px" }}
    >
      <div className="row">
        <div className="col-md-8 m-auto">
          <h1 className="display-4 text-center">Sign In </h1>
          <form>
            <div className="form-group">
              <input
                type="text"
                className="form-control form-control-lg"
                placeholder="Username"
                name="username"
                required
                value={username}
                onChange={(event) => setUsername(event.target.value)}
              />
            </div>
            <div className="form-group">
              <input
                type="password"
                className="form-control form-control-lg"
                placeholder="Password"
                name="fullName"
                value={password}
                onChange={(event) => setPassword(event.target.value)}
              />
            </div>
            <button
              type="submit"
              className="btn btn-primary btn-block mt-4"
              onClick={login}
            >
              Submit
            </button>
            <br />
            <br />
            <p className="text-danger">{error}</p>
            <br />
          </form>
        </div>
      </div>
    </div>
  );
}

export default Component;
