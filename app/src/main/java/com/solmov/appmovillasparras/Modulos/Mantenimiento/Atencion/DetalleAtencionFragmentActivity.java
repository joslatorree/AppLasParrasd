package com.solmov.appmovillasparras.Modulos.Mantenimiento.Atencion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.solmov.appmovillasparras.R;

import java.util.HashMap;
import java.util.Map;

public class DetalleAtencionFragmentActivity extends Fragment {



    //int position = 0;
    String position;

    String id;
    String nombre;
    String tipo_producto;
    String descripcion;
    String precio;
    String estado;


    TextView tvnombre;
    TextView tvtipo_producto;
    TextView tvdescripcion;
    TextView tvprecio;
    TextView tvestado;


    Integer value=0;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        // Toast.makeText(getContext(), "Valor Inicio DetalleUsuario "+value, Toast.LENGTH_SHORT).show();
        if(savedInstanceState == null){
            // Get back arguments
            if(getArguments() != null) {
                value=getArguments().getInt("value", 0);
                //position = getArguments().getInt("position", 0);
                id = getArguments().getString("id", "");
                nombre = getArguments().getString("nombre", "");
                tipo_producto = getArguments().getString("tipo_producto", "");
                descripcion = getArguments().getString("descripcion", "");
                precio = getArguments().getString("precio", "");
                estado = getArguments().getString("estado", "");

                //  Toast.makeText(getContext(), "Valor Get Arguments DetalleUsuario "+value, Toast.LENGTH_SHORT).show();
            }

        }

    }



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {


        // Inflate the xml file for the fragment
        return inflater.inflate(R.layout.activity_modificar_atencion_fragment, parent, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {



        tvnombre = (TextView) view.findViewById(R.id.Nombre);
        tvtipo_producto = (TextView) view.findViewById(R.id.TipoProducto);
        tvdescripcion = (TextView) view.findViewById(R.id.Descripcion);
        tvprecio = (TextView) view.findViewById(R.id.Precio);
        tvestado= (TextView) view.findViewById(R.id.Estado);


        if(value!=0)
        {
            value=0;
            // Toast.makeText(getContext(), "Valor OnViewCreated DetalleUsuario "+value, Toast.LENGTH_SHORT).show();
            // Set values for view here


            // tvTitle = (TextView) view.findViewById(R.id.DNI);
            //
            // tvDetails = (TextView) view.findViewById(R.id.Nombre);

            // update view
            tvnombre.setText(nombre);
            tvtipo_producto.setText(tipo_producto);
            tvdescripcion.setText(descripcion);
            tvprecio.setText(precio);
            tvestado.setText(estado);

            // tvDetails.setText(GestionarUsuariosActivity.pizzaDetails[position]);

            Button btn= (Button) view.findViewById(R.id.Ejecutar);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Valor OnViewCreated DetalleAtencion MODIFICAR " +value, Toast.LENGTH_SHORT).show();
                    updateView();
                }
            });

        }

        else
        {

            Toast.makeText(getContext(), "Valor OnViewCreated DetalleAtencion AGREGAR " +value, Toast.LENGTH_SHORT).show();
            Button btn= (Button) view.findViewById(R.id.Ejecutar);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!tvnombre.getText().toString().isEmpty())
                    {
                        AgregarView();
                    }
                    else {
                        Toast.makeText(getContext(), "Rellene al menos el campo Nombre ", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

        //ACTUALIZARA LAS LISTAS DEL MAIN ACTIVITY (GESTIONAR USUARIOS)
        ((GestionarAtencionActivity)getActivity()).ActualizarLista();

    }




    public void AgregarView( ){


        FirebaseFirestore db=FirebaseFirestore.getInstance();

        String idagreg = db.collection("Productos").document().getId();

        DocumentReference noteRef = db.collection("Productos").document(idagreg);

        Map<String, Object> user = new HashMap<>();
        user.put("Nombre",tvnombre.getText().toString());
        user.put("Tipo_Producto",tvtipo_producto.getText().toString());
        user.put("Descripcion",tvdescripcion.getText().toString());
        user.put("Precio",tvprecio.getText().toString());
        user.put("Estado",tvestado.getText().toString());


        noteRef.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful())
                {
                    Toast.makeText(getContext(), "Se Agrego", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "No se Agrego ", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

    // Activity is calling this to update view on Fragment
    public void updateView( ){


        FirebaseFirestore db=FirebaseFirestore.getInstance();

        DocumentReference noteRef = db.collection("Productos").document(id);


        noteRef.update("Nombre",tvnombre.getText().toString(),
                "Tipo_Producto",tvtipo_producto.getText().toString(),
                "Descripcion",tvdescripcion.getText().toString(),
                "Precio",tvprecio.getText().toString(),
                "Estado",tvestado.getText().toString()
        )
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(getContext(), "Se Modifico", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getContext(), "No se Modifico ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


/*
        tvapellidos.setText(GestionarUsuariosActivity.pizzaMenu[0]);
        tvdni.setText(GestionarUsuariosActivity.pizzaMenu[1]);
        tvemail.setText(GestionarUsuariosActivity.pizzaMenu[2]);
        tvestado.setText(GestionarUsuariosActivity.pizzaMenu[3]);
        tvmovil.setText(GestionarUsuariosActivity.pizzaMenu[4]);
        tvnombre.setText(GestionarUsuariosActivity.pizzaMenu[5]);
        tvpassword.setText(GestionarUsuariosActivity.pizzaMenu[6]);
        tvsexo.setText(GestionarUsuariosActivity.pizzaMenu[7]);
        tvtipo_usuario.setText(GestionarUsuariosActivity.pizzaMenu[8]);
        tvusuario.setText(GestionarUsuariosActivity.pizzaMenu[9]);
        */

    }
}
