package se.lexicon.emil.CompanyManager.forms;

import java.util.Arrays;
import java.util.Objects;

public class DepartmentEmployeeForm {

    public int departmentId;
    public int[] employeeIds;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentEmployeeForm that = (DepartmentEmployeeForm) o;
        return departmentId == that.departmentId &&
                Arrays.equals(employeeIds, that.employeeIds);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(departmentId);
        result = 31 * result + Arrays.hashCode(employeeIds);
        return result;
    }
}
