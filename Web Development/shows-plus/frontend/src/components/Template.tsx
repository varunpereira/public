import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";

function Component() {
    const [count, setCount] = useState(0);
    const [users, setUsers] = useState([]);
    document.title = `Home`;
  
    useEffect(() => {
      const fetchData = async () => {
        // first request
        // const response = await axios.post(`${window.domain}/api/user/GetUser/all`);
        // setUsers(response.data);
        // second request
      };
      fetchData();
    }, []);
  
    return (
      <div className="container bg-light pl-0 pr-0 text-body">
    
      </div>
    );
  }
  
  export default Component;