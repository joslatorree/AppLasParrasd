package com.solmov.appmovillasparras.Modulos.Atencion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.solmov.appmovillasparras.Modulos.Mantenimiento.Atencion.GestionarAtencionActivity;
import com.solmov.appmovillasparras.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DetalleProductoFragmentActivity extends Fragment {



    //int position = 0;
    String position;

    //modelo atencion
    String id;
    String nombre;
    String tipo_producto;
    String descripcion;
    String precio;
    String cantidad;
    String estado;


    TextView tvcantidad;
    TextView tvnombre;
    TextView tvtipo_producto;
    TextView tvdescripcion;
    TextView tvprecio;
    TextView tvestado;


    Integer value=0;
    Integer valueapertura=0;
    Integer calculototal=0;
    Integer valorAperturaDetallePedido=0;



    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;

   // Button buttonApertura;
    Button buttonApertura;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mAuth=FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();

        // Toast.makeText(getContext(), "Valor Inicio DetalleUsuario "+value, Toast.LENGTH_SHORT).show();
        if(savedInstanceState == null){
            // Get back arguments
            if(getArguments() != null) {
                calculototal=getArguments().getInt("calculototal", 0);
                valueapertura=getArguments().getInt("valueapertura", 0);
                valorAperturaDetallePedido=getArguments().getInt("valorAperturaDetallePedido", 0);
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
        return inflater.inflate(R.layout.activity_list_solicitar_pedido, parent, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {



        tvnombre = (TextView) view.findViewById(R.id.Nombre);
        tvtipo_producto = (TextView) view.findViewById(R.id.TipoProducto);
        tvdescripcion = (TextView) view.findViewById(R.id.Descripcion);
        tvprecio = (TextView) view.findViewById(R.id.Precio);
        tvcantidad=(TextView) view.findViewById(R.id.Cantidad);
        tvestado=(TextView) view.findViewById(R.id.Estado);

        tvnombre.setFocusable(false);
        tvtipo_producto.setFocusable(false);
        tvdescripcion.setFocusable(false);
        tvprecio.setFocusable(false);
        tvestado.setFocusable(false);


        if(value!=0)
        {
            tvnombre.setText(nombre);
            tvtipo_producto.setText(tipo_producto);
            tvdescripcion.setText(descripcion);
            tvprecio.setText(precio);
            tvcantidad.setText(cantidad);
            tvestado.setText(estado);

            // tvDetails.setText(GestionarUsuariosActivity.pizzaDetails[position]);


            //DETERMINAR SI AGREGA O NO


            buttonApertura = view.findViewById(R.id.Ejecutar);
            VerificacionDeApertura();

        //ACTUALIZARA LAS LISTAS DEL MAIN ACTIVITY (GESTIONAR USUARIOS)
        ((SolicitarPedido)getActivity()).ActualizarLista();
        }
    }


    public void VerificacionDeApertura( ){

        if(SolicitarPedido.valorAperturaPedido!=0)
        {
            buttonApertura.setEnabled(true);
            buttonApertura.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AgregarView( );
                    Toast.makeText(getContext(), "Valor OnViewCreated DetallePedido AgregarPEDIDO " +value, Toast.LENGTH_SHORT).show();
                    // updateView();
                    value=0;
                }
            });
        }
        else
        {
            buttonApertura.setEnabled(false);
            Toast.makeText(getContext(), "No ha escogido si va a agregar un pedido ", Toast.LENGTH_SHORT).show();

        }
    }



    public void AgregarView( ){


        FirebaseFirestore db=FirebaseFirestore.getInstance();


        //String idagreg = db.collection("Pedidos").document(SolicitarPedido.IDPEDIDO).get();
        //conseguir ID
        String idagreg = db.collection("Pedidos").document(SolicitarPedido.IDPEDIDO).collection("Detalle_Pedido").document().getId();
        //Ingresar ID
        DocumentReference noteRef = db.collection("Pedidos").document(SolicitarPedido.IDPEDIDO).collection("Detalle_Pedido").document(idagreg);

        Map<String, Object> detallepedido = new HashMap<>();
        detallepedido.put("Cantidad",tvcantidad.getText().toString());
        detallepedido.put("Nombre",tvnombre.getText().toString());
        detallepedido.put("Precio", String.valueOf((Integer.parseInt(tvprecio.getText().toString()) * Integer.parseInt(tvcantidad.getText().toString()))) );
        //calcula bien? a probar
        ListarProductoFragmentActivity.calculototal= ListarProductoFragmentActivity.calculototal + (Integer.parseInt(tvprecio.getText().toString()) * Integer.parseInt(tvcantidad.getText().toString())) ;



        /*
        Map<String, Object> user = new HashMap<>();
        user.put("Nombre",tvnombre.getText().toString());
        user.put("Tipo_Producto",tvtipo_producto.getText().toString());
        user.put("Descripcion",tvdescripcion.getText().toString());
        user.put("Precio",tvprecio.getText().toString());

         */

        noteRef.set(detallepedido).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful())
                {


                    ActualizarPedido();
                    Toast.makeText(getContext(), "Se Agrego", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "No se Agrego ", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

    public void ActualizarPedido()
    {
        FirebaseFirestore db=FirebaseFirestore.getInstance();

        DocumentReference noteRef = db.collection("Pedidos").document(SolicitarPedido.IDPEDIDO);

        //noteRef.update("PrecioTotal",String.valueOf((Integer.parseInt(tvprecio.getText().toString()) * Integer.parseInt(tvcantidad.getText().toString())))
                noteRef.update("PrecioTotal",String.valueOf( ListarProductoFragmentActivity.calculototal)
        )
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(getContext(), "Se Modifico pedido", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getContext(), "No se Modifico pedido", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
