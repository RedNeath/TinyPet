package foo;

import java.util.Collections;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

public class AuthenticationVerifier {
    private static final String CLIENT_ID = "440998180387-r3rk5d6lbn3h9u1dksplncu8fdrdqctl.apps.googleusercontent.com";
    
    public static Identity verifyToken(String idTokenString) {
        GoogleIdToken idToken;
        GoogleIdToken.Payload tokenPayload;
        String username;
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();

        try {
            idToken = verifier.verify(idTokenString);
            if (idToken == null) return null;

            tokenPayload = idToken.getPayload();

            username = new StringBuilder()
                .append((String) tokenPayload.get("given_name"))
                .append("_")
                .append((String) tokenPayload.get("family_name"))
                .append("_<")
                .append(tokenPayload.getEmail())
                .append(">")
                .toString()
            ;
            
            return new Identity(username);
        } catch (Exception e) {
            return null;
        }
    }
}
