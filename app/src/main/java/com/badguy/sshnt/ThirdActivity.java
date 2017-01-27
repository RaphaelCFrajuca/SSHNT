package com.badguy.sshnt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class ThirdActivity extends AppCompatActivity {

    Button go;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);


        go = (Button) findViewById(R.id.go);
        //final TextView out;
        final EditText login = (EditText) findViewById(R.id.login);
        final EditText out2;
        final Button copyall;


        //out = (TextView) findViewById(R.id.out);
        out2 = (EditText) findViewById(R.id.out2);
        copyall = (Button) findViewById(R.id.copyall);
        go.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {

                new AsyncTask<Integer, Void, Void>(){
                    @Override
                    protected Void doInBackground(Integer... params) {

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://ssh.sshtls.com.br:8799/post.php");


                //Post Data
                final List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
                //runOnUiThread(new Runnable() {
                //    @Override
                //    public void run() {
                        nameValuePair.add(new BasicNameValuePair("user", login.getText().toString()));
                //    }
                //});


                //Encoding POST data
                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                //making POST request.
                try {
                    final HttpResponse response = httpClient.execute(httpPost);
                    BufferedReader rd = new BufferedReader(
                            new InputStreamReader(response.getEntity().getContent()));

                    final StringBuffer result = new StringBuffer();
                    String line = "";
                    while ((line = rd.readLine()) != null) {
                        result.append(line);
                    }
                    ;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                    AlertDialog.Builder created = new AlertDialog.Builder(ThirdActivity.this);
                    created.setTitle("Accont Created!");
                    created.setMessage(result.toString().replace(":<:space:>:", "\n"));
                    created.setNeutralButton("Ok", null);
                    created.show();
                        }
                    });
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            out2.setText(result.toString().replace(":<:space:>:", "\n"));
                            copyall.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                    ClipData clip = ClipData.newPlainText("login", result.toString().replace(":<:space:>:", "\n"));
                                    clipboard.setPrimaryClip(clip);
                                    Toast.makeText(ThirdActivity.this, "Text Copied to Clipboard !", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });


                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                        return null;
                    }
                }.execute(1);
            }

        });

    }
    }

