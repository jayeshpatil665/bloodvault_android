package in.specialsoft.bloodvault.AdminBloodRequestInOut;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.specialsoft.bloodvault.AdminBloodRequestsActivity;
import in.specialsoft.bloodvault.Api.ApiClient;
import in.specialsoft.bloodvault.Api.ApiInterface;
import in.specialsoft.bloodvault.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {

    List<Bloodrequest> bloodrequestList;
    Context context;

    public RequestAdapter(List<Bloodrequest> bloodrequestList, Context context) {
        this.bloodrequestList = bloodrequestList;
        this.context = context;
    }

    @NonNull
    @Override
    public RequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.itemviewbloodrequests,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestAdapter.ViewHolder holder, int position) {
        holder.tv_bloodG.setText(bloodrequestList.get(position).getBloodGroup());
        holder.tvName1.setText(bloodrequestList.get(position).getName());
        holder.tvCity1.setText(bloodrequestList.get(position).getCity());
        holder.tvDate_Time.setText(bloodrequestList.get(position).getDateTime());
        holder.tvPhone1.setText(bloodrequestList.get(position).getPhone());

        holder.llViewRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "tel:"+bloodrequestList.get(position).getPhone();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(uri));
                context.startActivity(intent);
            }
        });

        holder.llViewRequest.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Actions");
                builder.setMessage("Delete This Record !! ?");
                builder.setCancelable(false);

                builder.setPositiveButton("Yes_Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteRecord(bloodrequestList.get(position).getId() ,bloodrequestList.get(position).getBloodGroup(),bloodrequestList.get(position).getDateTime());
                    }
                });

                builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Cancled", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return false;
            }
        });
    }

    private void deleteRecord(String id, String bloodGroup, String dateTime) {
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        AdminBloodRequestRemove i = new AdminBloodRequestRemove();
        i.setId(id);
        i.setBloodGroup(bloodGroup);
        i.setDateTime(dateTime);

        api.removeBloodRequests(i).enqueue(new Callback<AdminGenralOutputs>() {
            @Override
            public void onResponse(Call<AdminGenralOutputs> call, Response<AdminGenralOutputs> response) {
                if (response.body().getOutput().equals("Success"))
                {
                    Toast.makeText(context, "Record Removed !"+response.body().getOutput(), Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, AdminBloodRequestsActivity.class));

                }
                else {
                    Toast.makeText(context, "Error in deletion", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AdminGenralOutputs> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return bloodrequestList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_bloodG,tvName1,tvPhone1,tvDate_Time,tvCity1;
        LinearLayout llViewRequest;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_bloodG = itemView.findViewById(R.id.tv_bloodG);
            tvName1 = itemView.findViewById(R.id.tvName1);
            tvPhone1 = itemView.findViewById(R.id.tvPhone1);
            tvDate_Time = itemView.findViewById(R.id.tvDate_Time);
            tvCity1 = itemView.findViewById(R.id.tvCity1);
            llViewRequest = itemView.findViewById(R.id.llViewRequest);
        }
    }
}
