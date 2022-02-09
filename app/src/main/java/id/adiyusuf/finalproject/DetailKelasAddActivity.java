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

public class DetailKelasAddActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText add_id_kls_dtl_kls, add_id_pst_dtl_kls;
    private Button btn_add_dtl_kls, btn_cancel_dtl_kls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kelas_add);

        add_id_kls_dtl_kls = findViewById(R.id.add_id_kls_dtl_kls);
        add_id_pst_dtl_kls = findViewById(R.id.add_id_pst_dtl_kls);
        btn_add_dtl_kls = findViewById(R.id.btn_add_dtl_kls);
        btn_cancel_dtl_kls = findViewById(R.id.btn_cancel_dtl_kls);

        btn_cancel_dtl_kls.setOnClickListener(this);
        btn_add_dtl_kls.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v == btn_cancel_dtl_kls) {
            Intent myIntent = new Intent(DetailKelasAddActivity.this, MainActivity.class);
            myIntent.putExtra("keyName", "detail kelas");
            startActivity(myIntent);
        } else if (v == btn_add_dtl_kls) {
            confirmAddDataDetailKelas();
        }
    }

    private void confirmAddDataDetailKelas() {
        //get value text field
        final String kom_id_kls = add_id_kls_dtl_kls.getText().toString().trim();
        final String kom_id_pst = add_id_pst_dtl_kls.getText().toString().trim();

        //Confirmation altert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Insert Data");
        builder.setMessage("Are you sure want to insert this data? \n" +
                "\n ID Kelas   : " + kom_id_kls +
                "\n ID Peserta : " + kom_id_pst);
        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_input_add));
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel",null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                simpanDataDetailKelas();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void simpanDataDetailKelas() {
        final String id_kls = add_id_kls_dtl_kls.getText().toString().trim();
        final String id_pst = add_id_pst_dtl_kls.getText().toString().trim();

        class SimpanDataDetailKelas extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailKelasAddActivity.this,
                        "Menyimpan Data", "Harap tunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put(KonfigurasiDetailKelas.KEY_DTL_KLS_ID_KLS, id_kls);
                params.put(KonfigurasiDetailKelas.KEY_DTL_KLS_ID_PST, id_pst);
                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(KonfigurasiDetailKelas.URL_ADD, params);
                //System.out.println("Result" + params);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(DetailKelasAddActivity.this, "pesan: " + message,
                        Toast.LENGTH_SHORT).show();
                clearText();
                Intent myIntent = new Intent(DetailKelasAddActivity.this, MainActivity.class);
                myIntent.putExtra("keyName", "detail kelas");
                startActivity(myIntent);
            }
        }
        SimpanDataDetailKelas simpanDataDetailKelas = new SimpanDataDetailKelas();
        simpanDataDetailKelas.execute();
    }

    private void clearText() {
        add_id_kls_dtl_kls.setText("");
        add_id_pst_dtl_kls.setText("");
        add_id_kls_dtl_kls.requestFocus();
    }
}