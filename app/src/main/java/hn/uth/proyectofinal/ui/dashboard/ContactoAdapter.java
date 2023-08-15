package hn.uth.proyectofinal.ui.dashboard;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hn.uth.proyectofinal.Entities.Contacto;

import hn.uth.proyectofinal.Entities.Lugar;
import hn.uth.proyectofinal.OnItemClickListener;
import hn.uth.proyectofinal.databinding.ContactoItemBinding;
import hn.uth.proyectofinal.databinding.LugarItemBinding;


public class ContactoAdapter extends  RecyclerView.Adapter<ContactoAdapter.ViewHolder> {
    List<Contacto> dataset;
    OnItemClickListener<Contacto> onItemClickContacto;

    public ContactoAdapter(List<Contacto>dataset, OnItemClickListener<Contacto> onItemClickLugar ) {
        this.dataset=dataset;
        this.onItemClickContacto=onItemClickContacto;

    }

    //creamos viewholder de nuestro adaptador
    @NonNull
    @Override
    public ContactoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ContactoItemBinding binding = ContactoItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoAdapter.ViewHolder holder, int position) {
        Contacto contacto = dataset.get(position);//aqui creamos un objeto y este nos enviara segun la posicion
        holder.binding.txtCNombre.setText(contacto.getNombre());
        holder.binding.txtCTelefono.setText(contacto.getTelefono());
        //holder.binding.txtCDireccion.setText(contacto.getEmail());
        holder.binding.txtCEmail.setText(contacto.getDireccion());
        holder.setOnClickListener(contacto,onItemClickContacto);

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
    public void setItems(List<Contacto> contactos){
        this.dataset = contactos;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
 ContactoItemBinding binding;


        public ViewHolder(@NonNull ContactoItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void  setOnClickListener(Contacto nombreContacto, OnItemClickListener<Contacto> clickListener){
            this.binding.cardContacto.setOnClickListener(v -> clickListener.onItemClickt(nombreContacto));

        }
    }


}
