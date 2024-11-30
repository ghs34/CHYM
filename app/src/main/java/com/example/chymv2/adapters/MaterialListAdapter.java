package com.example.chymv2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.chymv2.R;
import com.example.chymv2.model.CardMaterial;
import android.content.Context;

import java.util.List;

public class MaterialListAdapter extends RecyclerView.Adapter<MaterialListAdapter.ViewHolder> {
    private List<CardMaterial> mData;
    private LayoutInflater mInflater;
    private Context context;

    final MaterialListAdapter.OnItemClickListener listener;
    public MaterialListAdapter(List<CardMaterial> itemMaterial, Context context, OnItemClickListener listener){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemMaterial;
        this.listener = listener;
    }


    @Override
    public int getItemCount(){ return mData.size();}

    @Override
    public MaterialListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.card_material, null);
        return new MaterialListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MaterialListAdapter.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));
        CardMaterial card = mData.get(position);
        CheckBox checkBox = holder.itemView.findViewById(R.id.materialCheckBox);
        checkBox.setChecked(card.isStatus());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Actualiza el objeto correspondiente en la lista de datos
                //TuObjeto objeto = listaObjetos.get(position);
                //objeto.setSeleccionado(isChecked);
            }
        });
    }

    public void setItems(List<CardMaterial> items){ mData = items;}

    public interface OnItemClickListener {
        void onItemClick(CardMaterial item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        CheckBox checkBox;

        ViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.materialCardTxt);
            checkBox = itemView.findViewById(R.id.materialCheckBox);
        }

        void bindData(final CardMaterial item){
            name.setText(item.getName());
            checkBox.setEnabled(item.isStatus());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(checkBox.isChecked()){
                        checkBox.setChecked(false);
                    }
                    else{
                        checkBox.setChecked(true);
                    }
                    listener.onItemClick(item);
                }
            });
        }
    }
}
