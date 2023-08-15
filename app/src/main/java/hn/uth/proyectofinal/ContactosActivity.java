package hn.uth.proyectofinal;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.RoomSQLiteQuery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import hn.uth.proyectofinal.databinding.ActivityContactosBinding;

//se encarga de mostrar nmuestro layout de activity contatcto para agrergar un contacto al room

public class ContactosActivity extends AppCompatActivity {
    ActivityContactosBinding binding;
    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        contact = null;
        Intent recibido = getIntent();
        //aqui pasamos el conatctos que hemos tocado el fragmento de contactso del celular
        if (recibido.hasExtra("contact")){
            contact=(Contact)recibido.getSerializableExtra("contact");
            if(contact != null){
                binding.tilNombre.getEditText().setText(contact.getNombre());
                binding.tilTelefono.getEditText().setText(contact.getTelefono());
                binding.tilEmail.getEditText().setText(contact.getEmail());

            }
        }
        binding.btnCancelar.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });

        binding.btnGuardar.setOnClickListener(v ->{
            contact=new Contact(binding.tilNombre.getEditText().getText().toString(),binding.tilTelefono.getEditText().getText().toString(),binding.tilEmail.getEditText().getText().toString(), binding.tilDireccion.getEditText().getText().toString());
            Intent guardarIntent = new Intent();
            guardarIntent.putExtra("contact",contact);
            setResult(RESULT_OK, guardarIntent);
            finish();

        });
    }



}