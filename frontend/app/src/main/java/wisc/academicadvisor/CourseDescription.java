package wisc.academicadvisor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import android.text.Html;
import android.widget.TextView;


public class CourseDescription extends AppCompatActivity {

    private TextView course_UID, course_title, credits, breadth,
            professor, prof_rating,
            schedule,
            course_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_description);

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


        try {
            JSONParser parser = new JSONParser();
            String parseThis = "[{\"course\":\"CS302\",\"section\":1,\"title\":\"Introduction to Programming\",\"numCredits\":3,\"breadth\":[\"Test\",\"Natural Sciences\"],\"professor\":\"John Doe\",\"professorRating\":4.9,\"description\":\"This is a class where you will learn how to program.\",\"schedule\":[\"12:00-13:00\",\"\",\"12:00-13:00\",\"\",\"12:00-13:00&14:00-15:00\"],\"fullCourse\":\"CS302-001\",\"department\":\"CS\",\"number\":\"302\"}]";
            JSONArray ja_Courses = (JSONArray) parser.parse(parseThis);

            for (int c = 0; c < ja_Courses.size(); c++) {
                JSONObject joCourse = (JSONObject) ja_Courses.get(c);

                String breadthString = "";
                JSONArray jBrdth = (JSONArray) joCourse.get("breadth");
                for (int i = 0; i < jBrdth.size(); i++) {
                    breadthString += "" + jBrdth.get(i);
                    if (i + 1 < jBrdth.size())
                        breadthString += ", ";
                }

                String sectionNum = ("" + joCourse.get("fullCourse")).split("-")[1];

                String sourceString = "<b>" + joCourse.get("department") + " "
                        + joCourse.get("number") + "</b>" + " - <i>" + sectionNum + "</i>";
                course_UID.setText(Html.fromHtml(sourceString));

                course_title.setText("" + joCourse.get("title"));
                credits.setText("" + joCourse.get("numCredits"));
                breadth.setText(breadthString);
                professor.setText("" + joCourse.get("professor"));
                prof_rating.setText("" + joCourse.get("professorRating"));

                JSONArray jSch = (JSONArray) joCourse.get("schedule");
                String[] pjs = new String[jSch.size()];
                for (int i = 0; i < jSch.size(); i++) {
                    pjs[i] = (String) jSch.get(i);
                }
                schedule.setText(parseSchedule(pjs));
                course_desc.setText("" + joCourse.get("description"));
            }
        } catch (
                org.json.simple.parser.ParseException e)

        {
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
