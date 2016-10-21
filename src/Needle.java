import com.google.gson.*;
import java.io.*;
import java.net.*;
import java.util.*;

class Needle {
  private static final String URL_LOCATION_HAYSTACK = "http://challenge.code2040.org/api/haystack";
  private static final String URL_LOCATION_VALIDATE = "http://challenge.code2040.org/api/haystack/validate";
  private static Connection myConnection = new Connection(URL_LOCATION_HAYSTACK);

  static int findNeedle(HashMap<String,String> map) {
    String needle = map.get("needle");
    Object[] value = map.values().toArray();
    String haystack = value[0].toString();

    //Retrieve list of string values
    ArrayList<String> hayList = new ArrayList<>();
    for(int j = 1; j < haystack.length(); j+=10) {
      hayList.add(haystack.substring(j,j+8));
    }

    //Search for needle
    for(int i = 0; i < hayList.size(); i++) {
      if(hayList.get(i).equals(needle)) {
        return i;
      }
    }
    return -1;
  }

  public static void main(String[] args) {
    myConnection.establishConnection();
    if(myConnection.connected()) {
      JsonObject json = new JsonObject();
      json.addProperty("token", Connection.TOKEN);
      myConnection.postRequest(json);
      String response = myConnection.getRequest();
      HashMap<String,String> map = new HashMap<String,String>();
      int index = findNeedle((HashMap<String,String>)(new Gson()).fromJson(response, map.getClass()));
      myConnection = (new Connection(URL_LOCATION_VALIDATE));
      myConnection.establishConnection();
      if(myConnection.connected()) {
        json = new JsonObject();
        json.addProperty("token", Connection.TOKEN);
        json.addProperty("needle", index);
        myConnection.postRequest(json);
      }
    }
  }
} 