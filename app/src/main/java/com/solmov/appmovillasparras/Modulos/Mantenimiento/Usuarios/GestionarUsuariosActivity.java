package com.solmov.appmovillasparras.Modulos.Mantenimiento.Usuarios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.solmov.appmovillasparras.MainActivity;
import com.solmov.appmovillasparras.Modulos.Mantenimiento.Atencion.GestionarAtencionActivity;
import com.solmov.appmovillasparras.R;

import java.util.ArrayList;
import java.util.List;

public class GestionarUsuariosActivity extends AppCompatActivity implements ListarUsuarioFragmentActivity.OnItemSelectedListener{

    Integer value=0;
    ImageView mButtonAgregarUsuario;
    ImageView mButtonRegresarAMenu;
    public static String[] pizzaMenu = new String[10];

    public static List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_usuarios);

        Log.d("DEBUG", getResources().getConfiguration().orientation + "");

        if (savedInstanceState == null) {
            // Instance of first fragment
            ListarUsuarioFragmentActivity firstFragment = new ListarUsuarioFragmentActivity();

            // Add Fragment to FrameLayout (flContainer), using FragmentManager
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();// begin  FragmentTransaction
            ft.add(R.id.flContainer, firstFragment);                                // add    Fragment
            ft.commit();                                                            // commit FragmentTransaction
        }




        //3ercer fragment Agregar Usuarios


        mButtonAgregarUsuario = findViewById(R.id.AgregarUsuario);


        mButtonAgregarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ActualizarLista();

                value=0;
                // Load Pizza Detail Fragment
                DetalleUsuarioFragmentActivity thirdFragment = new DetalleUsuarioFragmentActivity();

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


        mButtonRegresarAMenu = findViewById(R.id.RegresarAMenu);


        mButtonRegresarAMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GestionarUsuariosActivity.this, MainActivity.class));

            }
        });


        ActualizarLista();
    }

    public void ActualizarLista()
    {
        //Modificar?
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference notebookRef =db.collection("Usuario");
        DocumentSnapshot lastresult;

        db.collection("Usuario").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                                    String apellidos,
                                    String dni,
                                    String email,
                                    String estado,
                                    String movil,
                                    String nombre,
                                    String password,
                                    String sexo,
                                    String tipo_usuario,
                                    String usuario) {
        Toast.makeText(this, "Called By Fragment A: position - "+ usuario, Toast.LENGTH_SHORT).show();



        pizzaMenu[0]=apellidos;
        pizzaMenu[1]=dni;
        pizzaMenu[2]=email;
        pizzaMenu[3]=estado;
        pizzaMenu[4]=movil;
        pizzaMenu[5]=nombre;
        pizzaMenu[6]=password;
        pizzaMenu[7]=tipo_usuario;
        pizzaMenu[8]=sexo;
        pizzaMenu[9]=usuario;


        value=1;


        ActualizarLista();
        // Load Pizza Detail Fragment
        DetalleUsuarioFragmentActivity secondFragment = new DetalleUsuarioFragmentActivity();

        Bundle args = new Bundle();
        args.putInt("value", value);
        args.putString("id", id);
        args.putString("apellidos", apellidos);
        args.putString("dni", dni);
        args.putString("email", email);
        args.putString("estado", estado);
        args.putString("movil", movil);
        args.putString("nombre", nombre);
        args.putString("password", password);
        args.putString("tipo_usuario", tipo_usuario);
        args.putString("sexo", sexo);
        args.putString("usuario", usuario);
       // args.putInt("position", position);


        secondFragment.setArguments(args);          // (1) Communicate with Fragment using Bundle
                    getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContainer, secondFragment) // replace flContainer
                    .addToBackStack(null)
                    .commit();
    }


}

