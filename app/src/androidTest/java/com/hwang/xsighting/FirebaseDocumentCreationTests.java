package com.hwang.xsighting;

import android.os.Looper;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hwang.xsighting.MainActivity;

import org.junit.Test;

import androidx.annotation.NonNull;

import static androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class FirebaseDocumentCreationTests {

    @Test
    public void testCreateUser() {
//        Looper.prepare();
        final MainActivity mainActivity = new MainActivity();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final String TAG = "testCreateUser";
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

//        DocumentReference docRef = db.collection("users").document("testUserId");
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
//                        assertTrue(false);
//                    } else {
//                        Log.d(TAG, "No such document");
//                        assertTrue(true);
//                    }
//                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
//                }
//            }
//        });

        mainActivity.createNewUserIfUserDoesNotExist("testUserId");

//        docRef = db.collection("users").document("testUserId");
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
//                        assertTrue(true);
//                    } else {
//                        Log.d(TAG, "No such document");
//                        assertTrue(false);
//                    }
//                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
//                }
//            }
//        });
    }
}
