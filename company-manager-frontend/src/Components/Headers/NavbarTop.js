import React, { Component } from "react";
import { Nav, NavItem, Navbar } from "react-bootstrap";
import { LinkContainer } from "react-router-bootstrap";

export class NavbarTop extends Component {
  render() {
    return (
      <Navbar sticky="top" bg="dark" variant="dark">
        <Navbar.Brand className="mx-5">Company Manager</Navbar.Brand>
        <Nav className="mr-auto">
          <LinkContainer to="/department">
            <Nav.Link>Department</Nav.Link>
          </LinkContainer>
          <LinkContainer to="/employee">
            <Nav.Link>Employee</Nav.Link>
          </LinkContainer>
          <LinkContainer to="/team">
            <Nav.Link>Team</Nav.Link>
          </LinkContainer>
        </Nav>
      </Navbar>
    );
  }
}

export default NavbarTop;
