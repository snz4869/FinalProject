package id.adiyusuf.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class MateriAddActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText add_nama_mat;
    private Button btn_add_mat, btn_cancel_mat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materi_add);

        add_nama_mat = findViewById(R.id.add_nama_mat);
        btn_add_mat = findViewById(R.id.btn_add_mat);
        btn_cancel_mat = findViewById(R.id.btn_cancel_mat);

        btn_cancel_mat.setOnClickListener(this);
        btn_add_mat.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_cancel_mat) {
            Intent myIntent = new Intent(MateriAddActivity.this, MainActivity.class);
            myIntent.putExtra("keyName", "materi");
            startActivity(myIntent);
        } else if (v == btn_add_mat) {
            confirmAddDataMateri();
        }
    }

    private void confirmAddDataMateri() {
        //get value text field
        final String kom_nama = add_nama_mat.getText().toString().trim();

        //Confirmation altert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Insert Data");
        builder.setMessage("Are you sure want to insert this data? \n" +
                "\n Nama Materi: " + kom_nama);
        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_input_add));
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel",null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                simpanDataMateri();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void simpanDataMateri() {
        final String nama = add_nama_mat.getText().toString().trim();

        class SimpanDataMateri extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MateriAddActivity.this,
                        "Menyimpan Data", "Harap tunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put(KonfigurasiMateri.KEY_MAT_NAMA, nama);
                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(KonfigurasiMateri.URL_ADD, params);
                //System.out.println("Result" + params);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(MateriAddActivity.this, "pesan: " + message,
                        Toast.LENGTH_SHORT).show();
                clearText();
                Intent myIntent = new Intent(MateriAddActivity.this, MainActivity.class);
                myIntent.putExtra("keyName", "materi");
                startActivity(myIntent);
            }
        }
        SimpanDataMateri simpanDataMateri = new SimpanDataMateri();
        simpanDataMateri.execute();
    }
    private void clearText() {
        add_nama_mat.setText("");
        add_nama_mat.requestFocus();
    }
}