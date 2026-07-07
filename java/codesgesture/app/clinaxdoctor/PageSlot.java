package codesgesture.app.clinaxdoctor;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import codesgesture.app.clinaxdoctor.Adapter.BookingAdapter;
import codesgesture.app.clinaxdoctor.Adapter.SlotAdapter;
import codesgesture.app.clinaxdoctor.Models.BookingModel;
import codesgesture.app.clinaxdoctor.Models.DoctorModel;
import codesgesture.app.clinaxdoctor.Models.SlotModel;
import codesgesture.app.clinaxdoctor.Services.CallJson;
import codesgesture.app.clinaxdoctor.Services.JsonCallbacks;
import codesgesture.app.clinaxdoctor.Services.NetParam;
import codesgesture.app.clinaxdoctor.Utils.SessionManage;

public class PageSlot extends AppCompatActivity {
    RecyclerView recyclerview;
    EditText txnopatient;
    Spinner spnroff,spnrmode,spnrdays;
    String s;
    DoctorModel doctorModel;
    ArrayList<SlotModel> slotModels=new ArrayList<>();
    SlotAdapter slotAdapter;
    TextView btsubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slot_schedule);
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
        title.setText("Add Slot-schedule");

        txnopatient=findViewById(R.id.txnopatient);
        recyclerview=findViewById(R.id.recyclerview);
        spnroff=findViewById(R.id.spnroff);
        spnrmode=findViewById(R.id.spnrmode);
        spnrdays=findViewById(R.id.spnrdays);
        btsubmit=findViewById(R.id.btsubmit);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.week_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnroff.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.mode_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrmode.setAdapter(adapter2);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.days_array, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrdays.setAdapter(adapter3);


        GridLayoutManager mLayoutManager = new GridLayoutManager(PageSlot.this, 1);
        recyclerview.setLayoutManager(mLayoutManager);
        slotAdapter = new SlotAdapter(PageSlot.this, slotModels, R.layout.item_slot);
        recyclerview.setAdapter(slotAdapter);
        recyclerview.setItemViewCacheSize(slotModels.size());
        GetData();

        btsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        
    }

    private void GetData() {
        slotModels.clear();
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJson jc = new CallJson(PageSlot.this);
        jc.SendRequest(s,"slot_shcedule", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                JSONArray array = new JSONArray(json);
                for (int s = 0; s < array.length(); s++) {
                    JSONObject obj = array.getJSONObject(s);
                    SlotModel product = new SlotModel();
                    product.setAvailable(obj.getString("available"));
                    product.setInterval(obj.getString("interval"));
                    product.setNo_patient(obj.getString("no_patient"));
                    slotModels.add(product);
                }
                slotAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPostError(String msg) {
            }
        }, "", "Loading..");
    }

    
}
