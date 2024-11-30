package com.example.chymv2.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chymv2.R;
import com.example.chymv2.model.ListExercice;
import com.example.chymv2.sources.InitializeData;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.stream.Collectors;

public class PopUpCrearRutinasAdapter extends RecyclerView.Adapter<PopUpCrearRutinasAdapter.ViewHolderPopUp>{
    private List<ListExercice> data;
    private List<ListExercice> filteredData;
    private LayoutInflater mInflater;
    private Context context;
    private List<ListExercice> originalExercices;


    final PopUpCrearRutinasAdapter.OnItemClickListener listener;
    public PopUpCrearRutinasAdapter(List<ListExercice> itemList, Context context, PopUpCrearRutinasAdapter.OnItemClickListener listener) {
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
    public PopUpCrearRutinasAdapter.ViewHolderPopUp onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.card_pop_up_crear_rutina, null);
        return new PopUpCrearRutinasAdapter.ViewHolderPopUp(view);
    }

    @Override
    public void onBindViewHolder(final PopUpCrearRutinasAdapter.ViewHolderPopUp holder, final int position) {

        //holder.bindData(InitializeData.getInstance(context).findExerciceById(data.get(position).getId()));
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


    public class ViewHolderPopUp extends RecyclerView.ViewHolder {
        //ImageView iconImage;
        TextView ejercicio, grupoMuscular, tipoEjercicio;
        LinearLayout backGround;
        Drawable backgroundDrawable;

        public ViewHolderPopUp(View itemView) {
            super(itemView);
            //iconImage = itemView.findViewById(R.id.iconImageView);
            ejercicio = itemView.findViewById(R.id.muscleTextViewPopUp);
            grupoMuscular = itemView.findViewById(R.id.bodyPartTextViewPopUp);
            tipoEjercicio = itemView.findViewById(R.id.typeTextViewPopUp);
            backGround = itemView.findViewById(R.id.cardPopUp);

        }


        public void bindData(final ListExercice item) {
            //iconImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            ejercicio.setText(item.getEjercicio());
            grupoMuscular.setText(item.getGrupoMuscular());
            tipoEjercicio.setText(item.getTipoEjercicio());
            backGround.setBackgroundColor(Color.parseColor(item.getSelected()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(item.getSelected().equals("#FFFFFF")){
                        item.setSelected("#D9D9D9");
                    }
                    else {
                        item.setSelected("#FFFFFF");
                    }
                    backGround.setBackgroundColor(Color.parseColor(item.getSelected()));
                    listener.onItemClick(item);

                }
            });

        }

    }
}


