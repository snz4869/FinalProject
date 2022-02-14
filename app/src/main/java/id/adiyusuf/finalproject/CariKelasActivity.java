package id.adiyusuf.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class CariKelasActivity extends AppCompatActivity {

    Button btn_cari_kls, cari_datepicker_tgl_mulai, cari_datepicker_tgl_akhir;
    String JSON_STRING_KLS,start,end;
    ListView list_cari_kls;
    private EditText cari_tgl_mulai, cari_tgl_akhir;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_kelas);

        btn_cari_kls = findViewById(R.id.btn_cari_kls);
        list_cari_kls = findViewById(R.id.list_cari_kls);
        cari_tgl_mulai = findViewById(R.id.cari_tgl_mulai);
        cari_tgl_akhir = findViewById(R.id.cari_tgl_akhir);
        cari_datepicker_tgl_mulai = findViewById(R.id.cari_datepicker_tgl_mulai);
        cari_datepicker_tgl_akhir = findViewById(R.id.cari_datepicker_tgl_akhir);

        cari_datepicker_tgl_mulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialogeTanggalMulai();
            }
        });

        cari_datepicker_tgl_akhir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialogeTanggalAkhir();
            }
        });

        btn_cari_kls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
    }

    private void showDateDialogeTanggalAkhir() {
        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                //txt_date.setText("Tanggal dipilih : "+dateFormatter.format(newDate.getTime()).toString());
                cari_tgl_akhir.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
                end = year + "-" + (monthOfYear+1) + "-" + dayOfMonth;
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        datePickerDialog.show();
    }

    private void showDateDialogeTanggalMulai() {
        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                //txt_date.setText("Tanggal dipilih : "+dateFormatter.format(newDate.getTime()).toString());
                cari_tgl_mulai.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
                start = year + "-" + (monthOfYear+1) + "-" + dayOfMonth;
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        datePickerDialog.show();
    }

    private void getData() {
//        Toast.makeText(this, "Awal: " + cari_tgl_mulai.getText().toString()
//                + " Akhir: " + cari_tgl_akhir.getText().toString(), Toast.LENGTH_SHORT).show();

        class GetData extends AsyncTask<Void, Void, String> { // boleh membuat class dalam method (Inner Class)
            ProgressDialog loading;

            @Override
            protected void onPreExecute() { // sebelum proses
                super.onPreExecute();
                loading = ProgressDialog.show(CariKelasActivity.this,
                        "Mengambil Data", "Harap Menunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) { // saat proses
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetRespDate(KonfigurasiKelas.URL_GET_DETAIL_TGL,
                        start, end);
                Log.d("res:", result);
                Log.d("start:", start);
                Log.d("end:", end);

                return result;
            }

            @Override
            protected void onPostExecute(String message) { // setelah proses
                super.onPostExecute(message);
                loading.dismiss();
                JSON_STRING_KLS = message;
                Log.d("DATA JSON: ", JSON_STRING_KLS);
//                Toast.makeText(CariKelasActivity.this, "Hasil: "+message, Toast.LENGTH_SHORT).show();

                displayAllData();
            }
        }
        GetData getDATA = new GetData();
        getDATA.execute();
    }

    private void displayAllData() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        try {
            jsonObject = new JSONObject(JSON_STRING_KLS);
            JSONArray result = jsonObject.getJSONArray(KonfigurasiKelas.TAG_JSON_ARRAY);
            Log.d("DATA JSON: ", JSON_STRING_KLS);

            for (int i = 0; i < result.length(); i++) {
                JSONObject object = result.getJSONObject(i);
                String materi = object.getString(KonfigurasiKelas.TAG_JSON_ID_MAT);
                String mulai = object.getString(KonfigurasiKelas.TAG_JSON_TGL_MULAI);
                String akhir = object.getString(KonfigurasiKelas.TAG_JSON_TGL_AKHIR);
                HashMap<String, String> kelas = new HashMap<>();
                kelas.put(KonfigurasiKelas.TAG_JSON_ID, materi);
                kelas.put(KonfigurasiKelas.TAG_JSON_ID_INS, mulai);
                kelas.put(KonfigurasiKelas.TAG_JSON_ID_MAT, akhir);

                //ubah format JSON menjadi Array List
                list.add(kelas);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // adapter untuk meletakan array list kedalam list view

        ListAdapter adapter = new SimpleAdapter(
                CariKelasActivity.this, list,
                R.layout.list_item_cari_kelas,
                new String[]{KonfigurasiKelas.TAG_JSON_ID, KonfigurasiKelas.TAG_JSON_ID_INS,
                        KonfigurasiKelas.TAG_JSON_ID_MAT},
                new int[]{R.id.txt_cari_mat_kls, R.id.txt_cari_tgl_masuk, R.id.txt_cari_tgl_akhir}
        );
        list_cari_kls.setAdapter(adapter);
    }
}