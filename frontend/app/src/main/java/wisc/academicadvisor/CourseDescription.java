package wisc.academicadvisor;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
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
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CourseDescription extends AppCompatActivity {

    private TextView course_UID, course_title, credits, breadth, course_desc;

    private String course = "", num = "";

    private String jsResponse = "";
    private ArrayList<String> sec_array = new ArrayList<String>();

    private ProgressBar pb;

    protected class readServerPage extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String s) {
            pb.setVisibility(View.INVISIBLE);
            findViewById(R.id.subLevel).setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... in) {
            try {
                HttpURLConnection urlConnection = null;
                URL url = null;
                try {
                    course = getIntent().getStringExtra("course");
                    num = getIntent().getStringExtra("courseNum");
                    String search_URL = "http://tyleroconnell.com:8080/course?course=" +
                            course + "&num=" + num;
                    url = new URL(search_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String line;
                    while ((line = rd.readLine()) != null)
                        jsResponse += line + "\n";

                    try {
                        JSONParser parser = new JSONParser();
                        JSONArray jaCourse = (JSONArray) parser.parse(jsResponse);

                        final JSONObject firstCourse = (JSONObject) jaCourse.get(0);
                        final String titleString = firstCourse.get("course") + " "
                                + firstCourse.get("courseNum");

                        String scrollText = "";
                        scrollText += firstCourse.get("description") + "<br />";
                        //Html.fromHtml(HTML-formatted string)

                        for (int sec = 0; sec < jaCourse.size(); sec++) {
                            scrollText += "<br />"; // OR USE <br />
                            JSONObject secItr = (JSONObject) jaCourse.get(sec);
                            scrollText += "<b>" + secItr.get("section") + "</b>";
                            scrollText += "<br />Professor: " + secItr.get("professors");
                            scrollText += "<br />Rating: " + secItr.get("ratings");
                            scrollText += "<br />Average GPA: " + secItr.get("gpa");
                            scrollText += "<br />";
                            sec_array.add(parseSchedule(secItr.get("schedules") + ""));
                            scrollText +=
                                    parseSchedule(secItr.get("schedules") + "");
                            scrollText += "<br />";
                        }
                        final String FscrollText = scrollText;
                        course_UID.post(new Runnable() {
                            @Override
                            public void run() {
                                course_UID.setText(titleString);
                                course_title.setText("" + firstCourse.get("title"));
                                credits.setText("" + firstCourse.get("numCredits"));
                                String bs = firstCourse.get("breadth") + "";
                                if (bs.length() == 0)
                                    bs = "N/A";
                                breadth.setText(bs);
                                course_desc.setText(Html.fromHtml(FscrollText));
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
        course_title = (TextView) findViewById(R.id.course_title);
        credits = (TextView) findViewById(R.id.credits);
        breadth = (TextView) findViewById(R.id.breadth);
        course_desc = (TextView) findViewById(R.id.course_desc);

        pb = (ProgressBar) findViewById(R.id.progressBar);


        readServerPage rSp = new readServerPage();
        rSp.execute();
    }

    public String parseSchedule(String sch) {
        /** Parses strings of the form
         *  MW 4:00-5:15PM\nTR 4:00-5:15PM         *
         */
        String[] daysOfWeek = {"", "", "", "", ""};
        String days = "MTWRF";
        String schOut = "";
        String[] schArr_NL = sch.split("\n");
        // for distinct class times
        for (int L = 0; L < schArr_NL.length; L++) {
            if (schArr_NL[L].length() > 0) {
                String[] perLineArr = schArr_NL[L].split("\\s+");
                // each day
                for (int D = 0; D < 5; D++) {
                    if (perLineArr[0].contains("" + days.charAt(D))) {
                        if (!daysOfWeek[D].equals(""))
                            daysOfWeek[D] += ", "; // multiple classes in 1 day
                        else
                            daysOfWeek[D] += days.charAt(D) + ": "; // 1st class of day
                        daysOfWeek[D] += perLineArr[1]; // time range
                    }
                }
            }
        }
        boolean firstDayReached = false;
        for (int D = 0; D < 5; D++) {
            if (daysOfWeek[D].length() > 0) {
                if (!firstDayReached)
                    firstDayReached = true;
                else // only add new line if we had class on an earlier day of the week
                    schOut += "<br />";
                schOut += daysOfWeek[D];
            }
        }
        return schOut;
    }

    public String parseScheduleMilitary(String[] sch) {
        String s = "";
        boolean first_DOW_reached = false;
        // have we encountered a day of the week with courses yet?
        String MTWRF = "MTWRF";
        for (int day = 0; day < 5; day++) {
            if (sch[day].length() > 0) { // any courses on that day?
                if (!first_DOW_reached)
                    first_DOW_reached = true;
                else // 2nd day of the week with courses, use newline
                    s += "\n";
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

            }
        }
        return s;
    }


}
