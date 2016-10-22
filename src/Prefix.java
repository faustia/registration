import com.google.gson.*;
import java.io.*;
import java.net.*;
import java.util.*;

class Prefix {
  private static final String URL_LOCATION_PREFIX = "http://challenge.code2040.org/api/prefix";
  private static final String URL_LOCATION_VALIDATE = "http://challenge.code2040.org/api/prefix/validate";
  private static Connection myConnection = new Connection(URL_LOCATION_PREFIX);

  private static JsonArray findPrefix(HashMap<String,String> map) {
    String prefix = map.get("prefix");
    Object[] value = map.values().toArray();
    String words = value[0].toString();

    //Search for words without prefix in the retrieved string array
    JsonArray stringList = new JsonArray();
    for(int j = 1; j < words.length(); j+=10) {
      String s = words.substring(j,j+8);
      if(!s.substring(0,prefix.length()).equals(prefix)) {
        stringList.add(s);
      }
    }

    return stringList;
  }

  public static void main(String[] args) {
    myConnection.establishConnection();
    if(myConnection.connected()) {
      JsonObject json = new JsonObject();
      json.addProperty("token", Connection.TOKEN);
      myConnection.postRequest(json);
      String response = myConnection.getRequest();
      HashMap<String,String> map = new HashMap<String,String>();
      JsonArray array = findPrefix((HashMap<String,String>)(new Gson()).fromJson(response, map.getClass()));
      myConnection = (new Connection(URL_LOCATION_VALIDATE));
      myConnection.establishConnection();
      if(myConnection.connected()) {
        json = new JsonObject();
        json.addProperty("token", Connection.TOKEN);
        json.add("array", array);
        myConnection.postRequest(json);
      }
    }
  }
} 