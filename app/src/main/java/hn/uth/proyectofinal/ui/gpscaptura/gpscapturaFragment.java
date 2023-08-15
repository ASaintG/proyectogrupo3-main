package hn.uth.proyectofinal.ui.gpscaptura;

import static android.app.appsearch.AppSearchResult.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;

import java.util.Date;

import hn.uth.proyectofinal.Entities.Lugar;
import hn.uth.proyectofinal.R;
import hn.uth.proyectofinal.databinding.FragmentGpscapturaBinding;


public class gpscapturaFragment extends Fragment implements LocationListener {

    private static final int REQUEST_CODE_GPS = 555;
    private FragmentGpscapturaBinding binding;
    private Lugar ubicacion;
    private Lugar lugarEditar;

    private LocationManager locationManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        gpscapturaViewModel gpsCapturaViewModel =
                new ViewModelProvider(this).get(gpscapturaViewModel.class);
        ubicacion = null;

        binding = FragmentGpscapturaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.cvData.setVisibility(View.INVISIBLE);

        try{
            lugarEditar = (Lugar) getArguments().getSerializable("lugar");
        }catch (Exception e) {
            lugarEditar = null;
        }
        mostrarlugaresedtidar();

        binding.btnSearch.setOnClickListener(v -> {
            solicitarPermisosGPS(this.getContext());
        });

        binding.btnGuardar.setOnClickListener(v -> {
            Date currentDate = new Date();
            String nombre =binding.tilNombre.getText().toString();
            String tipodelugar = binding.lvtipo.getText().toString();
            String longitudStr = binding.tvLon.getText().toString();
            String latitudStr = binding.tvLat.getText().toString();
            double longitud = Double.parseDouble(longitudStr);
            double latitud = Double.parseDouble(latitudStr);
            String descripcion = binding.tildescripcion.getEditText().getText().toString();
            boolean retorno=true;
            
          Lugar nuevo = new Lugar(nombre,tipodelugar,currentDate,longitud,latitud,descripcion,retorno);
            String mensaje = "Cliente agregado correctamente";
            if(lugarEditar == null){
                gpsCapturaViewModel.insert(nuevo);
            }else{
                nuevo.setIdlugar(lugarEditar.getIdlugar());
              gpsCapturaViewModel.update(nuevo);
                mensaje = "Cliente modificado correctamente";
            }

            Snackbar.make(binding.getRoot(), mensaje, Snackbar.LENGTH_LONG).show();
            limpiarCampos();
            NavController navController = Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment_activity_main);
            navController.navigate(R.id.navigation_home);
        });

        binding.btnEliminar.setOnClickListener(v -> {
            gpsCapturaViewModel.delete(lugarEditar);
            Snackbar.make(binding.getRoot(), "Cliente eliminado correctamente", Snackbar.LENGTH_LONG).show();
            NavController navController = Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment_activity_main);
            navController.navigate(R.id.navigation_home);
        } );
        return root;

    }

    private void limpiarCampos() {
        binding.tilNombre.setText("");
        binding.lvtipo.setText("");
        binding.tvLon.setText("");
        binding.tvLat.setText("");
        binding.tildescripcion.getEditText().setText("");
    }


    private void mostrarlugaresedtidar() {
        if(lugarEditar == null){
            //ES UNA CREACIÓN, MOSTRAR BUSQUEDA

            binding.btnGuardar.setText(R.string.btn_crear_lugar);
        }else{
            //ES UNA EDICIO, OCULTAR BUSQUED
            binding.cvData.setVisibility(View.VISIBLE);
            binding.tilNombre.setText(lugarEditar.getLugar());
            binding.lvtipo.setText(lugarEditar.getTipoLugar());
            binding.tvLon.setText(lugarEditar.getLongitudeStr());
            binding.tvLat.setText(lugarEditar.getLatitudeStr());
            binding.tildescripcion.getEditText().setText(lugarEditar.getDescripcion());
            binding.btnGuardar.setText(R.string.btn_modificar_lugar);
            binding.btnEliminar.setVisibility(View.VISIBLE);
        }
    }
    private void solicitarPermisosGPS(Context contexto) {
        if(ContextCompat.checkSelfPermission(contexto, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            //TENGO EL PERMISO, PUEDO UTILIZAR EL GPS
            useFineLocation();
        }else{
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_GPS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GPS){
            if(grantResults.length > 0){
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    useFineLocation();
                }else if(grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    useCoarseLocation();
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @SuppressLint({"ServiceCast", "MissingPermission"})
    private void useCoarseLocation() {
        //OBTIENE EL SERVICIO DE UBICACIÓN DEL DISPOSITIVO
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        //SOLICITAMOS ACTUALIZAR LA POSICIÓN GPS CON DETERMINADA APROXIMACIÓN (NETWORK_PROVIDER = COARSE_LOCATION = UBICACIÓN APROXIMADA)
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
    }
    @SuppressLint({"ServiceCast", "MissingPermission"})
    private void useFineLocation() {
        //OBTIENE EL SERVICIO DE UBICACIÓN DEL DISPOSITIVO
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        //SOLICITAMOS ACTUALIZAR LA POSICIÓN GPS CON DETERMINADA APROXIMACIÓN (GPS_PROVIDER = FINE_LOCATION = UBICACIÓN EXACTA)
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }
    @Override
    public void onLocationChanged(@NonNull Location location) {
        Date currentDate = new Date();
        ubicacion = new Lugar("","",currentDate,location.getLatitude(), location.getLongitude(),"",true);

        binding.tvLat.setText(ubicacion.getLatitudeStr());
        binding.tvLon.setText(ubicacion.getLongitudeStr());

        binding.cvData.setVisibility(View.VISIBLE);
        // DETENER ACTUALIZACION DE UBICACION PARA DEJARLO DE UN SOLO USO (SI SE QUIERE SEGUIMIENTO NO HACER ESTA PARTE)
      //  locationManager.removeUpdates(this);
    }





    //DETENER ACTUALIZACION DE UBICACION PARA DEJARLO DE UN SOLO USO (SI SE QUIERE SEGUIMIENTO NO HACER ESTA PARTE)




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}