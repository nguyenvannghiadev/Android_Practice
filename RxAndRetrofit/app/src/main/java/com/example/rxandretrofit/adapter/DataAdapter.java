package com.example.rxandretrofit.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rxandretrofit.R;
import com.example.rxandretrofit.model.Android;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

   private ArrayList<Android> mAndroiList;

   public DataAdapter(ArrayList<Android> mAndroiList) {
      this.mAndroiList = mAndroiList;
   }

   @NonNull
   @Override
   public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row, parent, false);

      return new ViewHolder(view);
   }

   @Override
   public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      holder.bind(position);
   }

   @Override
   public int getItemCount() {
      return mAndroiList.size();
   }

   public class ViewHolder extends RecyclerView.ViewHolder {
      private final TextView tvName;
      private final TextView tvVersion;
      private final TextView tvApi;

      public ViewHolder(@NonNull View itemView) {
         super(itemView);
         tvName = (TextView) itemView.findViewById(R.id.tvName);
         tvVersion = (TextView) itemView.findViewById(R.id.tvVersion);
         tvApi = (TextView) itemView.findViewById(R.id.tvApiLevel);
      }

      public void bind(int position) {
         tvName.setText(mAndroiList.get(position).getName());
         tvVersion.setText(mAndroiList.get(position).getVer());
         tvApi.setText(mAndroiList.get(position).getApi());
      }

   }
}
