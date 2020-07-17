package com.example.banco.logica;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.banco.datos.Conexion;

import java.util.ArrayList;

public class Entidad extends Conexion {

    private int codigo_entidad;
    private String nombre;

    public int getCodigo_entidad() {
        return codigo_entidad;
    }

    public void setCodigo_entidad(int codigo_entidad) {
        this.codigo_entidad = codigo_entidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public static ArrayList<Entidad> listaEntidad = new ArrayList<>();

    public void listar(){

        SQLiteDatabase bd = this.getReadableDatabase();

        String sql= "select codigo_entidad, nombre from entidad order by nombre ";

        Cursor resultado = bd.rawQuery(sql, null);

        listaEntidad.clear();

        while(resultado.moveToNext())
        {
            Entidad objEntidad = new Entidad();

            objEntidad.setCodigo_entidad(resultado.getInt(0));
            objEntidad.setNombre(resultado.getString(1));

            listaEntidad.add(objEntidad);
        }
    }

    public String[] obtenerNombreEntidad()
    {
        this.listar();
        String[] lista = new String[listaEntidad.size()];
        for (int i=0; i<listaEntidad.size(); i++){
            lista[i]=listaEntidad.get(i).getNombre();
        }
        return lista;
    }
}
