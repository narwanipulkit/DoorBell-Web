package hello;

import java.util.*;
import java.io.*;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;

class FirebaseServe{

	FirebaseServe(String detected){
		try{
			String key=System.getenv("firebase");
			FileOutputStream fos=new FileOutputStream(new File("src/main/resources/static/smartb-7cffa-firebase-adminsdk-y293y-f7d6eeec7a.json"));
			fos.write(key);
			FileInputStream serviceAccount = new FileInputStream("src/main/resources/static/smartb-7cffa-firebase-adminsdk-y293y-f7d6eeec7a.json");

			FirebaseOptions options = new FirebaseOptions.Builder()
			    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
			    .setDatabaseUrl("https://smartb-7cffa.firebaseio.com")
			    .build();

			FirebaseApp.initializeApp(options);

			final FirebaseDatabase database = FirebaseDatabase.getInstance();
			DatabaseReference ref = database.getReference("server/");

			DatabaseReference motionRef=ref.child("motion");
			motionRef.setValueAsync(detected);
		}
		catch(IOException e){
			System.out.println(e);
		}
	}
}