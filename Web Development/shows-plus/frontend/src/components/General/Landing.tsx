import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

function Component() {
  const [data, setData] = useState("");

  useEffect(() => {
    const fetchData = async () => {
      const response = await axios.post(
        `http://localhost:8888/.netlify/functions/test`
      );
      setData(response.data.msg);
    };
    fetchData();
  }, []);

  document.title = `Landing`;

  return (
    /* Landing Page */
    <div className="container bg-light ps-0 pe-0 text-body">
      <h1>{data}</h1>
      {/* carousel */}
      <div
        id="carouselExampleControls"
        className="carousel slide carousel-fade"
        data-ride="carousel"
      >
        <div className="carousel-inner">
          <div className="carousel-item active">
            <a
              href="https://www.youtube.com/watch?v=EXeTwQWrcwY"
              target="_blank"
              rel="noreferrer"
            >
              <img
                className="d-block w-100"
                src={`${process.env.PUBLIC_URL}/images/img2.jpg`}
                alt="First slide"
              ></img>
            </a>
          </div>
          <div className="carousel-item">
            <a
              href="https://www.youtube.com/watch?v=6ZfuNTqbHE8"
              target="_blank"
              rel="noreferrer"
            >
              <img
                className="d-block w-100"
                src={`${process.env.PUBLIC_URL}/images/img1.jpg`}
                alt="Second slide"
              ></img>
            </a>
          </div>
          <div className="carousel-item">
            <a
              href="https://www.youtube.com/watch?v=Q8Fmjr1okK4"
              target="_blank"
              rel="noreferrer"
            >
              <img
                className="d-block w-100"
                src={`${process.env.PUBLIC_URL}/images/img3.jpg`}
                alt="Third slide"
              ></img>
            </a>
          </div>
          <div className="carousel-item">
            <a
              href="https://www.youtube.com/watch?v=mNgwNXKBEW0"
              target="_blank"
              rel="noreferrer"
            >
              <img
                className="d-block w-100"
                src={`${process.env.PUBLIC_URL}/images/img4.jpg`}
                alt="Fourth slide"
              ></img>
            </a>
          </div>
        </div>
        <a
          className="carousel-control-prev"
          href="#carouselExampleControls"
          role="button"
          data-slide="prev"
        >
          <span
            className="carousel-control-prev-icon"
            aria-hidden="true"
          ></span>
          <span className="sr-only">Previous</span>
        </a>
        <a
          className="carousel-control-next"
          href="#carouselExampleControls"
          role="button"
          data-slide="next"
        >
          <span
            className="carousel-control-next-icon"
            aria-hidden="true"
          ></span>
          <span className="sr-only">Next</span>
        </a>
      </div>
    </div>
  );
}

export default Component;
