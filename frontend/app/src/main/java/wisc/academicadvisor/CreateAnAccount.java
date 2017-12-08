package wisc.academicadvisor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateAnAccount extends AppCompatActivity {
    private String[] majors;
    private ArrayList<String> interests;
    EditText nameText;
    EditText interestsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_an_account);

        // initialize majors with potential majors **** could be pulled from database later on
        this.majors = new String[] {"---Select a major---", "Computer Science", "Computer Engineering"};

        this.interests = new ArrayList<String>();

        interestsText = (EditText)findViewById(R.id.interestsInput);
        nameText = (EditText)findViewById(R.id.nameInputText);

        // Initialize spinner
        Spinner spinner = (Spinner)findViewById(R.id.majorInputSpinner);
        // set adapter for the spinner to be the major strings
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, majors);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spinner.setAdapter(adapter);


        ListView list = (ListView)findViewById(R.id.interestsInputList);
        final ArrayAdapter<String> adapterList = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, interests);
        list.setAdapter(adapterList);

        // handle listview clicks to remove
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // Remove interest from list if clicked
                Toast.makeText(CreateAnAccount.this, interests.get(position).toString() + " was removed.", Toast.LENGTH_SHORT).show();
                interests.remove(position);
                adapterList.notifyDataSetChanged();
            }
        });

        // Initialize the add interest button
        Button addInterest = (Button)findViewById(R.id.interestsAddBtn);

        // add interest button click handling
        addInterest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String interestToAdd = interestsText.getText().toString();
                // check if interest text is empty
                if(!interestToAdd.equals("")) {
                    // check if the interest had already been added
                    if(interests.contains(interestToAdd)){
                        Toast.makeText(CreateAnAccount.this, interestToAdd + " was added already.",
                                Toast.LENGTH_SHORT).show();
                    }
                    else {
                        // when add button clicked add interest to the arraylist for the listview
                        interests.add(interestToAdd);
                        interestsText.setText("");
                        // notify listview adapter that data changed
                        adapterList.notifyDataSetChanged();
                    }
                }
            }
        });

        // Initialize the done button, move to the next activity to fill in password
        Button donebtn = (Button)findViewById(R.id.done);

        donebtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO make sure all fields are filled in
                if(!nameText.getText().toString().equals("")) {
                    startActivity(new Intent(CreateAnAccount.this, CreateAccountPassword.class));
                }
                else {
                    Toast.makeText(CreateAnAccount.this, "Fields not filled in!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
