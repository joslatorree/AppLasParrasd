package com.solmov.appmovillasparras.Modulos.Mantenimiento.Usuarios;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.solmov.appmovillasparras.R;

import java.util.HashMap;
import java.util.Map;

public class DetalleUsuarioFragmentActivity extends Fragment{

    //int position = 0;
    String position;

    String id;
    String apellidos;
    String dni;
    String email;
    String estado;
    String movil;
    String nombre;
    String password;
    String sexo;
    String tipo_usuario;
    String usuario;


    TextView tvid;
    TextView tvapellidos;
    TextView tvdni;
    TextView tvemail;
    TextView tvestado;
    TextView tvmovil;
    TextView tvnombre;
    TextView tvpassword;
    TextView tvsexo;
    TextView tvtipo_usuario;
    TextView tvusuario;

    private FirebaseAuth mAuth;

    Integer value=0;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


         mAuth = FirebaseAuth.getInstance();

       // Toast.makeText(getContext(), "Valor Inicio DetalleUsuario "+value, Toast.LENGTH_SHORT).show();
        if(savedInstanceState == null){
            // Get back arguments
            if(getArguments() != null) {
                value=getArguments().getInt("value", 0);
                //position = getArguments().getInt("position", 0);
                id = getArguments().getString("id", "");
                apellidos = getArguments().getString("apellidos", "");
                dni = getArguments().getString("dni", "");
                email = getArguments().getString("email", "");
                estado = getArguments().getString("estado", "");
                movil = getArguments().getString("movil", "");
                nombre = getArguments().getString("nombre", "");
                password = getArguments().getString("password", "");
                sexo = getArguments().getString("sexo", "");
                tipo_usuario = getArguments().getString("tipo_usuario", "");
                usuario = getArguments().getString("usuario", "");

              //  Toast.makeText(getContext(), "Valor Get Arguments DetalleUsuario "+value, Toast.LENGTH_SHORT).show();
            }

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {

        // Inflate the xml file for the fragment
        return inflater.inflate(R.layout.activity_modificar_usuario_fragment, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {


        tvapellidos = (TextView) view.findViewById(R.id.Apellidos);
        tvdni = (TextView) view.findViewById(R.id.DNI);
        tvemail = (TextView) view.findViewById(R.id.Email);
        tvestado = (TextView) view.findViewById(R.id.Estado);
        tvmovil = (TextView) view.findViewById(R.id.Movil);
        tvnombre = (TextView) view.findViewById(R.id.Nombre);
        tvpassword = (TextView) view.findViewById(R.id.Password);
        tvsexo = (TextView) view.findViewById(R.id.Sexo);
        tvtipo_usuario = (TextView) view.findViewById(R.id.TipoUsuario);
        tvusuario = (TextView) view.findViewById(R.id.Usuario);


        if(value!=0)
        {
           // Toast.makeText(getContext(), "Valor OnViewCreated DetalleUsuario "+value, Toast.LENGTH_SHORT).show();
            // Set values for view here


           // tvTitle = (TextView) view.findViewById(R.id.DNI);
           //
            // tvDetails = (TextView) view.findViewById(R.id.Nombre);

            // update view
            tvapellidos.setText(apellidos);
            tvdni.setText(dni);
            tvemail.setText(email);
            tvestado.setText(estado);
            tvmovil.setText(movil);
            tvnombre.setText(nombre);
            tvpassword.setText(password);
            tvsexo.setText(sexo);
            tvtipo_usuario.setText(tipo_usuario);
            tvusuario.setText(usuario);

            // tvDetails.setText(GestionarUsuariosActivity.pizzaDetails[position]);

            Button btn= (Button) view.findViewById(R.id.Ejecutar);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Valor OnViewCreated DetalleUsuario MODIFICAR " +value, Toast.LENGTH_SHORT).show();
                    updateView();
                    value=0;
                }
            });

        }

        else
        {

            Toast.makeText(getContext(), "Valor OnViewCreated DetalleUsuario AGREGAR " +value, Toast.LENGTH_SHORT).show();
            Button btn= (Button) view.findViewById(R.id.Ejecutar);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!tvdni.getText().toString().isEmpty())
                    {
                        AgregarView();



                    }
                    else {
                        Toast.makeText(getContext(), "Rellene al menos el campo DNI", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

        //ACTUALIZARA LAS LISTAS DEL MAIN ACTIVITY (GESTIONAR USUARIOS)
        ((GestionarUsuariosActivity)getActivity()).ActualizarLista();

    }
    public void AgregarView( ){


        FirebaseFirestore db=FirebaseFirestore.getInstance();

        String idagreg = db.collection("Usuario").document().getId();

        DocumentReference noteRef = db.collection("Usuario").document(idagreg);

        Map<String, Object> user = new HashMap<>();
        user.put("Apellidos",tvapellidos.getText().toString());
        user.put("DNI",tvdni.getText().toString());
        user.put("Email",tvemail.getText().toString());
        user.put("Estado",tvestado.getText().toString());
        user.put("Movil",tvmovil.getText().toString());
        user.put("Nombre",tvnombre.getText().toString());
        user.put("Password",tvpassword.getText().toString());
        user.put("Sexo",tvsexo.getText().toString());
        user.put("Tipo_Usuario",tvtipo_usuario.getText().toString());
        user.put("Usuario",tvusuario.getText().toString());
/*
        noteRef.update("Apellidos",tvapellidos.getText().toString(),
                "DNI",tvdni.getText().toString(),
                "Email",tvemail.getText().toString(),
                "Estado",tvestado.getText().toString(),
                "Movil",tvmovil.getText().toString(),
                "Nombre",tvnombre.getText().toString(),
                "Password",tvpassword.getText().toString(),
                "Sexo",tvsexo.getText().toString(),
                "Tipo_Usuario",tvtipo_usuario.getText().toString(),
                "Usuario",tvusuario.getText().toString()
        )*/


        mAuth.createUserWithEmailAndPassword(tvemail.getText().toString().trim(),
                tvpassword.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                   // Log.d("FirebaseAuth", "onComplete" + task.getException().getMessage());
                    Toast.makeText(getContext(), "Se Agrego en AUTHENTICATION ", Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(getContext(), "No se Agrego en AUTHENTICATION ", Toast.LENGTH_SHORT).show();
                }
            }
        });

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

        DocumentReference noteRef = db.collection("Usuario").document(id);


        noteRef.update("Apellidos",tvapellidos.getText().toString(),
                "DNI",tvdni.getText().toString(),
                "Email",tvemail.getText().toString(),
                "Estado",tvestado.getText().toString(),
                "Movil",tvmovil.getText().toString(),
                "Nombre",tvnombre.getText().toString(),
                "Password",tvpassword.getText().toString(),
                "Sexo",tvsexo.getText().toString(),
                "Tipo_Usuario",tvtipo_usuario.getText().toString(),
                "Usuario",tvusuario.getText().toString()
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
