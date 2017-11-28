package wisc.academicadvisor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spanned;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import android.text.Html;

import java.util.ArrayList;

public class CourseSearch extends AppCompatActivity {

    private ArrayList<Spanned> a_ClassList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_search_new);/**
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final CollapsingToolbarLayout collapseToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    collapseToolbar.setTitle("Found # courses:");
                    // Fully Expanded
                } else {
                    // Somewhere in between
                    collapseToolbar.setTitle("# search results:");
                }
            }
        });

        collapseToolbar.setTitle("# search results: ");*/

        JSONParser parser = new JSONParser();
        String parseThis = "[{\"course\":\"CS302\",\"section\":1,\"title\":\"Introduction to Programming\",\"numCredits\":3,\"breadth\":[\"Natural Sciences\"],\"professor\":\"John Doe\",\"professorRating\":4.9,\"description\":\"This is a class where you will learn how to program.\",\"schedule\":[\"12:00-13:00\",\"\",\"12:00-13:00\",\"\",\"12:00-13:00&14:00-15:00\"],\"fullCourse\":\"CS302-001\",\"department\":\"CS\",\"number\":\"302\"},{\"course\":\"CS367\",\"section\":1,\"title\":\"Introduction to Programming II\",\"numCredits\":3,\"breadth\":[\"Natural Sciences\"],\"professor\":\"John Doe\",\"professorRating\":4.9,\"description\":\"This is a class where you will learn how to program.\",\"schedule\":[\"12:00-13:00\",\"\",\"12:00-13:00\",\"\",\"12:00-13:00&14:00-15:00\"],\"fullCourse\":\"CS302-001\",\"department\":\"CS\",\"number\":\"367\"},{\"course\":\"CS354\",\"section\":1,\"title\":\"Machine Org & Programming\",\"numCredits\":3,\"breadth\":[\"Natural Sciences\"],\"professor\":\"John Doe\",\"professorRating\":4.9,\"description\":\"This is a class where you will learn how to program.\",\"schedule\":[\"12:00-13:00\",\"\",\"12:00-13:00\",\"\",\"12:00-13:00&14:00-15:00\"],\"fullCourse\":\"CS302-001\",\"department\":\"CS/ECE\",\"number\":\"354\"},{\"course\":\"ECE 352\",\"section\":1,\"title\":\"Digital System Fundamentals\",\"numCredits\":3,\"breadth\":[\"Natural Sciences\"],\"professor\":\"John Doe\",\"professorRating\":4.9,\"description\":\"This is a class where you will learn how to program.\",\"schedule\":[\"12:00-13:00\",\"\",\"12:00-13:00\",\"\",\"12:00-13:00&14:00-15:00\"],\"fullCourse\":\"CS302-001\",\"department\":\"ECE\",\"number\":\"352\"}]";
        final JSONArray ja_Courses;
        JSONObject joCourse;
        try {
            ja_Courses = (JSONArray) parser.parse(parseThis);
            a_ClassList = new ArrayList<Spanned>();
            for (int c = 0; c < ja_Courses.size(); c++){
                joCourse = (JSONObject) ja_Courses.get(c);
                String sectionNum = ("" + joCourse.get("fullCourse")).split("-")[1];

                String cInfo = "<b>" + joCourse.get("title") + "</b>";

                cInfo += "\n<i>" + joCourse.get("department") + " "
                        + joCourse.get("number") + " - " + sectionNum
                        + "</i> (" + joCourse.get("numCredits") + " credits)";

                a_ClassList.add(Html.fromHtml(cInfo));

            }
        }
        catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
            return;
        }

        ListView courseList = (ListView)findViewById(R.id.courseList);
        final ArrayAdapter<Spanned> adapterList = new ArrayAdapter<Spanned>(this,
                android.R.layout.simple_list_item_1, a_ClassList);
        courseList.setAdapter(adapterList);

        courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // Remove interest from list if clicked
                Toast.makeText(CourseSearch.this, a_ClassList.get(position).toString() + " was selected.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CourseSearch.this, CourseDescription.class);
                intent.putExtra("jsonCourseData", ja_Courses.get(position).toString());
                System.out.println(ja_Courses.get(position).toString());
                startActivity(intent);
            }
        });



    }
}
