package wisc.academicadvisor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONObject;

import android.text.Html;
import android.widget.TextView;


public class CourseSearch extends AppCompatActivity {

    private TextView course_UID, course_title, credits, breadth,
            professor, prof_rating,
            schedule,
            course_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_search);

        course_UID = (TextView) findViewById(R.id.course_UID);
        // combines course department, course #, and section #
        course_title = (TextView) findViewById(R.id.course_title);
        credits = (TextView) findViewById(R.id.credits);
        breadth = (TextView) findViewById(R.id.breadth);
        professor = (TextView) findViewById(R.id.professor);
        prof_rating = (TextView) findViewById(R.id.prof_rating);
        schedule = (TextView) findViewById(R.id.schedule);
        course_desc = (TextView) findViewById(R.id.course_desc);
        // course_desc should be appeneded with \n to see last line of text!
        //      (if scrolling required)

        JSONObject jsonObj = new JSONObject();

        String[] bArray = {"Natural Sciences", "Test"};
        String[] scheduleArray = {"10:00-13:00", "", "12:00-8:00", "", "7:00-13:00&7:00-8:00"};

        try {
            jsonObj.put("course", "CS302");
            jsonObj.put("section", 1);
            jsonObj.put("course_title", "Introduction to Programming");
            jsonObj.put("credits", 3);
            jsonObj.put("breadth", bArray);
            jsonObj.put("professor", "John Doe");
            jsonObj.put("professorRating", 4.9);
            jsonObj.put("description", "This is a class where you will learn how to program.");
            jsonObj.put("schedule", scheduleArray);
            jsonObj.put("fullCourse", "CS302-001");
            jsonObj.put("department", "CS");
            jsonObj.put("number", 302);

            String breadthString = "";
            String[] breadthArray = (String[]) jsonObj.get("breadth");
            for (int i = 0; i < breadthArray.length; i++) {
                breadthString += breadthArray[i];
                if (i + 1 < breadthArray.length)
                    breadthString += ", ";
            }

            String sectionNum = jsonObj.getString("fullCourse").split("-")[1];

            String sourceString = "<b>" + jsonObj.getString("department") + " "
                    + jsonObj.getInt("number") + "</b>" + " - <i>" + sectionNum + "</i>";
            course_UID.setText(Html.fromHtml(sourceString));

            course_title.setText(jsonObj.getString("course_title"));
            credits.setText("" + jsonObj.getInt("credits"));
            breadth.setText(breadthString);
            professor.setText(jsonObj.getString("professor"));
            prof_rating.setText("" + jsonObj.getDouble("professorRating"));
            schedule.setText(parseSchedule((String[]) jsonObj.get("schedule")));
            //course_desc.setText(jsonObj.getString("description"));


            jsonObj.getString("course");
            jsonObj.getInt("");

        } catch (org.json.JSONException e) {
            e.printStackTrace();
            return;
        }

    }

    public String parseSchedule(String[] sch) {
        String s = "";
        String MTWRF = "MTWRF";
        for (int day = 0; day < 5; day++) {
            if (sch[day].length() > 0) {
                s += (MTWRF.charAt(day) + ":");
                String[] classRepeats = sch[day].split("&");
                for (int j = 0; j < classRepeats.length; j++) {
                    String[] times = classRepeats[j].split("-");
                    String start = times[0], end = times[1];
                    String startHour = start.split(":")[0];
                    String m = " AM - ";
                    int sHr = Integer.parseInt(startHour);
                    if (sHr >= 12) {
                        m = " PM - ";
                        if (sHr > 12) {
                            sHr -= 12;
                            start = sHr + ":" + start.split(":")[1];
                        }
                    }
                    s += " " + start + m;
                    String endHour = end.split(":")[0];
                    m = " AM";
                    int eHr = Integer.parseInt(endHour);
                    if (Integer.parseInt(endHour) >= 12) {
                        m = " PM";
                        if (eHr > 12) {
                            eHr -= 12;
                            end = eHr + ":" + end.split(":")[1];
                        }
                    }
                    s += end + m;
                    if (j + 1 < classRepeats.length)
                        s += ","; // last class of day
                }
                if (day != 4)
                    s += "\n";
                // last day, don't need extra \n whitespace between schedule and course_desc
            }
        }
        return s;
    }

}
