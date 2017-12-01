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
import android.view.View;
import android.widget.ProgressBar;
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

    private ProgressBar pb;

    protected class readServerPage extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            pb.setProgress(0);
            pb.setMax(100);
        }

        @Override
        protected void onPostExecute(String s) {
            pb.setVisibility(View.INVISIBLE);
            findViewById(R.id.subLevel).setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... in) {
            try {

                URL url = null;
                try {
                    url = new URL("http://tyleroconnell.com:8080/courses");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    try {
                        BufferedReader rd = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        String content = "", line;
                        while ((line = rd.readLine()) != null) {
                            content += line + "\n";
                        }
                        //System.out.println(content);

                        JSONParser parser = new JSONParser();
                        final String cDataIn = getIntent().getStringExtra("jsonCourseData");
                        final String useJSONbool = getIntent().getStringExtra("useJSONbool");

                        final JSONObject joCourse;
                        JSONArray jaCourse;

                        if (useJSONbool.equals("true")) {
                            jaCourse = (JSONArray) parser.parse(content);
                            joCourse = (JSONObject) jaCourse.get(0);
                        }
                        else
                            joCourse = (JSONObject) parser.parse(cDataIn);

                        final String breadthString;
                        String bs = "";
                        JSONArray jBrdth = (JSONArray) joCourse.get("breadth");
                        for (int i = 0; i < jBrdth.size(); i++) {
                            bs += "" + jBrdth.get(i);
                            if (i + 1 < jBrdth.size())
                                bs += ", ";
                        }
                        breadthString = bs;

                        String sectionNum = ("" + joCourse.get("fullCourse")).split("-")[1];

                        final String sourceString = "<b>" + joCourse.get("department") + " "
                                + joCourse.get("number") + "</b>" + " - <i>" + sectionNum + "</i>";

                        course_UID.post(new Runnable() {
                            @Override
                            public void run() {
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
                        });




                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
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

        pb = (ProgressBar) findViewById(R.id.progressBar);


        readServerPage rSp = new readServerPage();
        rSp.execute();
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
