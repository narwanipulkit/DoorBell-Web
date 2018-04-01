package hello;
import java.util.*;
import java.io.*;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import com.google.cloud.storage.Bucket;
import com.google.firebase.database.*;

class Storage{
	public static void save(byte[] input) throws Exception{
		String face="-";
		String key=System.getenv("firebase");
		FileOutputStream fos=new FileOutputStream(new File("src/main/resources/static/smartb-7cffa-firebase-adminsdk-y293y-f7d6eeec7a.json"));
		fos.write(key.getBytes());
		FileInputStream serviceAccount = new FileInputStream("src/main/resources/static/smartb-7cffa-firebase-adminsdk-y293y-f7d6eeec7a.json");

		FirebaseOptions options = new FirebaseOptions.Builder()
		    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
		    .setStorageBucket("smartb-7cffa.appspot.com")
		    .setDatabaseUrl("https://smartb-7cffa.firebaseio.com")
		    .build();

		boolean hasBeenInitialized=false;
		List<FirebaseApp> firebaseApps = FirebaseApp.getApps();
		for(FirebaseApp app : firebaseApps){
    		if(app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)){
       			hasBeenInitialized=true;
       			//finestayApp = app;
   			}
		}		
		if(!hasBeenInitialized) {
			FirebaseApp.initializeApp(options);
		}
		FirebaseApp.initializeApp(options);
		String timestamp=(new Date()).toString();

		Bucket bucket = StorageClient.getInstance().bucket();
		bucket.create(timestamp+".jpeg",input);

		
		final FirebaseDatabase database = FirebaseDatabase.getInstance();
		DatabaseReference ref = database.getReference("storage/");

		DatabaseReference motionRef=ref.child(timestamp);
		motionRef.setValueAsync(face);
			
	}
}