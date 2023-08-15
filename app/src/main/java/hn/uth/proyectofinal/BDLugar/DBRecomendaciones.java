package hn.uth.proyectofinal.BDLugar;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hn.uth.proyectofinal.DAO.ContactoDAO;
import hn.uth.proyectofinal.DAO.LugarDAO;
import hn.uth.proyectofinal.Entities.Contacto;
import hn.uth.proyectofinal.Entities.Lugar;
import hn.uth.proyectofinal.ui.DateConverter;


@Database(version = 1, exportSchema = false, entities = {Lugar.class, Contacto.class})
    @TypeConverters(DateConverter.class)
    public abstract class DBRecomendaciones extends RoomDatabase {
        public abstract LugarDAO lugarDao();
        public abstract ContactoDAO contactoDAO();


        private static volatile DBRecomendaciones INSTANCE;
        private static final int NUMBER_OF_THREADS = 4;

        public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        //Generando una sola instancia con el patron singleton
        public static DBRecomendaciones getDataBase(final Context context){
            if(INSTANCE == null){
                synchronized (DBRecomendaciones.class){
                    if (INSTANCE == null){
                        Callback callback = new Callback(){
                            @Override
                            public void onCreate(@NonNull SupportSQLiteDatabase db){
                                super.onCreate(db);
                                databaseWriteExecutor.execute(() -> {
                                   LugarDAO testDao = INSTANCE.lugarDao();
                                    testDao.deleteAll();

                                testDao.insert(new Lugar("San Pedro Sula","Valle",new Date(),23.23,23.23,"Test San pedro",true));
                                testDao.insert(new Lugar("Villanueva","Valle",new Date(),25.23,26.23,"Test Villanueva",true));
                                testDao.insert(new Lugar("Lago de Yojoa","Valle",new Date(),25.23,26.23,"Test Lago",false));

                                    ContactoDAO contactoDao = INSTANCE.contactoDAO();
                                    contactoDao.deleteAll();
                                    contactoDao.insert(new Contacto("Moises Torres","722435631","moises.torres@gmail.com","San Pedro Sula"));
                                    contactoDao.insert(new Contacto("Alejandra Ayala","722435631","ale.torres@gmail.com","San Pedro Sula"));
                                    contactoDao.insert(new Contacto("Moises Torres","722435631","moises.torres@gmail.com","San Pedro Sula"));
                                    contactoDao.insert(new Contacto("Moises Torres","722435631","moises.torres@gmail.com","San Pedro Sula"));




                                });
                            }
                        };
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),DBRecomendaciones.class,"db_recomendaciones").addCallback(callback).build();

                    }
                }
            }
            return INSTANCE;
        }

    }

