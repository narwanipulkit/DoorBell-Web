package hello;
import java.util.*;
import java.io.*;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import com.google.cloud.storage.Bucket;

class Storage{
	public static void save(byte[] input) throws Exception{
		String key=System.getenv("firebase");
		FileOutputStream fos=new FileOutputStream(new File("src/main/resources/static/smartb-7cffa-firebase-adminsdk-y293y-f7d6eeec7a.json"));
		fos.write(key.getBytes());
		FileInputStream serviceAccount = new FileInputStream("src/main/resources/static/smartb-7cffa-firebase-adminsdk-y293y-f7d6eeec7a.json");

		FirebaseOptions options = new FirebaseOptions.Builder()
		    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
		    .setStorageBucket("smartb-7cffa.appspot.com")
		    .build();
		FirebaseApp.initializeApp(options);

		Bucket bucket = StorageClient.getInstance().bucket();
		bucket.create((new Date()).toString()+".jpeg",input);
	}
}