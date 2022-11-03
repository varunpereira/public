import React, { useState, useEffect } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import axios from "axios";

function Component() {
  const location = useLocation();
  const { searchTerm } = location.state;
  document.title = "Search";
  const [searchResults, setSearchResults] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchData = async () => {
      // first request
      let body = {
        search_term: searchTerm,
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
          setSearchResults(response.data.searchResults);
        });
    };
    fetchData();
  }, []);

  const showHandler = (showTitle: React.FormEvent<HTMLElement>) => {
    navigate(`/show/${showTitle}`, { state: { showTitle: showTitle } });
  };

  return (
    <div className="container ps-0 pe-0 text-white">
      {searchResults.length + " results found for '" + searchTerm + "'"}
      <br />
      <br />
      {searchResults.map((element) => (
        <a
          key={element}
          href=""
          className="text-decoration-none row text-white"
          onClick={() => showHandler((element as any).title)}
          style={{
            // color: "lightskyblue",
            borderRadius: "10px",
            backgroundColor: "#181818",
          }}
        >
          <img
            src={`${process.env.PUBLIC_URL}/images/shows/${
              (element as any).image_name
            }`}
            alt="image"
            style={{ borderRadius: "10px", width: "10%" }}
            className="col-8"
          ></img>
          <div className="col-4">
            <p>{(element as any).title}</p>
            <p>Cast: {(element as any).cast}</p>
          </div>
        </a>
      ))}
    </div>
  );
}

export default Component;
