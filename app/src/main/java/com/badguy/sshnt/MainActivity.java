package com.badguy.sshnt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Properties;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.ChannelExec;
import java.io.ByteArrayOutputStream;
import java.lang.String;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sPreferences = null;
        sPreferences = getSharedPreferences("firstRun", MODE_PRIVATE);
        if (sPreferences.getBoolean("firstRun", true)) {
            sPreferences.edit().putBoolean("firstRun", false).apply();
            AlertDialog.Builder changelog = new AlertDialog.Builder(MainActivity.this);
            changelog.setTitle("Changelog");
            changelog.setMessage("Telegram Official Channel: @sshnt\n1.5.5 - Design Updates\n1.5.6 - Added New Function\n1.5.7 - Local Shell Shows Output\n1.6.0 - Fix & Design Updates\n1.6.1 - App Updates\n1.6.5 - Now the App Saves the latest user inputs\n1.6.6 - Fixed Toast Activity\n1.6.7 - Force to use app only in Portrait Mode\n1.7.0 - Create SSH for FREE ! & Security Fix\n1.7.5 - Fixes & new mode to Create Free Logins\n1.7.6 - Set Output in create Free SSH Logins in EditText\n1.7.7 - Added Button COPY ALL\n1.7.8 - Fix LOCAL SHELL\n1.8.0 - Performance Updates & Replaced Password for Public Key");
            changelog.setNeutralButton("Ok", null);
            changelog.show();
        }
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();
        final EditText sship;
        final EditText sshport;
        final EditText sshuser;
        final EditText sshpassword;
        final EditText comando;
        Button btn;
        Button second;
        Button third;
        final TextView by;

        btn = (Button) findViewById(R.id.btn);
        second = (Button) findViewById(R.id.second);
        third = (Button) findViewById(R.id.third);
        sshpassword = (EditText) findViewById(R.id.sshpassword);
        sshuser = (EditText) findViewById(R.id.sshuser);
        sshport = (EditText) findViewById(R.id.sshport);
        sship = (EditText) findViewById(R.id.sship);
        comando = (EditText) findViewById(R.id.comando);
        by = (TextView) findViewById(R.id.by);
        String sshuserp = pref.getString("sshuserp", null);
        String sshpasswordp = pref.getString("sshpasswordp", null);
        String sshipp = pref.getString("sshipp", null);
        String sshportp = pref.getString("sshportp", null);
        sshuser.setText(sshuserp);
        sshpassword.setText(sshpasswordp);
        sship.setText(sshipp);
        sshport.setText(sshportp);
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(i);
            }
        });
        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ThirdActivity.class);
                startActivity(i);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Executing Command...", Toast.LENGTH_LONG).show();
                new AsyncTask<Integer, Void, Void>(){
                    @Override
                    protected Void doInBackground(Integer... params) {
                        try {
                            String sshuserstring = sshuser.getText().toString();
                            String sshpasswordstring = sshpassword.getText().toString();
                            String sshipstring = sship.getText().toString();
                            String sshportstring = sshport.getText().toString();
                            editor.putString("sshuserp", sshuserstring);  // Saving string
                            editor.putString("sshpasswordp", sshpasswordstring);  // Saving string
                            editor.putString("sshipp", sshipstring);  // Saving string
                            editor.putString("sshportp", sshportstring);  // Saving string
                            editor.commit(); // commit changes
                            executeRemoteCommand(sshuserstring, sshpasswordstring,sshipstring, Integer.parseInt(sshportstring));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute(1);
                Toast.makeText(MainActivity.this, "Executed Suceffuly", Toast.LENGTH_SHORT).show();

            }

            public String executeRemoteCommand(String username, String password, String hostname, int port)
                    throws Exception {
                JSch jsch = new JSch();
                Session session = jsch.getSession(username, hostname, port);
                session.setPassword(password);
                // Avoid asking for key confirmation
                Properties prop = new Properties();
                prop.put("StrictHostKeyChecking", "no");
                prop.put("Compression", "yes");
                prop.put("ConnectionAttempts","2");
                session.setConfig(prop);

                session.connect();

                // SSH Channel
                ChannelExec channelssh = (ChannelExec)
                        session.openChannel("exec");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                channelssh.setOutputStream(baos);

                // Execute command
                String comandostring = comando.getText().toString();
                channelssh.setCommand(comandostring);
                channelssh.connect();
                channelssh.disconnect();
                return baos.toString();
            }
        });
        by.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder easteregg = new AlertDialog.Builder(MainActivity.this);
                easteregg.setTitle("Easter Egg :P");
                easteregg.setMessage("Its a Easter Egg (or not)\nCreate a SSH Login with name EasterEgg2 and get a 30 day free ssh login :P");
                easteregg.setNeutralButton("Thanks", null);
                easteregg.show();
            }
        });

    }

}
