package pipapp.com.piptendance;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import pipapp.com.piptendance.Model.User;

public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    TextInputEditText tietUsername, tietEmail, tietPassword, tietConfirmPassword;
    String Username, Email, Password, ConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        tietUsername = findViewById(R.id.tietUsername);
        tietEmail = findViewById(R.id.tietEmail);
        tietPassword = findViewById(R.id.tietPassword);
        tietConfirmPassword = findViewById(R.id.tietConfirmPassword);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();



    }

    public void btnCreateAccount(View view) {
        getText();
        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String uid = mAuth.getUid();
                            User users = new User();
                            users.setUsername(Username);
                            mDatabase.child("User").child(String.valueOf(uid)).setValue(users);
                            FirebaseAuth.getInstance().signOut();
                            Toast.makeText(SignupActivity.this, "Account Successfully created!", Toast.LENGTH_SHORT).show();
                            finish();

                        } else {
                            Toast.makeText(SignupActivity.this, "failed", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void getText(){
        Username = tietUsername.getText().toString();
        Email = tietEmail.getText().toString();
        Password = tietPassword.getText().toString();
        ConfirmPassword = tietConfirmPassword.getText().toString();
    }
}
