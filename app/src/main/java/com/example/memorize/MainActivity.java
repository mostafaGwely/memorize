package com.example.memorize;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    String Url = "https://mostafa-memorize.herokuapp.com/word";
    TextView mytextView;
    EditText editTextWord;
    EditText editTextdesc;
    EditText editTextCat;
    EditText editTextP;
    OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextWord = findViewById(R.id.editTextWord);
        editTextdesc = findViewById(R.id.editTextDesc);
        editTextCat = findViewById(R.id.editTextCat);
        editTextP = findViewById(R.id.editTextP);
        mytextView = findViewById(R.id.textViewid);
        client = new OkHttpClient();

    }

    public void getWord(View view) {

        Request request = new Request.Builder()
                .url(Url)
                .addHeader("content-type", "application/json")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mytextView.setText(e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mytextView.setText(myResponse);
                    }
                });
            }
        });
    }

    public void postWord(View view) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("word", editTextWord.getText().toString().trim());
        json.put("description", editTextdesc.getText().toString().trim());
        json.put("cat", editTextCat.getText().toString().trim());
        json.put("p", editTextP.getText().toString().trim());

//        RequestBody formBody = new FormBody.Builder()
//                .add("word", editTextWord.getText().toString().trim())
//                .add("description", editTextdesc.getText().toString().trim())
//                .add("cat", editTextCat.getText().toString().trim())
//                .add("p", editTextP.getText().toString().trim())
//                .build();

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"), json.toString());

        Request request = new Request.Builder()
                .url(Url)
                .addHeader("content-type", "application/json")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mytextView.setText(e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mytextView.setText(myResponse);
                    }
                });
            }
        });
    }
}