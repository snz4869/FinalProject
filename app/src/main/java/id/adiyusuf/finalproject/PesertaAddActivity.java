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

public class PesertaAddActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText add_nama_pst, add_email_pst, add_hp_pst, add_instansi_pst;
    private Button btn_add_pst, btn_cancel_pst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peserta_add);

        add_nama_pst = findViewById(R.id.add_nama_pst);
        add_email_pst = findViewById(R.id.add_email_pst);
        add_hp_pst = findViewById(R.id.add_hp_pst);
        add_instansi_pst = findViewById(R.id.add_instansi_pst);
        btn_add_pst = findViewById(R.id.btn_add_pst);
        btn_cancel_pst = findViewById(R.id.btn_cancel_pst);

        btn_cancel_pst.setOnClickListener(this);
        btn_add_pst.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_cancel_pst) {
            Intent myIntent = new Intent(PesertaAddActivity.this, MainActivity.class);
            myIntent.putExtra("keyName", "peserta");
            startActivity(myIntent);
        } else if (v == btn_add_pst) {
            confirmAddDataPeserta();
        }
    }

    private void confirmAddDataPeserta() {
        //get value text field
        final String kom_nama = add_nama_pst.getText().toString().trim();
        final String kom_email = add_email_pst.getText().toString().trim();
        final String kom_hp_pst = add_hp_pst.getText().toString().trim();
        final String kom_instansi_pst = add_instansi_pst.getText().toString().trim();

        //Confirmation altert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Insert Data");
        builder.setMessage("Are you sure want to insert this data? \n" +
                "\n Nama      : " + kom_nama +
                "\n Email     : " + kom_email +
                "\n No Hp     :  " + kom_hp_pst +
                "\n Instansi  : " + kom_instansi_pst);
        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_input_add));
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel",null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                simpanDataPeserta();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void simpanDataPeserta() {
        final String nama_pst = add_nama_pst.getText().toString().trim();
        final String email_pst = add_email_pst.getText().toString().trim();
        final String hp_pst = add_hp_pst.getText().toString().trim();
        final String instansi = add_instansi_pst.getText().toString().trim();

        class SimpanDataPeserta extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(PesertaAddActivity.this,
                        "Menyimpan Data", "Harap tunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put(KonfigurasiPeserta.KEY_PST_NAMA, nama_pst);
                params.put(KonfigurasiPeserta.KEY_PST_EMAIL, email_pst);
                params.put(KonfigurasiPeserta.KEY_PST_HP, hp_pst);
                params.put(KonfigurasiPeserta.KEY_PST_INSTANSI, instansi);
                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(KonfigurasiPeserta.URL_ADD, params);
                //System.out.println("Result" + params);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(PesertaAddActivity.this, "pesan: " + message,
                        Toast.LENGTH_SHORT).show();
                clearText();
                Intent myIntent = new Intent(PesertaAddActivity.this, MainActivity.class);
                myIntent.putExtra("keyName", "peserta");
                startActivity(myIntent);
            }
        }
        SimpanDataPeserta simpanDataPeserta = new SimpanDataPeserta();
        simpanDataPeserta.execute();
    }
    private void clearText() {
        add_nama_pst.setText("");
        add_email_pst.setText("");
        add_hp_pst.setText("");
        add_instansi_pst.setText("");
        add_nama_pst.requestFocus();
    }
}