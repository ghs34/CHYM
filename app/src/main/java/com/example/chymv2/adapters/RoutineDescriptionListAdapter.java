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
import com.example.chymv2.model.ListExercice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RoutineDescriptionListAdapter extends RecyclerView.Adapter<RoutineDescriptionListAdapter.ViewHolder>{
    private List<ListExercice> data;
    private List<ListExercice> filteredData;
    private LayoutInflater mInflater;
    private Context context;
    private List<ListExercice> originalExercices;

    final RoutineDescriptionListAdapter.OnItemClickListener listener;
    public RoutineDescriptionListAdapter(List<ListExercice> itemList, Context context, RoutineDescriptionListAdapter.OnItemClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.data = itemList;
        this.originalExercices = new ArrayList<>();
        originalExercices.addAll(itemList);
        this.filteredData = new ArrayList<>();
        filteredData.addAll(itemList);
        this.listener = listener;
    }
    public interface OnItemClickListener{
        void onItemClick(ListExercice item);
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public RoutineDescriptionListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.card_routine_description, null);
        return new RoutineDescriptionListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RoutineDescriptionListAdapter.ViewHolder holder, final int position) {
        holder.bindData(data.get(position));

    }

    public void setItems(List<ListExercice> items) {
        data = items;
    }

    public void filter(String strSearch) {

        nameFiltrationAlgortihm(strSearch);
    }

    public void filterByType(String strFilter){
        if(strFilter.equals("Todo")){
            data.clear();
            data.addAll(originalExercices);
            filteredData.clear();
            filteredData.addAll(originalExercices);
        }
        else {
            data.clear();
            List<ListExercice> collect = originalExercices.stream().filter(i -> i.getGrupoMuscular().equals(strFilter)).collect(Collectors.toList());
            data.addAll(collect);
            filteredData.clear();
            filteredData.addAll(collect);
        }
    }


    public void nameFiltrationAlgortihm(String strSearch){
        if (strSearch.length() == 0) {
            data.clear();
            data.addAll(filteredData);
        } else {
            data.clear();
            List<ListExercice> collect = filteredData.stream().filter(i -> i.getEjercicio().toLowerCase().contains(strSearch.toLowerCase())).collect(Collectors.toList());
            data.addAll(collect);
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView ejercicio, tipoEjercicio,series,reps,kg;
        String s,r,k;

        public ViewHolder(View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.ivRoutineDescription);
            ejercicio = itemView.findViewById(R.id.tvExerciceRoutineDescription);
            tipoEjercicio = itemView.findViewById(R.id.tvTypeRoutineDescription);
            series = itemView.findViewById(R.id.tvSeriesRoutineDescription);
            reps = itemView.findViewById(R.id.tvRepsRoutineDescription);
            kg = itemView.findViewById(R.id.tvKgRoutineDescription);

        }

        public void bindData(final ListExercice item) {
            iconImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            ejercicio.setText(item.getEjercicio());
            tipoEjercicio.setText(item.getTipoEjercicio());
            series.setText("");
            reps.setText("");
            kg.setText("");

            s = context.getResources().getString(R.string.series) + item.getSeries();
            r = context.getResources().getString(R.string.reps) + item.getRepeticiones();
            k = context.getResources().getString(R.string.kg)+ item.getKg();
            if(item.getSeries() != null){
                series.setText(s);
            }
            if(item.getRepeticiones()!=null){
                reps.setText(r);
            }

            if(item.getKg()!=null){
                kg.setText(k);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });

        }

    }
}

