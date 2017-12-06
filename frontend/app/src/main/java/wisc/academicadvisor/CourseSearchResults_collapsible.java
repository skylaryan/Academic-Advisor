package wisc.academicadvisor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;

public class CourseSearchResults_collapsible extends AppCompatActivity {

    private ArrayList<Spanned> a_ClassList;
    int numResults = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_search_results_collapsible);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final CollapsingToolbarLayout resultsTitle;

        JSONParser parser = new JSONParser();
        String parseThis = "[{\"course\":\"CS302\",\"section\":1,\"title\":\"Introduction to Programming\",\"numCredits\":3,\"breadth\":[\"Natural Sciences\"],\"professor\":\"John Doe\",\"professorRating\":4.9,\"description\":\"Introduces students to Object-Oriented Programming using classes and objects to solve more complex problems. The course also introduces array-based and linked data structures: including lists, stacks, and queues. Programming assignments require writing and developing multi-class (file) programs using interfaces, generics, and exception handling to solve challenging real world problems. Topics reviewed include reading/writing data and objects from/to files and exception handling, and command line arguments. Topics introduced: object-oriented design; class vs. object; create and define interfaces and iterators; searching and sorting; abstract data types (List,Stack,Queue,PriorityQueue(Heap),Binary Search Tree); generic interfaces (parametric polymorphism); how to design and write test methods and classes; array based vs. linked node implementations; introduction to complexity analysis; recursion.\",\"schedule\":[\"12:00-13:00\",\"\",\"12:00-13:00\",\"\",\"12:00-13:00&14:00-15:00\"],\"fullCourse\":\"CS302-001\",\"department\":\"CS\",\"number\":\"302\"}," +
                "{\"course\":\"CS367\",\"section\":1,\"title\":\"Introduction to Data Structures\",\"numCredits\":3,\"breadth\":[\"Natural Sciences\"],\"professor\":\"John Doe\",\"professorRating\":4.9,\"description\":\"Study of data structures (including stacks, queues, trees, graphs, and hash tables) and their applications. Development, implementation, and analysis of efficient data structures and algorithms (including sorting and searching). Experience in use of an object-oriented programming language.\",\"schedule\":[\"\",\"12:00-13:00&14:00-15:00\",\"12:00-13:00\",\"\",\"\"],\"fullCourse\":\"CS302-001\",\"department\":\"CS\",\"number\":\"367\"}," +
                "{\"course\":\"CS302\",\"section\":1,\"title\":\"Introduction to Programming\",\"numCredits\":3,\"breadth\":[\"Natural Sciences\"],\"professor\":\"John Doe\",\"professorRating\":4.9,\"description\":\"Introduces students to Object-Oriented Programming using classes and objects to solve more complex problems. The course also introduces array-based and linked data structures: including lists, stacks, and queues. Programming assignments require writing and developing multi-class (file) programs using interfaces, generics, and exception handling to solve challenging real world problems. Topics reviewed include reading/writing data and objects from/to files and exception handling, and command line arguments. Topics introduced: object-oriented design; class vs. object; create and define interfaces and iterators; searching and sorting; abstract data types (List,Stack,Queue,PriorityQueue(Heap),Binary Search Tree); generic interfaces (parametric polymorphism); how to design and write test methods and classes; array based vs. linked node implementations; introduction to complexity analysis; recursion.\",\"schedule\":[\"12:00-13:00\",\"\",\"12:00-13:00\",\"\",\"12:00-13:00&14:00-15:00\"],\"fullCourse\":\"CS302-001\",\"department\":\"CS\",\"number\":\"302\"}," +
                "{\"course\":\"CS302\",\"section\":1,\"title\":\"Introduction to Programming\",\"numCredits\":3,\"breadth\":[\"Natural Sciences\"],\"professor\":\"John Doe\",\"professorRating\":4.9,\"description\":\"Introduces students to Object-Oriented Programming using classes and objects to solve more complex problems. The course also introduces array-based and linked data structures: including lists, stacks, and queues. Programming assignments require writing and developing multi-class (file) programs using interfaces, generics, and exception handling to solve challenging real world problems. Topics reviewed include reading/writing data and objects from/to files and exception handling, and command line arguments. Topics introduced: object-oriented design; class vs. object; create and define interfaces and iterators; searching and sorting; abstract data types (List,Stack,Queue,PriorityQueue(Heap),Binary Search Tree); generic interfaces (parametric polymorphism); how to design and write test methods and classes; array based vs. linked node implementations; introduction to complexity analysis; recursion.\",\"schedule\":[\"12:00-13:00\",\"\",\"12:00-13:00\",\"\",\"12:00-13:00&14:00-15:00\"],\"fullCourse\":\"CS302-001\",\"department\":\"CS\",\"number\":\"302\"}," +
                "{\"course\":\"CS302\",\"section\":1,\"title\":\"Introduction to Programming\",\"numCredits\":3,\"breadth\":[\"Natural Sciences\"],\"professor\":\"John Doe\",\"professorRating\":4.9,\"description\":\"Introduces students to Object-Oriented Programming using classes and objects to solve more complex problems. The course also introduces array-based and linked data structures: including lists, stacks, and queues. Programming assignments require writing and developing multi-class (file) programs using interfaces, generics, and exception handling to solve challenging real world problems. Topics reviewed include reading/writing data and objects from/to files and exception handling, and command line arguments. Topics introduced: object-oriented design; class vs. object; create and define interfaces and iterators; searching and sorting; abstract data types (List,Stack,Queue,PriorityQueue(Heap),Binary Search Tree); generic interfaces (parametric polymorphism); how to design and write test methods and classes; array based vs. linked node implementations; introduction to complexity analysis; recursion.\",\"schedule\":[\"12:00-13:00\",\"\",\"12:00-13:00\",\"\",\"12:00-13:00&14:00-15:00\"],\"fullCourse\":\"CS302-001\",\"department\":\"CS\",\"number\":\"302\"}," +
                "{\"course\":\"CS302\",\"section\":1,\"title\":\"Introduction to Programming\",\"numCredits\":3,\"breadth\":[\"Natural Sciences\"],\"professor\":\"John Doe\",\"professorRating\":4.9,\"description\":\"Introduces students to Object-Oriented Programming using classes and objects to solve more complex problems. The course also introduces array-based and linked data structures: including lists, stacks, and queues. Programming assignments require writing and developing multi-class (file) programs using interfaces, generics, and exception handling to solve challenging real world problems. Topics reviewed include reading/writing data and objects from/to files and exception handling, and command line arguments. Topics introduced: object-oriented design; class vs. object; create and define interfaces and iterators; searching and sorting; abstract data types (List,Stack,Queue,PriorityQueue(Heap),Binary Search Tree); generic interfaces (parametric polymorphism); how to design and write test methods and classes; array based vs. linked node implementations; introduction to complexity analysis; recursion.\",\"schedule\":[\"12:00-13:00\",\"\",\"12:00-13:00\",\"\",\"12:00-13:00&14:00-15:00\"],\"fullCourse\":\"CS302-001\",\"department\":\"CS\",\"number\":\"302\"}," +
                "{\"course\":\"CS302\",\"section\":1,\"title\":\"Introduction to Programming\",\"numCredits\":3,\"breadth\":[\"Natural Sciences\"],\"professor\":\"John Doe\",\"professorRating\":4.9,\"description\":\"Introduces students to Object-Oriented Programming using classes and objects to solve more complex problems. The course also introduces array-based and linked data structures: including lists, stacks, and queues. Programming assignments require writing and developing multi-class (file) programs using interfaces, generics, and exception handling to solve challenging real world problems. Topics reviewed include reading/writing data and objects from/to files and exception handling, and command line arguments. Topics introduced: object-oriented design; class vs. object; create and define interfaces and iterators; searching and sorting; abstract data types (List,Stack,Queue,PriorityQueue(Heap),Binary Search Tree); generic interfaces (parametric polymorphism); how to design and write test methods and classes; array based vs. linked node implementations; introduction to complexity analysis; recursion.\",\"schedule\":[\"12:00-13:00\",\"\",\"12:00-13:00\",\"\",\"12:00-13:00&14:00-15:00\"],\"fullCourse\":\"CS302-001\",\"department\":\"CS\",\"number\":\"302\"}," +
                "{\"course\":\"CS302\",\"section\":1,\"title\":\"Introduction to Programming\",\"numCredits\":3,\"breadth\":[\"Natural Sciences\"],\"professor\":\"John Doe\",\"professorRating\":4.9,\"description\":\"Introduces students to Object-Oriented Programming using classes and objects to solve more complex problems. The course also introduces array-based and linked data structures: including lists, stacks, and queues. Programming assignments require writing and developing multi-class (file) programs using interfaces, generics, and exception handling to solve challenging real world problems. Topics reviewed include reading/writing data and objects from/to files and exception handling, and command line arguments. Topics introduced: object-oriented design; class vs. object; create and define interfaces and iterators; searching and sorting; abstract data types (List,Stack,Queue,PriorityQueue(Heap),Binary Search Tree); generic interfaces (parametric polymorphism); how to design and write test methods and classes; array based vs. linked node implementations; introduction to complexity analysis; recursion.\",\"schedule\":[\"12:00-13:00\",\"\",\"12:00-13:00\",\"\",\"12:00-13:00&14:00-15:00\"],\"fullCourse\":\"CS302-001\",\"department\":\"CS\",\"number\":\"302\"}," +
                "{\"course\":\"CS302\",\"section\":1,\"title\":\"Introduction to Programming\",\"numCredits\":3,\"breadth\":[\"Natural Sciences\"],\"professor\":\"John Doe\",\"professorRating\":4.9,\"description\":\"Introduces students to Object-Oriented Programming using classes and objects to solve more complex problems. The course also introduces array-based and linked data structures: including lists, stacks, and queues. Programming assignments require writing and developing multi-class (file) programs using interfaces, generics, and exception handling to solve challenging real world problems. Topics reviewed include reading/writing data and objects from/to files and exception handling, and command line arguments. Topics introduced: object-oriented design; class vs. object; create and define interfaces and iterators; searching and sorting; abstract data types (List,Stack,Queue,PriorityQueue(Heap),Binary Search Tree); generic interfaces (parametric polymorphism); how to design and write test methods and classes; array based vs. linked node implementations; introduction to complexity analysis; recursion.\",\"schedule\":[\"12:00-13:00\",\"\",\"12:00-13:00\",\"\",\"12:00-13:00&14:00-15:00\"],\"fullCourse\":\"CS302-001\",\"department\":\"CS\",\"number\":\"302\"}," +
                "{\"course\":\"CS302\",\"section\":1,\"title\":\"Introduction to Programming\",\"numCredits\":3,\"breadth\":[\"Natural Sciences\"],\"professor\":\"John Doe\",\"professorRating\":4.9,\"description\":\"Introduces students to Object-Oriented Programming using classes and objects to solve more complex problems. The course also introduces array-based and linked data structures: including lists, stacks, and queues. Programming assignments require writing and developing multi-class (file) programs using interfaces, generics, and exception handling to solve challenging real world problems. Topics reviewed include reading/writing data and objects from/to files and exception handling, and command line arguments. Topics introduced: object-oriented design; class vs. object; create and define interfaces and iterators; searching and sorting; abstract data types (List,Stack,Queue,PriorityQueue(Heap),Binary Search Tree); generic interfaces (parametric polymorphism); how to design and write test methods and classes; array based vs. linked node implementations; introduction to complexity analysis; recursion.\",\"schedule\":[\"12:00-13:00\",\"\",\"12:00-13:00\",\"\",\"12:00-13:00&14:00-15:00\"],\"fullCourse\":\"CS302-001\",\"department\":\"CS\",\"number\":\"302\"}," +
                "{\"course\":\"CS302\",\"section\":1,\"title\":\"Introduction to Programming\",\"numCredits\":3,\"breadth\":[\"Natural Sciences\"],\"professor\":\"John Doe\",\"professorRating\":4.9,\"description\":\"Introduces students to Object-Oriented Programming using classes and objects to solve more complex problems. The course also introduces array-based and linked data structures: including lists, stacks, and queues. Programming assignments require writing and developing multi-class (file) programs using interfaces, generics, and exception handling to solve challenging real world problems. Topics reviewed include reading/writing data and objects from/to files and exception handling, and command line arguments. Topics introduced: object-oriented design; class vs. object; create and define interfaces and iterators; searching and sorting; abstract data types (List,Stack,Queue,PriorityQueue(Heap),Binary Search Tree); generic interfaces (parametric polymorphism); how to design and write test methods and classes; array based vs. linked node implementations; introduction to complexity analysis; recursion.\",\"schedule\":[\"12:00-13:00\",\"\",\"12:00-13:00\",\"\",\"12:00-13:00&14:00-15:00\"],\"fullCourse\":\"CS302-001\",\"department\":\"CS\",\"number\":\"302\"}," +
                "{\"course\":\"CS302\",\"section\":1,\"title\":\"Introduction to Programming\",\"numCredits\":3,\"breadth\":[\"Natural Sciences\"],\"professor\":\"John Doe\",\"professorRating\":4.9,\"description\":\"Introduces students to Object-Oriented Programming using classes and objects to solve more complex problems. The course also introduces array-based and linked data structures: including lists, stacks, and queues. Programming assignments require writing and developing multi-class (file) programs using interfaces, generics, and exception handling to solve challenging real world problems. Topics reviewed include reading/writing data and objects from/to files and exception handling, and command line arguments. Topics introduced: object-oriented design; class vs. object; create and define interfaces and iterators; searching and sorting; abstract data types (List,Stack,Queue,PriorityQueue(Heap),Binary Search Tree); generic interfaces (parametric polymorphism); how to design and write test methods and classes; array based vs. linked node implementations; introduction to complexity analysis; recursion.\",\"schedule\":[\"12:00-13:00\",\"\",\"12:00-13:00\",\"\",\"12:00-13:00&14:00-15:00\"],\"fullCourse\":\"CS302-001\",\"department\":\"CS\",\"number\":\"302\"}," +
                "{\"course\":\"CS302\",\"section\":1,\"title\":\"Introduction to Programming\",\"numCredits\":3,\"breadth\":[\"Natural Sciences\"],\"professor\":\"John Doe\",\"professorRating\":4.9,\"description\":\"Introduces students to Object-Oriented Programming using classes and objects to solve more complex problems. The course also introduces array-based and linked data structures: including lists, stacks, and queues. Programming assignments require writing and developing multi-class (file) programs using interfaces, generics, and exception handling to solve challenging real world problems. Topics reviewed include reading/writing data and objects from/to files and exception handling, and command line arguments. Topics introduced: object-oriented design; class vs. object; create and define interfaces and iterators; searching and sorting; abstract data types (List,Stack,Queue,PriorityQueue(Heap),Binary Search Tree); generic interfaces (parametric polymorphism); how to design and write test methods and classes; array based vs. linked node implementations; introduction to complexity analysis; recursion.\",\"schedule\":[\"12:00-13:00\",\"\",\"12:00-13:00\",\"\",\"12:00-13:00&14:00-15:00\"],\"fullCourse\":\"CS302-001\",\"department\":\"CS\",\"number\":\"302\"}," +
                "{\"course\":\"CS354\",\"section\":1,\"title\":\"Machine Org & Programming\",\"numCredits\":3,\"breadth\":[\"Natural Sciences\"],\"professor\":\"John Doe\",\"professorRating\":4.9,\"description\":\"An introduction to fundamental structures of computer systems and the C programming language with a focus on the low-level interrelationships and impacts on performance. Topics include the virtual address space and virtual memory, the heap and dynamic memory management, the memory hierarchy and caching, assembly language and the stack, communication and interrupts/signals, compiling and assemblers/linkers.\",\"schedule\":[\"12:00-13:00\",\"\",\"\",\"\",\"\"],\"fullCourse\":\"CS302-001\",\"department\":\"CS/ECE\",\"number\":\"354\"}," +
                "{\"course\":\"ECE 352\",\"section\":1,\"title\":\"Digital System Fundamentals\",\"numCredits\":3,\"breadth\":[\"Natural Sciences\"],\"professor\":\"John Doe\",\"professorRating\":4.9,\"description\":\"Logic components, Boolean algebra, combinational logic analysis and synthesis, synchronous and asynchronous sequential logic analysis and design, digital subsystems, computer organization and design.\",\"schedule\":[\"7:00-9:15\",\"4:00-4:15\",\"13:00-14:15\",\"14:30-15:45\",\"12:00-13:00&14:00-15:00\"],\"fullCourse\":\"CS302-001\",\"department\":\"ECE\",\"number\":\"352\"}," +
                "{\"course\":\"CS302\",\"section\":1,\"title\":\"Load JSON data from backend\",\"numCredits\":3,\"breadth\":[\"Natural Sciences\"],\"professor\":\"John Doe\",\"professorRating\":4.9,\"description\":\"This is a class where you will learn how to program.\",\"schedule\":[\"12:00-13:00\",\"16:00-17:15\",\"12:00-13:00\",\"11:55-13:15\",\"12:00-13:00&14:00-15:00\"],\"fullCourse\":\"CS302-001\",\"department\":\"JS\",\"number\":\"101\"}]";
        final JSONArray ja_Courses;
        JSONObject joCourse;
        try {
            ja_Courses = (JSONArray) parser.parse(parseThis);

            resultsTitle = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
            resultsTitle.setTitle(ja_Courses.size() + " Course Search Results");
            numResults = ja_Courses.size();
            a_ClassList = new ArrayList<Spanned>();
            for (int c = 0; c < ja_Courses.size(); c++) {
                joCourse = (JSONObject) ja_Courses.get(c);
                String sectionNum = ("" + joCourse.get("fullCourse")).split("-")[1];

                String cInfo = "<b>" + joCourse.get("title") + "</b>";

                cInfo += "<br /><i>" + joCourse.get("department") + " "
                        + joCourse.get("number") + " - " + sectionNum
                        + "</i> (" + joCourse.get("numCredits") + " credits)";

                a_ClassList.add(Html.fromHtml(cInfo));

            }
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
            return;
        }

        ListView courseList = (ListView) findViewById(R.id.courseList_collapse);
        final ArrayAdapter<Spanned> adapterList = new ArrayAdapter<Spanned>(this,
                R.layout.search_results_textview, a_ClassList);
        courseList.setAdapter(adapterList);
        ViewCompat.setNestedScrollingEnabled(courseList, true);

        courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // Remove interest from list if clicked
                Toast.makeText(CourseSearchResults_collapsible.this, a_ClassList.get(position).toString() + " was selected.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CourseSearchResults_collapsible.this, CourseDescription.class);
                intent.putExtra("jsonCourseData", ja_Courses.get(position).toString());
                String bool = "false";
                if (position == ja_Courses.size() - 1)
                    bool = "true";
                intent.putExtra("useJSONbool", bool);
                System.out.println(ja_Courses.get(position).toString());
                startActivity(intent);
            }
        });

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0)
                    resultsTitle.setTitle(numResults + " course search results found");
                else
                    resultsTitle.setTitle(numResults + " course search results found");
            }
        });
    }
}
