package in.specialsoft.bloodvault.BloodGroupCountInOut;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.specialsoft.bloodvault.DonorDetails.DonorAdapter;
import in.specialsoft.bloodvault.R;

public class BloodGroupCountAdapter extends RecyclerView.Adapter<BloodGroupCountAdapter.ViewHolder> {
    public List<BloodCount> bloodCounts;
    public Context context;

    public BloodGroupCountAdapter(List<BloodCount> bloodCounts, Context context) {
        this.bloodCounts = bloodCounts;
        this.context = context;
    }

    @NonNull
    @Override
    public BloodGroupCountAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.itemviewbloodcount,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BloodGroupCountAdapter.ViewHolder holder, int position) {
            holder.tv_bloodGorup.setText(""+bloodCounts.get(position).getBloodGroup());
            holder.tv_bloodGroupCount.setText(""+bloodCounts.get(position).getCount());
    }

    @Override
    public int getItemCount() {
        return bloodCounts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_bloodGorup,tv_bloodGroupCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_bloodGroupCount = itemView.findViewById(R.id.tv_bloodGroupCount);
            tv_bloodGorup = itemView.findViewById(R.id.tv_bloodGorup);
        }
    }
}
