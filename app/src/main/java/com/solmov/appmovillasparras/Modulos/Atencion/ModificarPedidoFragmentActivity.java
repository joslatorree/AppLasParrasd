package com.solmov.appmovillasparras.Modulos.Atencion;

import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.solmov.appmovillasparras.Modulos.Mantenimiento.Atencion.GestionarAtencionActivity;
import com.solmov.appmovillasparras.R;

import java.util.HashMap;
import java.util.Map;

public class ModificarPedidoFragmentActivity extends Fragment {



    //int position = 0;
    String position;

    String id;
    String estado;
    String fecha;
    String hora;
    String preciototal;
    String usuario;


    TextView tvestado;
    TextView tvfecha;
    TextView tvhora;
    TextView tvpreciototal;
    TextView tvusuario;


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
            estado = getArguments().getString("estado", "");
            fecha = getArguments().getString("fecha", "");
            hora = getArguments().getString("hora", "");
            preciototal = getArguments().getString("preciototal", "");
            usuario = getArguments().getString("usuario", "");
            //Toast.makeText(getContext(), "Valor Get Arguments DetalleUsuario "+value, Toast.LENGTH_SHORT).show();
        }

    }

}



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {


        // Inflate the xml file for the fragment
        return inflater.inflate(R.layout.activity_modificar_pedido_fragment, parent, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        tvestado = (TextView) view.findViewById(R.id.Estado);
        tvfecha = (TextView) view.findViewById(R.id.Fecha);
        tvhora = (TextView) view.findViewById(R.id.Hora);
        tvpreciototal = (TextView) view.findViewById(R.id.PrecioTotal);
        tvusuario = (TextView) view.findViewById(R.id.Usuario);


        tvfecha.setFocusable(false);
        tvhora.setFocusable(false);
        tvpreciototal.setFocusable(false);
        tvusuario.setFocusable(false);


            tvestado.setText(estado);
            tvfecha.setText(fecha);
            tvhora.setText(hora);
            tvpreciototal.setText(preciototal);
            tvusuario.setText(usuario);

            // tvDetails.setText(GestionarUsuariosActivity.pizzaDetails[position]);

            Button btn= (Button) view.findViewById(R.id.Ejecutar);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Valor OnViewCreated DetalleAtencion MODIFICAR " +value, Toast.LENGTH_SHORT).show();
                    updateView();
                    value=0;
                }
            });
        //ACTUALIZARA LAS LISTAS DEL MAIN ACTIVITY (SolicitarPedido)
        ((SolicitarPedido)getActivity()).ActualizarLista();

    }





    // Activity is calling this to update view on Fragment
    public void updateView( ){
        FirebaseFirestore db=FirebaseFirestore.getInstance();

        DocumentReference noteRef = db.collection("Pedidos").document(id);


        noteRef.update("Usuario",tvusuario.getText().toString(),
                "PrecioTotal",tvpreciototal.getText().toString(),
                "Hora",tvhora.getText().toString(),
                "Fecha",tvfecha.getText().toString(),
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

    }
}
