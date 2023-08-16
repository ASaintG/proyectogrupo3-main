package hn.uth.proyectofinal.ui.dashboard;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import hn.uth.proyectofinal.Entities.Contacto;
import hn.uth.proyectofinal.Entities.Lugar;
import hn.uth.proyectofinal.OnItemClickListener;
import hn.uth.proyectofinal.databinding.FragmentDashboardBinding;
import hn.uth.proyectofinal.databinding.PopUpCompartirlugarBinding;
import hn.uth.proyectofinal.ui.home.LugarAdapter;

public class DashboardFragment extends Fragment implements OnItemClickListener<Contacto> {
    private ContactoAdapter adaptador;

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        List<Contacto> contactoList = new ArrayList<>();
        adaptador = new ContactoAdapter(contactoList,this);



        dashboardViewModel.getAllContactos().observe(getViewLifecycleOwner(), contactos -> {
            if(contactos.isEmpty()){
                Snackbar.make(binding.rvContactos,"No hay contactos creados", Snackbar.LENGTH_LONG).show();
            }else{
                adaptador.setItems(contactos);
            }
        });


        setupRecyclerView();
        return root;
    }
    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        binding.rvContactos.setLayoutManager(linearLayoutManager);
        binding.rvContactos.setAdapter(adaptador);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }





    @Override
    public void onItemClickt(Contacto data, int acccion) {

    }


    ///
}