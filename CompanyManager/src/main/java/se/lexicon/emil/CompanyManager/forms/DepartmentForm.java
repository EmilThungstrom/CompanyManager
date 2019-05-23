package se.lexicon.emil.CompanyManager.forms;

import java.util.Objects;

public class DepartmentForm {

    public String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentForm that = (DepartmentForm) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
