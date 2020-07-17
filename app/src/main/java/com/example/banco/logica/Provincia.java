package com.example.banco.logica;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.banco.datos.Conexion;

import java.util.ArrayList;

public class Provincia extends Conexion
{
    private String codigoDepartamento;
    private String codigoProvincia;
    private String nombreProvincia;

    public static ArrayList<Provincia> listaProvincias =
            new ArrayList<Provincia>();

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public String getCodigoProvincia() {
        return codigoProvincia;
    }

    public void setCodigoProvincia(String codigoProvincia) {
        this.codigoProvincia = codigoProvincia;
    }

    public String getNombreProvincia() {
        return nombreProvincia;
    }

    public void setNombreProvincia(String nombreProvincia) {
        this.nombreProvincia = nombreProvincia;
    }

    public void cargarListaProvincias(String codigoDepartamento)
    {
        SQLiteDatabase bd = this.getReadableDatabase();
        Cursor resultado =
            bd.rawQuery("select * from provincia where codigo_departamento='"+codigoDepartamento+"' order by nombre", null);
        listaProvincias.clear();
        while(resultado.moveToNext())
        {
            Provincia objProvincia = new Provincia();
            objProvincia.setCodigoDepartamento(resultado.getString(0));
            objProvincia.setCodigoProvincia(resultado.getString(1));
            objProvincia.setNombreProvincia(resultado.getString(2));
            listaProvincias.add(objProvincia);
        }
    }

    public String[] obtenerListaProvincias(String codigoDepartamento)
    {
        this.cargarListaProvincias(codigoDepartamento);
        String[] lista = new String[listaProvincias.size()];
        for(int i = 0; i<listaProvincias.size(); i++)
        {
            lista[i] = listaProvincias.get(i).getNombreProvincia();
        }
        return lista;
    }
}
