package hn.uth.proyectofinal.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import hn.uth.proyectofinal.Entities.Lugar;
import hn.uth.proyectofinal.OnItemClickListener;
import hn.uth.proyectofinal.databinding.LugarItemBinding;


public class LugarAdapter extends RecyclerView.Adapter<LugarAdapter.ViewHolder> {
    List<Lugar> dataset;
    OnItemClickListener<Lugar> onItemClickLugar;
    public LugarAdapter(List<Lugar>dataset, OnItemClickListener<Lugar> onItemClickLugar ) {
        this.dataset=dataset;
        this.onItemClickLugar=onItemClickLugar;

    }

    @NonNull
    @Override
    public LugarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LugarItemBinding binding = LugarItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LugarAdapter.ViewHolder holder, int position) {
        Lugar lugar = dataset.get(position);
        holder.binding.txtNombreLugar.setText(lugar.getLugar());
        String fechaFormat = new  SimpleDateFormat("dd-MM-yyyy").format(lugar.getFecha());
        holder.binding.txtFechaLugar.setText(fechaFormat);
        holder.setOnClickListener(lugar,onItemClickLugar);


    }

    @Override
    public int getItemCount() {

        return dataset.size();

    }

    public void setItems(List<Lugar> lugares){
        this.dataset = lugares;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        LugarItemBinding binding;


        public ViewHolder(@NonNull  LugarItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }


      public void  setOnClickListener(Lugar nombreLugar, OnItemClickListener<Lugar> clickListener){
            this.binding.verLugar.setOnClickListener(v -> clickListener.onItemClickt(nombreLugar));

      }

    }


}
