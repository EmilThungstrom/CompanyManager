package se.lexicon.emil.CompanyManager.forms;

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

    private String formatString(String s){
        s = s.trim().toLowerCase();
        return s.replaceFirst(s.substring(0,1), s.substring(0,1).toUpperCase());
    }
}
