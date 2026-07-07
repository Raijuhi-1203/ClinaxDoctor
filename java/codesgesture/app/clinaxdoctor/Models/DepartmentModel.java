package codesgesture.app.clinaxdoctor.Models;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class DepartmentModel implements Serializable {
    private String id;
    private String department_id;
    private String department_name;
    private String department_cover_photo;
    private String department_status;


    // Getter Methods

    public String getId() {
        return id;
    }

    public String getDepartment_id() {
        return department_id;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public String getDepartment_cover_photo() {
        return department_cover_photo;
    }

    public String getDepartment_status() {
        return department_status;
    }

    // Setter Methods

    public void setId(String id) {
        this.id = id;
    }

    public void setDepartment_id(String department_id) {
        this.department_id = department_id;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public void setDepartment_cover_photo(String department_cover_photo) {
        this.department_cover_photo = department_cover_photo;
    }

    public void setDepartment_status(String department_status) {
        this.department_status = department_status;
    }

    @NonNull
    @Override
    public String toString() {
        return department_name;
    }
}
