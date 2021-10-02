import React, { Component } from "react";
import "./App.css";
import Dashboard from "./components/Dashboard";
import Header from "./components/Layout/Header";
import Footer from "./components/Layout/Footer";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Route } from "react-router-dom";
import AddPerson from "./components/Persons/AddPerson";
import { Provider } from "react-redux";
import store from "./store";

import PaymentDetails from "./components/Books/PaymentDetails";
import PaymentConfirm from "./components/Books/PaymentConfirm";
import PaymentResult from "./components/Books/PaymentResult";
import BookCurrentOrder from "./components/Books/BookCurrentOrder";
import BookReviews from "./components/Books/BookReviews";
import SearchResults from "./components/Books/SearchResults";
import BookDetails from "./components/Books/BookDetails";
import Landing from "./components/Layout/Landing";
import About from "./components/Layout/About";
import Contact from "./components/Layout/Contact";
import Register from "./components/Login/Register";
import Login from "./components/Login/Login";
import HomeAdmin from "./components/Admin/Home";
import HomeCustomer from "./components/Customer/Home";
import HomePublisher from "./components/Publisher/Home";
import HomeShopOwner from "./components/ShopOwner/Home";

class App extends Component {
  render() {
    return (
      <Provider store={store}>
        <Router>
          <div className="App">
            {/* <Header/> */}

            {
              //Public Routes
            }

            <Route exact path="/" component={Landing} />
            <Route exact path="/about" component={About} />
            <Route exact path="/contact" component={Contact} />
            <Route exact path="/register" component={Register} />
            <Route exact path="/login" component={Login} />
            <Route exact path="/search" component={SearchResults} />
            <Route exact path="/book-details" component={BookDetails} />
            <Route exact path="/book-currentorder/:id" component={BookCurrentOrder} />
            <Route exact path="/book-reviews/:id" component={BookReviews} />
            <Route exact path="/admin/home/:id" component={HomeAdmin} />
            <Route path="/customer/home/:id" component={HomeCustomer} />
            <Route exact path="/publisher/home/:id" component={HomePublisher} />
            <Route exact path="/shop-owner/home/:id" component={HomeShopOwner} />
            <Route path="/paymentdetails/:id" component={PaymentDetails} />
            <Route path="/paymentconfirm/:id" component={PaymentConfirm} />
            <Route path="/:id/paymentresult/success" component={PaymentResult} />

            <Route exact path="/dashboard" component={Dashboard} />
            {
              //Private Routes
            }

            <Route exact path="/addPerson" component={AddPerson} />
            <Footer />
          </div>
        </Router>
      </Provider>
    );
  }
}
export default App;
