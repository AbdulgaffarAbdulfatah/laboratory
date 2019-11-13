package abdulfatah.com;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class EditUserActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    String username, password;
    int formsuccess, userID;

    dbhelper db;

    ArrayList<HashMap<String, String>> selected_user;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        db = new dbhelper(this);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        Intent intent = getIntent();
        userID = intent.getIntExtra(db.TBL_USER_ID, 0);

        selected_user = db.getSelectedUserData(userID);

        etUsername.setText(selected_user.get(0).get(db.TBL_USER_USERNAME));
        etPassword.setText(selected_user.get(0).get(db.TBL_USER_PASSWORD));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_cancel, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnsave:

                formsuccess = 2;
                username = etUsername.getText().toString();
                password = etPassword.getText().toString();

                if (username.equals("")) {
                    etUsername.setError("This field is required");
                    formsuccess--;
                }

                if (password.equals("")) {
                    etPassword.setError("This field is required");
                    formsuccess--;
                }

                if (formsuccess == 2) {
                    HashMap<String, String> map_user = new HashMap();
                    map_user.put(db.TBL_USER_ID, String.valueOf(userID));
                    map_user.put(db.TBL_USER_USERNAME, username);
                    map_user.put(db.TBL_USER_PASSWORD, password);

                    db.updateUser(map_user);
                    Toast.makeText(this, "Data Successfully Updated", Toast.LENGTH_SHORT).show();
                    this.finish();

                }



                break;
            case R.id.btncancel:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}




