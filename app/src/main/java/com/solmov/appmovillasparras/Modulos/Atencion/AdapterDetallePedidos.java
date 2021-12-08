package com.solmov.appmovillasparras.Modulos.Atencion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.solmov.appmovillasparras.Modulos.Mantenimiento.Atencion.AdapterAtencion;
import com.solmov.appmovillasparras.Modulos.Mantenimiento.Atencion.ModeloAtencion;
import com.solmov.appmovillasparras.R;

import java.util.ArrayList;

public class AdapterDetallePedidos extends RecyclerView.Adapter<AdapterDetallePedidos.ViewHolder> implements View.OnClickListener {


    Context context;


    private FirestoreRecyclerAdapter adapterrec;
    private FirebaseFirestore firebaseFirestore;


    private ArrayList<ModeloDetallePedidos> userArrayList;
    LayoutInflater inflater;

    private View.OnClickListener listener;

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }

    }

    @Override
    public AdapterDetallePedidos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design, parent, false);

        view.setOnClickListener(this);


        return new AdapterDetallePedidos.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDetallePedidos.ViewHolder holder, int position) {
        ModeloDetallePedidos user=userArrayList.get(position);


        holder.textView.setText(user.getNombre());
        holder.textView3.setText(user.getCantidad());
        holder.textView4.setText(user.getPrecio());
    }



    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {

        this.listener = listener;
    }
    public AdapterDetallePedidos(Context context, ArrayList<ModeloDetallePedidos> model) {
        this.context=context;
        this.inflater = LayoutInflater.from(context);
        this.userArrayList = model;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private ImageView imageView;
        private TextView textView;
        private TextView textView2;
        private TextView textView3;
        private TextView textView4;
        private TextView divider;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageview1);
            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView1);
            textView3 = itemView.findViewById(R.id.textView2);
            textView4 = itemView.findViewById(R.id.textView3);
            divider = itemView.findViewById(R.id.divider);
        }
/*
        public void setData(int resource, String name, String msg, String time, String line) {
            imageView.setImageResource(resource);
            textView.setText(name);
            textView3.setText(msg);
            textView2.setText(time);
            divider.setText(line);

        }

*/
    }
}
