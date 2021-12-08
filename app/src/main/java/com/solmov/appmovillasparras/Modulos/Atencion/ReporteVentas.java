package com.solmov.appmovillasparras.Modulos.Atencion;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.solmov.appmovillasparras.MainActivity;
import com.solmov.appmovillasparras.R;

import java.util.ArrayList;
import java.util.List;

public class ReporteVentas extends AppCompatActivity  implements ListarPedidoFragmentActivity.OnItemSelectedListener{
    Integer value=0;
    public static Integer valorAperturaPedido=0;
    public static String fechaconsulta;
    public static List<String> list;

    public static String[] pizzaMenu = new String[10];

    ImageView mButtonRegresar;
    EditText etPlannedDate;
    TextView Ingreso;

    Integer ValorReporteVentasAuth;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //get auth actual
        mAuth= FirebaseAuth.getInstance();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_ventas);

        Log.d("DEBUG", getResources().getConfiguration().orientation + "");


        if (savedInstanceState == null) {
            // Instance of first fragment



            ListarPedidoFragmentActivity firstFragment = new ListarPedidoFragmentActivity();


            // Add Fragment to FrameLayout (flContainer), using FragmentManager
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();// begin  FragmentTransaction
            ft.add(R.id.flContainer, firstFragment);                                // add    Fragment
            ft.commit();                                                            // commit FragmentTransaction
        }



        //REGRESAR A MAIN
        mButtonRegresar = findViewById(R.id.Regresar);


        mButtonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReporteVentas.this, MainActivity.class));

            }
        });


     etPlannedDate = (EditText) findViewById(R.id.etPlannedDate);
        etPlannedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();

            }
        });

    }
    private void showDatePickerDialog() {
        FragmentoFecha newFragment = FragmentoFecha.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                //
                final String selectedDate = year+ "-" +twoDigits(month+1) + "-" +twoDigits(day)    ;
                etPlannedDate.setText(selectedDate);
                // +1 because January is zero
                //final String selectedDate = day + " / " + (month+1) + " / " + year;
               // etPlannedDate.setText(selectedDate);



                //pedir que se realizen la consulta por fecha

                fechaconsulta=etPlannedDate.getText().toString();
                Bundle args = new Bundle();
                //  args.putInt("valorpermiso", MainActivity.ValorPermiso);
                args.putString("fechaconsulta", fechaconsulta);

                ListarPedidoFragmentActivity thirdfragment = new ListarPedidoFragmentActivity();
                thirdfragment.setArguments(args);          // (1) Communicate with Fragment using Bundle
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flContainer, thirdfragment) // replace flContainer
                        .addToBackStack(null)
                        .commit();


            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }



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

    @Override
    public void onPedidoItemSelected(String id, String usuario, String estado, String fecha, String hora, String preciototal) {

        //Toast.makeText(this, "Called By Fragment A: position - "+ usuario, Toast.LENGTH_SHORT).show();



        pizzaMenu[0]=usuario;
        pizzaMenu[1]=estado;
        pizzaMenu[2]=fecha;
        pizzaMenu[3]=hora;
        pizzaMenu[4]=preciototal;


        value=1;


        ActualizarLista();
        // Load Pizza Detail Fragment


        Bundle args = new Bundle();
        //  args.putInt("valorpermiso", MainActivity.ValorPermiso);
        args.putInt("valueapertura", valorAperturaPedido);
        args.putInt("value", value);
        args.putString("id", id);
        args.putString("usuario", usuario);
        args.putString("estado", estado);
        args.putString("fecha", fecha);
        args.putString("hora", hora);
        args.putString("preciototal", preciototal);

        if(MainActivity.ValorPermiso!=0 && MainActivity.ValorPermiso!=2)
        {
            ModificarPedidoFragmentActivity secondFragment = new ModificarPedidoFragmentActivity();
            secondFragment.setArguments(args);          // (1) Communicate with Fragment using Bundle
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContainer, secondFragment) // replace flContainer
                    .addToBackStack(null)
                    .commit();
        }

        else
        if(MainActivity.ValorPermiso==2)
        {
            //Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
        else

        {
            DetallePedidoFragmentActivity secondFragment = new DetallePedidoFragmentActivity();
            secondFragment.setArguments(args);          // (1) Communicate with Fragment using Bundle
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContainer, secondFragment) // replace flContainer
                    .addToBackStack(null)
                    .commit();
        }




        // args.putInt("position", position);


    }
}
