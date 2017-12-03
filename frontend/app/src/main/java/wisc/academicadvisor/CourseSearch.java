package wisc.academicadvisor;

import android.content.Intent;
import android.media.Rating;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CourseSearch extends AppCompatActivity {

    private String department, number, credits, professor, professorRating;
    private String url;
    private Spinner credit_spinner;
    private SeekBar avgGPA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_search);

        // make it so editText doesn't pop up immediately
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        credit_spinner = (Spinner) findViewById(R.id.credits_spinner);

        // all breadth options for spinnger (maybe add Ethnic Study (not a breadth option on course guide))
        String[] credits = new String[]
                {"-----N/A-----", "      1      ", "      2      ", "      3      ", "      4      ",
                        "      5      ", "      6      ", "      7      "};
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(this,
                R.layout.spinner_item, credits);
        adapterSpinner.setDropDownViewResource(R.layout.spinner_dropdown);
        credit_spinner.setAdapter(adapterSpinner);

        avgGPA = (SeekBar) findViewById(R.id.gpa_seekBar);
        final TextView gpaTextDisplay = (TextView) findViewById(R.id.avgGPA_text);

        avgGPA.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String gpa = String.format("%.02f", progress / 100.0);
                gpaTextDisplay.setText("Average GPA: " + gpa + " / 4.00");
            }

        });

    }

    public void launchURL() {
        url = "tyleroconnell.com:8080/courses";

        department = ((EditText) findViewById(R.id.dept_entry)).getText().toString().replace(" ", "%20");
        number = ((EditText) findViewById(R.id.courseNumberEdit)).getText().toString();
        credits = credit_spinner.getSelectedItem().toString().trim();
        professor = ((EditText) findViewById(R.id.profEntry)).getText().toString();

        if (department.length() > 0)
            url += "?department=" + department;
        if (number.length() > 0)
            url += "?number=" + number;
        if (credit_spinner.getSelectedItemPosition() != 0)
            url += "?credits=" + credits;
        if (professor.length() > 0)
            url += "?professor=" + professor;

        professorRating = ((RatingBar) findViewById(R.id.prof_rating_bar)).getRating() + "";

        boolean firstBreadthReached = false;
        if (((CheckBox) findViewById(R.id.humanities)).isChecked()) {
            if (firstBreadthReached)
                url += "-";
            else {
                firstBreadthReached = true;
                url += "?breadth=";
            }
            url += "Humanities";
        }
        if (((CheckBox) findViewById(R.id.literature)).isChecked()) {
            if (firstBreadthReached)
                url += "-";
            else {
                firstBreadthReached = true;
                url += "?breadth=";
            }
            url += "Literature";
        }
        if (((CheckBox) findViewById(R.id.biological_sciences)).isChecked()) {
            if (firstBreadthReached)
                url += "-";
            else {
                firstBreadthReached = true;
                url += "?breadth=";
            }
            url += "Biological%20Science";
        }
        if (((CheckBox) findViewById(R.id.natural_science)).isChecked()) {
            if (firstBreadthReached)
                url += "-";
            else {
                firstBreadthReached = true;
                url += "?breadth=";
            }
            url += "Natural%20Science";
        }
        if (((CheckBox) findViewById(R.id.physical_science)).isChecked()) {
            if (firstBreadthReached)
                url += "-";
            else {
                firstBreadthReached = true;
                url += "?breadth=";
            }
            url += "Physical%20Science";
        }
        if (((CheckBox) findViewById(R.id.social_science)).isChecked()) {
            if (firstBreadthReached)
                url += "-";
            else {
                firstBreadthReached = true;
                url += "?breadth=";
            }
            url += "Social%20Science";
        }
        if (((CheckBox) findViewById(R.id.interdivisional)).isChecked()) {
            if (firstBreadthReached)
                url += "-";
            else {
                firstBreadthReached = true;
                url += "?breadth=";
            }
            url += "Interdivisional";
        }


        startActivity(new Intent(CourseSearch.this, CourseSearchResults.class));
    }

}