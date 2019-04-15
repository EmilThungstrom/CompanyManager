import React from "react";
import Card from "react-bootstrap/Card";
import "./Department.css";
import Employee from "./Employee";

const Department = props => {
  return (
    <Card>
      <div className="d-flex flex-row row-hl">
        <div className="departmentName p-5 item-hl">
          <h3 className="mb-2">
            <u>{props.department.name}</u>
          </h3>
          <h6 className="text-muted">
            <b>
              Head: {props.department.head.firstName} {props.department.head.lastName}
            </b>
          </h6>
        </div>
        <div className="p-5 item-hl">
          <Employee />
        </div>
        <div className="p-5 item-hl">
          <b>Teams: {props.department.teams.length}</b>
        </div>
      </div>
    </Card>
  );
};

export default Department;
