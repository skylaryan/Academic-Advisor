package wisc.academicadvisor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import java.util.ArrayList;

public class CourseSearch extends AppCompatActivity {

    private SearchView sv;
    // private ListView courseListView;
    private ArrayList<String> courseList; // used with Adapter for the listview to output
    private ArrayList<String> courses = new ArrayList<String>(){{
        add("CS302 Programming I");
        add("CS367 Programming II");
        add("CS/ECE354 Machine Org and Programming");
        add("ECE352 Digital System Fundamentals");
    }};  // list of courses to search through until we have database set up
    // private ArrayAdapter<String> adapterList;

    // array of breadth
    private String[] breadth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_search);

        // make it so editText doesn't pop up immediately
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Listview that holds search result
        ListView courseListView = (ListView)findViewById(R.id.courseList);
        courseList = new ArrayList<String>();

        // set array adapter for the Listview
        final ArrayAdapter<String> adapterList = new ArrayAdapter<String>(this,
                R.layout.course_search_output, courseList);
        courseListView.setAdapter(adapterList);

        courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // Toast.makeText(CourseSearchResults.this, courseList.get(position).toString() + " was added to class schedule", Toast.LENGTH_SHORT).show();
                // courseList.remove(position);
                // adapterList.notifyDataSetChanged();
                // startActivity(new Intent(CourseSearchResults.this, CourseSearchResults.class));

                // pass the course id to the next activity in order to diplay information
                // TODO have a courseId for each course in database
                Intent intent = new Intent(CourseSearch.this, CourseSearchResults.class);
                intent.putExtra("CourseID", courseList.get(position).toString());
                startActivity(intent);
            }
        });

        // all breadth options for spinnger (maybe add Ethnic Study (not a breadth option on course guide))
        this.breadth = new String[] {"","Biological Science", "Humanities", "Interdivisional",
                "Literature", "Natural Science", "Physical Science", "Social Science"};

        // Initialize spinner
        Spinner breadthSpinner = (Spinner)findViewById(R.id.breadthSpinner);
        // set adapter for the spinner to be the major strings
        ArrayAdapter<String> adapterSpin = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, breadth);
        breadthSpinner.setAdapter(adapterSpin);

        // inititate a search view
        sv = (SearchView)findViewById(R.id.courseSearch);
        // set default value of search bar
        sv.setQueryHint("Search here");

        // listener for the search interface
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            // handle a search query
            public boolean onQueryTextSubmit(String query) {
                // clear the previouse search
                courseList.clear();
                adapterList.notifyDataSetChanged();
                // look for what user searched for
                // TODO query the database
                for(int i = 0; i < courses.size(); i++){
                    if(courses.get(i).contains(query)){
                        courseList.add(courses.get(i));
                        adapterList.notifyDataSetChanged();
                    }
                }
                // Toast.makeText(getBaseContext(), query, Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            // handle change in text in the search bar
            public boolean onQueryTextChange(String newText) {
                // Toast.makeText(getBaseContext(), newText, Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }


}