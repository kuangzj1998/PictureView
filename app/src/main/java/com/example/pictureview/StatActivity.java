package com.example.pictureview;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class StatActivity extends AppCompatActivity {
    private Setting setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //ActionBar actionbar=this.getSupportActionBar();
        setting = (Setting)getIntent().getSerializableExtra("Setting");
        if(setting!=null) {
            int hold = setting.getCount();
            hold++;
            setting.setCount(hold);
            TextView textView = findViewById(R.id.textView);
            textView.setText(setting.toString());
        }
        else{
            Toast.makeText(this,"Setting is Null",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent();
                intent.putExtra("Setting",setting);
                setResult(1, intent);
                finish();
                break;
        }
        return true;
    }
}
