package hn.uth.proyectofinal.ui.home;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import hn.uth.proyectofinal.Entities.Contacto;
import hn.uth.proyectofinal.Entities.Lugar;
import hn.uth.proyectofinal.OnItemClickListener;
import hn.uth.proyectofinal.R;
import hn.uth.proyectofinal.databinding.FragmentHomeBinding;
import hn.uth.proyectofinal.databinding.PopUpCompartirlugarBinding;
import hn.uth.proyectofinal.ui.dashboard.DashboardFragment;
import hn.uth.proyectofinal.ui.dashboard.DashboardViewModel;
import hn.uth.proyectofinal.ui.notifications.ContactAdapter;
import hn.uth.proyectofinal.ui.notifications.NotificationsViewModel;


public class HomeFragment extends Fragment implements OnItemClickListener<Lugar> {
    private LugarAdapter adaptador;
    private ContactAdapter contactAdapter;
    private FragmentHomeBinding binding;

    private DashboardViewModel dashboardViewModel;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        List<Lugar> lugarList = new ArrayList<>();
        adaptador = new LugarAdapter(lugarList,this);

        //
        homeViewModel.getAllLugares().observe(getViewLifecycleOwner(), lugares -> {
            if(lugares.isEmpty()){
                Snackbar.make(binding.RVLugares,"No hay lugares creados", Snackbar.LENGTH_LONG).show();
            }else{
                adaptador.setItems(lugares);
            }
        });

        //



        setupRecyclerView();
        return root;
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        binding.RVLugares.setLayoutManager(linearLayoutManager);
        binding.RVLugares.setAdapter(adaptador);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClickt(Lugar data, int accion) {
        if(accion == 0){
            Bundle bundle = new Bundle();
            bundle.putSerializable("lugar", data);

            NavController navController = Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment_activity_main);
            navController.navigate(R.id.navigation_title_capturagps, bundle);

        }else {

            mostrarPopupAppMensaje();
        }

    }





    ///
    public void mostrarPopupAppMensaje(){

        // Crear un AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Aplicaciónes de mensajería");

        PopUpCompartirlugarBinding binding1 = PopUpCompartirlugarBinding.inflate(getLayoutInflater());
        builder.setView(binding1.getRoot());

        builder.setPositiveButton("Whatsapp", (dialog, which) -> {
            recorrerContactos("Whatsapp");
            dialog.dismiss(); //
        });
        builder.setNeutralButton("Correo electrónico", (dialog, which) -> {
            recorrerContactos("Email");
            dialog.dismiss(); //
        });

        builder.setNegativeButton("SMS", (dialog, which) -> {
            recorrerContactos("SMS");
            dialog.dismiss();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void recorrerContactos(String appMsg){
        dashboardViewModel.getAllContactos().observe(getViewLifecycleOwner(), shareContactos -> {
            if(shareContactos.isEmpty()){
                Snackbar.make(binding.RVLugares,"No hay existen Lugares", Snackbar.LENGTH_LONG).show();
            }else{
                for (Contacto contacto: shareContactos) {
                    if (appMsg.equals("Whatsapp"))sendWhatsApp(contacto.getTelefono());
                    else if(appMsg.equals("Email"))if(!contacto.getEmail().isEmpty())sendEmail(contacto.getEmail());
                    else sendSMS(contacto.getTelefono());
                }
            }
        });
    }
    private void sendWhatsApp(String numero) {
        // Números de contacto (separados por coma) a los que deseas enviar el mensaje

        // Mensaje que deseas enviar
        String message = "Esta es una prueba para la aplicacion EmergyContantApp";

        // Crear un URI con el número y el mensaje
        Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + numero + "&text=" + message);

        // Crear el intent para abrir WhatsApp
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    private void sendEmail(String destinatario) {
        String[] recipients = {destinatario}; // Reemplaza con la dirección de correo electrónico del destinatario
        String subject = "Visista este lugar";
        String body = "Te recomiendo este lugar para que lo visites en ambiente familiar.";

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);


        startActivity(intent);

    }

    private void sendSMS(String numero) {
        String message = "Test envio de mensaje de lugares";

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(numero, null, message, null, null);
    }


///




}