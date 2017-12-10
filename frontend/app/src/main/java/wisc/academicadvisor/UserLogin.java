package wisc.academicadvisor;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserLogin extends AppCompatActivity {

    private EditText username, password;
    private Button btn_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        btn_Login = (Button)findViewById(R.id.login_Enter_btn);


        username = (EditText) findViewById(R.id.username_entry);
        password = (EditText) findViewById(R.id.password_entry);

        btn_Login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(username.getText().toString().equals("") || password.getText().toString().equals("")){
                    Toast.makeText(UserLogin.this, "Fill in all fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    // TODO query the database for matching username and password
                    startActivity(new Intent(UserLogin.this, CourseSearch.class));
                }
            }
        });

    }

    
}
