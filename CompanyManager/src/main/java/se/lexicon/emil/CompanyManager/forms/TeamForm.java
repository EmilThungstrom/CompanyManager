package se.lexicon.emil.CompanyManager.forms;

import java.util.Arrays;
import java.util.Objects;

public class TeamForm {

    public int departmentId;
    public int teamId;
    public int leaderId;
    public int[] employeeIds;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamForm teamForm = (TeamForm) o;
        return departmentId == teamForm.departmentId &&
                teamId == teamForm.teamId &&
                leaderId == teamForm.leaderId &&
                Arrays.equals(employeeIds, teamForm.employeeIds);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(departmentId, teamId, leaderId);
        result = 31 * result + Arrays.hashCode(employeeIds);
        return result;
    }
}
