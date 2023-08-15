package hn.uth.proyectofinal.ui.gpscaptura;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;



import hn.uth.proyectofinal.Entities.Lugar;
import hn.uth.proyectofinal.Repositories.LugarRepository;

public class gpscapturaViewModel extends AndroidViewModel {


    private LugarRepository repository;
    private final LiveData<List<Lugar>> dataset;

    public gpscapturaViewModel(@NonNull Application app) {
        super(app);
        this.repository=new LugarRepository(app);
        this.dataset=repository.getAllLugares();


    }

    public LiveData<List<Lugar>> getAllLugares(){
        return dataset;
    }

    public void insert(Lugar lugar){repository.insertLugar(lugar);}
    public void update(Lugar lugar){repository.updateLugar(lugar);}
    public void delete(Lugar lugar){repository.deleteLugar(lugar);}
}