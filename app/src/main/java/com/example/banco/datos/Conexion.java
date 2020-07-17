package com.example.banco.datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conexion extends SQLiteOpenHelper
{
    public static Context context;
    public static String nombreBD = "bdbanco";
    public static int versionBD = 1;

    public Conexion()
    {
        super(context, nombreBD, null, versionBD);
    }

    @Override
    public void onCreate(SQLiteDatabase bd)
    {
        // Crear la tabla Tipo
        bd.execSQL(Tablas.tablaTipo);

        // Cargar datos a la tabla Tipo
        for(int i=0; i<Tablas.tablaTipoDatos.length; i++)
        {
            bd.execSQL(Tablas.tablaTipoDatos[i]);
        }

        // Crear la tabla Entidad
        bd.execSQL(Tablas.tablaEntidad);

        // Cargar datos a la tabla Entidad
        for(int i=0; i<Tablas.tablaEntidadDatos.length; i++)
        {
            bd.execSQL(Tablas.tablaEntidadDatos[i]);
        }

        // Crear la tabla Agencia
        bd.execSQL(Tablas.tablaAgencia);

        // Cargar datos a la tabla Agencia
        for(int i=0; i<Tablas.tablaAgenciaDatos.length; i++)
        {
            bd.execSQL(Tablas.tablaAgenciaDatos[i]);
        }

        // Crear la tabla departamento
        bd.execSQL(Tablas.tablaDepartamento);

        // Cargar datos a la tabla departamento
        for(int i=0; i<Tablas.tablaDepartamentoDatos.length; i++)
        {
            bd.execSQL(Tablas.tablaDepartamentoDatos[i]);
        }

        // Crear la tabla provincia
        bd.execSQL(Tablas.tablaProvincia);

        // Cargar datos a la tabla provincia
        for(int i=0; i<Tablas.tablaProvinciaDatos.length; i++)
        {
            bd.execSQL(Tablas.tablaProvinciaDatos[i]);
        }

        // Crear la tabla distrito
        bd.execSQL(Tablas.tablaDistrito);

        // Cargar datos a la tabla distrito
        for(int i=0; i<Tablas.tablaDistritoDatos.length; i++)
        {
            bd.execSQL(Tablas.tablaDistritoDatos[i]);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }
}
