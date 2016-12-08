package me.stupideme.shucampus.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import me.stupideme.shucampus.R;

public class EventAddActivity extends AppCompatActivity {

    TextInputEditText mTitle;
    TextInputEditText mPhone;
    TextInputEditText mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_add);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("AddEvent");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mTitle = (TextInputEditText) findViewById(R.id.event_title);
        mPhone = (TextInputEditText) findViewById(R.id.event_phone);
        mContent = (TextInputEditText) findViewById(R.id.event_content);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.event_add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.actions_save) {
            Intent intent = new Intent(EventAddActivity.this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("title", mTitle.getText().toString());
            bundle.putString("phone", mPhone.getText().toString());
            bundle.putString("content", mContent.getText().toString());
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            EventAddActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
