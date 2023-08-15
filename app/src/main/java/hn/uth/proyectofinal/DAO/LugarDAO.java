package hn.uth.proyectofinal.DAO;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import hn.uth.proyectofinal.Entities.Lugar;

@Dao
public interface LugarDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Lugar lugar);


    @Update
    void update(Lugar lugar);
    @Delete
    void delete(Lugar lugar);

    @Query("DELETE FROM lugar_table")
    void deleteAll();

    @Query("SELECT * FROM lugar_table ORDER BY  lugar ASC")
    LiveData<List<Lugar>> getLugar();


}
