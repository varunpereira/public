import "./App.css";
import React, { useState, useEffect } from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";

// General
import Navbar from "./components/General/Navbar";
import Footer from "./components/General/Footer";
import Landing from "./components/General/Landing";
import Trending from "./components/General/Trending";
// Login
import SignUp from "./components/Login/SignUp";
import Login from "./components/Login/SignIn";
// Show
import SearchResultShows from "./components/Show/Search";
import AllShows from "./components/Show/All";
import Show from "./components/Show/Show";

function Component() {
  let backend_domain: string;
  backend_domain = "http://localhost:4000";
  //backend_domain = "https://zenix-back.herokuapp.com";
  // backend_domain =
  //   "https://ap-southeast-2.aws.data.mongodb-api.com/app/application-0-rthdv/endpoint";

  (window as any).localStorage.setItem("backend", backend_domain);

  return (
    <Router>
      <div>
        <Navbar />
        <Routes>
          <Route path="/" element={<Landing />}></Route>
          <Route path="/signup" element={<SignUp />}></Route>
          <Route path="/login" element={<Login />}></Route>
          <Route path="/trending" element={<Trending />}></Route>
          <Route path="/search" element={<SearchResultShows />}></Route>
          <Route path="/show/all" element={<AllShows />}></Route>
          <Route path="/show/:title" element={<Show />}></Route>
        </Routes>
        <Footer />
      </div>
    </Router>
  );
}

export default Component;
