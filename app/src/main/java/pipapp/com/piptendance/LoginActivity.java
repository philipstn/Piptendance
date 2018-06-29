package pipapp.com.piptendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText etEmail, etPassword;
    TextView tvStatus;
    String Email, Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        tvStatus = findViewById(R.id.tvStatus);

        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser!= null) {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
        }
    }


    public void btnSignup(View view) {
        Intent i = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(i);
    }


    public void btnLogin(View view) {
        getText();
        mAuth.signInWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
//                            i.putExtra("user", user);
                            startActivity(i);
//                            updateUI(user);
                        } else {
                            Toast.makeText(LoginActivity.this, "Incorrect Username/Password", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            tvStatus.setText("User ID= " + user.getUid());
        } else {
            tvStatus.setText("Sign in Failed");
        }

    }

    private void getText (){
        Email = etEmail.getText().toString();
        Password = etPassword.getText().toString();
    }


}
