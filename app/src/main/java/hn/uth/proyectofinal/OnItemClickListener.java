package hn.uth.proyectofinal;


import hn.uth.proyectofinal.Entities.Lugar;

public interface OnItemClickListener<T> {
    void onItemClickt(T data);

    void onItemClick(Lugar data);
}
