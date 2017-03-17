package ecs189.querying.github;

import ecs189.querying.Util;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Vincent on 10/1/2017.
 */
public class GithubQuerier {

    private static final String BASE_URL = "https://api.github.com/users/";

    public static String eventsAsHTML(String user) throws IOException, ParseException {
        List<JSONObject> response = getEvents(user);
        StringBuilder sb = new StringBuilder();
        sb.append("<div>");
        for (int i = 0; i < response.size(); i++) {
            JSONObject event = response.get(i);
            // Get event type
            String type = event.getString("type");
            // Get created_at date, and format it in a more pleasant style
            String creationDate = event.getString("created_at");
            SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
            SimpleDateFormat outFormat = new SimpleDateFormat("dd MMM, yyyy");
            Date date = inFormat.parse(creationDate);
            String formatted = outFormat.format(date);

            //Get commits. Note, JSONObject event = "fields"
            JSONObject payload = event.getJSONObject("payload");
            JSONArray commits = payload.getJSONArray("commits");


            // Add type of event as header
            sb.append("<h3 class=\"type\">");
            sb.append(type);
            sb.append("</h3>");
            // Add formatted date
            sb.append(" on ");
            sb.append(formatted);
            sb.append("<br />");


            //Insert commits
            sb.append("<br>");
            sb.append("<table>");
            sb.append("<col width=\"200\">");
            sb.append("<tr>");
            sb.append("<th>SHA</th>");
            sb.append("<th>Message</th>");
            sb.append("</tr>");

            for (int j = 0; j < commits.length(); j++) {
                JSONObject currentCommit = commits.getJSONObject(j);
                String sha = currentCommit.getString("sha");
                sha = sha.substring(0,8);
                String msg = currentCommit.getString("message");
                sb.append("<tr>");
                sb.append("<td>");
                sb.append(sha);
                sb.append("</td>");
//                sb.append("<br />");
                sb.append("<td>");
                sb.append(msg);
                sb.append("</td>");
//                sb.append("<br />");
                sb.append("</tr>");
            }
            sb.append("</table>");
            sb.append("<br>");

            // Add collapsible JSON textbox (don't worry about this for the homework; it's just a nice CSS thing I like)
            sb.append("<a data-toggle=\"collapse\" href=\"#event-" + i + "\">JSON</a>");
            sb.append("<div id=event-" + i + " class=\"collapse\" style=\"height: auto;\"> <pre>");
            sb.append(event.toString());
            sb.append("</pre> </div>");
        }
        sb.append("</div>");
        return sb.toString();
    }

    private static List<JSONObject> getEvents(String user) throws IOException {
        List<JSONObject> eventList = new ArrayList<JSONObject>();
        String url = BASE_URL + user + "/events";
        System.out.println(url);

        int page = 1;
        boolean empty = false;
        int numFound = 0;

        do {
            JSONObject json = Util.queryAPI(new URL(url + "?page=" + page));
            System.out.println(json);
            JSONArray events = json.getJSONArray("root");

            if (events.length() > 0) { //if array not empty
                for (int i = 0; i < events.length() && numFound < 10; i++) {
                    JSONObject fields = events.getJSONObject(i);
                    if (fields.getString("type").equals("PushEvent")) {
                        eventList.add(events.getJSONObject(i));
                        numFound++;
                    }
                }
            }
            else {
                empty = true;
            }
            page++;
        }
        while (!empty && numFound < 10); //loop again if number of push events found < 10, and we still have pages

        return eventList;
    }
}