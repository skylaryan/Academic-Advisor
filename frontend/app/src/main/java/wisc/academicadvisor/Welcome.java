package wisc.academicadvisor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button loginBtn = (Button)findViewById(R.id.login);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Welcome.this, UserLogin.class));
            }
        });

        Button createAnAccount = (Button)findViewById(R.id.createanaccount);

        createAnAccount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Welcome.this, CreateAnAccount.class));
            }
        });
    }

    public void launchCourseSearchResultsActivity(View v){
        startActivity(new Intent(Welcome.this, CourseSearch.class));
    }
}
