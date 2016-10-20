import java.io.*;
import java.net.*;
import com.google.gson.*;

public class Connection {

  private final String URL_LOCATION;
  private final String TOKEN = "8755d63d6136806e0b80e0648ef168e4";
  private final String GITHUB = "https://github.com/faustinoaguirre/registration";
  HttpURLConnection urlConnection;

  public Connection(String location) {
    URL_LOCATION = location;
  }

  private HttpURLConnection establishConnection() {
    try {
      URL url = new URL(URL_LOCATION);
      urlConnection = (HttpURLConnection)url.openConnection();
      urlConnection.setRequestMethod("POST");
      urlConnection.setDoOutput(true);
      urlConnection.setDoInput(true);
      //urlConnection.setAllowUserInteraction(true);
      urlConnection.setRequestProperty("Content-Type", "application/json");
      return urlConnection;
    } catch (Exception e) {
        System.out.println("Connection failed");
        return null;
    }
  }

  private void sendRequest(JsonObject json) {
    try{
      OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
      String message = json.toString();
      writer.write(message);
      System.out.printf("Request: %s sent to %s\n",message,URL_LOCATION);
      writer.flush();
      writer.close();
      urlConnection.connect();

      int responseCode = urlConnection.getResponseCode();
      System.out.printf("Response Code: %d", responseCode);
      int i =0;
    } catch (Exception e) {
      System.out.println("Request failed");
    }
  }

  public static void main(String[] args) {
    Connection myConnection = new Connection("http://challenge.code2040.org/api/register");
    if(myConnection != null) {
      HttpURLConnection urlConnection = myConnection.establishConnection();
      
      JsonObject json = new JsonObject();
      
      json.addProperty("token", myConnection.TOKEN);
      json.addProperty("github", myConnection.GITHUB);

      
      myConnection.sendRequest(json);
    } 
  }
}