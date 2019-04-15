import React, { Component } from "react";
import { NavbarTop } from "./Components/Headers/NavbarTop";
import { BrowserRouter as Router, Route } from "react-router-dom";
import Departments from "./Components/Department/Departments";
import Employee from "./Components/Employee/Employee";
import Team from "./Components/Team/Team";

import "bootstrap/dist/css/bootstrap.css";

class App extends Component {
  render() {
    return (
      <Router>
        <div className="App">
          <NavbarTop />
          <Route path="/department" component={Departments} />
          <Route path="/employee" component={Employee} />
          <Route path="/team" component={Team} />
        </div>
      </Router>
    );
  }
}

export default App;
