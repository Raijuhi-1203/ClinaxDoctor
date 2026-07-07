package codesgesture.app.clinaxdoctor;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import codesgesture.app.clinaxdoctor.Adapter.BookingAdapter;
import codesgesture.app.clinaxdoctor.Models.BookingModel;
import codesgesture.app.clinaxdoctor.Models.DoctorModel;
import codesgesture.app.clinaxdoctor.Services.CallJson;
import codesgesture.app.clinaxdoctor.Services.JsonCallbacks;
import codesgesture.app.clinaxdoctor.Services.NetParam;
import codesgesture.app.clinaxdoctor.Utils.SessionManage;

public class PageMyAppoinment extends AppCompatActivity {

    ArrayList<BookingModel> bookingModels=new ArrayList<>();
    BookingAdapter bookingAdapter;
    RecyclerView recyclerview;
    LinearLayout norecord;
    String s;
    DoctorModel doctorModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_appoinment);
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
        title.setText("My Appoinment");

        norecord=findViewById(R.id.norecord);
        recyclerview=findViewById(R.id.recyclerview);

        GridLayoutManager mLayoutManager = new GridLayoutManager(PageMyAppoinment.this, 1);
        recyclerview.setLayoutManager(mLayoutManager);
        bookingAdapter = new BookingAdapter(PageMyAppoinment.this, bookingModels, R.layout.item_appoinment);
        recyclerview.setAdapter(bookingAdapter);
        recyclerview.setItemViewCacheSize(bookingModels.size());
        GetData();

    }

    private void GetData() {
        bookingModels.clear();
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJson jc = new CallJson(PageMyAppoinment.this);
        param.add(new NetParam("doctor_id",doctorModel.getDoctors_id()));
        jc.SendRequest(s,"get_doctor_appoinment", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                JSONArray array = new JSONArray(json);
                for (int s = 0; s < array.length(); s++) {
                    JSONObject obj = array.getJSONObject(s);
                    BookingModel product = new BookingModel();
                    product.setAuto_id(obj.getString("auto_id"));
                    product.setId(obj.getString("id"));
                    product.setDoctors_clinic_name(obj.getString("doctors_clinic_name"));
                    product.setDoctors_address(obj.getString("doctors_address"));
                    product.setDoctors_state_name(obj.getString("doctors_state_name"));
                    product.setDoctors_city_name(obj.getString("doctors_city_name"));
                    product.setAccess_token_no(obj.getString("access_token_no"));
                    product.setDoctor_id(obj.getString("doctor_id"));
                    product.setDoctor_name(obj.getString("doctor_name"));
                    product.setBooking_date(obj.getString("booking_date"));
                    product.setBooking_type(obj.getString("booking_type"));
                    product.setBooking_time(obj.getString("booking_time"));
                    product.setTotal_booking_amount(obj.getString("total_booking_amount"));
                    product.setPatient_id(obj.getString("patient_id"));
                    product.setPatient_name(obj.getString("patient_name"));
                    product.setPayment_status(obj.getString("payment_status"));
                    product.setSchedule_date(obj.getString("schedule_date"));
                    product.setSchedule_time(obj.getString("schedule_time"));
                    product.setBooking_id(obj.getString("booking_id"));
                    product.setBooking_status(obj.getString("booking_status"));

                    bookingModels.add(product);
                }
                bookingAdapter.notifyDataSetChanged();
                BindDataView();
            }

            @Override
            public void onPostError(String msg) {
                BindDataView();
            }
        }, "", "Loading..");
    }

    private void BindDataView() {
        if (bookingModels.size()==0){
            norecord.setVisibility(View.VISIBLE);
            recyclerview.setVisibility(View.GONE);
        }else {
            norecord.setVisibility(View.GONE);
            recyclerview.setVisibility(View.VISIBLE);
        }
    }

}
