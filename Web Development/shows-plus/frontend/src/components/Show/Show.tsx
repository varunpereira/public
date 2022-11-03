import React, { useState, useEffect } from "react";
import { Link, useLocation } from "react-router-dom";
import axios from "axios";

function Component() {
  const location = useLocation();
  const { showTitle } = location.state;
  document.title = `Show`;
  const [showDetails, setShowDetails] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      // first request
      let body = {
        search_term: showTitle,
      };
      await axios
        .post(
          `${(window as any).localStorage.getItem(
            "backend"
          )}/api_show/find_by_title`,
          body
        )
        .then((response) => {
          console.log(response.data.searchResults);
          setShowDetails(response.data.searchResults);
        });
    };
    fetchData();
  }, []);

  return (
    <div className="container ps-0 pe-0 text-white">
      <div className="row">
        <div className="text-center">
          {/* {showTitle} */}
          {showDetails.map((element) => (
            <div key={element}>
              <img
                className="w-50"
                src={`${process.env.PUBLIC_URL}/images/shows/${
                  (element as any).image_name
                }`}
                alt="image"
                style={{ borderRadius: "10px" }}
              ></img>
              <br /> <br />
              <p className="text-white">
                Title: {(element as any).title} <br />
                Cast: {(element as any).cast}
              </p>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default Component;
