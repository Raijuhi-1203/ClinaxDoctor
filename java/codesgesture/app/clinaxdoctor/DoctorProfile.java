package codesgesture.app.clinaxdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import codesgesture.app.clinaxdoctor.Adapter.ProblemAdapter;
import codesgesture.app.clinaxdoctor.Adapter.SpecialityAdapter;
import codesgesture.app.clinaxdoctor.Models.CityModel;
import codesgesture.app.clinaxdoctor.Models.DepartmentModel;
import codesgesture.app.clinaxdoctor.Models.DoctorModel;
import codesgesture.app.clinaxdoctor.Models.HProblemModel;
import codesgesture.app.clinaxdoctor.Models.SpecialityModel;
import codesgesture.app.clinaxdoctor.Models.StateModel;
import codesgesture.app.clinaxdoctor.Models.SubDepartmentModel;
import codesgesture.app.clinaxdoctor.Services.CallJson;
import codesgesture.app.clinaxdoctor.Services.CallJsonWithoutProgress;
import codesgesture.app.clinaxdoctor.Services.JsonCallbacks;
import codesgesture.app.clinaxdoctor.Services.NetParam;
import codesgesture.app.clinaxdoctor.Services.UserUtil;
import codesgesture.app.clinaxdoctor.Services.Utility;
import codesgesture.app.clinaxdoctor.Utils.SessionManage;
import codesgesture.app.clinaxdoctor.interfaces.Notify;

public class DoctorProfile extends AppCompatActivity {
    String s,stateid,cityid,citynm,statenm,deptid,deptnm,sdeptid,sdeptnm;
    DoctorModel doctorModel;
    EditText txnm,txcname,txmob,txexp,txadd,txqualification,txoqualification,txregno,txcouncil,txdescp,txfee;
    Spinner spnrstate,spnrcity,spnrdept,spnrsdept;
    Button btn_submit,btn_submit2,btn_submit3;
    RecyclerView rvspeciality,rvsymptoms;

    ArrayList<HProblemModel> hProblemModels=new ArrayList<>();
    ArrayList<SpecialityModel> specialityModels=new ArrayList<>();
    SpecialityAdapter specialityAdapter;
    ProblemAdapter problemAdapter;
    ArrayList<StateModel> stateModels=new ArrayList<>();
    ArrayList<CityModel> cityModels=new ArrayList<>();
    ArrayList<DepartmentModel> departmentModels=new ArrayList<>();
    ArrayList<SubDepartmentModel> subDepartmentModels=new ArrayList<>();
    ArrayAdapter<StateModel> stateModelArrayAdapter;
    ArrayAdapter<CityModel> cityModelArrayAdapter;
    ArrayAdapter<DepartmentModel> departmentModelArrayAdapter;
    ArrayAdapter<SubDepartmentModel> subDepartmentModelArrayAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_profile);
        doctorModel=(DoctorModel) SessionManage.getCurrentUser(getApplicationContext());
        s=getString(R.string.con);

        ImageView btback=findViewById(R.id.btback);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView title= findViewById(R.id.title);
        title.setText("My Profile");

        BindId();

        GridLayoutManager mLayoutManagers = new GridLayoutManager(DoctorProfile.this, 2);
        rvspeciality.setLayoutManager(mLayoutManagers);
        specialityAdapter = new SpecialityAdapter(DoctorProfile.this, specialityModels, R.layout.item_problem);
        rvspeciality.setAdapter(specialityAdapter);
        rvspeciality.setItemViewCacheSize(specialityModels.size());
        GetSpecialityData();

        GridLayoutManager mLayoutManager = new GridLayoutManager(DoctorProfile.this, 2);
        rvsymptoms.setLayoutManager(mLayoutManager);
        problemAdapter = new ProblemAdapter(DoctorProfile.this, hProblemModels, R.layout.item_problem);
        rvsymptoms.setAdapter(problemAdapter);
        rvsymptoms.setItemViewCacheSize(hProblemModels.size());
        GetSympotmsData();

        stateModels = new ArrayList<>();
        stateModelArrayAdapter = new ArrayAdapter<StateModel>(DoctorProfile.this, android.R.layout.simple_spinner_item, stateModels);
        stateModelArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrstate.setAdapter(stateModelArrayAdapter);

        cityModels = new ArrayList<>();
        cityModelArrayAdapter = new ArrayAdapter<CityModel>(DoctorProfile.this, android.R.layout.simple_spinner_item, cityModels);
        cityModelArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrcity.setAdapter(cityModelArrayAdapter);

        departmentModels = new ArrayList<>();
        departmentModelArrayAdapter = new ArrayAdapter<DepartmentModel>(DoctorProfile.this, android.R.layout.simple_spinner_item, departmentModels);
        departmentModelArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrdept.setAdapter(departmentModelArrayAdapter);

        subDepartmentModels = new ArrayList<>();
        subDepartmentModelArrayAdapter = new ArrayAdapter<SubDepartmentModel>(DoctorProfile.this, android.R.layout.simple_spinner_item, subDepartmentModels);
        subDepartmentModelArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrsdept.setAdapter(subDepartmentModelArrayAdapter);

        spnrstate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = spnrstate.getSelectedItemPosition();
                stateid=String.valueOf(stateModels.get(pos).getState_id());
                statenm=String.valueOf(stateModels.get(pos).getState_name());
                CityCall(stateid);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spnrcity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = spnrcity.getSelectedItemPosition();
                cityid=String.valueOf(cityModels.get(pos).getDistrict_id());
                citynm=String.valueOf(cityModels.get(pos).getDistrict_name());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        AreaJsonCall();

        spnrdept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = spnrdept.getSelectedItemPosition();
                deptid=String.valueOf(departmentModels.get(pos).getDepartment_id());
                deptnm=String.valueOf(departmentModels.get(pos).getDepartment_name());
                SubDeptCall(stateid);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spnrsdept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = spnrsdept.getSelectedItemPosition();
                sdeptid=String.valueOf(subDepartmentModels.get(pos).getSub_department_id());
                sdeptnm=String.valueOf(subDepartmentModels.get(pos).getSub_department_name());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        DeptCall();


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateProfile();
            }
        });
        btn_submit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 UpdateSpecility();
            }
        });
        btn_submit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateSympotms();
            }
        });
    }
    private void UpdateSympotms() {
        String symptomps= new Gson().toJson(hProblemModels);
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJson jc = new CallJson(DoctorProfile.this);
        param.add(new NetParam("symptomps",symptomps));
        param.add(new NetParam("doctor_id",doctorModel.getDoctors_id()));
        jc.SendRequest(s,"update_doctor_symtomps", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                UserUtil.ShowMsg("Details Updated !!",DoctorProfile.this);
                finish();
            }
            @Override
            public void onPostError(String msg) {

            }
        }, " ", "Loading..");
    }
    private void UpdateSpecility() {
        String speciality= new Gson().toJson(specialityModels);
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJson jc = new CallJson(DoctorProfile.this);
        param.add(new NetParam("speciality",speciality));
        param.add(new NetParam("doctor_id",doctorModel.getDoctors_id()));
        jc.SendRequest(s,"update_doctor_speciality", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                UserUtil.ShowMsg("Details Updated !!",DoctorProfile.this);
                finish();
            }
            @Override
            public void onPostError(String msg) {

            }
        }, " ", "Loading..");
    }
    private void UpdateProfile() {
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJson jc = new CallJson(DoctorProfile.this);
        param.add(new NetParam("doctors_name",txnm.getText().toString()));
        param.add(new NetParam("doctors_reg_no",txregno.getText().toString()));
        param.add(new NetParam("doctors_clinic_name",txcname.getText().toString()));
        param.add(new NetParam("doctors_mobile_no",txmob.getText().toString()));
        param.add(new NetParam("doctors_state_id",stateid));
        param.add(new NetParam("doctors_state_name",statenm));
        param.add(new NetParam("doctors_city_id",cityid));
        param.add(new NetParam("doctors_city_name",citynm));
        param.add(new NetParam("department_id",deptid));
        param.add(new NetParam("department_name",deptnm));
        param.add(new NetParam("sub_department_id",sdeptid));
        param.add(new NetParam("sub_department_name",sdeptnm));
        param.add(new NetParam("doctor_description",txdescp.getText().toString()));
        param.add(new NetParam("doctors_experience",txexp.getText().toString()));
        param.add(new NetParam("doctors_qualification",txqualification.getText().toString()));
        param.add(new NetParam("other_qualification_certification",txoqualification.getText().toString()));
        param.add(new NetParam("medical_council",txcouncil.getText().toString()));
        param.add(new NetParam("doctors_fee",txfee.getText().toString()));
        param.add(new NetParam("doctors_address",txadd.getText().toString()));
        param.add(new NetParam("doctor_id",doctorModel.getDoctors_id()));
        jc.SendRequest(s,"update_doctor_profile", param, new JsonCallbacks() {
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
                        SessionManage.SetCustomerSessions(getApplicationContext(), product);
                        Intent act = new Intent(DoctorProfile.this, Dashboard.class);
                        startActivity(act);
                        UserUtil.ShowMsg("Details Updated !!",DoctorProfile.this);
                        finish();
                    } else {
                        Utility.ShowMEssage(DoctorProfile.this, "Invalid details !");
                    }
                } catch (JSONException e) {
                    Utility.ShowMEssage(DoctorProfile.this, "Invalid details !");
                    e.printStackTrace();
                }
            }
            @Override
            public void onPostError(String msg) {

            }
        }, " ", "Loading..");
    }
    private void SubDeptCall(String stateid) {
        subDepartmentModels.clear();
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJsonWithoutProgress jc = new CallJsonWithoutProgress(DoctorProfile.this);
        param.add(new NetParam("department_id",stateid));
        jc.SendRequest(s,"get_sub_department", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                JSONArray array = new JSONArray(json);
                for (int s = 0; s < array.length(); s++) {
                    JSONObject obj = array.getJSONObject(s);
                    SubDepartmentModel mod = new SubDepartmentModel();
                    mod.setSub_department_id(obj.getString("sub_department_id"));
                    mod.setSub_department_name(obj.getString("sub_department_name"));
                    mod.setId(obj.getString("id"));
                    subDepartmentModels.add(mod);
                    subDepartmentModelArrayAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onPostError(String msg) {
            }
        }, "", "Please wait while getting..");
    }
    private void DeptCall() {
        departmentModels.clear();
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJson jc = new CallJson(DoctorProfile.this);
        jc.SendRequest(s,"get_department", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                JSONArray array = new JSONArray(json);
                for (int s = 0; s < array.length(); s++) {
                    JSONObject obj = array.getJSONObject(s);
                    DepartmentModel mod = new DepartmentModel();
                    mod.setDepartment_id(obj.getString("department_id"));
                    mod.setDepartment_name(obj.getString("department_name"));
                    mod.setId(obj.getString("id"));
                    departmentModels.add(mod);
                    departmentModelArrayAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onPostError(String msg) {
            }
        }, "LOGIN", "Please wait while getting..");
    }
    private void CityCall(String spnrstid) {
        cityModels.clear();
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJsonWithoutProgress jc = new CallJsonWithoutProgress(DoctorProfile.this);
        param.add(new NetParam("state_id",spnrstid));
        jc.SendRequest(s,"get_city", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                JSONArray array = new JSONArray(json);
                for (int s = 0; s < array.length(); s++) {
                    JSONObject obj = array.getJSONObject(s);
                    CityModel mod = new CityModel();
                    mod.setDistrict_id(obj.getString("district_id"));
                    mod.setDistrict_name(obj.getString("district_name"));
                    cityModels.add(mod);
                    cityModelArrayAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onPostError(String msg) {
            }
        }, "", "Please wait while getting..");
    }
    private void AreaJsonCall() {
        stateModels.clear();
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJson jc = new CallJson(DoctorProfile.this);
        jc.SendRequest(s,"get_state", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                JSONArray array = new JSONArray(json);
                for (int s = 0; s < array.length(); s++) {
                    JSONObject obj = array.getJSONObject(s);
                    StateModel mod = new StateModel();
                    mod.setState_id(obj.getString("state_id"));
                    mod.setState_name(obj.getString("state_name"));
                    mod.setId(obj.getString("id"));
                    stateModels.add(mod);
                    stateModelArrayAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onPostError(String msg) {
            }
        }, "LOGIN", "Please wait while getting..");
    }
    private void GetSympotmsData() {
        hProblemModels.clear();
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJsonWithoutProgress jc = new CallJsonWithoutProgress(DoctorProfile.this);
        param.add(new NetParam("doctor_id",doctorModel.getDoctors_id()));
        jc.SendRequest(s,"get_symptoms_doctors", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                JSONArray array = new JSONArray(json);
                for (int s = 0; s < array.length(); s++) {
                    JSONObject obj = array.getJSONObject(s);
                    HProblemModel product = new HProblemModel();
                    product.setStatus(obj.getString("status"));
                    product.setId(obj.getString("id"));
                    product.setSymptoms_icon(obj.getString("symptoms_icon"));
                    product.setSymptoms_name(obj.getString("symptoms_name"));
                    hProblemModels.add(product);
                }
                problemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPostError(String msg) {
            }
        }, "", "Loading..");
    }
    private void GetSpecialityData() {
        specialityModels.clear();
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJsonWithoutProgress jc = new CallJsonWithoutProgress(DoctorProfile.this);
        param.add(new NetParam("doctor_id",doctorModel.getDoctors_id()));
        jc.SendRequest(s,"get_speciality_doctors", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                JSONArray array = new JSONArray(json);
                for (int s = 0; s < array.length(); s++) {
                    JSONObject obj = array.getJSONObject(s);
                    SpecialityModel product = new SpecialityModel();
                    product.setStatus(obj.getString("status"));
                    product.setSpeciality_icon(obj.getString("speciality_icon"));
                    product.setSpeciality_name(obj.getString("speciality_name"));
                    product.setSpeciality_status(obj.getString("speciality_status"));
                    product.setId(obj.getString("id"));
                    specialityModels.add(product);
                }
                specialityAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPostError(String msg) {
            }
        }, "", "Loading..");
    }
    private void BindId() {
        rvsymptoms=findViewById(R.id.rvsymptoms);rvspeciality=findViewById(R.id.rvspeciality);

        btn_submit=findViewById(R.id.btn_submit);btn_submit2=findViewById(R.id.btn_submit2);
        btn_submit3=findViewById(R.id.btn_submit3);

        spnrstate=findViewById(R.id.spnrstate);spnrcity=findViewById(R.id.spnrcity);
        spnrdept=findViewById(R.id.spnrdept);spnrsdept=findViewById(R.id.spnrsdept);

        txnm=findViewById(R.id.txnm);txcname=findViewById(R.id.txcname);
        txmob=findViewById(R.id.txmob);txexp=findViewById(R.id.txexp);
        txadd=findViewById(R.id.txadd);txqualification=findViewById(R.id.txqualification);
        txoqualification=findViewById(R.id.txoqualification);txregno=findViewById(R.id.txregno);
        txcouncil=findViewById(R.id.txcouncil);txdescp=findViewById(R.id.txdescp);
        txfee=findViewById(R.id.txfee);

        txnm.setText(doctorModel.getDoctors_name());
        txcname.setText(doctorModel.getDoctors_clinic_name());
        txmob.setText(doctorModel.getDoctors_mobile_no());
        txadd.setText(doctorModel.getDoctors_address());
        txqualification.setText(doctorModel.getDoctors_qualification());
        txoqualification.setText(doctorModel.getOther_qualification_certification());
        txcouncil.setText(doctorModel.getMedical_council());
        txdescp.setText(doctorModel.getDoctor_description());
        txfee.setText(doctorModel.getDoctors_fee());


    }

}
