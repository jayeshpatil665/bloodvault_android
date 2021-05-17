package in.specialsoft.bloodvault.DonorDetails;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.specialsoft.bloodvault.R;

public class DonorAdapter extends RecyclerView.Adapter<DonorAdapter.ViewHolder> {

    //step3
    public List<Donor> donorList;
    public Context context;

    public DonorAdapter(List<Donor> donorList, Context context) {
        this.donorList = donorList;
        this.context = context;
    }

    @NonNull
    @Override
    public DonorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //step1
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.itemviewdonor,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonorAdapter.ViewHolder holder, int position) {

        holder.tv_mobile.setText("Mobile : "+donorList.get(position).getPhone());
        holder.tv_name.setText("Name : "+donorList.get(position).getName());
        holder.tv_Address.setText("Address : "+ donorList.get(position).getAddress());
        holder.tv_bloodgroup.setText(""+donorList.get(position).getBloodGroup());

        holder.rlitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "tel:"+donorList.get(position).getPhone();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(uri));
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        //step4
        return donorList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //step2-findView
        TextView tv_name, tv_Address,tv_mobile,tv_bloodgroup;
        RelativeLayout rlitem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_Address = itemView.findViewById(R.id.tv_Address);
            tv_mobile = itemView.findViewById(R.id.tv_mobile);
            tv_bloodgroup = itemView.findViewById(R.id.tv_bloodgroup);
            rlitem = itemView.findViewById(R.id.rlitem);
        }
    }

}
