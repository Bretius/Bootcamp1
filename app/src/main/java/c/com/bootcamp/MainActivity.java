package c.com.bootcamp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayAdapter arrayAdapter;
    ArrayList<String> arrayList;
    Button bouton_save;
    EditText edt;
    public static int code = 5;
    public static String edittext = "edittext";
    public  static String positionedt = "positionedt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listview);
        bouton_save = (Button)findViewById(R.id.bouton_add);
        edt = (EditText)findViewById(R.id.additem);

        readitem();

        arrayAdapter =  new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);

        bouton_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Enter a new item", Toast.LENGTH_SHORT).show();
                }else{
                    arrayAdapter.add(edt.getText().toString());
                    Toast.makeText(MainActivity.this, "Added successfuly to list", Toast.LENGTH_SHORT).show();
                    edt.setText("");
                    writeitem();
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra(edittext,arrayList.get(i));
                intent.putExtra(positionedt,i);
                startActivityForResult(intent,code);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Voulez-vous le supprimer?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        writeitem();
                        arrayList.remove(position);
                        arrayAdapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "Item delete successfull", Toast.LENGTH_SHORT).show();
                    }
                })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                alert.create();
                alert.show();


                return true;
            }
        });
    }
    private File getitem(){
        return new File(getFilesDir(),"listitem.txt");
    }
    private void readitem(){
        try {
            arrayList = new ArrayList<>(FileUtils.readLines(getitem(), Charset.defaultCharset()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeitem() {
        try {
            FileUtils.writeLines(getitem(),arrayList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == code){
            String edit = data.getExtras().getString(edittext);
            int pst = data.getExtras().getInt(positionedt);
            arrayList.set(pst,edit);
            arrayAdapter.notifyDataSetChanged();
            writeitem();
            Toast.makeText(this, "Updated item succesfuly to list", Toast.LENGTH_SHORT).show();
        }
    }
}
