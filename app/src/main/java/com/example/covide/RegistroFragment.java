package com.example.covide;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;


public class RegistroFragment extends Fragment {

    private static final String TAG = "Covide";
    private FirebaseAuth mAuth;
    private EditText Email;
    private EditText Password;
    private EditText Password2;
    private EditText Nombre;
    private EditText Apellido;
    private EditText Numero;
    private EditText mDireccion;
    private Button btnRegistrar;

    public RegistroFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registro, container, false);
    }
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController= Navigation.findNavController(view);

        Nombre = view.findViewById(R.id.Name);
        Apellido = view.findViewById(R.id.lastName);
        Numero = view.findViewById(R.id.Phone);
        //mDireccion = findViewById(R.id.direccion);
        Email= view.findViewById(R.id.Email);
        Password= view.findViewById(R.id.Password);
        Password2= view.findViewById(R.id.Password2);
        mAuth = FirebaseAuth.getInstance();



        btnRegistrar = view.findViewById(R.id.btnRegistrar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombre=Nombre.getText().toString();
                int telefono=Integer.parseInt( Numero.getText().toString());
              //  String direccion=mDireccion.getText().toString();
                String apellido=Apellido.getText().toString();
                String password=Password.getText().toString();
                String email=Email.getText().toString();

                 if(nombre.isEmpty() && apellido.isEmpty() && password.isEmpty() && email.isEmpty()){
                     Toast.makeText(getContext(),"Debe llenar todos los datos",Toast.LENGTH_LONG).show();
                }else{

                cargardatos(nombre, telefono, apellido, password, email);
                registrarse(navController);
             }
            }
        });

    }
    //Escribir en la base de datos Firebase
    private void cargardatos(String nombre, int telefono, String apellido, String password, String email) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        Map<String, Object> datosUsuario= new HashMap<>();
        datosUsuario.put("Nombre", nombre);
        datosUsuario.put("Telefono", telefono);
        //datosUsuario.put("Direccion", direccion);
        datosUsuario.put("Apellido", apellido);
        datosUsuario.put("Password", password);
        datosUsuario.put("Email", email);


        myRef.child("Usuarios").push().setValue(datosUsuario);
    }


    private void registrarse(NavController navController) {

        String email=Email.getText().toString();
        String password=Password.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            navController.navigate(R.id.Inicio);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }


                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if(user != null){
            Toast.makeText(getContext(),"No se a podido registrar intente mas tarde",Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(getContext(),"Registro Exitoso", Toast.LENGTH_LONG).show();

        }

    }
}
