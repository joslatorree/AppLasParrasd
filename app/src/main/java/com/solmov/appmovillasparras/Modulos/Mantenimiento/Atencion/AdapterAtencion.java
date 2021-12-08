package com.solmov.appmovillasparras.Modulos.Mantenimiento.Atencion;

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
import com.solmov.appmovillasparras.R;

import java.util.ArrayList;

public class AdapterAtencion extends RecyclerView.Adapter<AdapterAtencion.ViewHolder> implements View.OnClickListener {

    Context context;


    private FirestoreRecyclerAdapter adapterrec;
    private FirebaseFirestore firebaseFirestore;


    private ArrayList<ModeloAtencion> userArrayList;
    LayoutInflater inflater;

    private View.OnClickListener listener;

    public void setOnClickListener(View.OnClickListener listener) {

        this.listener = listener;
    }


    public AdapterAtencion(Context context, ArrayList<ModeloAtencion> model) {
        this.context=context;
        this.inflater = LayoutInflater.from(context);
        this.userArrayList = model;

    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }

    }

    @NonNull

    @Override
    public AdapterAtencion.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design, parent, false);

        view.setOnClickListener(this);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  AdapterAtencion.ViewHolder holder, int position) {

        ModeloAtencion user=userArrayList.get(position);


        holder.textView.setText(user.getNombre());
        holder.textView2.setText(user.getTipo_Producto());
        holder.textView3.setText(user.getPrecio());
/*
        int resource = userArrayList.get(position).getImageview1();
        String name = userArrayList.get(position).getTextview1();
        String msg = userArrayList.get(position).getTextview3();
        String time = userArrayList.get(position).getTextview2();
        String line = userArrayList.get(position).getDivider();

        holder.setData(resource, name, msg, time, line);*/
    }


    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private ImageView imageView;
        private TextView textView;
        private TextView textView2;
        private TextView textView3;
        private TextView divider;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageview1);
            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView1);
            textView3 = itemView.findViewById(R.id.textView2);
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
