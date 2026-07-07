package codesgesture.app.clinaxdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import codesgesture.app.clinaxdoctor.Models.DoctorModel;
import codesgesture.app.clinaxdoctor.Models.UserModel;
import codesgesture.app.clinaxdoctor.Services.CallJson;
import codesgesture.app.clinaxdoctor.Services.JsonCallbacks;
import codesgesture.app.clinaxdoctor.Services.NetParam;
import codesgesture.app.clinaxdoctor.Services.UserUtil;
import codesgesture.app.clinaxdoctor.Services.Utility;
import codesgesture.app.clinaxdoctor.Utils.SessionManage;


public class LoginPage extends AppCompatActivity implements JsonCallbacks {
    Button btsave;
    EditText pass,mobileno;
    ArrayList<NetParam> params;
    DoctorModel userModel;
    String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        s=getString(R.string.con);
        CheckLogins();

        mobileno=findViewById(R.id.mobileno);
        pass=findViewById(R.id.pass);
        btsave=findViewById(R.id.btsave);

        btsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validte()) {
                    params = new ArrayList<NetParam>();
                    CallJson jc = new CallJson(LoginPage.this);
                    params.add(new NetParam("mobileno", mobileno.getText().toString()));
                    params.add(new NetParam("password", pass.getText().toString()));
                    jc.SendRequest(s,"doctor_login", params, LoginPage.this, "patient_login", "Please wait while verifying..");
                }
            }
        });


    }

    @Override
    public void onPostSuceess(String json, String method) throws JSONException {
        DoctorModel product = new DoctorModel();
        try {
            JSONObject obj = UserUtil.ConvertStringToJsonObject(json);
            if (obj.length() != 1) {
                product.setAuto_id(obj.getString("auto_id"));
                product.setId(obj.getString("id"));
                product.setDoctors_id(obj.getString("doctors_id"));
                product.setDoctors_name(obj.getString("doctors_name"));
                product.setDoctors_reg_no(obj.getString("doctors_reg_no"));
                product.setDoctors_clinic_name(obj.getString("doctors_clinic_name"));
                product.setDoctors_mobile_no(obj.getString("doctors_mobile_no"));
                product.setDoctor_password(obj.getString("doctor_password"));
                product.setDepartment_id(obj.getString("department_id"));
                product.setSub_department_name(obj.getString("sub_department_name"));
                product.setDepartment_name(obj.getString("department_name"));
                product.setSymptoms_check(obj.getString("symptoms_check"));
                product.setDoctor_description(obj.getString("doctor_description"));
                product.setDoctors_speciality(obj.getString("doctors_speciality"));
                product.setDoctors_experience(obj.getString("doctors_experience"));
                product.setDoctors_qualification(obj.getString("doctors_qualification"));
                product.setOther_qualification_certification(obj.getString("other_qualification_certification"));
                product.setMedical_council(obj.getString("medical_council"));
                product.setDoctors_photo(obj.getString("doctors_photo"));
                product.setDoctors_by(obj.getString("doctors_by"));
                product.setDoctors_fee(obj.getString("doctors_fee"));
                product.setDoctors_address(obj.getString("doctors_address"));
                product.setDoctors_state_id(obj.getString("doctors_state_id"));
                product.setDoctors_state_name(obj.getString("doctors_state_name"));
                product.setDoctors_city_id(obj.getString("doctors_city_id"));
                product.setDoctors_city_name(obj.getString("doctors_city_name"));
                product.setDoctors_join_date(obj.getString("doctors_join_date"));
                product.setDoctors_modify_date(obj.getString("doctors_modify_date"));
                product.setDoctors_status(obj.getString("doctors_status"));
                product.setAccess_token_no(obj.getString("access_token_no"));
//                product.setStart_time(obj.getString("start_time"));
//                product.setEnd_time(obj.getString("end_time"));
//                product.setAval(obj.getString("aval"));
//                product.setFeedback(obj.getString("feedback"));
                SessionManage.SetCustomerSessions(getApplicationContext(), product);
                Intent act = new Intent(LoginPage.this, Dashboard.class);
                startActivity(act);
                UserUtil.ShowMsg("Login Successfully !", LoginPage.this);
                finish();
            } else {
                Utility.ShowMEssage(LoginPage.this, "Invalid details !");
            }
        } catch (JSONException e) {
            Utility.ShowMEssage(LoginPage.this, "Invalid details !");
            e.printStackTrace();
        }
    }

    @Override
    public void onPostError(String msg) {

    }

    private boolean validte() {
        Boolean valid = true;
        if (mobileno.getText().length()==0){
            mobileno.setError("Please enter mobile no");
            valid=false;
        }
        else  if (pass.getText().length()==0){
            pass.setError("Please enter password");
            valid=false;
        }
        return valid;
    }

    private void CheckLogins() {
        userModel = SessionManage.getCurrentUser(this);
        if (userModel != null) {
            if (userModel.getDoctors_id() != null) {
                Intent act = new Intent(LoginPage.this, Dashboard.class);
                startActivity(act);
                finish();
            }
        }
    }

}