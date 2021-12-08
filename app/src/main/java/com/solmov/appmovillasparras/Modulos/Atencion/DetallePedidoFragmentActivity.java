package com.solmov.appmovillasparras.Modulos.Atencion;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.solmov.appmovillasparras.Modulos.Mantenimiento.Atencion.AdapterAtencion;
import com.solmov.appmovillasparras.Modulos.Mantenimiento.Atencion.GestionarAtencionActivity;
import com.solmov.appmovillasparras.Modulos.Mantenimiento.Atencion.ModeloAtencion;
import com.solmov.appmovillasparras.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DetallePedidoFragmentActivity extends Fragment {


    //modelo pedidos
    String id;
    String estadopedido;
    String fechapedido;
    String horapedido;
    String preciototalpedido;
    String usuariopedido;


    TextView tvestadopedido;
    TextView tvfechapedido;
    TextView tvhorapedido;
    TextView tvpreciototalpedido;
    TextView tvusuariopedido;


    Integer value=0;
    Integer valueapertura=0;
    Integer valorpermiso=0;


    AdapterDetallePedidos adapterPersona;
    RecyclerView recyclerViewPersonsa;
    ArrayList<ModeloDetallePedidos> arraylistapersonas;
    ProgressDialog progressDialog;

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
              //  valorpermiso=getArguments().getInt("valorpermiso", 0);
                valueapertura=getArguments().getInt("valueapertura", 0);
                value=getArguments().getInt("value", 0);
                //position = getArguments().getInt("position", 0);
                id = getArguments().getString("id", "");
                estadopedido = getArguments().getString("estadopedido", "");
                fechapedido = getArguments().getString("fechapedido", "");
                horapedido = getArguments().getString("horapedido", "");
                preciototalpedido = getArguments().getString("preciototalpedido", "");
                usuariopedido = getArguments().getString("usuariopedido", "");

                //  Toast.makeText(getContext(), "Valor Get Arguments DetalleUsuario "+value, Toast.LENGTH_SHORT).show();
            }

        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_list_atencion,parent,false);
        arraylistapersonas =new ArrayList<>();

        mAuth=FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();
        recyclerViewPersonsa = view.findViewById(R.id.recyclerView);
        recyclerViewPersonsa.setEnabled(false);
        recyclerViewPersonsa.setClickable(false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching data....");
        progressDialog.show();

        recyclerViewPersonsa.setHasFixedSize(true);
        recyclerViewPersonsa.setLayoutManager(new LinearLayoutManager(getContext()));

        firebaseFirestore = FirebaseFirestore.getInstance();
        arraylistapersonas =new ArrayList<ModeloDetallePedidos>();
        adapterPersona= new AdapterDetallePedidos(getContext(), arraylistapersonas);



        recyclerViewPersonsa.setAdapter(adapterPersona);

        EventChangeListener();
        // cargarlista();
        mostrarData();
        // Inflate the xml file for the fragment


/*
        firebaseFirestore.collection("Usuario").whereArrayContains("Email",mAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {



                            Toast.makeText(getContext(), "Se consiguio la info", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getContext(), "No se consiguio la info ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
*/

        return view;
    }

    private void mostrarData() {

        // EventChangeListener();
        recyclerViewPersonsa.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterPersona=new AdapterDetallePedidos(getContext(),arraylistapersonas);
        recyclerViewPersonsa.setAdapter(adapterPersona);



        adapterPersona.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                 String id = GestionarAtencionActivity.list.get(recyclerViewPersonsa.getChildAdapterPosition(view));

                String nombre= arraylistapersonas.get(recyclerViewPersonsa.getChildAdapterPosition(view)).getNombre();
                String cantidad= arraylistapersonas.get(recyclerViewPersonsa.getChildAdapterPosition(view)).getCantidad();
                String precio= arraylistapersonas.get(recyclerViewPersonsa.getChildAdapterPosition(view)).getPrecio();


                //Toast.makeText(getContext(),"Selecciono "+nombre,Toast.LENGTH_SHORT).show();

                /*
                listener.onPizzaItemSelected(
                        id,
                        nombre,
                        tipo_producto,
                        descripcion,
                        precio




                ); // (3) Communicate with Activity using Listener

                 */
            }


        });

    }

    public void EventChangeListener(){


        //ACTUALIZARA LAS LISTAS PARA LLAMAR
        ((SolicitarPedido)getActivity()).ActualizarLista();

        firebaseFirestore.collection("Pedidos").
                document(id).
                collection("Detalle_Pedido").
                orderBy("Nombre", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error!=null)
                        {
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                            Log.e("Firestore error",error.getMessage());
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges())
                        {
                            if(dc.getType()==DocumentChange.Type.ADDED)
                            {
                                arraylistapersonas.add(dc.getDocument().toObject(ModeloDetallePedidos.class));
                            }

                            adapterPersona.notifyDataSetChanged();
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    }
                });

    }
/*
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {



        /*
        tvestadopedido = (TextView) view.findViewById(R.id.Nombre);
        tvfechapedido = (TextView) view.findViewById(R.id.TipoProducto);
        tvhorapedido = (TextView) view.findViewById(R.id.Descripcion);
        tvpreciototalpedido = (TextView) view.findViewById(R.id.Precio);
        tvusuariopedido = (TextView) view.findViewById(R.id.Precio);

        tvestadopedido.setFocusable(false);
        tvfechapedido.setFocusable(false);
        tvhorapedido.setFocusable(false);
        tvpreciototalpedido.setFocusable(false);
        tvusuariopedido.setFocusable(false);


        if(value!=0)
        {
            /*
            tvnombre.setText(nombre);
            tvtipo_producto.setText(tipo_producto);
            tvdescripcion.setText(descripcion);
            tvprecio.setText(precio);

            // tvDetails.setText(GestionarUsuariosActivity.pizzaDetails[position]);


            //DETERMINAR SI AGREGA O NO


            buttonApertura = view.findViewById(R.id.Ejecutar);
            VerificacionDeApertura();

            //ACTUALIZARA LAS LISTAS DEL MAIN ACTIVITY (GESTIONAR USUARIOS)
            ((SolicitarPedido)getActivity()).ActualizarLista();
        }
    }

*/
    public void VerificacionDeApertura( ){

        if(SolicitarPedido.valorAperturaPedido!=0)
        {
            buttonApertura.setEnabled(true);
            buttonApertura.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                  //  Toast.makeText(getContext(), "Valor OnViewCreated DetallePedido AgregarPEDIDO " +value, Toast.LENGTH_SHORT).show();
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

        String idagreg = db.collection("Pedidos").document().getId();

        DocumentReference noteRef = db.collection("Pedidos").document(idagreg);

        Map<String, Object> pedido = new HashMap<>();
        pedido.put("Estado","En Proceso");

        //calculo fecha
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'");
        Date date = new Date();
        String dateTime = dateFormat.format(date);

        //calculo hora
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss'Z'");
        Date date2 = new Date();
        String dateTime2 = dateFormat2.format(date2);

        pedido.put("Fecha",dateTime);

        pedido.put("Hora",dateTime2);

        pedido.put("PrecioTotal","0");


        //conseguir usuario
        /*
        firebaseFirestore.collection("Usuario").whereArrayContains("Email",mAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull  Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {



                    Toast.makeText(getContext(), "Se consiguio la info", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "No se consiguio la info ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        */
        pedido.put("Usuario","usuariox");


        /**/
        Map<String, Object> user = new HashMap<>();
        user.put("Nombre",tvusuariopedido.getText().toString());
        user.put("Tipo_Producto",tvpreciototalpedido.getText().toString());
        user.put("Descripcion",tvestadopedido.getText().toString());
        user.put("Precio",tvfechapedido.getText().toString());


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
}


