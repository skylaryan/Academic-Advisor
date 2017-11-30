package wisc.academicadvisor;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import android.content.Intent;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CourseDescription extends AppCompatActivity {

    private TextView course_UID, course_title, credits, breadth,
            professor, prof_rating,
            schedule,
            course_desc;

    private String JSresponse = "";
    int numLines = 0;

    /**
     * URL url = null;
     String line = "";
     try {
     url = new URL("http://tyleroconnell.com:8080/courses");
     HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
     try {
     BufferedReader rd = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
     while ((line = rd.readLine()) != null) {
     JSresponse += line + "\n";
     numLines++;
     }
     } finally {
     urlConnection.disconnect();
     }
     } catch (Exception e) {
     e.printStackTrace();
     }

     return JSresponse;
     */

    protected class readServerPage extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... in) {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("http://tyleroconnell.com:8080/courses");
            try {
                HttpResponse execute = client.execute(httpGet);
                InputStream content = execute.getEntity().getContent();

                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = buffer.readLine()) != null) {
                    sb.append(line);
                    numLines++;
                }
                JSresponse = sb.toString();
                content.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }

            return JSresponse;
        }
    }

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

        /**String jsCheck = "";
        if (JSresponse.length() == 0)
            jsCheck = "\nWebPage Reader returns NULL!";
        course_desc.setText("NumLines: " + numLines + jsCheck);*/




        readServerPage rSp = new readServerPage();
        rSp.execute();

        try {
            JSONParser parser = new JSONParser();
            final String cDataIn = getIntent().getStringExtra("jsonCourseData");

            JSONObject joCourse;
            if (JSresponse.length() == 0)
                joCourse = (JSONObject) parser.parse(cDataIn);
            else
                joCourse = (JSONObject) parser.parse(JSresponse);

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
