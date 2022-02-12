package id.adiyusuf.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class CariInstrukturActivity extends AppCompatActivity {
    Button btn_cari_detail_ins;
    EditText cari_id_ins;
    TextView txt_cari_id_ins, txt_cari_nama_ins, txt_cari_email_ins, txt_cari_hp_ins;
    String cari_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_instruktur);

        btn_cari_detail_ins = findViewById(R.id.btn_cari_detail_ins);
        cari_id_ins = findViewById(R.id.cari_id_ins);
        txt_cari_id_ins = findViewById(R.id.txt_cari_id_ins);
        txt_cari_nama_ins = findViewById(R.id.txt_cari_nama_ins);
        txt_cari_email_ins = findViewById(R.id.txt_cari_email_ins);
        txt_cari_hp_ins = findViewById(R.id.txt_cari_hp_ins);

        btn_cari_detail_ins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cariDataInstruktur();
            }
        });

    }

    private void cariDataInstruktur() {
        cari_value = cari_id_ins.getText().toString();
        if (cari_value.equals("")) {
            alertMsg();
        } else {
            getData();
        }
    }

    private void alertMsg() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Message");
        builder.setMessage("Data Tidak Ditemukan");
        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_dialog_alert));
        builder.setCancelable(false);
        builder.setNegativeButton("Ok", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void getData() {
        class GetData extends AsyncTask<Void, Void, String> { // boleh membuat class dalam method (Inner Class)
            ProgressDialog loading;

            @Override
            protected void onPreExecute() { // sebelum proses
                super.onPreExecute();
                loading = ProgressDialog.show(CariInstrukturActivity.this,
                        "Mengambil Data", "Harap Menunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) { // saat proses
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_GET_DETAIL, cari_value);
                return result;
            }

            @Override
            protected void onPostExecute(String message) { // setelah proses
                super.onPostExecute(message);
                loading.dismiss();
                setDetailData(message);
            }
        }
        GetData getDATA = new GetData();
        getDATA.execute();
    }

    private void setDetailData(String message) {
        try {
            JSONObject jsonObject = new JSONObject(message);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            JSONObject object = result.getJSONObject(0);

            String nama_ins = object.getString(Konfigurasi.TAG_JSON_NAMA);
            String email = object.getString(Konfigurasi.TAG_JSON_EMAIL);
            String hp_ins = object.getString(Konfigurasi.TAG_JSON_HP);


            txt_cari_id_ins.setText("ID: " + cari_value);
            txt_cari_nama_ins.setText("Nama: " + nama_ins);
            txt_cari_email_ins.setText("Email: " + email);
            txt_cari_hp_ins.setText("No Telp: " + hp_ins);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}