package codesgesture.app.clinaxdoctor.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import codesgesture.app.clinaxdoctor.Models.BookingModel;
import codesgesture.app.clinaxdoctor.Models.FeedbackModel;
import codesgesture.app.clinaxdoctor.PageMyAppoinment;
import codesgesture.app.clinaxdoctor.R;
import codesgesture.app.clinaxdoctor.Services.CallJson;
import codesgesture.app.clinaxdoctor.Services.JsonCallbacks;
import codesgesture.app.clinaxdoctor.Services.NetParam;
import codesgesture.app.clinaxdoctor.Services.UserUtil;
import codesgesture.app.clinaxdoctor.Utils.SessionManage;
import codesgesture.app.clinaxdoctor.interfaces.ExtraCallBack;


public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.ViewHolder> {
    private ArrayList<FeedbackModel> arrayList;
    private Context context;
    String Userid="";
    private int layout;
    String s,sm;
    int index=-1;
    public ExtraCallBack ecb;

    public FeedbackAdapter(Context context, ArrayList<FeedbackModel> arrayList, int layout) {
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
        final FeedbackModel data = arrayList.get(i);

        holder.txreview.setText(data.getReviewer_comments());
        holder.txnm.setText(data.getReviewer_name());
        holder.txdate.setText(data.getReviewer_date());

        holder.ratingBar.setRating(Float.parseFloat(data.getReviewer_rating()));
        holder.ratingBar.setClickable(false);
        holder.ratingBar.setOnClickListener(null);
        holder.ratingBar.setFocusable(false);

        holder.btdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteReview(data.getId());
            }
        });

        holder.btunpublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UnpublishReview(data.getId());
            }
        });


    }

    private void UnpublishReview(String id) {
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJson jc = new CallJson((Activity) context);
        param.add(new NetParam("doctor_id",Userid));
        param.add(new NetParam("id",id));
        jc.SendRequest(s,"unpublish_review", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                UserUtil.ShowMsg("Review unpublished!",context);
                ((Activity) context).finish();
            }

            @Override
            public void onPostError(String msg) {

            }
        }, "", "Loading..");
    }

    private void DeleteReview(String id) {
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJson jc = new CallJson((Activity) context);
        param.add(new NetParam("doctor_id",Userid));
        param.add(new NetParam("id",id));
        jc.SendRequest(s,"delete_review", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                UserUtil.ShowMsg("Review deleted!",context);
                ((Activity) context).finish();
            }
            @Override
            public void onPostError(String msg) {

            }
        }, "", "Loading..");
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txnm,txreview,txdate;
        RatingBar ratingBar;
        Button btdelete,btunpublish;

        ViewHolder(View view) {
            super(view);
            ratingBar = view.findViewById(R.id.ratingBar);
            txnm = view.findViewById(R.id.txnm);
            txreview = view.findViewById(R.id.txreview);
            txdate = view.findViewById(R.id.txdate);
            btunpublish = view.findViewById(R.id.btunpublish);
            btdelete = view.findViewById(R.id.btdelete);

        }
    }

    public void updateList(ArrayList<FeedbackModel> list) {
        arrayList = list;
        notifyDataSetChanged();
    }

}