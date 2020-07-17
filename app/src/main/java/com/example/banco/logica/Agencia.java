package com.example.banco.logica;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.banco.datos.Conexion;

import java.util.ArrayList;

public class Agencia extends Conexion {

    private int id_agencia;
    private String nombre;
    private String telefono;
    private String horario_atencion;
    private int codigo_tipo;
    private int codigo_entidad;
    private String codigo_departamento;
    private String codigo_provincia;
    private String codigo_distrito;
    private String foto;
    private String direccion;
    private String referencia;
    private double latitud;
    private double longitud;

    private String nombre_tipo;
    private String nombre_entidad;
    private String nombre_departamento;
    private String nombre_provincia;
    private String nombre_distrito;

    public String getNombre_departamento() { return nombre_departamento; }

    public void setNombre_departamento(String nombre_departamento) { this.nombre_departamento = nombre_departamento; }

    public String getNombre_provincia() { return nombre_provincia; }

    public void setNombre_provincia(String nombre_provincia) { this.nombre_provincia = nombre_provincia; }

    public String getNombre_distrito() { return nombre_distrito; }

    public void setNombre_distrito(String nombre_distrito) { this.nombre_distrito = nombre_distrito; }

    public String getNombre_tipo() { return nombre_tipo; }

    public void setNombre_tipo(String nombre_tipo) { this.nombre_tipo = nombre_tipo; }

    public String getNombre_entidad() { return nombre_entidad; }

    public void setNombre_entidad(String nombre_entidad) {
        this.nombre_entidad = nombre_entidad;
    }

    public int getId_agencia() { return id_agencia; }

    public void setId_agencia(int id_agencia) { this.id_agencia = id_agencia; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }

    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getHorario_atencion() { return horario_atencion; }

    public void setHorario_atencion(String horario_atencion) { this.horario_atencion = horario_atencion; }

    public int getCodigo_tipo() { return codigo_tipo; }

    public void setCodigo_tipo(int codigo_tipo) { this.codigo_tipo = codigo_tipo; }

    public int getCodigo_entidad() { return codigo_entidad; }

    public void setCodigo_entidad(int codigo_entidad) { this.codigo_entidad = codigo_entidad; }

    public String getCodigo_departamento() { return codigo_departamento; }

    public void setCodigo_departamento(String codigo_departamento) { this.codigo_departamento = codigo_departamento; }

    public String getCodigo_provincia() { return codigo_provincia; }

    public void setCodigo_provincia(String codigo_provincia) { this.codigo_provincia = codigo_provincia; }

    public String getCodigo_distrito() { return codigo_distrito; }

    public void setCodigo_distrito(String codigo_distrito) { this.codigo_distrito = codigo_distrito; }

    public String getFoto() { return foto; }

    public void setFoto(String foto) { this.foto = foto; }

    public String getDireccion() { return direccion; }

    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getReferencia() { return referencia; }

    public void setReferencia(String referencia) { this.referencia = referencia; }

    public double getLatitud() { return latitud; }

    public void setLatitud(double latitud) { this.latitud = latitud; }

    public double getLongitud() { return longitud; }

    public void setLongitud(double longitud) { this.longitud = longitud; }

    public static ArrayList<Agencia> lisaAgencia = new ArrayList<>();


    public void listar()
    {
        SQLiteDatabase bd = this.getReadableDatabase();

        String sql="select " +
                "a.id_agencia," +
                "a.nombre," +
                "a.telefono," +
                "a.horario_atencion," +
                "t.nombre as nombre_tipo," +
                "e.nombre as nombre_entidad," +
                "a.foto," +
                "dep.nombre as departamento," +
                "p.nombre as provincia," +
                "d.nombre as distrito" +
                " from agencia a inner join tipo t on (a.codigo_tipo = t.codigo_tipo) " +
                "inner join entidad e on (a.codigo_entidad = e.codigo_entidad) " +
                "inner join distrito d on " +
                "(a.codigo_departamento = d.codigo_departamento " +
                "and a.codigo_provincia = d.codigo_provincia and " +
                "a.codigo_distrito = d.codigo_distrito) " +
                "inner join provincia p " +
                "on (a.codigo_departamento=p.codigo_departamento and " +
                "a.codigo_provincia = p.codigo_provincia) " +
                "inner join departamento dep on " +
                "a.codigo_departamento = dep.codigo_departamento " +
                " group by a.id_agencia " +
                " order by a.nombre ";

        Cursor resultado = bd.rawQuery(sql, null);

        lisaAgencia.clear();

        while (resultado.moveToNext())
        {
            Agencia objAgencia = new Agencia();

            objAgencia.setId_agencia(resultado.getInt(0));
            objAgencia.setNombre(resultado.getString(1));
            objAgencia.setTelefono(resultado.getString(2));
            objAgencia.setHorario_atencion(resultado.getString(3));
            objAgencia.setNombre_tipo(resultado.getString(4));
            objAgencia.setNombre_entidad(resultado.getString(5));
            objAgencia.setFoto(resultado.getString(6));
            objAgencia.setNombre_departamento(resultado.getString(7));
            objAgencia.setNombre_provincia(resultado.getString(8));
            objAgencia.setNombre_distrito(resultado.getString(9));

            lisaAgencia.add(objAgencia);
        }
    }



    public  long agregar(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues registro = new ContentValues();

        registro.put("nombre", this.getNombre());
        registro.put("telefono", this.getTelefono());
        registro.put("horario_atencion", this.getHorario_atencion());
        registro.put("codigo_tipo", this.getCodigo_tipo());
        registro.put("codigo_entidad", this.getCodigo_entidad());
        registro.put("codigo_departamento", this.getCodigo_departamento());
        registro.put("codigo_provincia", this.getCodigo_provincia());
        registro.put("codigo_distrito", this.getCodigo_distrito());
        registro.put("foto", this.getFoto());
        registro.put("direccion", this.getDireccion());
        registro.put("referencia", this.getDireccion());
        registro.put("latitud", this.getLatitud());
        registro.put("longitud", this.getLongitud());

        long resultado = db.insert("agencia", null, registro);
        return resultado;
    }

    public Agencia leerDatos(int agencia_id){
        SQLiteDatabase bd = this.getReadableDatabase();

        String sql="select " +
                "a.id_agencia," +
                "a.nombre," +
                "a.telefono," +
                "a.horario_atencion," +
                "t.nombre as nombre_tipo," +
                "e.nombre as nombre_entidad," +
                "dep.nombre as departamento," +
                "p.nombre as provincia," +
                "d.nombre as distrito," +
                "a.foto," +
                "a.direccion," +
                "a.referencia," +
                "a.latitud," +
                "a.longitud" +
                " from agencia a inner join tipo t on (a.codigo_tipo = t.codigo_tipo) " +
                "inner join entidad e on (a.codigo_entidad = e.codigo_entidad) " +
                "inner join distrito d on " +
                "(a.codigo_departamento = d.codigo_departamento " +
                "and a.codigo_provincia = d.codigo_provincia and " +
                "a.codigo_distrito = d.codigo_distrito) " +
                "inner join provincia p " +
                "on (a.codigo_departamento=p.codigo_departamento and " +
                "a.codigo_provincia = p.codigo_provincia) " +
                "inner join departamento dep on " +
                "a.codigo_departamento = dep.codigo_departamento " +
                " where a.id_agencia= ? ";

        String id = String.valueOf(agencia_id);
        Cursor cursor = bd.rawQuery(sql, new String[]{id});

        Agencia objAgencia = new Agencia();

        if (cursor.moveToNext()){

            objAgencia.setId_agencia(cursor.getInt(0));
            objAgencia.setNombre(cursor.getString(1));
            objAgencia.setTelefono(cursor.getString(2));
            objAgencia.setHorario_atencion(cursor.getString(3));
            objAgencia.setNombre_tipo(cursor.getString(4));
            objAgencia.setNombre_entidad(cursor.getString(5));
            objAgencia.setNombre_departamento(cursor.getString(6));
            objAgencia.setNombre_provincia(cursor.getString(7));
            objAgencia.setNombre_distrito(cursor.getString(8));
            objAgencia.setFoto(cursor.getString(9));
            objAgencia.setDireccion(cursor.getString(10));
            objAgencia.setReferencia(cursor.getString(11));
            objAgencia.setLatitud(cursor.getDouble(12));
            objAgencia.setLongitud(cursor.getDouble(13));
        }
        return objAgencia;
    }


    public long editar(){
        SQLiteDatabase bd = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //Agregar los valores a cada columna que vamos a editar
        values.put("nombre", this.getNombre());
        values.put("telefono", this.getTelefono());
        values.put("horario_atencion", this.getHorario_atencion());
        values.put("codigo_tipo", this.getCodigo_tipo());
        values.put("codigo_entidad", this.getCodigo_entidad());
        values.put("codigo_departamento", this.getCodigo_departamento());
        values.put("codigo_provincia", this.getCodigo_provincia());
        values.put("codigo_distrito", this.getCodigo_distrito());
        values.put("foto", this.getFoto());
        values.put("latitud", this.getLatitud());
        values.put("longitud", this.getLongitud());
        values.put("direccion", this.getDireccion());
        values.put("referencia", this.getReferencia());

        //Definir el código del lugar turístico que vamos a editar
        String cod = String.valueOf(this.getId_agencia());

        return bd.update("agencia", values, "id_agencia = ?", new String[]{cod});
    }


    public long eliminar(){
        SQLiteDatabase bd = this.getWritableDatabase();

        //Definir el código del lugar turístico que vamos a eliminar
        String id = String.valueOf(this.getId_agencia());
        return bd.delete("agencia", "id_agencia = ?", new String[]{id});
    }
}
