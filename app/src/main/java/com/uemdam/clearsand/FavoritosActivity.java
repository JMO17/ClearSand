package com.uemdam.clearsand;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.uemdam.clearsand.javabean.AdaptadorCartaPlaya;
import com.uemdam.clearsand.javabean.Playa;
import com.uemdam.clearsand.javabean.Usuario;

import java.util.ArrayList;

public class FavoritosActivity extends menuAbstractActivity {

    private ArrayList<Playa> datosPlaya;

    /*VISTA*/
    private ProgressBar progBar;

    /*RECYCLED VIEW*/
    private RecyclerView rvCartaPlaya;
    private AdaptadorCartaPlaya adaptador;
    private LinearLayoutManager llManager;

    /*DATABASE*/
    private DatabaseReference dbR;
    private ChildEventListener cel;

    /*USUARIO*/
    private Usuario[] user; //el query devuelve un array de usuarios pero solo utilizamos el primero
    private ArrayList<String> favoritos;

    @Override
    public int cargarLayout() {
        return R.layout.activity_favoritos;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_favoritos);
        setActActual(3);

        /*--------------------            DATABASE USUARIO                  ----------------------*/
        dbR = FirebaseDatabase.getInstance().getReference().child("usuarios");
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        Query q = dbR.orderByChild("emailUsuario").equalTo(email);
        user = new Usuario[1];

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            //Cargar datos de usuario
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    user[0] = dataSnapshot1.getValue(Usuario.class);
                    favoritos = user[0].getPlayasUsuarioFav();
                    cargarRecycleView();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void cargarRecycleView() {
        /*---------------------                RECYCLE VIEW             --------------------------*/
        rvCartaPlaya = findViewById(R.id.rvFavoritos);
        rvCartaPlaya.setHasFixedSize(true);
        /*LinearLayout*/
        llManager = new LinearLayoutManager(this);
        rvCartaPlaya.setLayoutManager(llManager);

        datosPlaya = new ArrayList<Playa>();
        adaptador = new AdaptadorCartaPlaya(datosPlaya, user);
        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO borrar el mensaje
                String msg = "Seleccionada la opción " + rvCartaPlaya.getChildAdapterPosition(v) ;
                Toast.makeText(FavoritosActivity.this,msg,Toast.LENGTH_SHORT).show();

//                Intent i = new Intent(MainActivity.this, PerfilPlayaActivity.class);
//                i.putExtra("ID", datosPlaya.get(rvCartaPlaya.getChildAdapterPosition(v)).getId());
//                startActivity(i);
            }
        });
        rvCartaPlaya.setAdapter(adaptador);

        rvCartaPlaya.setItemAnimator(new DefaultItemAnimator());

        /*DATABASE*/
        dbR = FirebaseDatabase.getInstance().getReference().child("PLAYAS");

        addChildEventListener();
    }

    private void addChildEventListener() {
        if(cel == null) {
            cel = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    //System.out.println("Nueva playa");
                    //progBar.setVisibility(View.VISIBLE);
                    Playa p = dataSnapshot.getValue(Playa.class);
                    if(favoritos.contains(p.getId())) {
                        datosPlaya.add(p);
                        //System.out.println(m.getNombre());
                        adaptador.notifyItemChanged(datosPlaya.size()-1);
                        adaptador.notifyDataSetChanged();
                    }

                    //progBar.setVisibility(View.GONE);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };

            dbR.addChildEventListener(cel);
        }
    }
}
