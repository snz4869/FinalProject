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

public class InstrukturAddActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText add_nama_ins, add_email, add_hp_ins;
    private Button btn_add_ins, btn_cancel_ins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruktur_add);

        add_nama_ins = findViewById(R.id.add_nama_ins);
        add_email = findViewById(R.id.add_email);
        add_hp_ins = findViewById(R.id.add_hp_ins);
        btn_add_ins = findViewById(R.id.btn_add_ins);
        btn_cancel_ins = findViewById(R.id.btn_cancel_ins);

        btn_cancel_ins.setOnClickListener(this);
        btn_add_ins.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_cancel_ins) {
            Intent myIntent = new Intent(InstrukturAddActivity.this, MainActivity.class);
            myIntent.putExtra("keyName", "instruktur");
            startActivity(myIntent);
        } else if (v == btn_add_ins) {
            if (add_nama_ins.getText().toString().equals("")) {
                validation();
            } else {
                confirmAddDataInstruktur();
            }
            //simpanDataInstruktur();
        }
    }

    private void validation() {
        //Confirmation altert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Message");
        builder.setMessage("Nama Instruktur Tidak Boleh Kosong");
        builder.setIcon(getResources().getDrawable(android.R.drawable.stat_sys_warning));
        builder.setCancelable(false);
        builder.setNegativeButton("Ok",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void confirmAddDataInstruktur() {
        //get value text field
        final String kom_nama = add_nama_ins.getText().toString().trim();
        final String kom_email = add_email.getText().toString().trim();
        final String kom_hp_ins = add_hp_ins.getText().toString().trim();

        //Confirmation altert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Insert Data");
        builder.setMessage("Are you sure want to insert this data? \n" +
                "\n Nama : " + kom_nama +
                "\n Email: " + kom_email +
                "\n No Hp: " + kom_hp_ins);
        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_input_add));
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel",null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                simpanDataInstruktur();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void simpanDataInstruktur() {
        final String nama = add_nama_ins.getText().toString().trim();
        final String email = add_email.getText().toString().trim();
        final String hp_ins = add_hp_ins.getText().toString().trim();

        class SimpanDataInstruktur extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(InstrukturAddActivity.this,
                        "Menyimpan Data", "Harap tunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_INS_NAMA, nama);
                params.put(Konfigurasi.KEY_INS_EMAIL, email);
                params.put(Konfigurasi.KEY_INS_HP, hp_ins);
                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(Konfigurasi.URL_ADD, params);
                //System.out.println("Result" + params);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(InstrukturAddActivity.this, "pesan: " + message,
                        Toast.LENGTH_SHORT).show();
                clearText();
                Intent myIntent = new Intent(InstrukturAddActivity.this, MainActivity.class);
                myIntent.putExtra("keyName", "instruktur");
                startActivity(myIntent);
            }
        }
        SimpanDataInstruktur simpanDataInstruktur = new SimpanDataInstruktur();
        simpanDataInstruktur.execute();
    }

    private void clearText() {
        add_nama_ins.setText("");
        add_email.setText("");
        add_hp_ins.setText("");
        add_nama_ins.requestFocus();
    }
}