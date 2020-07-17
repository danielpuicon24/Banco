package com.example.banco.logica;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.banco.datos.Conexion;

import java.util.ArrayList;

public class Departamento extends Conexion
{
    private String codigoDepartamento;
    private String descripcion;

    public static ArrayList<Departamento> listaDepartamentos =
            new ArrayList<Departamento>();

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void cargarListaDepartamentos()
    {
        SQLiteDatabase bd = this.getReadableDatabase();
        Cursor resultado = bd.rawQuery("select * from departamento order by nombre", null);
        listaDepartamentos.clear();
        while(resultado.moveToNext())
        {
            Departamento objDepartamento = new Departamento();
            objDepartamento.setCodigoDepartamento(resultado.getString(0));
            objDepartamento.setDescripcion(resultado.getString(1));
            listaDepartamentos.add(objDepartamento);
        }
    }

    public String[] obtenerListaDepartamentos()
    {
        this.cargarListaDepartamentos();
        String[] lista = new String[listaDepartamentos.size()];
        for(int i = 0; i<listaDepartamentos.size(); i++)
        {
            lista[i] = listaDepartamentos.get(i).getDescripcion();
        }
        return lista;
    }
}
