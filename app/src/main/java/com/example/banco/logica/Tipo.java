package com.example.banco.logica;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.banco.datos.Conexion;

import java.util.ArrayList;

public class Tipo extends Conexion {

    private int codigo_tipo;
    private String nombre;

    public int getCodigo_tipo() {
        return codigo_tipo;
    }

    public void setCodigo_tipo(int codigo_tipo) {
        this.codigo_tipo = codigo_tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public static ArrayList<Tipo> listaTipo = new ArrayList<>();

    public void listar(){

        SQLiteDatabase bd = this.getReadableDatabase();

        String sql= "select codigo_tipo, nombre from tipo order by nombre ";

        Cursor resultado = bd.rawQuery(sql, null);

        listaTipo.clear();

        while(resultado.moveToNext())
        {
            Tipo objTipo = new Tipo();

            objTipo.setCodigo_tipo(resultado.getInt(0));
            objTipo.setNombre(resultado.getString(1));

            listaTipo.add(objTipo);
        }
    }

    public String[] obtenerNombreTipo()
    {
        this.listar();
        String[] lista = new String[listaTipo.size()];
        for (int i=0; i<listaTipo.size(); i++){
            lista[i]=listaTipo.get(i).getNombre();
        }
        return lista;
    }
}
