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

import org.jetbrains.annotations.NotNull;


public class InicioFragment extends Fragment {
    final String TAG = "Covide";
    FirebaseAuth mAuth;
    EditText mEmailField, mPasswordField;



    public InicioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController= Navigation.findNavController(view);

        mEmailField= view.findViewById(R.id.email);
        mPasswordField= view.findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();



        Button btnlog = view.findViewById(R.id.btnlog);
        Button btnreg = view.findViewById(R.id.btnreg);


        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              navController.navigate(R.id.Registro);
            }
        });



        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    loginUsuario(navController);

            }

        });




    }





    private void updateUI(FirebaseUser user) {
        if(user != null){
            Toast.makeText(getContext(),"Sesión Iniciada",Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(getContext(),"Usuario No Registrado",Toast.LENGTH_LONG).show(); }

    }
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);


    }

    private void loginUsuario(NavController navController){

        String email=mEmailField.getText().toString();
        String password=mPasswordField.getText().toString();
        if(!email.isEmpty() && !password.isEmpty()){
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                                navController.navigate(R.id.mainFragment);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(getContext(), "Authentication failed.",Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }

                            // ...
                        }
                    });
        }else{
        Toast.makeText(getContext(),"No ah escrito el Usuario o Contraseña",Toast.LENGTH_LONG).show();

    }




    }



}
