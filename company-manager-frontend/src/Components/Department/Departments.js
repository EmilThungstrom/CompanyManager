import React, { Component } from "react";
import Department from "./Items/Department";
import axios from "axios";

export class Departments extends Component {
  state = {
    departments: []
  };

  componentDidMount() {
    axios
      .get("http://localhost:8080/api/department")
      .then(res => {
        this.setState({ departments: res.data });
        console.log(res.data);
      })
      .catch(err => {
        console.error(err);
      });
  }

  render() {
    const departments = this.state.departments.map(department => {
      return <Department key={department.id} department={department} />;
    });

    return <div>{departments}</div>;
  }
}

export default Departments;
