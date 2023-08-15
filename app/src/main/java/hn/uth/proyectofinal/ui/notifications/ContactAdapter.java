package hn.uth.proyectofinal.ui.notifications;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hn.uth.proyectofinal.Contact;
import hn.uth.proyectofinal.Entities.Contacto;
import hn.uth.proyectofinal.OnItemClickListener;
import hn.uth.proyectofinal.databinding.ContactItemBinding;
import hn.uth.proyectofinal.databinding.ContactoItemBinding;


public class ContactAdapter extends  RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    List<Contact> dataset;
    OnItemClickListener<Contact> onItemClickContact;

    public ContactAdapter(List<Contact>dataset, OnItemClickListener<Contact> onItemClickContact ) {
        this.dataset=dataset;
        this.onItemClickContact=onItemClickContact;

    }

    //creamos viewholder de nuestro adaptador
    @NonNull
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ContactItemBinding binding = ContactItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ViewHolder holder, int position) {
        Contact contact = dataset.get(position);//aqui creamos un objeto y este nos enviara segun la posicion
        holder.binding.txtNombre.setText(contact.getNombre());
        holder.binding.txtTelefono.setText(contact.getTelefono());
        //holder.binding.txtCDireccion.setText(contact.getEmail());
        holder.binding.txtEmail.setText(contact.getEmail());
        holder.setOnClickListener(contact,onItemClickContact);

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
    public void setItems(List<Contact> contacts){
        this.dataset = contacts;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
 ContactItemBinding binding;


        public ViewHolder(@NonNull ContactItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void  setOnClickListener(Contact nombreContact, OnItemClickListener<Contact> clickListener){
            this.binding.cardContact.setOnClickListener(v -> clickListener.onItemClickt(nombreContact));

        }
    }


}
