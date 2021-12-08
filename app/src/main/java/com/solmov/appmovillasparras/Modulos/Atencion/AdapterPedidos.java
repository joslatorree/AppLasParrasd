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
import com.solmov.appmovillasparras.R;

import java.util.ArrayList;

public class AdapterPedidos  extends RecyclerView.Adapter<AdapterPedidos.ViewHolder> implements View.OnClickListener {


    Context context;


    private FirestoreRecyclerAdapter adapterrec;
    private FirebaseFirestore firebaseFirestore;


    private ArrayList<ModeloPedidos> userArrayList;
    LayoutInflater inflater;

    private View.OnClickListener listener;

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }

    }


    @Override
    public AdapterPedidos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design, parent, false);

        view.setOnClickListener(this);


        return new ViewHolder(view);

    }
    public void setOnClickListener(View.OnClickListener listener) {

        this.listener = listener;
    }
    public AdapterPedidos(Context context, ArrayList<ModeloPedidos> model) {
        this.context=context;
        this.inflater = LayoutInflater.from(context);
        this.userArrayList = model;

    }
    @Override
    public void onBindViewHolder(@NonNull AdapterPedidos.ViewHolder holder, int position) {
        ModeloPedidos user=userArrayList.get(position);


        holder.textView.setText(user.getEstado());
        holder.textView4.setText(user.getFecha()+" "+user.getHora());
        holder.textView3.setText(user.getUsuario());
        holder.textView2.setText(user.getPrecioTotal());
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
