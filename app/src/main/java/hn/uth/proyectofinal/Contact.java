package hn.uth.proyectofinal;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import java.io.Serializable;

public class Contact implements Serializable {


    private String nombre;
    private String telefono;
    private String email;
    private String direccion;


    //constructor 1
    public Contact( String nombre, String telefono, String email) {

        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.direccion = "";

    }
    //constructor 2
    public Contact( String nombre, String telefono, String email, String direccion) {

        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion(){
        return direccion;
    }



}
