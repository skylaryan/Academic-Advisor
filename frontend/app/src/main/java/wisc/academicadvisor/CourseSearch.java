package wisc.academicadvisor;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CourseSearch extends AppCompatActivity {

    private String department, number, credits, professor, professorRating;
    private String url_S;
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

    public void launchURL(View v) {
        url_S = "tyleroconnell.com:8080/courses";

        department = ((EditText) findViewById(R.id.dept_entry)).getText().toString().replace(" ", "%20");
        number = ((EditText) findViewById(R.id.courseNumberEdit)).getText().toString();
        credits = credit_spinner.getSelectedItem().toString().trim();
        professor = ((EditText) findViewById(R.id.profEntry)).getText().toString();

        if (department.length() > 0)
            url_S += "?department=" + department;
        if (number.length() > 0)
            url_S += "?number=" + number;
        if (credit_spinner.getSelectedItemPosition() != 0)
            url_S += "?credits=" + credits;
        if (professor.length() > 0)
            url_S += "?professor=" + professor;

        professorRating = ((RatingBar) findViewById(R.id.prof_rating_bar)).getRating() + "";

        boolean firstBreadthReached = false;
        if (((CheckBox) findViewById(R.id.humanities)).isChecked()) {
            if (firstBreadthReached)
                url_S += "-";
            else {
                firstBreadthReached = true;
                url_S += "?breadth=";
            }
            url_S += "Humanities";
        }
        if (((CheckBox) findViewById(R.id.literature)).isChecked()) {
            if (firstBreadthReached)
                url_S += "-";
            else {
                firstBreadthReached = true;
                url_S += "?breadth=";
            }
            url_S += "Literature";
        }
        if (((CheckBox) findViewById(R.id.biological_sciences)).isChecked()) {
            if (firstBreadthReached)
                url_S += "-";
            else {
                firstBreadthReached = true;
                url_S += "?breadth=";
            }
            url_S += "Biological%20Science";
        }
        if (((CheckBox) findViewById(R.id.natural_science)).isChecked()) {
            if (firstBreadthReached)
                url_S += "-";
            else {
                firstBreadthReached = true;
                url_S += "?breadth=";
            }
            url_S += "Natural%20Science";
        }
        if (((CheckBox) findViewById(R.id.physical_science)).isChecked()) {
            if (firstBreadthReached)
                url_S += "-";
            else {
                firstBreadthReached = true;
                url_S += "?breadth=";
            }
            url_S += "Physical%20Science";
        }
        if (((CheckBox) findViewById(R.id.social_science)).isChecked()) {
            if (firstBreadthReached)
                url_S += "-";
            else {
                firstBreadthReached = true;
                url_S += "?breadth=";
            }
            url_S += "Social%20Science";
        }
        if (((CheckBox) findViewById(R.id.interdivisional)).isChecked()) {
            if (firstBreadthReached)
                url_S += "-";
            else {
                firstBreadthReached = true;
                url_S += "?breadth=";
            }
            url_S += "Interdivisional";
        }

        // start connection and pull data from back end
        readServerPage rSp = new readServerPage();
        rSp.execute();

        startActivity(new Intent(CourseSearch.this, CourseSearchResults.class));
    }

    protected class readServerPage extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection urlConnection = null;
            URL url = null;
            try {
                url = new URL(url_S);
                urlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader rd = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String content = "", line;
                while ((line = rd.readLine()) != null)
                    content += line + "\n";
                // content String contains web page data pulled from Tyler's server!!!!
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}