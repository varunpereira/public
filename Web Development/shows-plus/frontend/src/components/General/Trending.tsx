import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";

function Component() {
  document.title = "Trending";

  return (
    <div className="container ps-0 pe-0">
      <h1 className="text-center">Browse Trending</h1>
      {/* Home Page */}
      <div className="row">
        <div className="text-center">
          <img
            className="w-50"
            src={`${process.env.PUBLIC_URL}/images/trending_1.jpg`}
            alt="image"
            style={{ borderRadius: "10px" }}
          ></img>
        </div>
      </div>
    </div>
  );
}

export default Component;
