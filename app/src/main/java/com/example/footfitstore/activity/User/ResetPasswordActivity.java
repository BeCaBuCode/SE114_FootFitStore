package com.example.footfitstore.activity.User;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.footfitstore.R;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText emailInput;
    private Button resetPasswordButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        // Khởi tạo FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Liên kết UI
        emailInput = findViewById(R.id.email_input);
        resetPasswordButton = findViewById(R.id.reset_password_button);

        // Xử lý sự kiện khi người dùng nhấn nút gửi email khôi phục mật khẩu
        resetPasswordButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            if (!email.isEmpty()) {
                sendPasswordResetEmail(email);
            } else {
                Toast.makeText(ResetPasswordActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendPasswordResetEmail(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        AlertDialog.Builder builder=new AlertDialog.Builder(this,R.style.CustomAlertDialog);
                        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_reset_password,null);
                        builder.setView(customLayout);
                        Button positiveButton = customLayout.findViewById(R.id.pos_button);
                        AlertDialog alertDialog=builder.create();
                        positiveButton.setOnClickListener(v->{
                            //startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                            super.onBackPressed();
                            alertDialog.cancel();
                        });
                        builder.show();
                    } else {
                        Toast.makeText(ResetPasswordActivity.this, "Failed to send reset email.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
