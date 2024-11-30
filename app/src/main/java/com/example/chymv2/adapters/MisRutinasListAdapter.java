package com.example.chymv2.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.chymv2.R;
import com.example.chymv2.model.Rutina;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MisRutinasListAdapter extends RecyclerView.Adapter<MisRutinasListAdapter.ViewHolder>{
    private List<Rutina> data;
    private LayoutInflater mInflater;
    private Context context;
    private List<Rutina> originalRoutines;
    private List<Rutina> filteredData;

    MisRutinasListAdapter.OnItemClickListener listener, eliminar, subir;
    public MisRutinasListAdapter(List<Rutina> itemList, Context context,MisRutinasListAdapter.OnItemClickListener listener,MisRutinasListAdapter.OnItemClickListener eliminar,MisRutinasListAdapter.OnItemClickListener subir) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.data = itemList;
        this.originalRoutines = new ArrayList<>();
        originalRoutines.addAll(itemList);
        this.filteredData = new ArrayList<>();
        filteredData.addAll(itemList);
        this.listener = listener;
        this.eliminar =eliminar;
        this.subir = subir;
        this.context = context;
    }

    public interface OnItemClickListener{
        void onItemClick(Rutina item);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public MisRutinasListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.card_mis_rutinas, null);
        return new MisRutinasListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MisRutinasListAdapter.ViewHolder holder, final int position) {
        holder.bindData(data.get(position));
    }

    public void setItems(List<Rutina> items) {
        data = items;
    }
    public List<Rutina> getItems() {
        return data;
    }

    public void filter(String strSearch) {
        if (strSearch.length() == 0) {
            data.clear();
            data.addAll(filteredData);
        } else {
            data.clear();
            List<Rutina> collect = filteredData.stream().filter(i -> i.getNombre().toLowerCase().contains(strSearch.toLowerCase())).collect(Collectors.toList());
            data.addAll(collect);
        }
    }
    public void filterByType(String strFilter){
        if(strFilter.equals("Todo")){
            data.clear();
            data.addAll(originalRoutines);
            filteredData.clear();
            filteredData.addAll(originalRoutines);
        }
        else {
            data.clear();
            List<Rutina> collect = originalRoutines.stream().filter(i -> i.getRoutineType().equals(strFilter)).collect(Collectors.toList());
            data.addAll(collect);
            filteredData.clear();
            filteredData.addAll(data);

        }
    }
    public void eliminateItem(Rutina item){
        if(data.contains(item)){
            data.remove(item);
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage, eliminate, upload;
        TextView nombreRutina;

        public ViewHolder(View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.cardMisRutinasIconImageView_r);
            nombreRutina = itemView.findViewById(R.id.cardMisRutinasTextView);
            eliminate = itemView.findViewById(R.id.ivEliminarRutina);
            upload = itemView.findViewById(R.id.ivUploadRutina);
        }

        public void bindData(final Rutina item) {
            iconImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            nombreRutina.setText(item.getNombre());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });

            eliminate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    eliminar.onItemClick(item);
                }
            });

            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    subir.onItemClick(item);
                }
            });
        }
    }
}
