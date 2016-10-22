import java.io.*;
import java.net.*;
import com.google.gson.*;

public class Connection {

  private boolean connected;
  private final String URL_LOCATION;
  public static final String TOKEN = "8755d63d6136806e0b80e0648ef168e4";
  private final String GITHUB = "https://github.com/faustinoaguirre/registration";
  HttpURLConnection urlConnection;

  public Connection(String location) {
    URL_LOCATION = location;
    connected = false;
  }

  void establishConnection() {
    try {
      URL url = new URL(URL_LOCATION);
      urlConnection = (HttpURLConnection)url.openConnection();
      urlConnection.setRequestMethod("POST");
      urlConnection.setDoOutput(true);
      urlConnection.setDoInput(true);
      urlConnection.setRequestProperty("Content-Type", "application/json");
      connected = true;
    } catch (Exception e) {
        System.out.println("Connection failed");
    }
  }

  void postRequest(JsonObject json) {
    try{
      OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
      String message = json.toString();
      writer.write(message);
      System.out.printf("Request: %s sent to %s\n",message,URL_LOCATION);
      writer.flush();
      writer.close();
      urlConnection.connect();

      int responseCode = urlConnection.getResponseCode();
      System.out.printf("Response Code: %d\n", responseCode);
      if(responseCode == 400) {
        System.out.println(getError());
      }
    } catch (Exception e) {
      System.out.println("POST failed");
    } 
  }

  String getRequest() {
    try{
      BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
      String inputLine;
      StringBuffer response = new StringBuffer();
      while((inputLine=in.readLine()) != null) {
        response.append(inputLine);
      }
      in.close();
      return response.toString();
    } catch (Exception e) {
      System.out.println("GET failed");
      return null;
    }
  }

  String getError() {
    try{
      BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream()));
      String inputLine;
      StringBuffer response = new StringBuffer();
      while((inputLine=in.readLine()) != null) {
        response.append(inputLine);
      }
      in.close();
      return response.toString();
    } catch (Exception e) {
      System.out.println("GET failed");
      return null;
    }
  }

  boolean connected() {
    return connected;
  }

  public static void main(String[] args) {
    Connection myConnection = new Connection("http://challenge.code2040.org/api/register");
    myConnection.establishConnection();
    if(myConnection.connected()) {
      
      JsonObject json = new JsonObject();
      
      json.addProperty("token", myConnection.TOKEN);
      json.addProperty("github", myConnection.GITHUB);

      myConnection.postRequest(json);
    } 
  }
}