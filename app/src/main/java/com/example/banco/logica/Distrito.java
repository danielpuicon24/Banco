package com.example.banco.logica;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.banco.datos.Conexion;

import java.util.ArrayList;

public class Distrito extends Conexion
{
    private String codigoDepartamento;
    private String codigoProvincia;
    private String codigoDistrito;
    private String nombreDistrito;

    public static ArrayList<Distrito> listaDistritos =
            new ArrayList<Distrito>();

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

    public String getCodigoDistrito() {
        return codigoDistrito;
    }

    public void setCodigoDistrito(String codigoDistrito) {
        this.codigoDistrito = codigoDistrito;
    }

    public String getNombreDistrito() {
        return nombreDistrito;
    }

    public void setNombreDistrito(String nombreDistrito) {
        this.nombreDistrito = nombreDistrito;
    }

    public void cargarListaDistritos(String codigoDepartamento, String codigoProvincia)
    {
        SQLiteDatabase bd = this.getReadableDatabase();
        Cursor resultado =
                bd.rawQuery("select * from distrito " +
                        "where codigo_departamento='"+codigoDepartamento+"' and " +
                        "codigo_provincia='"+codigoProvincia+"' order by nombre", null);
        listaDistritos.clear();
        while(resultado.moveToNext())
        {
            Distrito objDistrito = new Distrito();
            objDistrito.setCodigoDepartamento(resultado.getString(0));
            objDistrito.setCodigoProvincia(resultado.getString(1));
            objDistrito.setCodigoDistrito(resultado.getString(2));
            objDistrito.setNombreDistrito(resultado.getString(3));
            listaDistritos.add(objDistrito);
        }
    }

    public String[] obtenerListaDistritos(String codigoDepartamento, String codigoProvincia)
    {
        this.cargarListaDistritos(codigoDepartamento, codigoProvincia);
        String[] lista = new String[listaDistritos.size()];
        for(int i = 0; i<listaDistritos.size(); i++)
        {
            lista[i] = listaDistritos.get(i).getNombreDistrito();
        }
        return lista;
    }
}
