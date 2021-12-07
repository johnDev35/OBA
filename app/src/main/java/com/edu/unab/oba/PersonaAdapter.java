package com.edu.unab.oba;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import model.Persona;

public class PersonaAdapter extends FirestoreRecyclerAdapter<Persona, PersonaAdapter.PersonaVH> {

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public PersonaAdapter(@NonNull FirestoreRecyclerOptions<Persona> options) {
        super(options);
    }



    @Override
    protected void onBindViewHolder(@NonNull PersonaVH holder, int position, @NonNull Persona pers) {
        DocumentSnapshot personaId = getSnapshots().getSnapshot(holder.getAdapterPosition());
        final String nomUsuID = personaId.getId();
        holder.lnomUsu.setText(nomUsuID);
        holder.lnombre.setText(pers.getNombre());
        holder.lapellido.setText(pers.getApellido());
        holder.ldireccion.setText(pers.getDireccion());
        holder.lcelular.setText(pers.getCelular());
        holder.lemail.setText(pers.getEmail());
        holder.lperfil.setText(pers.getPerfil());

        holder.btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //editar();

            }
        });

        holder.btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firestore.collection("Persona").document(nomUsuID.toString()).delete();
            }
        });



    }

    @NonNull
    @Override
    public PersonaVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_personas, parent, false);
        return new PersonaVH(vista);
    }

    public class PersonaVH extends RecyclerView.ViewHolder {
        private TextView lnomUsu, lnombre, lapellido, lcelular, lemail, ldireccion, lperfil;
        //private Button btnBorrar, btnModificar;
        private ImageButton btnBorrar, btnModificar;

        public PersonaVH(@NonNull View itemView) {
            super(itemView);
            lnomUsu = itemView.findViewById(R.id.lblusuario);
            lnombre = itemView.findViewById(R.id.lblnombre);
            lapellido = itemView.findViewById(R.id.lblapellido);
            lcelular = itemView.findViewById(R.id.lblcelular);
            lemail = itemView.findViewById(R.id.lblemail);
            ldireccion = itemView.findViewById(R.id.lbldireccion);
            lperfil = itemView.findViewById(R.id.lblperfil);

            btnModificar = itemView.findViewById(R.id.btnmodificar1);
            btnBorrar = itemView.findViewById(R.id.btnborrar1);
        }
    }



}
