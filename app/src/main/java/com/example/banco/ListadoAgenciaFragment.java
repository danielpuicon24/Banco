package com.example.banco;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.banco.adaptador.AdaptadorAgencia;
import com.example.banco.datos.Conexion;
import com.example.banco.logica.Agencia;
import com.example.banco.util.Funciones;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListadoAgenciaFragment extends Fragment implements View.OnClickListener {
    RecyclerView agenciaoRecyclerView;
    FloatingActionButton fab;
    AdaptadorAgencia adaptador;

    public ListadoAgenciaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_listado_agencia, container, false);

        //Asignar un título
        this.getActivity().setTitle("Agencia");

        //Referenciar a la variable context que esta en la clase Conexion
        Conexion.context = this.getContext();

        //Configurar el RecyclerView
        agenciaoRecyclerView = (RecyclerView) view.findViewById(R.id.AgenciaoRecyclerView);
        agenciaoRecyclerView.setHasFixedSize(true);
        agenciaoRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        //Configurar el botón flotante
        fab = view.findViewById(R.id.fabAgregar);
        fab.setOnClickListener(this);

        //Enlazar el fragment con el adaptador
        adaptador = new AdaptadorAgencia(this.getContext());
        agenciaoRecyclerView.setAdapter(adaptador);

        //Llamar al método listar()
        this.listar();

        //Retornar la vista
        return view;

    }

    public static final int REQUEST_AGREGAR=1;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fabAgregar:
                Intent intent = new Intent(this.getActivity(),AgenciaAgregarEditar.class);
                startActivityForResult(intent, REQUEST_AGREGAR);
        }
    }


    private void listar(){
        //Permite ejecutar el método listar de la clase LugarTuristico

        Agencia obj = new Agencia();
        obj.listar(); //Ejecuta la consulta SQL y los datos que devuelve van al ArrayList

        //El array que ya esta lleno lo envío al adaptador
        adaptador.cargarLista(Agencia.lisaAgencia);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        //Identificar la opción de menú en la cual estoy haciendo clic (Editar/Eliminar/Ver en el mapa)
        switch (item.getItemId()){
            case 1: //Editar
                //Para leer los datos del lugar turístico se necesita leer el código del lugar turístico seleccioado
                int pos = adaptador.posicionItemSeleccionado;

                //Obtener el código del lugar turístico del item seleccinoado
                int codigo_agencia = Agencia.lisaAgencia.get(pos).getId_agencia();

                Bundle bundle = new Bundle();
                bundle.putInt("p_cod_agencia", codigo_agencia);

                Intent intent = new Intent(this.getActivity(), AgenciaAgregarEditar.class);
                intent.putExtras(bundle);

                startActivityForResult(intent, REQUEST_AGREGAR);

                break;

            case 2: //Eliminar
                Runnable deleteRun = new Runnable() {
                    @Override
                    public void run() {
                        Agencia obj = new Agencia();

                        //Para leer los datos del lugar turístico se necesita leer el código del lugar turístico seleccioado
                        int pos = adaptador.posicionItemSeleccionado;

                        //Obtener el código del lugar turístico del item seleccinoado
                        int codigo_agencia = Agencia.lisaAgencia.get(pos).getId_agencia();

                        obj.setId_agencia(codigo_agencia);
                        obj.eliminar();
                        listar();
                    }
                };

                Funciones.dialog(this.getActivity(), "Esta seguro de eliminar", "Si, eliminar", "No", deleteRun);

                break;

            case 3: //Ver en el mapa
                Intent mapa = new Intent(this.getActivity(), AgenciaMapaActivity.class);
                Bundle parametro = new Bundle();

                int cod = Agencia.lisaAgencia.get(adaptador.posicionItemSeleccionado).getId_agencia();
                parametro.putInt("p_cod_agencia", cod); //Cuando le hacemos clic en ver mapa

                mapa.putExtras(parametro);
                startActivity(mapa);
                break;

        }


        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_AGREGAR){ //Le pregunta si el activity que se ha carrado es el activity LugarTuristicoAgregarEditarActivity
            if (resultCode == Activity.RESULT_OK){ //Quiere decir que grabó correctamente
                listar();
            }
        }
    }
}
