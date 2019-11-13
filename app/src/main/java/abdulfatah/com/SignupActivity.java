package abdulfatah.com;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    EditText etUsername,etPassword;
    String Username,Password;
    int formsuccess;
    dbhelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        db = new dbhelper(this);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_cancel,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())  {
            case R.id.btnsave:

                formsuccess = 2;

                Username = etUsername.getText().toString();
                Password = etPassword.getText().toString();


                if(Username.equals(""))  {
                    etUsername.setError("This field is required");
                    formsuccess--;
                }

                if(Password.equals(""))  {
                    etPassword.setError("This field is required");
                    formsuccess--;
                }



                if(formsuccess == 2)  {
                    HashMap<String, String> map_user = new HashMap();
                    map_user.put(db.TBL_USER_USERNAME, Username);
                    map_user.put(db.TBL_USER_PASSWORD, Password);



                    if(db.addUser(map_user) > 0)  {
                        etUsername.setError("Username already taken");
                    }
                    else {
                        Toast.makeText(this, "Account successfully created", Toast.LENGTH_SHORT).show();
                        this.finish();
                    }


                }

                break;
            case R.id.btncancel:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
