package codesgesture.app.clinaxdoctor;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import codesgesture.app.clinaxdoctor.Adapter.FeedbackAdapter;
import codesgesture.app.clinaxdoctor.Models.DoctorModel;
import codesgesture.app.clinaxdoctor.Models.FeedbackModel;
import codesgesture.app.clinaxdoctor.Services.CallJson;
import codesgesture.app.clinaxdoctor.Services.JsonCallbacks;
import codesgesture.app.clinaxdoctor.Services.NetParam;
import codesgesture.app.clinaxdoctor.Utils.SessionManage;

public class PatientReview extends AppCompatActivity {
    String s;
    DoctorModel doctorModel;
    FeedbackAdapter feedbackAdapter;
    ArrayList<FeedbackModel> feedbackModels=new ArrayList<>();
    RecyclerView recyclerview;
    LinearLayout norecord;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_review);
        doctorModel=(DoctorModel) SessionManage.getCurrentUser(getApplicationContext());
        s=getString(R.string.con);

        ImageView btback=findViewById(R.id.btback);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView title=findViewById(R.id.title);
        title.setText("Patient Review");

        norecord=findViewById(R.id.norecord);
        recyclerview=findViewById(R.id.recyclerview);


        GridLayoutManager mLayoutManager = new GridLayoutManager(PatientReview.this, 1);
        recyclerview.setLayoutManager(mLayoutManager);
        feedbackAdapter = new FeedbackAdapter(PatientReview.this, feedbackModels, R.layout.item_review);
        recyclerview.setAdapter(feedbackAdapter);
        recyclerview.setItemViewCacheSize(feedbackModels.size());
        GetData();
        
    }

    private void GetData() {
        feedbackModels.clear();
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJson jc = new CallJson(PatientReview.this);
        param.add(new NetParam("doctor_id",doctorModel.getDoctors_id()));
        jc.SendRequest(s,"get_feedback", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                JSONArray array = new JSONArray(json);
                for (int s = 0; s < array.length(); s++) {
                    JSONObject obj = array.getJSONObject(s);
                    FeedbackModel product = new FeedbackModel();
                    product.setReviewer_comments(obj.getString("reviewer_comments"));
                    product.setReviewer_name(obj.getString("reviewer_name"));
                    product.setReviewer_rating(obj.getString("reviewer_rating"));
                    product.setReviewer_date(obj.getString("reviewer_date"));
                    product.setReviewer_status(obj.getString("reviewer_status"));
                    product.setId(obj.getString("id"));
                    feedbackModels.add(product);
                }
                feedbackAdapter.notifyDataSetChanged();

            }

            @Override
            public void onPostError(String msg) {

            }
        }, "", "Loading..");
    }

}
