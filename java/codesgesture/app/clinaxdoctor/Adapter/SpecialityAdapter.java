package codesgesture.app.clinaxdoctor.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

import codesgesture.app.clinaxdoctor.Models.SpecialityModel;
import codesgesture.app.clinaxdoctor.R;
import codesgesture.app.clinaxdoctor.Utils.SessionManage;


public class SpecialityAdapter extends RecyclerView.Adapter<SpecialityAdapter.ViewHolder> {
    private ArrayList<SpecialityModel> arrayList;
    private Context context;
    String Userid="";
    private int layout;
    String s,sm;

    public SpecialityAdapter(Context context, ArrayList<SpecialityModel> arrayList, int layout) {
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
        final SpecialityModel data = arrayList.get(i);

        holder.problems.setText(data.getSpeciality_name());

        holder.problems.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    data.setStatus("true");
                }else {
                    data.setStatus("");
                }
            }
        });

        if (data.getStatus().equals("true")){
            holder.problems.setChecked(true);
        }else {
            holder.problems.setChecked(false);
        }


    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox problems;

        ViewHolder(View view) {
            super(view);
            problems = view.findViewById(R.id.problems);
        }
    }

    public void updateList(ArrayList<SpecialityModel> list) {
        arrayList = list;
        notifyDataSetChanged();
    }
}