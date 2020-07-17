package com.example.banco.adaptador;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banco.R;
import com.example.banco.logica.Agencia;
import com.example.banco.util.Funciones;

import java.util.ArrayList;

public class AdaptadorAgencia extends RecyclerView.Adapter<AdaptadorAgencia.ViewHolder> {

    private Context context;
    private ArrayList<Agencia>listaAgenciaAuxiliar;
    public int posicionItemSeleccionado;


    public AdaptadorAgencia(Context context){
        this.context=context;
        this.listaAgenciaAuxiliar= new ArrayList<>();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Permite enlazar el adaptador (AdaptadorLugarTuristico) con el archivo XML que contiene al CardView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.agencia_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Permite mostrar los datos del lugar turístico dentro de cada control del CradView

        //1. Crear un objeto de clase LugarTurisco para extraer los datos
        Agencia obj = listaAgenciaAuxiliar.get(position);

        //2. Mostrar los datos que estan en el objeto (obj) dentro de los controles del cardview
        holder.txtNombre.setText(obj.getNombre());
        holder.txtHorarioAtencion.setText(obj.getHorario_atencion());
        holder.txtTelefono.setText(String.valueOf(obj.getTelefono()));
        holder.txtTipo.setText(obj.getNombre_tipo());
        holder.txtEntidad.setText(obj.getNombre_entidad());
        holder.txtDepartamento.setText(obj.getNombre_departamento());
        holder.txtProvincia.setText(obj.getNombre_provincia());
        holder.txtDistrito.setText(obj.getNombre_distrito());

        //cargar la foto
        if (obj.getFoto() == null){ //Quiere decir que no hay foto cargada
            holder.imgAgencia.setImageResource(R.drawable.ic_menu_send);
        }else{
            //cargar la foto que esta almacenada en la base de datos en base64
            holder.imgAgencia.setImageBitmap(Funciones.base64ToImage(obj.getFoto()));
        }
    }

    @Override
    public int getItemCount() {
        return listaAgenciaAuxiliar.size();
    }

    public void cargarLista(ArrayList<Agencia> arrayList){
        //Perite recibir la lista de lugares turísticos que estan almacenados en el ArrayList Principal (Es el arrayList que tiene los registros de la BD)
        listaAgenciaAuxiliar = arrayList;

        //Permite notificar al adaptador que hay cambio en los registros o en los datos
        notifyDataSetChanged();

    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, View.OnLongClickListener{

        //Declarar y relacionar los controles que estan el CardView con el Java
        ImageView imgAgencia;
        TextView txtNombre, txtHorarioAtencion, txtTelefono, txtTipo, txtEntidad, txtDepartamento, txtProvincia, txtDistrito;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //Enlazar los controles java co los controles XML
            imgAgencia = itemView.findViewById(R.id.imgAgencia);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtHorarioAtencion = itemView.findViewById(R.id.txtHorario);
            txtTelefono = itemView.findViewById(R.id.txtTelefono);
            txtTipo = itemView.findViewById(R.id.txtTipo);
            txtEntidad = itemView.findViewById(R.id.txtEntidad);
            txtDepartamento=itemView.findViewById(R.id.txtDepartamento);
            txtProvincia=itemView.findViewById(R.id.txtProvincia);
            txtDistrito=itemView.findViewById(R.id.txtDistrito);

            itemView.setOnCreateContextMenuListener(this); //Permite crear un menú contextual dentro del cardview
            itemView.setOnLongClickListener(this); //Permite reconocer el evento clic prolongado dentro del cardview

            itemView.setOnCreateContextMenuListener(this); //Permite crear un menú contextual dentro del cardview
            itemView.setOnLongClickListener(this); //Permite reconocer el evento clic prolongado dentro del cardview
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            //Permite crear un menú contextual
            contextMenu.setHeaderTitle("Opciones");
            contextMenu.add(0,1,0,"Editar");
            contextMenu.add(0,2,0,"Eliminar");
            contextMenu.add(0,3,0,"Ver en el mapa");
        }

        @Override
        public boolean onLongClick(View v) {
            //Permite almacenar la posición actual del item seleccionado en el RecyclerView
            posicionItemSeleccionado = this.getAdapterPosition();
            return false;
        }
    }

}
