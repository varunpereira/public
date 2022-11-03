import React, { useState, useEffect } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import axios from "axios";

function Component() {
  const location = useLocation();
  document.title = "All";
  const [searchResults, setSearchResults] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchData = async () => {
      // first request
      await axios
        .post(
          `${(window as any).localStorage.getItem(
            "backend"
          )}/api_show/find_all`
        )
        .then((response) => {
          console.log(response.data.searchResults);
          setSearchResults(response.data.searchResults);
        });
    };
    fetchData();
  }, []);

  const showHandler = (showTitle: React.FormEvent<HTMLElement>) => {
    navigate(`/show/${showTitle}`, { state: { showTitle: showTitle } });
  };

  return (
    <div className="container ps-0 pe-0">
      <h1 className="text-center">Browse All</h1>
      <div className="row">
        {searchResults.map((element) => (
          <div
            className="col-sm-4"
            //style={{ float: "left", width: "25%", padding: "0 10px" }}
          >
            <a
              href=""
              onClick={() => showHandler((element as any).title)}
              style={{ color: "lightblue" }}
            >
              <img
                className="w-100"
                src={`${process.env.PUBLIC_URL}/images/shows/${
                  (element as any).image_name
                }`}
                alt="image"
                style={{borderRadius:"10px"}}
              ></img>
            </a>
            <p className="text-white">
              {(element as any).title} <br />
            </p>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Component;
