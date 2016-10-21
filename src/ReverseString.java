import com.google.gson.*;
import java.io.*;
import java.net.*;

class ReverseString {
  private static final String URL_LOCATION_REVERSE = "http://challenge.code2040.org/api/reverse";
  private static final String URL_LOCATION_VALIDATE = "http://challenge.code2040.org/api/reverse/validate";
  private static Connection myConnection = new Connection(URL_LOCATION_REVERSE);

  public static void main(String[] args) {
    //Get string to reverse.
    myConnection.establishConnection();
    if(myConnection.connected()) {
      JsonObject json = new JsonObject();
      json.addProperty("token", Connection.TOKEN);
      myConnection.postRequest(json);
      String response = myConnection.getRequest();

      //Post reversed string.
      myConnection = (new Connection(URL_LOCATION_VALIDATE));
      myConnection.establishConnection();
      if(myConnection.connected()) {
        json = new JsonObject();
        json.addProperty("token", Connection.TOKEN);
        json.addProperty("string", new StringBuilder(response).reverse().toString());
        myConnection.postRequest(json);
      }
    }
  }
} 