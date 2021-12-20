package application;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;


public class ConectorFirebase {
	
	public static Firestore bdd;
	
	public static void conectar () throws IOException
	{
		
		
		FileInputStream serviceAccount =
		new FileInputStream("firebase/app-olmedo-firebase-adminsdk-elztd-eb56c37ff5.json");
	
		@SuppressWarnings("deprecation")
		FirebaseOptions options = new FirebaseOptions.Builder()
		  .setCredentials(GoogleCredentials.fromStream(serviceAccount))
		  .setDatabaseUrl("https://app-olmedo-default-rtdb.firebaseio.com")
		  .build();
		
		
		
		
		FirebaseApp.initializeApp(options);
		bdd = FirestoreClient.getFirestore();
		
	}
	
	
}
