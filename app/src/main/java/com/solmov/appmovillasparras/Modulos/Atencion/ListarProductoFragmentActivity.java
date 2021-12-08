package com.solmov.appmovillasparras.Modulos.Atencion;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.solmov.appmovillasparras.Modulos.Mantenimiento.Atencion.AdapterAtencion;
import com.solmov.appmovillasparras.Modulos.Mantenimiento.Atencion.GestionarAtencionActivity;
import com.solmov.appmovillasparras.Modulos.Mantenimiento.Atencion.ModeloAtencion;
import com.solmov.appmovillasparras.R;

import java.util.ArrayList;

public class ListarProductoFragmentActivity extends Fragment {


    AdapterAtencion adapterPersona;
    RecyclerView recyclerViewPersonsa;
    ArrayList<ModeloAtencion> arraylistapersonas;
    ProgressDialog progressDialog;

    private FirebaseAuth mAuth;
    public static String[] ListID;

    private FirestoreRecyclerAdapter adapterrec;
    private FirebaseFirestore firebaseFirestore;
    //  private RecyclerView mFirestorelist;


    public static Integer calculototal=0;

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
        progressDialog.setMessage("Fetching data....");
        progressDialog.show();

        recyclerViewPersonsa.setHasFixedSize(true);
        recyclerViewPersonsa.setLayoutManager(new LinearLayoutManager(getContext()));

        firebaseFirestore = FirebaseFirestore.getInstance();
        arraylistapersonas =new ArrayList<ModeloAtencion>();
        adapterPersona= new AdapterAtencion(getContext(), arraylistapersonas);



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
        adapterPersona=new AdapterAtencion(getContext(),arraylistapersonas);
        recyclerViewPersonsa.setAdapter(adapterPersona);



        adapterPersona.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                String id = GestionarAtencionActivity.list.get(recyclerViewPersonsa.getChildAdapterPosition(view));

                String nombre= arraylistapersonas.get(recyclerViewPersonsa.getChildAdapterPosition(view)).getNombre();
                String descripcion= arraylistapersonas.get(recyclerViewPersonsa.getChildAdapterPosition(view)).getDescripcion();
                String precio= arraylistapersonas.get(recyclerViewPersonsa.getChildAdapterPosition(view)).getPrecio();
                String tipo_producto= arraylistapersonas.get(recyclerViewPersonsa.getChildAdapterPosition(view)).getTipo_Producto();
                String estado= arraylistapersonas.get(recyclerViewPersonsa.getChildAdapterPosition(view)).getEstado();



                Toast.makeText(getContext(),"Selecciono "+nombre,Toast.LENGTH_SHORT).show();


                listener.onProductoItemSelected(
                        id,
                        nombre,
                        tipo_producto,
                        descripcion,
                        precio,
                        estado,
                        calculototal.toString()
                ); // (3) Communicate with Activity using Listener
            }
        });

    }
    private ListarProductoFragmentActivity.OnItemSelectedListener listener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ListarProductoFragmentActivity.OnItemSelectedListener){      // context instanceof YourActivity
            this.listener = (ListarProductoFragmentActivity.OnItemSelectedListener) context; // = (YourActivity) context
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement PizzaMenuFragment.OnItemSelectedListener");
        }
    }
    public void EventChangeListener(){


        //ACTUALIZARA LAS LISTAS PARA LLAMAR
        ((SolicitarPedido)getActivity()).ActualizarLista();

        firebaseFirestore.collection("Productos").whereEqualTo("Estado","Activo")
                .orderBy("Nombre", Query.Direction.ASCENDING)
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
                                arraylistapersonas.add(dc.getDocument().toObject(ModeloAtencion.class));
                            }

                            adapterPersona.notifyDataSetChanged();
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    }
                });

    }

    public interface OnItemSelectedListener {
        // This can be any number of events to be sent to the activity
        void onProductoItemSelected(String id,
                                 String nombre,
                                 String tipo_producto,
                                 String descripcion,
                                 String precio,
                                    String estado,
                                    String calculototal
        );


    }
}
