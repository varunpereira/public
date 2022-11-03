import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";

function Component() {
  const [username, setUsername] = useState("");
  const [fullName, setFullName] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [country, setCountry] = useState("Australia");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    // Update the document title using the browser API
    document.title = `Sign Up`;
  });

  const createUser = (event: React.FormEvent<HTMLButtonElement>) => {
    event.preventDefault();
    const userCreated = {
      username: username,
      password: password,
      confirm_password: confirmPassword,
      full_name: fullName,
      country: country,
    };
    console.log(userCreated)
    axios
      .post(
        `${(window as any).localStorage.getItem(
          "backend"
        )}/api/user/signup`,
        userCreated
      )
      .then((response) => {
        console.log(response);
        if (response.data.success == true) {
          const loginModel = {
            username: username,
            password: password,
          };
          axios
            .post(
              `${(window as any).localStorage.getItem(
                "backend"
              )}/api/user/signin`,
              loginModel
            )
            .then((response) => {
              console.log(response.data);
              if (response.data == true) {
                (window as any).localStorage.setItem("username", username);
                navigate("/");
              } else {
                console.log(response.data.error);
                setError(response.data);
              }
            })
            .catch((error) => {
              console.log(error.data);
              // document.getElementById("error").innerHTML = error.response.data;
            });
        } else {
          console.log(response.data);
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
          <h1 className="display-4 text-center">Sign Up</h1>
          <form>
            <div className="form-group">
              <input
                type="text"
                className="form-control form-control-lg"
                placeholder="Username"
                name="username"
                value={username}
                onChange={(event) => setUsername(event.target.value)}
              />
            </div>
            <div className="form-group">
              <input
                type="text"
                className="form-control form-control-lg"
                placeholder="Full Name"
                name="password"
                value={fullName}
                onChange={(event) => setFullName(event.target.value)}
              />
            </div>
            <div className="form-group">
              <input
                type="text"
                className="form-control form-control-lg"
                placeholder="Password"
                name="fullName"
                value={password}
                onChange={(event) => setPassword(event.target.value)}
              />
            </div>
            <div className="form-group">
              <input
                type="text"
                className="form-control form-control-lg"
                placeholder="Confirm Password"
                name="fullName"
                value={confirmPassword}
                onChange={(event) => setConfirmPassword(event.target.value)}
              />
            </div>
            <div className="form-group">
              <label htmlFor="sel1">Country:</label>
              <select
                name="accountType"
                value={country}
                onChange={(event) => setCountry(event.target.value)}
                className="form-control"
                id="sel1"
              >
                <option>Australia</option>
                <option>Other</option>
              </select>
            </div>
            <button
              type="submit"
              className="btn btn-primary btn-block mt-4"
              onClick={createUser}
            >
              Submit
            </button>
            <br />
            <br />
            {/* {error.map((item) => (
              <p key={item} className="text-danger" style={{ margin: "0px" }}>
                {item}
              </p>
            ))} */}
            {error}
            <br /> <br />
          </form>
        </div>
      </div>
    </div>
  );
}

export default Component;
