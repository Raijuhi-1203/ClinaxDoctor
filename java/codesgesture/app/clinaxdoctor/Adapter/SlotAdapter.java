package codesgesture.app.clinaxdoctor.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import codesgesture.app.clinaxdoctor.Models.SlotModel;
import codesgesture.app.clinaxdoctor.R;
import codesgesture.app.clinaxdoctor.Services.DateFormate;
import codesgesture.app.clinaxdoctor.Utils.SessionManage;
import codesgesture.app.clinaxdoctor.interfaces.ExtraCallBack;


public class SlotAdapter extends RecyclerView.Adapter<SlotAdapter.ViewHolder> {
    private ArrayList<SlotModel> arrayList;
    private Context context;
    String Userid="";
    private int layout;
    String s,sm;
    int index=-1;
    public ExtraCallBack ecb;

    public SlotAdapter(Context context, ArrayList<SlotModel> arrayList, int layout) {
        this.arrayList = arrayList;
        this.context = context;
        this.layout=layout;
        s=context.getString(R.string.con);
        sm=context.getString(R.string.maincon);
        this.Userid = SessionManage.getCurrentUser(context.getApplicationContext()).getDoctors_id();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int i) {
        final SlotModel data = arrayList.get(i);

        holder.time.setText(DateFormate.getTimeformte(data.getInterval()));
        holder.txnopatient.setText(data.getNo_patient());

        if (data.getAvailable().equals("Yes")){
            holder.available.setChecked(true);
        }else {
            holder.available.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        CheckBox available;
        EditText txnopatient;
        ViewHolder(View view) {
            super(view);
            time = view.findViewById(R.id.time);
            available = view.findViewById(R.id.available);
            txnopatient = view.findViewById(R.id.txnopatient);
        }
    }

    public void updateList(ArrayList<SlotModel> list) {
        arrayList = list;
        notifyDataSetChanged();
    }
}