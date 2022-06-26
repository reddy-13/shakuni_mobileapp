package fcm;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.nexzenstudent.MainActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Config.ConstValue;
import util.CommonClass;
import util.JSONParser;
import util.JSONReader;

//import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by Rajesh on 2017-09-09.
 */

public class MyFirebaseRegister  {

    AppCompatActivity _context;
    CommonClass common;

    public MyFirebaseRegister(MainActivity context) {
        this._context = context;
        common = new CommonClass(context);

    }

    public void RegisterUser() {
        // [START subscribe_topics]
        Task<String> token = FirebaseMessaging.getInstance().getToken()

                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @SuppressLint("LongLogTag")
                    @Override

                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
//                            Log.w(T "Fetching FCM registration token failed", task.getException());
                            Log.w("Registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
//                        Log.d(TAG, msg);
                        Log.e("token", token);
                        String school_id = new JSONReader(_context).getJSONkeyString("student_data", "school_id");
                        FirebaseMessaging.getInstance().subscribeToTopic(ConstValue.TOPIC_GLOBAL + "_" + school_id);
                        // [END subscribe_topics]

                        Log.e("token", token);

                        new loadDataTask(token).execute();
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });


    }



    class loadDataTask extends AsyncTask<Void, Void, String> {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

        public loadDataTask(String token) {
            nameValuePairs.add(new BasicNameValuePair("student_id", common.getSession(ConstValue.COMMON_KEY)));
            nameValuePairs.add(new BasicNameValuePair("token", token));
            nameValuePairs.add(new BasicNameValuePair("device", "android"));
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected String doInBackground(Void... params) {
            // TODO Auto-generated method stub
            String responseString = null;

            try {

                JSONParser jParser = new JSONParser();
                String json = jParser.makeHttpRequest(ConstValue.FCM_REGISTER_URL, "POST", nameValuePairs);
                Log.e("responce_token", json);

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return responseString;

        }


        @Override
        protected void onPostExecute(String result) {
            if (result != null) {

            } else {

            }

        }

        @Override
        protected void onCancelled() {
            // TODO Auto-generated method stub
        }
    }

}
