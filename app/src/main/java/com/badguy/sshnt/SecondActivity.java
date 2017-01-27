package com.badguy.sshnt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.learn2crack.androidshell.ShellExecuter;
public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        final TextView out;
        final EditText input;
        Button button;

        input = (EditText) findViewById(R.id.input);
        button = (Button) findViewById(R.id.button);
        out = (TextView) findViewById(R.id.out);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShellExecuter exe = new ShellExecuter();
                String outp = exe.Executer(input.getText().toString());
                out.setMovementMethod(new ScrollingMovementMethod());
                out.setText(outp);
                Log.d("Output", outp);
            }
        });
    }
}
