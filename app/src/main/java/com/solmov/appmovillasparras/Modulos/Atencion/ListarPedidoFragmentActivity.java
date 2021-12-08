package com.solmov.appmovillasparras.Modulos.Atencion;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.solmov.appmovillasparras.Modulos.Mantenimiento.Atencion.AdapterAtencion;
import com.solmov.appmovillasparras.Modulos.Mantenimiento.Atencion.GestionarAtencionActivity;
import com.solmov.appmovillasparras.Modulos.Mantenimiento.Atencion.ModeloAtencion;
import com.solmov.appmovillasparras.R;

import java.util.ArrayList;
import java.util.List;

public class ListarPedidoFragmentActivity extends Fragment {


    AdapterPedidos adapterPersona;
    RecyclerView recyclerViewPersonsa;
    ArrayList<ModeloPedidos> arraylistapersonas;
    ProgressDialog progressDialog;

    ArrayList<Integer> ListValor;

    private FirebaseAuth mAuth;
    public static String[] ListID;

    String fechaconsulta;
    private FirestoreRecyclerAdapter adapterrec;
    private FirebaseFirestore firebaseFirestore;
    Integer ingresototal=0;
    public  static  Integer valoradecuado;
    //  private RecyclerView mFirestorelist;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mAuth=FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();

        if(savedInstanceState == null){
            // Get back arguments
            if(getArguments() != null) {
                fechaconsulta=getArguments().getString("fechaconsulta", "");


            }

        }

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {

//        Toast.makeText(getContext(),"Cantidad GestList "+GestionarUsuariosActivity.list.size(),Toast.LENGTH_SHORT).show();

        View view = inflater.inflate(R.layout.activity_list_atencion,parent,false);
        arraylistapersonas =new ArrayList<>();

        mAuth=FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();
        recyclerViewPersonsa = view.findViewById(R.id.recyclerView);
        recyclerViewPersonsa.setEnabled(false);
        recyclerViewPersonsa.setClickable(false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);

        //tal vez por aqui arreglar
        progressDialog.setMessage("Fetching data....");
        progressDialog.show();

        recyclerViewPersonsa.setHasFixedSize(true);
        recyclerViewPersonsa.setLayoutManager(new LinearLayoutManager(getContext()));

        firebaseFirestore = FirebaseFirestore.getInstance();
        arraylistapersonas =new ArrayList<ModeloPedidos>();
        adapterPersona= new AdapterPedidos(getContext(), arraylistapersonas);



        recyclerViewPersonsa.setAdapter(adapterPersona);



        ListValor =new ArrayList<>();
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
            adapterPersona=new AdapterPedidos(getContext(),arraylistapersonas);
            recyclerViewPersonsa.setAdapter(adapterPersona);


            adapterPersona.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {

                    String id = SolicitarPedido.list.get(recyclerViewPersonsa.getChildAdapterPosition(view));

                    String usuario= arraylistapersonas.get(recyclerViewPersonsa.getChildAdapterPosition(view)).getUsuario();
                    String estado= arraylistapersonas.get(recyclerViewPersonsa.getChildAdapterPosition(view)).getEstado();
                    String fecha= arraylistapersonas.get(recyclerViewPersonsa.getChildAdapterPosition(view)).getFecha();
                    String hora= arraylistapersonas.get(recyclerViewPersonsa.getChildAdapterPosition(view)).getHora();
                    String preciototal= arraylistapersonas.get(recyclerViewPersonsa.getChildAdapterPosition(view)).getPrecioTotal();


                    Toast.makeText(getContext(),"Selecciono "+usuario,Toast.LENGTH_SHORT).show();

                    listener.onPedidoItemSelected(
                            id,
                            usuario,
                            estado,
                            fecha,
                            hora,
                            preciototal
                    ); // (3) Communicate with Activity using Listener
                }


            });



    }
    private ListarPedidoFragmentActivity.OnItemSelectedListener listener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ListarPedidoFragmentActivity.OnItemSelectedListener){      // context instanceof YourActivity
            this.listener = (ListarPedidoFragmentActivity.OnItemSelectedListener) context; // = (YourActivity) context
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement PizzaMenuFragment.OnItemSelectedListener");
        }
    }

    public static List<String> list;

    public void ActualizarLista()
    {
        //Modificar?
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference notebookRef =db.collection("Pedidos");
        DocumentSnapshot lastresult;

        db.collection("Pedidos").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            private static final String TAG = "Work";

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    list = new ArrayList<>();

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        list.add(document.getId());
                    }
                    Log.d(TAG, list.toString());
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }

            }
        });

    }

    public void EventChangeListener(){


        //ACTUALIZARA LAS LISTAS PARA LLAMAR
       ActualizarLista();



                if(fechaconsulta!=null)
                {

                    ConsultaPorFecha();

                }
                else
                {
                    firebaseFirestore.collection("Pedidos").orderBy("Estado", Query.Direction.ASCENDING)
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
                                            arraylistapersonas.add(dc.getDocument().toObject(ModeloPedidos.class));
                                        }

                                        adapterPersona.notifyDataSetChanged();
                                        if(progressDialog.isShowing())
                                            progressDialog.dismiss();
                                    }
                                }
                            });
                }





    }


    public void ConsultaPorFecha(){


        //ACTUALIZARA LAS LISTAS PARA LLAMAR
        //ActualizarLista();

        firebaseFirestore.collection("Pedidos").whereEqualTo( "Fecha",fechaconsulta)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        Integer i=0;
                        if(error!=null )
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
                                arraylistapersonas.add(dc.getDocument().toObject(ModeloPedidos.class));
                                /*
                                ingresototal = ingresototal + Integer.parseInt(arraylistapersonas.get(i).getPrecioTotal());
                                ListValor.add(ingresototal);
                                Toast.makeText(getContext(),"Selecciono "+arraylistapersonas.get(i).getPrecioTotal(),Toast.LENGTH_SHORT).show();
*/

                            }
                            adapterPersona.notifyDataSetChanged();
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                       /*     Toast.makeText(getContext(),"Ingreso total "+ingresototal,Toast.LENGTH_SHORT).show();
                            i++;*/
                        }


                /*        Toast.makeText(getContext(),"Ingreso pre  "+ingresototal,Toast.LENGTH_SHORT).show();
                        Toast.makeText(getContext(),"Ingreso final  "+ListValor.get(ListValor.size()-1),Toast.LENGTH_SHORT).show();
                        */
                    }

                });

    }
    public interface OnItemSelectedListener {
        // This can be any number of events to be sent to the activity
        void onPedidoItemSelected(String id,
                                 String usuario,
                                 String estado,
                                 String fecha,
                                 String hora,
                                 String preciototal
        );
    }
}
