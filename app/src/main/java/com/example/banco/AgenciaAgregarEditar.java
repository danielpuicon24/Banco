package com.example.banco;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.banco.logica.Agencia;
import com.example.banco.logica.Departamento;
import com.example.banco.logica.Distrito;
import com.example.banco.logica.Entidad;
import com.example.banco.logica.Provincia;
import com.example.banco.logica.Tipo;
import com.example.banco.util.Funciones;

public class AgenciaAgregarEditar extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener, AdapterView.OnItemSelectedListener {

    ImageView imgFotoAgenciaAddEdit;
    EditText txtNombreAgenciaAddEdit, txtTelefonoAgenciaAddEdit, txtUbicacionAgenciaAddEdit, txtReferenciaAgenciaAddEdit, txtHorarioAtencionAgenciaAddEdit;
    Spinner spDistrito, spProvincia,spDepartamento, spEntidadAddEdit, spTipoAgenciaAddEdit ;
    Button btnGrabarAgenciaAddEdit, btnMapaAgenciaAddEdit;
    TextView txtCodigoAgenciaAddEdit, txtLatitudAgenciaAddEdit, txtLongitudAgenciaAddEdit;

    boolean usuarioTocaPantalla = false;

    //Indicador que permite saber si estamos agregando o editando
    private String tipoOperacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agencia_agregar_editar);

        this.setTitle("Agregar/editar");

        imgFotoAgenciaAddEdit = findViewById(R.id.imgFotoAgenciaAddEdit);

        txtNombreAgenciaAddEdit = findViewById(R.id.txtNombreAgenciaAddEdit);
        txtTelefonoAgenciaAddEdit = findViewById(R.id.txtTelefonoAgenciaAddEdit);
        txtUbicacionAgenciaAddEdit = findViewById(R.id.txtUbicacionAgenciaAddEdit);
        txtReferenciaAgenciaAddEdit = findViewById(R.id.txtReferenciaAgenciaAddEdit);
        txtHorarioAtencionAgenciaAddEdit = findViewById(R.id.txtHorarioAtencionAgenciaAddEdit);

        spTipoAgenciaAddEdit = findViewById(R.id.spTipoAgenciaAddEdit);
        spEntidadAddEdit = findViewById(R.id.spEntidadAddEdit);
        spDepartamento = findViewById(R.id.spDepartamentoAddEdit);
        spProvincia = findViewById(R.id.spProvinciaAddEdit);
        spDistrito = findViewById(R.id.spDistritoAddEdit);

        spDepartamento.setOnItemSelectedListener(this);
        spProvincia.setOnItemSelectedListener(this);
        spDistrito.setOnItemSelectedListener(this);


        btnMapaAgenciaAddEdit = findViewById(R.id.btnMapaAgenciaAddEdit);
        btnGrabarAgenciaAddEdit = findViewById(R.id.btnGrabarAgenciaAddEdit);

        txtCodigoAgenciaAddEdit = findViewById(R.id.txtCodigoAgenciaAddEdit);
        txtLatitudAgenciaAddEdit = findViewById(R.id.txtLatitudAgenciaAddEdit);
        txtLongitudAgenciaAddEdit = findViewById(R.id.txtLongitudAgenciaAddEdit);

        btnMapaAgenciaAddEdit.setOnClickListener(this);
        btnGrabarAgenciaAddEdit.setOnClickListener(this);
        imgFotoAgenciaAddEdit.setOnClickListener(this);

        //Llamar al método llenarSpinnerTipo()
        this.llenarSpinnerTipo();

        //Llamar al método llenarSpinnerEntidad()
        this.llenarSpinnerEntidad();

        //Indicarle a los spinner que reconozacan el evento onTouch
        spDepartamento.setOnTouchListener(this);
        spProvincia.setOnTouchListener(this);
        spDistrito.setOnTouchListener(this);

        //Llamar al método cargarSpinnerDepartamento()
        this.cargarSpinnerDepartamento();

        //Leer parámetros
        if (this.getIntent().getExtras() == null){
            //No ha recibido parámetros -> AGREGAR
            tipoOperacion = "agregar";
            imgFotoAgenciaAddEdit.setTag("1");
            this.usuarioTocaPantalla = false;
        }else{
            tipoOperacion = "editar";
            //Si ha recibido parametros -> EDITAR
            Bundle bundle = this.getIntent().getExtras();
            int codigo_agencia = bundle.getInt("p_cod_agencia");
            this.usuarioTocaPantalla = true;

            //Leer los datos
            leerDatos(codigo_agencia);
        }

    }


    //Método llenarSpinnerTipo()
    private void llenarSpinnerTipo(){
        String array[] = new Tipo().obtenerNombreTipo();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, array);
        spTipoAgenciaAddEdit.setAdapter(adapter);
    }


    //Método llenarSpinnerEntidad()
    private void llenarSpinnerEntidad(){
        String array[] = new Entidad().obtenerNombreEntidad();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, array);
        spEntidadAddEdit.setAdapter(adapter);
    }

    //Método cargarSpinnerDepartamento()
    private void cargarSpinnerDepartamento() {
        String listaDepartamentos[] = new Departamento().obtenerListaDepartamentos();
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listaDepartamentos);
        spDepartamento.setAdapter(adapter);
    }

    private void cargarSpinnerProvincia(String codigoDepartamento) {
        String listaProvincias[] = new Provincia().obtenerListaProvincias(codigoDepartamento);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listaProvincias);
        spProvincia.setAdapter(adapter);
    }

    private void cargarSpinnerDistrito(String codigoDepartamento, String codigoProvincia) {
        String listaDistritos[] = new Distrito().obtenerListaDistritos(codigoDepartamento, codigoProvincia);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listaDistritos);
        spDistrito.setAdapter(adapter);
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnGrabarAgenciaAddEdit:
                grabarDatosBD();
                break;

            case R.id.imgFotoAgenciaAddEdit:
                Funciones.tomarFoto(this, REQUEST_IMAGE_CAPTURE);
                break;

            case R.id.btnMapaAgenciaAddEdit:
                verMapa();
                break;

        }
    }



    @Override
    public boolean onTouch(View v, MotionEvent event) {
        usuarioTocaPantalla = true ; //significa que el usuario ha tocado alguno de los spinner
        //Si el usuario ha tocado alguno de los spinner, debe cargar la provincia y los distritos
        return  false;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        //Solo se hace la carga en cascada cuando el usuario toca el spinner o toca la pantalla
        if (usuarioTocaPantalla == true){ //Siginifica que el usuario tocó alguno de los spinner
            switch (adapterView.getId()){
                case R.id.spDepartamentoAddEdit:
                {
                    String codigoDepartamento = Departamento.listaDepartamentos.get(spDepartamento.getSelectedItemPosition()).getCodigoDepartamento();
                    cargarSpinnerProvincia(codigoDepartamento);
                }break;
                case R.id.spProvinciaAddEdit:
                {
                    String codigoDepartamento = Departamento.listaDepartamentos.get(spDepartamento.getSelectedItemPosition()).getCodigoDepartamento();
                    String codigoProvincia = Provincia.listaProvincias.get(spProvincia.getSelectedItemPosition()).getCodigoProvincia();
                    cargarSpinnerDistrito(codigoDepartamento, codigoProvincia);
                }break;

            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    private void grabarDatosBD(){
        //validaciones
        if (txtNombreAgenciaAddEdit.getText().toString().isEmpty()){
            Toast.makeText(this, "Debe ingresar el nombre", Toast.LENGTH_SHORT).show();
            return;

        }else if (txtTelefonoAgenciaAddEdit.getText().toString().isEmpty()) {
            Toast.makeText(this, "Debe ingresar el telefono", Toast.LENGTH_SHORT).show();
            return;

        }else if (txtHorarioAtencionAgenciaAddEdit.getText().toString().isEmpty()) {
            Toast.makeText(this, "Debe ingresar el horario de atención del lugar turístico", Toast.LENGTH_SHORT).show();
            return;

        }else if (txtTelefonoAgenciaAddEdit.getText().toString().isEmpty()) {
            Toast.makeText(this, "Debe ingresar el telefono", Toast.LENGTH_SHORT).show();
            return;
        }

        //Instanciar a la clase
        Agencia obj = new Agencia();

        //Obtener los datos seleccionados en los Spinner
        String codDep = Distrito.listaDistritos.get(spDistrito.getSelectedItemPosition()).getCodigoDepartamento();
        String codProv = Distrito.listaDistritos.get(spDistrito.getSelectedItemPosition()).getCodigoProvincia();
        String codDis = Distrito.listaDistritos.get(spDistrito.getSelectedItemPosition()).getCodigoDistrito();

        //Cargar los datos al objeto
        obj.setCodigo_tipo(Tipo.listaTipo.get(spTipoAgenciaAddEdit.getSelectedItemPosition()  ).getCodigo_tipo()  );
        obj.setCodigo_entidad(Entidad.listaEntidad.get(spEntidadAddEdit.getSelectedItemPosition()  ).getCodigo_entidad()  );
        obj.setNombre( txtNombreAgenciaAddEdit.getText().toString() );
        obj.setHorario_atencion(txtHorarioAtencionAgenciaAddEdit.getText().toString());
        obj.setTelefono(txtTelefonoAgenciaAddEdit.getText().toString());

        obj.setCodigo_departamento(codDep);
        obj.setCodigo_provincia(codProv);
        obj.setCodigo_distrito(codDis);


        if (imgFotoAgenciaAddEdit.getTag().equals("2")){
            //Si hay una foto válida
            obj.setFoto
                    (
                            Funciones.imageToBase64
                                    (
                                            ((BitmapDrawable)  imgFotoAgenciaAddEdit.getDrawable()).getBitmap()
                                    )
                    );

        }else {
            //No hay foto, se graba en nulo
            obj.setFoto(null);
        }

        obj.setLatitud(Double.parseDouble(txtLatitudAgenciaAddEdit.getText().toString()));
        obj.setLongitud(Double.parseDouble(txtLongitudAgenciaAddEdit.getText().toString()));
        obj.setDireccion(txtUbicacionAgenciaAddEdit.getText().toString());
        obj.setReferencia(txtReferenciaAgenciaAddEdit.getText().toString());



        long resultado = -1;

        if (tipoOperacion.equalsIgnoreCase("agregar")){
            //Log.e("agregar", "si");
            resultado = obj.agregar();

        }else{
            //Log.e("editar", "si");
            obj.setId_agencia(Integer.parseInt(txtCodigoAgenciaAddEdit.getText().toString()));
            resultado = obj.editar();
        }

        if (resultado != -1){ //Si ha ejecutado correctamente
            Toast.makeText(this, "Grabado correctamente", Toast.LENGTH_SHORT).show();
            this.setResult(Activity.RESULT_OK); //El activity actual se ha cerrado correctamente y ha devuelto el resultado esperado
            this.finish(); //Cerrando el activity
        }
    }



    private void leerDatos(int codigo_agencia){
        Agencia obj = new Agencia().leerDatos(codigo_agencia);
        txtNombreAgenciaAddEdit.setText(obj.getNombre());
        txtTelefonoAgenciaAddEdit.setText(obj.getTelefono());
        txtHorarioAtencionAgenciaAddEdit.setText(obj.getHorario_atencion());

        txtUbicacionAgenciaAddEdit.setText(obj.getDireccion());
        txtReferenciaAgenciaAddEdit.setText(obj.getReferencia());

        cargarSpinnerProvincia(obj.getCodigo_departamento());
        cargarSpinnerDistrito(obj.getCodigo_departamento(), obj.getCodigo_provincia());

        //Leer la categoría en el spinner
        Funciones.selectedItemSpinner(spTipoAgenciaAddEdit, obj.getNombre_tipo());
        Funciones.selectedItemSpinner(spEntidadAddEdit, obj.getNombre_entidad());

        Funciones.selectedItemSpinner(spDepartamento, obj.getNombre_departamento());
        Funciones.selectedItemSpinner(spProvincia, obj.getNombre_provincia());
        Funciones.selectedItemSpinner(spDistrito, obj.getNombre_distrito());


        txtCodigoAgenciaAddEdit.setText(String.valueOf(obj.getId_agencia()));

        //Leer las coordenadas para el mapa (latitud, longitud)
        txtLatitudAgenciaAddEdit.setText(String.valueOf(obj.getLatitud()));
        txtLongitudAgenciaAddEdit.setText(String.valueOf(obj.getLongitud()));

        //Leer la foto
        if (obj.getFoto()==null){
            imgFotoAgenciaAddEdit.setImageResource(R.drawable.ic_menu_camera);
            imgFotoAgenciaAddEdit.setTag("1"); //marcador de la foto 1
        }else{
            imgFotoAgenciaAddEdit.setImageBitmap(Funciones.base64ToImage(obj.getFoto()));
            imgFotoAgenciaAddEdit.setTag("2"); //marcador de la foto 2
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgFotoAgenciaAddEdit.setImageBitmap(imageBitmap);
            imgFotoAgenciaAddEdit.setTag("2");
        }

        if (requestCode == REQUEST_MAP && resultCode == RESULT_OK) {
            Bundle parametros = data.getExtras();
            txtLatitudAgenciaAddEdit.setText(String.valueOf(parametros.getDouble("latitud")));
            txtLongitudAgenciaAddEdit.setText(String.valueOf(parametros.getDouble("longitud")));
            this.txtUbicacionAgenciaAddEdit.setText(parametros.getString("direccion"));
        }
    }

    public static final int REQUEST_MAP = 2;

    private void verMapa(){
        Intent mapa = new Intent(this, AgenciaMapaActivity.class);
        Bundle parametro = new Bundle();

        if (tipoOperacion.equalsIgnoreCase("agregar")){
            parametro.putInt("p_cod_agencia", 0); //Cuando estamos agregando
        }else{
            int cod = Integer.parseInt(txtCodigoAgenciaAddEdit.getText().toString());
            parametro.putInt("p_cod_agencia", cod); //Cuando estamos editando enviamos el código del lugar turístico
        }

        mapa.putExtras(parametro);
        startActivityForResult(mapa, REQUEST_MAP);
    }
}
