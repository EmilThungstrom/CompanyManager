package se.lexicon.emil.CompanyManager.forms;

import java.util.Objects;

public class EmployeeForm {

    private String firstName;
    private String lastName;
    private String address;
    private String email;

    public int employeeId;
    public int departmentId;
    public int teamId;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = formatString(firstName);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = formatString(lastName);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = formatString(address);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.trim().toLowerCase();
    }

    private String formatString(String s) {
        if(s.isEmpty())
            return s;

        s = s.trim().toLowerCase();
        return s.replaceFirst(s.substring(0, 1), s.substring(0, 1).toUpperCase());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeForm that = (EmployeeForm) o;
        return employeeId == that.employeeId &&
                departmentId == that.departmentId &&
                teamId == that.teamId &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(address, that.address) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, address, email, employeeId, departmentId, teamId);
    }
}
