package com.solmov.appmovillasparras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.solmov.appmovillasparras.Modulos.Atencion.ReporteVentas;
import com.solmov.appmovillasparras.Modulos.Atencion.SolicitarPedido;
import com.solmov.appmovillasparras.Modulos.Mantenimiento.Atencion.GestionarAtencionActivity;
import com.solmov.appmovillasparras.Modulos.Mantenimiento.Usuarios.GestionarUsuariosActivity;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    ImageView mButtonAccessGestUsu;
    ImageView mButtonAccessGestAte;
    ImageView mButtonAccessSoliPedi;
    ImageView mButtonAccessVeriPedi;
    ImageView mButtonAccessGestPedi;
    ImageView mButtonAccessGestCobro;
    ImageView mButtonAccessRepVentas;
    String Usuario;
    TextView tvusuario;

    public static Integer ValorPermiso=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ValorPermiso=0;
        //ESTO ES GMAIL VERIFICATION
        mAuth=FirebaseAuth.getInstance();
        Usuario=mAuth.getCurrentUser().getEmail();
        tvusuario=(TextView) findViewById(R.id.contacto);
        tvusuario.setText(Usuario);



        mButtonAccessGestUsu = findViewById(R.id.AccessGestUsu);


        mButtonAccessGestUsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, GestionarUsuariosActivity.class));

            }
        });

        mButtonAccessGestAte = findViewById(R.id.AccessGestAte);


        mButtonAccessGestAte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, GestionarAtencionActivity.class));

            }
        });

        mButtonAccessSoliPedi = findViewById(R.id.AccessSoliPedi);


        mButtonAccessSoliPedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SolicitarPedido.class));

            }
        });

        mButtonAccessGestPedi = findViewById(R.id.AccessGestPedi);


        mButtonAccessGestPedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValorPermiso=1;
                startActivity(new Intent(MainActivity.this, SolicitarPedido.class));

            }
        });


        mButtonAccessVeriPedi = findViewById(R.id.AccessVeriPedi);


        mButtonAccessVeriPedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValorPermiso=1;
                startActivity(new Intent(MainActivity.this, SolicitarPedido.class));

            }
        });

        mButtonAccessGestCobro = findViewById(R.id.AccessGestCobro);


        mButtonAccessGestCobro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, SolicitarPedido.class));

            }
        });

        mButtonAccessRepVentas = findViewById(R.id.AccessRepVentas);


        mButtonAccessRepVentas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ValorPermiso=2;
                startActivity(new Intent(MainActivity.this, ReporteVentas.class));

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}