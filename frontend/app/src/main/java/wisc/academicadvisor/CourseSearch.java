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

    private String[] credits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_search);

        // make it so editText doesn't pop up immediately
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        final Spinner creditSpinner = (Spinner) findViewById(R.id.credits_spinner);

        // all breadth options for spinnger (maybe add Ethnic Study (not a breadth option on course guide))
        credits = new String[]
                {"-----N/A-----","      1      ", "      2      ", "      3      ", "      4      ",
                        "      5      ", "      6      ", "      7      "};
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(this,
                R.layout.spinner_item, credits);
        adapterSpinner.setDropDownViewResource(R.layout.spinner_dropdown);
        creditSpinner.setAdapter(adapterSpinner);

        final RatingBar profRating = (RatingBar) findViewById(R.id.prof_rating_bar);

        final CheckBox humanities = (CheckBox) findViewById(R.id.humanities);

        humanities.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (humanities.isChecked()){
                    String credits = creditSpinner.getSelectedItem().toString().trim();
                    if (creditSpinner.getSelectedItemPosition() == 0)
                        credits = "No ";
                    Toast.makeText(CourseSearch.this, credits + " credits selected!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(CourseSearch.this, profRating.getRating() + " professor rating", Toast.LENGTH_SHORT).show();
                }
            }
        });

        SeekBar avgGPA = (SeekBar) findViewById(R.id.gpa_seekBar);
        final TextView gpaText = (TextView) findViewById(R.id.avgGPA_text);

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
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                String gpa = String.format("%.02f", progress / 100.0);
                gpaText.setText("Average GPA: " + gpa + " / 4.00");
            }

        });






    }

    public void launchCourseSearchResultsActivity(View v){
        startActivity(new Intent(CourseSearch.this, CourseSearchResults.class));
    }


}