package com.solmov.appmovillasparras.Modulos.Atencion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.solmov.appmovillasparras.MainActivity;
import com.solmov.appmovillasparras.R;

import org.w3c.dom.Comment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolicitarPedido extends AppCompatActivity
        implements ListarPedidoFragmentActivity.OnItemSelectedListener,
            ListarDetallePedidoFragmentActivity.OnItemSelectedListener,
            ListarProductoFragmentActivity.OnItemSelectedListener{

    Integer value=0;
    ImageView mButtonRegresar;
    ImageView mButtonAgregarPedido;
    public static String[] pizzaMenu = new String[10];

    public static List<String> list;
    public static List<String> listgetusuario;
    //public static List<String> listvalores;

    public static String[] listvalores = new String[10];
    FrameLayout Contenedor;


    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    //String Usuario hallado
    public static String UsuHallado;
    public static String IDPEDIDO;
    public static Integer valorAperturaPedido=0;

   // public static Integer valorAperturaDetallePedido=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //get auth actual
        mAuth=FirebaseAuth.getInstance();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar_pedido);

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
                startActivity(new Intent(SolicitarPedido.this, MainActivity.class));
                finish();
            }
        });

        //solicitar pedido

        mButtonAgregarPedido = findViewById(R.id.AgregarPedido);


        mButtonAgregarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                //Agregar Document Fields de Pedido
                AgregarPedido();


                ActualizarLista();

                value=0;

                valorAperturaPedido=0;
                ListarProductoFragmentActivity firstFragment = new ListarProductoFragmentActivity();


                // Add Fragment to FrameLayout (flContainer), using FragmentManager
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();// begin  FragmentTransaction
                ft.add(R.id.flContainer, firstFragment);                                // add    Fragment
                ft.commit();

            }
        });

        //SOLICITAR PEDIDOS

        ActualizarLista();

    }
    //

    public void AgregarPedido()
    {

        //LLAMA ID
        firebaseFirestore =FirebaseFirestore.getInstance();


        //calculo fecha
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String dateTime = dateFormat.format(date);

        //calculo hora
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");
        Date date2 = new Date();
        String dateTime2 = dateFormat2.format(date2);

        //conseguir usuario
        //(firebaseFirestore.collection("Usuario").whereArrayContains("Email",mAuth.getCurrentUser().getEmail()).get()).getResult();


        //UsuHallado=new String();
        listgetusuario = new ArrayList<>();
        //listvalores = new ArrayList<>();
        firebaseFirestore.collection("Usuario").whereEqualTo("Email",mAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull  Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    //poner en list
                    //List list = new ArrayList(firebaseFirestore.collection("Usuario").whereArrayContains("Email",mAuth.getCurrentUser().getEmail()).get());
                   for (QueryDocumentSnapshot document : task.getResult()) {
                       listgetusuario.add(document.getId());
                   }


                    firebaseFirestore.collection("Usuario").document(listgetusuario.get(0)).get().addOnCompleteListener(
                            new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull  Task<DocumentSnapshot> task2) {
                                    DocumentSnapshot document2 = task2.getResult();
                                    if (document2 != null && document2.exists()) {
                                        UsuHallado = document2.getString("Usuario");
                                        //listvalores.add(document2.getString("Nombre"));
                                        listvalores[0]=UsuHallado;
                                    }
                                    // Toast.makeText(SolicitarPedido .this,"Se consiguio al usuario " + UsuHallado,Toast.LENGTH_SHORT).show();

                                    IDPEDIDO = firebaseFirestore.collection("Pedidos").document().getId();

                                    DocumentReference noteRef = firebaseFirestore.collection("Pedidos").document(IDPEDIDO);
                                    Map<String, Object> pedido = new HashMap<>();
                                    pedido.put("Estado","En Proceso");
                                    pedido.put("Fecha",dateTime);
                                    pedido.put("Hora",dateTime2);
                                    pedido.put("PrecioTotal","0");
                                    pedido.put("Usuario",listvalores[0]);

                                    noteRef.set(pedido).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful())
                                            {
                                                Toast.makeText(SolicitarPedido.this, "Se Agrego pedido " + listvalores[0], Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                            {
                                                Toast.makeText(SolicitarPedido.this, "No se Agrego ", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });




                                    //Aqui tenemos al condenado
                                }
                            }
                    );

                   //Toast.makeText(SolicitarPedido .this,"Se consiguio la info "+listgetusuario.get(0) +" "+ UsuHallado,Toast.LENGTH_SHORT).show();

                }
                else
                {
                    //pedido.put("Usuario",UsuHallado);
                    Toast.makeText(SolicitarPedido.this, "No se consiguio la info ", Toast.LENGTH_SHORT).show();
                }
            }
        });







        /*
        Map<String, Object> user = new HashMap<>();
        user.put("Nombre",tvnombre.getText().toString());
        user.put("Tipo_Producto",tvtipo_producto.getText().toString());
        user.put("Descripcion",tvdescripcion.getText().toString());
        user.put("Precio",tvprecio.getText().toString());

        */





    }


    @Override
    public void onDetallePedidoSelected(String id,
                                     String tipo_producto,
                                     String descripcion,
                                     String nombre,
                                     String precio,
                                        String estado) {

        //Toast.makeText(this, "Called By Fragment A: position - "+ nombre, Toast.LENGTH_SHORT).show();



        pizzaMenu[0]=tipo_producto;
        pizzaMenu[1]=descripcion;
        pizzaMenu[2]=nombre;
        pizzaMenu[3]=precio;
        pizzaMenu[4]=estado;


        value=1;


        ActualizarLista();
        // Load Pizza Detail Fragment
        DetalleProductoFragmentActivity secondFragment = new DetalleProductoFragmentActivity();

        Bundle args = new Bundle();
        args.putInt("valueapertura", valorAperturaPedido);
        args.putInt("value", value);
        args.putString("id", id);
        args.putString("tipo_producto", tipo_producto);
        args.putString("descripcion", descripcion);
        args.putString("nombre", nombre);
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
    public void onPedidoItemSelected(String id,
                                    String usuario,
                                    String estado,
                                    String fecha,
                                    String hora,
                                    String preciototal) {

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

        if(MainActivity.ValorPermiso!=0)
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
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onProductoItemSelected(String id,
                                       String nombre,
                                       String tipo_producto,
                                       String descripcion,
                                       String precio,
                                       String estado,
                                       String calculototal) {


        Toast.makeText(this, "Called By Fragment A: position - "+ nombre, Toast.LENGTH_SHORT).show();



        pizzaMenu[0]=nombre;
        pizzaMenu[1]=tipo_producto;
        pizzaMenu[2]=descripcion;
        pizzaMenu[3]=precio;
        pizzaMenu[4]=estado;


        value=1;
        valorAperturaPedido=1;

        ActualizarLista();
        // Load Pizza Detail Fragment
        DetalleProductoFragmentActivity secondFragment = new DetalleProductoFragmentActivity();

        Bundle args = new Bundle();
        args.putInt("calculototal", ListarProductoFragmentActivity.calculototal);
        args.putInt("valueapertura", valorAperturaPedido);
       // args.putInt("valorAperturaDetallePedido", valorAperturaDetallePedido);
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
}