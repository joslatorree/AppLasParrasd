package com.solmov.appmovillasparras.Modulos.Mantenimiento.Usuarios;

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
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.solmov.appmovillasparras.R;

import java.util.ArrayList;

public class    ListarUsuarioFragmentActivity extends Fragment {
    AdapterUsuario adapterPersona;
    RecyclerView recyclerViewPersonsa;
    ArrayList<ModeloUsuarios> arraylistapersonas;
    ProgressDialog progressDialog;

    public static String[] ListID;

    private FirestoreRecyclerAdapter adapterrec;
    private FirebaseFirestore firebaseFirestore;
  //  private RecyclerView mFirestorelist;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {

//        Toast.makeText(getContext(),"Cantidad GestList "+GestionarUsuariosActivity.list.size(),Toast.LENGTH_SHORT).show();

        View view = inflater.inflate(R.layout.activity_test,parent,false);
        arraylistapersonas =new ArrayList<>();

        firebaseFirestore= FirebaseFirestore.getInstance();
        recyclerViewPersonsa = view.findViewById(R.id.recyclerView);


        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching data....");
        progressDialog.show();

        recyclerViewPersonsa.setHasFixedSize(true);
        recyclerViewPersonsa.setLayoutManager(new LinearLayoutManager(getContext()));

        firebaseFirestore = FirebaseFirestore.getInstance();
        arraylistapersonas =new ArrayList<ModeloUsuarios>();
        adapterPersona= new AdapterUsuario(getContext(), arraylistapersonas);


        recyclerViewPersonsa.setAdapter(adapterPersona);

        EventChangeListener();
      // cargarlista();
       mostrarData();
        // Inflate the xml file for the fragment




        return view;
    }

    public void EventChangeListener(){


        //ACTUALIZARA LAS LISTAS PARA LLAMAR
        ((GestionarUsuariosActivity)getActivity()).ActualizarLista();

        firebaseFirestore.collection("Usuario").orderBy("Nombre",Query.Direction.ASCENDING)
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
                                arraylistapersonas.add(dc.getDocument().toObject(ModeloUsuarios.class));
                            }

                            adapterPersona.notifyDataSetChanged();
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    }
                });

    }

    private void mostrarData() {

       // EventChangeListener();
        recyclerViewPersonsa.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterPersona=new AdapterUsuario(getContext(),arraylistapersonas);
        recyclerViewPersonsa.setAdapter(adapterPersona);



        adapterPersona.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                String id = GestionarUsuariosActivity.list.get(recyclerViewPersonsa.getChildAdapterPosition(view));

                String apellidos= arraylistapersonas.get(recyclerViewPersonsa.getChildAdapterPosition(view)).getApellidos();
                String dni= arraylistapersonas.get(recyclerViewPersonsa.getChildAdapterPosition(view)).getDNI();
                String email= arraylistapersonas.get(recyclerViewPersonsa.getChildAdapterPosition(view)).getEmail();
                String estado= arraylistapersonas.get(recyclerViewPersonsa.getChildAdapterPosition(view)).getEstado();
                String movil= arraylistapersonas.get(recyclerViewPersonsa.getChildAdapterPosition(view)).getMovil();
                String nombre= arraylistapersonas.get(recyclerViewPersonsa.getChildAdapterPosition(view)).getNombre();
                String password= arraylistapersonas.get(recyclerViewPersonsa.getChildAdapterPosition(view)).getPassword();
                String sexo= arraylistapersonas.get(recyclerViewPersonsa.getChildAdapterPosition(view)).getSexo();
                String tipo_usuario= arraylistapersonas.get(recyclerViewPersonsa.getChildAdapterPosition(view)).getTipo_Usuario();
                String usuario= arraylistapersonas.get(recyclerViewPersonsa.getChildAdapterPosition(view)).getUsuario();


                Toast.makeText(getContext(),"Selecciono "+nombre,Toast.LENGTH_SHORT).show();

                listener.onPizzaItemSelected(
                        id,
                        apellidos,
                        dni,
                        email,
                        estado,
                        movil,
                        nombre,
                        password,
                        sexo,
                        tipo_usuario,
                        usuario
                        ); // (3) Communicate with Activity using Listener
            }


        });

    }

    private ListarUsuarioFragmentActivity.OnItemSelectedListener listener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ListarUsuarioFragmentActivity.OnItemSelectedListener){      // context instanceof YourActivity
            this.listener = (ListarUsuarioFragmentActivity.OnItemSelectedListener) context; // = (YourActivity) context
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement PizzaMenuFragment.OnItemSelectedListener");
        }
    }


    // Define the events that the fragment will use to communicate
    public interface OnItemSelectedListener {
        // This can be any number of events to be sent to the activity
        void onPizzaItemSelected(String id,
                                 String apellidos,
                                 String dni,
                                 String email,
                                 String estado,
                                 String movil,
                                 String nombre,
                                 String password,
                                 String sexo,
                                 String tipo_usuario,
                                 String usuario
                                 );
    }

    /*
    private void cargarlista() {

        listapersonas.add(new ModeloUsuarios(R.drawable.app_logo_2,"Anjali","10:35 AM","How Are You?","________________"));
        listapersonas.add(new ModeloUsuarios(R.drawable.button1_bg,"Sami","09:45 AM","Dar You?","________________"));
        listapersonas.add(new ModeloUsuarios(R.drawable.common_full_open_on_phone,"Alaah","08:15 AM","HASs You?","________________"));
        listapersonas.add(new ModeloUsuarios(R.drawable.common_google_signin_btn_icon_dark_focused,"Reaje","06:25 AM","Hasd You?","________________"));
        listapersonas.add(new ModeloUsuarios(R.drawable.common_google_signin_btn_icon_disabled,"Reagana","11:30 AM","Hoasde You?","________________"));


    }

*/

}