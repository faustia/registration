import java.io.*;
import java.net.*;
import com.google.gson.*;

public class Connection {

  private final String URL_LOCATION;
  private final String TOKEN = "8755d63d6136806e0b80e0648ef168e4";
  private final String GITHUB = "https://github.com/faustinoaguirre/registration";
  URLConnection urlConnection;

  public Connection(String location) {
    URL_LOCATION = location;
  }

  private URLConnection establishConnection() {
    try {
      URL url = new URL(URL_LOCATION);
      urlConnection = url.openConnection();
      urlConnection.setDoOutput(true);
      return urlConnection;
    } catch (Exception e) {
        System.out.println("Connection failed");
        return null;
    }
  }

  private void sendRequest(JsonObject json) {
    try{
      OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
      writer.write(json.toString());
      writer.close();
    } catch (Exception e) {
      System.out.println("Request failed");
    }
  }

  public static void main(String[] args) {
    Connection myConnection = new Connection("http://challenge.code2040.org/api/register");
    if(myConnection != null) {
      URLConnection urlConnection = myConnection.establishConnection();
      
      JsonObject json = new JsonObject();
      json.addProperty("token", myConnection.TOKEN);
      json.addProperty("github", myConnection.GITHUB);
      
      myConnection.sendRequest(json);
    } 
  }
}