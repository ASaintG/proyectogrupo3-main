package hn.uth.proyectofinal.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contactos_table")
public class Contacto {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "idcontacto")
    private int idcontacto;

    @NonNull
    @ColumnInfo(name = "nombre")
    private String nombre;

    @NonNull
    @ColumnInfo(name = "telefono")
    private String telefono;

    @NonNull
    @ColumnInfo(name = "email")
    private String email;
    @NonNull
    @ColumnInfo(name = "direccion")
    private String direccion;

    public Contacto( @NonNull String nombre, @NonNull String telefono, @NonNull String email, @NonNull String direccion) {
        this.idcontacto = idcontacto;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
    }

    public int getIdcontacto() {
        return idcontacto;
    }

    public void setIdcontacto(int idcontacto) {
        this.idcontacto = idcontacto;
    }

    @NonNull
    public String getNombre() {
        return nombre;
    }

    public void setNombre(@NonNull String nombre) {
        this.nombre = nombre;
    }

    @NonNull
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(@NonNull String telefono) {
        this.telefono = telefono;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(@NonNull String direccion) {
        this.direccion = direccion;
    }
}

