package abdulfatah.com;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etUsername,etPassword;
    Button btnlogin;
    TextView tvcreateaccount;
    String username,password;
    int formsuccess;

    dbhelper db;

    SharedPreferences shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new dbhelper(this);

        shared = getSharedPreferences("123", Context.MODE_PRIVATE);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnlogin = findViewById(R.id.btnlogin);
        tvcreateaccount = findViewById(R.id.tvcreateaccount);


        btnlogin.setOnClickListener(this);
        tvcreateaccount.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnlogin:

                formsuccess = 2;
                username = etUsername.getText().toString();
                password = etPassword.getText().toString();

                if(username.equals("")) {
                    etUsername.setError("This field is required");
                    formsuccess--;

                }

                if(password.equals(""))    {
                    etPassword.setError("This field is required");
                    formsuccess--;
                }


                if(formsuccess == 2) {
                    HashMap<String, String> map_user = new HashMap();
                    map_user.put(db.TBL_USER_USERNAME, username);
                    map_user.put(db.TBL_USER_PASSWORD, password);
                    int userID = db.checkUser(map_user);
                    if(userID >  0) {
                        SharedPreferences.Editor editor = shared.edit();
                        editor.putInt(db.TBL_USER_ID, userID).commit();
                        this.finish();
                        startActivity(new Intent(this, DisplayUserActivity.class));
                    }
                    else {
                        etUsername.setError("Invalid Login Credentials");
                    }

                }


                break;
            case R.id.tvcreateaccount:
                startActivity(new Intent(this, SignupActivity.class));
                break;
        }

    }

    @Override
    protected void onResume() {
        if(shared.contains(db.TBL_USER_ID))  {
            this.finish();
            startActivity(new Intent(this, DisplayUserActivity.class));
        }
        super.onResume();
    }
}
