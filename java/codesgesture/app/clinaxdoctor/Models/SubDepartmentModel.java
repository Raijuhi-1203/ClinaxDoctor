package codesgesture.app.clinaxdoctor.Models;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class SubDepartmentModel implements Serializable {
    private String id;
    private String department_id;
    private String sub_department_id;
    private String sub_department_name;
    private String sub_department_status;


    // Getter Methods

    public String getId() {
        return id;
    }

    public String getDepartment_id() {
        return department_id;
    }

    public String getSub_department_id() {
        return sub_department_id;
    }

    public String getSub_department_name() {
        return sub_department_name;
    }

    public String getSub_department_status() {
        return sub_department_status;
    }

    // Setter Methods

    public void setId(String id) {
        this.id = id;
    }

    public void setDepartment_id(String department_id) {
        this.department_id = department_id;
    }

    public void setSub_department_id(String sub_department_id) {
        this.sub_department_id = sub_department_id;
    }

    public void setSub_department_name(String sub_department_name) {
        this.sub_department_name = sub_department_name;
    }

    public void setSub_department_status(String sub_department_status) {
        this.sub_department_status = sub_department_status;
    }

    @NonNull
    @Override
    public String toString() {
        return sub_department_name;
    }
}
