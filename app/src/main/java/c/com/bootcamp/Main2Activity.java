package c.com.bootcamp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static c.com.bootcamp.MainActivity.edittext;
import static c.com.bootcamp.MainActivity.positionedt;

public class Main2Activity extends AppCompatActivity {
    EditText edt;
    Button button;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getSupportActionBar().setTitle("Edit Item");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edt = (EditText)findViewById(R.id.editTextedit);
        button = (Button)findViewById(R.id.button_edit);

        edt.setText(getIntent().getStringExtra(edittext));
        position = getIntent().getIntExtra(positionedt,0);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(edittext,edt.getText().toString());
                intent.putExtra(positionedt,position);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
