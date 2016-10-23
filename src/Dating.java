import com.google.gson.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.text.SimpleDateFormat;

class Dating {
  private static final String URL_LOCATION_DATING = "http://challenge.code2040.org/api/dating";
  private static final String URL_LOCATION_VALIDATE = "http://challenge.code2040.org/api/dating/validate";
  private static Connection myConnection = new Connection(URL_LOCATION_DATING);

  static String getDate(HashMap<String,String> map) {
    Object interval2 = map.get("interval");
    Double interval = Double.parseDouble(interval2.toString());
    int seconds = interval.intValue();
    String stamp = map.get("datestamp");

    //Create Calendar class with given date for simple addition of interval
    String pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    try{
      SimpleDateFormat sdf = new SimpleDateFormat(pattern);
      Date date = sdf.parse(stamp);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      calendar.add(Calendar.SECOND, seconds);
      return sdf.format(calendar.getTime());
    } 
    catch(Exception e) {
      System.out.println(e);
      return "";
    }
  }

  public static void main(String[] args) {
    myConnection.establishConnection();
    if(myConnection.connected()) {
      JsonObject json = new JsonObject();
      json.addProperty("token", Connection.TOKEN);
      myConnection.postRequest(json);
      String response = myConnection.getRequest();
      HashMap<String,String> map = new HashMap<String,String>();
      String dateStamp = getDate((HashMap<String,String>)(new Gson()).fromJson(response, map.getClass()));
      myConnection = (new Connection(URL_LOCATION_VALIDATE));
      myConnection.establishConnection();
      if(myConnection.connected()) {
        json = new JsonObject();
        json.addProperty("token", Connection.TOKEN);
        json.addProperty("datestamp", dateStamp);
        myConnection.postRequest(json);
      }
    }
  }
} 