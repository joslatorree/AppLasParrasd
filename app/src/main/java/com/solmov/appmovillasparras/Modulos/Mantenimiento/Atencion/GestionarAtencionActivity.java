package com.solmov.appmovillasparras.Modulos.Mantenimiento.Atencion;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.solmov.appmovillasparras.MainActivity;
import com.solmov.appmovillasparras.Modulos.Mantenimiento.Usuarios.GestionarUsuariosActivity;
import com.solmov.appmovillasparras.R;

import java.util.ArrayList;
import java.util.List;

public class GestionarAtencionActivity extends AppCompatActivity implements ListarAtencionFragmentActivity.OnItemSelectedListener{


    Integer value=0;
    ImageView mButtonAgregarAtencion;

    ImageView mButtonRegresarMenu;
    public static String[] pizzaMenu = new String[10];

    public static List<String> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_atencion);

        Log.d("DEBUG", getResources().getConfiguration().orientation + "");

        if (savedInstanceState == null) {
            // Instance of first fragment
            ListarAtencionFragmentActivity firstFragment = new ListarAtencionFragmentActivity();

            // Add Fragment to FrameLayout (flContainer), using FragmentManager
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();// begin  FragmentTransaction
            ft.add(R.id.flContainer, firstFragment);                                // add    Fragment
            ft.commit();                                                            // commit FragmentTransaction
        }




        //3ercer fragment Agregar Usuarios


        mButtonAgregarAtencion = findViewById(R.id.AgregarAtencion);


        mButtonAgregarAtencion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ActualizarLista();

                value=0;
                // Load Pizza Detail Fragment
                DetalleAtencionFragmentActivity thirdFragment = new DetalleAtencionFragmentActivity();

                Bundle args = new Bundle();
                args.putInt("value", value);
                //  args.putString("id", id);
                // args.putInt("position", position);


                thirdFragment.setArguments(args);          // (1) Communicate with Fragment using Bundle
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flContainer, thirdFragment) // replace flContainer
                        .addToBackStack(null)
                        .commit();





                //  startActivity(new Intent(GestionarUsuariosActivity.this, GestionarUsuariosActivity.class));
                // finish();
            }
        });

        mButtonRegresarMenu = findViewById(R.id.RegresarAMenu);


        mButtonRegresarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GestionarAtencionActivity.this, MainActivity.class));

            }
        });
        ActualizarLista();
    }


    public void ActualizarLista()
    {
        //Modificar?
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference notebookRef =db.collection("Productos");
        DocumentSnapshot lastresult;

        db.collection("Productos").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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





    @Override
    public void onPizzaItemSelected(String id,
                                    String nombre,
                                    String tipo_producto,
                                    String descripcion,
                                    String precio,
                                    String estado) {

        Toast.makeText(this, "Called By Fragment A: position - "+ nombre, Toast.LENGTH_SHORT).show();



        pizzaMenu[0]=nombre;
        pizzaMenu[1]=tipo_producto;
        pizzaMenu[2]=descripcion;
        pizzaMenu[3]=precio;
        pizzaMenu[4]=estado;


        value=1;


        ActualizarLista();
        // Load Pizza Detail Fragment
        DetalleAtencionFragmentActivity secondFragment = new DetalleAtencionFragmentActivity();

        Bundle args = new Bundle();
        args.putInt("value", value);
        args.putString("id", id);
        args.putString("nombre", nombre);
        args.putString("tipo_producto", tipo_producto);
        args.putString("descripcion", descripcion);
        args.putString("precio", precio);
        args.putString("estado", estado);
        // args.putInt("position", position);


        secondFragment.setArguments(args);          // (1) Communicate with Fragment using Bundle
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContainer, secondFragment) // replace flContainer
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
