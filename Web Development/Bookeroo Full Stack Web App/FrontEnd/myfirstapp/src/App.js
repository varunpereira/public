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

import BookPastPayments from "./components/Books/BookPastPayments";
import BookPastPaymentOrders from "./components/Books/BookPastPaymentOrders";
import PaymentDetails from "./components/Books/PaymentDetails";
import PaymentConfirm from "./components/Books/PaymentConfirm";
import PaymentResult from "./components/Books/PaymentResult";
import BookSellBook from "./components/Books/BookSellBook";
import BookCurrentOrder from "./components/Books/BookCurrentOrder";
import BookReviews from "./components/Books/BookReviews";
import SearchResults from "./components/Books/SearchResults";
import BookDetails from "./components/Books/BookDetails";
import Landing from "./components/Layout/Landing";
import About from "./components/Layout/About";
import Contact from "./components/Layout/Contact";
import Register from "./components/Login/Register";
import Login from "./components/Login/Login";
import AdminHome from "./components/Admin/Home";
import AdminManageUsers from "./components/Admin/ManageUsers";
import AdminAddUser from "./components/Admin/AddUser";
import AdminViewUser from "./components/Admin/ViewUser";
import AdminEditUser from "./components/Admin/EditUser";
import AdminManageBooks from "./components/Admin/ManageBooks";
import AdminAddBook from "./components/Admin/AddBook";
import AdminViewBook from "./components/Admin/ViewBook";
import AdminEditBook from "./components/Admin/EditBook";
import AdminManagePayments from "./components/Admin/ManagePayments";
import AdminViewPaymentOrders from "./components/Admin/ViewPaymentOrders";
import AdminManageReports from "./components/Admin/ManageReports";
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
            <Route exact path="/book-sellbook/:id" component={BookSellBook} />
            <Route
              exact
              path="/book-currentorder/:id"
              component={BookCurrentOrder}
            />
            <Route
              exact
              path="/book-pastpayments/:id"
              component={BookPastPayments}
            />
            <Route
              exact
              path="/book-pastpaymentorders/:id"
              component={BookPastPaymentOrders}
            />
            <Route exact path="/book-reviews/:id" component={BookReviews} />

            {/* Admin exxclusive Pages */}
            <Route exact path="/admin/home/:id" component={AdminHome} />
            <Route
              exact
              path="/admin/manageusers/:id"
              component={AdminManageUsers}
            />
            <Route exact path="/admin/adduser/:id" component={AdminAddUser} />
            <Route exact path="/admin/viewuser/:id" component={AdminViewUser} />
            <Route exact path="/admin/edituser/:id" component={AdminEditUser} />
            <Route
              exact
              path="/admin/managebooks/:id"
              component={AdminManageBooks}
            />
            <Route exact path="/admin/addbook/:id" component={AdminAddBook} />
            <Route exact path="/admin/viewbook/:id" component={AdminViewBook} />
            <Route exact path="/admin/editbook/:id" component={AdminEditBook} />
            <Route
              exact
              path="/admin/managepayments/:id"
              component={AdminManagePayments}
            />
            <Route
              exact
              path="/admin/viewpaymentorders/:id"
              component={AdminViewPaymentOrders}
            />
            <Route
              exact
              path="/admin/managereports/:id"
              component={AdminManageReports}
            />

            <Route path="/customer/home/:id" component={HomeCustomer} />
            <Route exact path="/publisher/home/:id" component={HomePublisher} />
            <Route
              exact
              path="/shop-owner/home/:id"
              component={HomeShopOwner}
            />
            <Route path="/paymentdetails/:id" component={PaymentDetails} />
            <Route path="/paymentconfirm/:id" component={PaymentConfirm} />
            <Route
              path="/:id/paymentresult/success"
              component={PaymentResult}
            />

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
