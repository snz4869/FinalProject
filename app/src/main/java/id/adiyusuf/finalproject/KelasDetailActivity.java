package id.adiyusuf.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import id.adiyusuf.finalproject.databinding.ActivityMainBinding;

public class KelasDetailActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edit_id_kls,edit_tgl_mulai, edit_tgl_akhir; //, edit_id_ins_kls, edit_id_mat_kls;
    String id_kls,public_nama_ins, public_nama_mat,spinner_value_ins,
            spinner_value_mat,
            JSON_STRING_INS, JSON_STRING_MAT;
    Button btn_update_kls,btn_delete_kls,btn_datepicker_mulai_edit,btn_datepicker_akhir_edit;
    private ActivityMainBinding binding;
    private DatePickerDialog datePickerDialog;
    private Spinner spinner_ins_kls_edit, spinner_mat_kls_edit;
    Toolbar toolbar_kls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelas_detail);

        edit_id_kls = findViewById(R.id.edit_id_kls);
        edit_tgl_mulai = findViewById(R.id.edit_tgl_mulai);
        edit_tgl_akhir = findViewById(R.id.edit_tgl_akhir);
//        edit_id_ins_kls = findViewById(R.id.edit_id_ins_kls);
//        edit_id_mat_kls = findViewById(R.id.edit_id_mat_kls);
        btn_update_kls = findViewById(R.id.btn_update_kls);
        btn_delete_kls = findViewById(R.id.btn_delete_kls);
        btn_datepicker_mulai_edit = findViewById(R.id.btn_datepicker_mulai_edit);
        btn_datepicker_akhir_edit  = findViewById(R.id.btn_datepicker_akhir_edit);
        spinner_ins_kls_edit = findViewById(R.id.spinner_ins_kls_edit);
        spinner_mat_kls_edit = findViewById(R.id.spinner_mat_kls_edit);
        toolbar_kls = findViewById(R.id.toolbar_kls);

        setSupportActionBar(toolbar_kls);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent receiveIntent = getIntent();
        id_kls = receiveIntent.getStringExtra(KonfigurasiKelas.KLS_ID);
        edit_id_kls.setText(id_kls);

        getJSON();

        btn_datepicker_mulai_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialogeTanggalMulai();
            }
        });

        btn_datepicker_akhir_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialogeTanggalAkhir();
            }
        });

        btn_update_kls.setOnClickListener(this);
        btn_delete_kls.setOnClickListener(this);

        getDataInstruktur();
        getDataMateri();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void showDateDialogeTanggalAkhir() {
        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                //txt_date.setText("Tanggal dipilih : "+dateFormatter.format(newDate.getTime()).toString());
                edit_tgl_akhir.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        datePickerDialog.show();
    }

    private void showDateDialogeTanggalMulai() {
        Calendar newCalendar = Calendar.getInstance();
//        DateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
//        Calendar cal = Calendar.getInstance();
//        Date date = null;
//        try {
//            date = formatter.parse(edit_tgl_mulai.toString());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        cal.setTime(date);
//        int year = cal.get(Calendar.YEAR);
//        int month = cal.get(Calendar.MONTH);
//        int day = cal.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                //txt_date.setText("Tanggal dipilih : "+dateFormatter.format(newDate.getTime()).toString());
                edit_tgl_mulai.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        datePickerDialog.show();
    }

    private void getDataMateri() {
        //bantuan dari class AsyncTask
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(KelasDetailActivity.this,
                        "Mengambil Data", "Harap Tunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(KonfigurasiMateri.URL_GET_ALL);

                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                JSON_STRING_MAT = message;
//                Log.d("DATA JSON: ", JSON_STRING_MAT);

                spinnerMateri();
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void spinnerMateri() {
        JSONObject jsonObject = null;
        ArrayList<String> listIdMat = new ArrayList<>();
        ArrayList<String> listNamaMat = new ArrayList<>();

        try {
            jsonObject = new JSONObject(JSON_STRING_MAT);
            JSONArray result = jsonObject.getJSONArray(KonfigurasiMateri.TAG_JSON_ARRAY);
//            Log.d("DATA JSON: ", JSON_STRING_MAT);
//            Toast.makeText(DetailKelasDetailActivity.this, "DATA JSON Result: " + result, Toast.LENGTH_SHORT).show();

            for (int i = 0; i < result.length(); i++) {
                JSONObject object = result.getJSONObject(i);
                String id_mat = object.getString(KonfigurasiMateri.TAG_JSON_ID);
                String nama_mat = object.getString(KonfigurasiMateri.TAG_JSON_NAMA);
                listIdMat.add(id_mat);
                listNamaMat.add(nama_mat);
            }
//            Toast.makeText(this, "test: "+listNamaKls.toString(), Toast.LENGTH_SHORT).show();

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_spinner_item, listNamaMat); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_mat_kls_edit.setAdapter(spinnerArrayAdapter);

            spinner_mat_kls_edit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    spinner_value_mat = listIdMat.get(i);
//                    Toast.makeText(DetailKelasDetailActivity.this, "True Value: "+spinner_value_kelas, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            spinner_mat_kls_edit.setSelection(listNamaMat.indexOf(public_nama_mat));//set selected value in spinner

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void getDataInstruktur() {
        //bantuan dari class AsyncTask
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(KelasDetailActivity.this,
                        "Mengambil Data", "Harap Tunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_GET_ALL);

                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                JSON_STRING_INS = message;
                Log.d("DATA JSON: ", JSON_STRING_INS);

                spinnerInstruktur();
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void spinnerInstruktur() {
        JSONObject jsonObject = null;
        ArrayList<String> listIdIns = new ArrayList<>();
        ArrayList<String> listNamaIns = new ArrayList<>();

        try {
            jsonObject = new JSONObject(JSON_STRING_INS);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            Log.d("DATA JSON: ", JSON_STRING_INS);
//            Toast.makeText(DetailKelasDetailActivity.this, "DATA JSON Result: " + result, Toast.LENGTH_SHORT).show();

            for (int i = 0; i < result.length(); i++) {
                JSONObject object = result.getJSONObject(i);
                String id_ins = object.getString(Konfigurasi.TAG_JSON_ID);
                String nama_ins = object.getString(Konfigurasi.TAG_JSON_NAMA);
                listIdIns.add(id_ins);
                listNamaIns.add(nama_ins);
            }
//            Toast.makeText(this, "test: "+listNamaKls.toString(), Toast.LENGTH_SHORT).show();

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_spinner_item, listNamaIns); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_ins_kls_edit.setAdapter(spinnerArrayAdapter);

            spinner_ins_kls_edit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    spinner_value_ins = listIdIns.get(i);
//                    Toast.makeText(DetailKelasDetailActivity.this, "True Value: "+spinner_value_kelas, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            spinner_ins_kls_edit.setSelection(listNamaIns.indexOf(public_nama_ins));//set selected value in spinner

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void getJSON() {
        // batuan dari class AsynTask
        class GetJSON extends AsyncTask<Void,Void,String> { // boleh membuat class dalam method (Inner Class)
            ProgressDialog loading;

            @Override
            protected void onPreExecute() { // sebelum proses
                super.onPreExecute();
                loading = ProgressDialog.show(KelasDetailActivity.this,
                        "Mengambil Data","Harap Menunggu...",
                        false,false);
            }

            @Override
            protected String doInBackground(Void... voids) { // saat proses
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(KonfigurasiKelas.URL_GET_DETAIL,id_kls);
                return result;
            }

            @Override
            protected void onPostExecute(String message) { // setelah proses
                super.onPostExecute(message);
                loading.dismiss();
                displayDetailData(message);
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayDetailData(String message) {
        try {
            JSONObject jsonObject = new JSONObject(message);
            JSONArray result = jsonObject.getJSONArray(KonfigurasiKelas.TAG_JSON_ARRAY);
            JSONObject object = result.getJSONObject(0);

            String tgl_mulai = object.getString(KonfigurasiKelas.TAG_JSON_TGL_MULAI);
            String tgl_akhir = object.getString(KonfigurasiKelas.TAG_JSON_TGL_AKHIR);
            String id_ins_kls = object.getString(KonfigurasiKelas.TAG_JSON_ID_INS);
            String id_mat_kls = object.getString(KonfigurasiKelas.TAG_JSON_ID_MAT);

            edit_tgl_mulai.setText(tgl_mulai);
            edit_tgl_akhir.setText(tgl_akhir);
            public_nama_mat = id_mat_kls;
            public_nama_ins = id_ins_kls;


        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }
    @Override
    public void onClick(View myButton) {
        if(myButton == btn_update_kls){
            if (edit_tgl_mulai.getText().toString().equals("") || edit_tgl_akhir.getText().toString().equals("")) {
                validation();
            } else {
                updateDataKelas();
            }
        } else if(myButton == btn_delete_kls){
            confirmDeleteDataKelas();
        }
    }

    private void validation() {
        //Confirmation altert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Message");
        builder.setMessage("Tanggal Mulai Dan Akhir Tidak Boleh Kosong");
        builder.setIcon(getResources().getDrawable(android.R.drawable.stat_sys_warning));
        builder.setCancelable(false);
        builder.setNegativeButton("Ok",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void confirmDeleteDataKelas() {
        //Confirmation altert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Data");
        builder.setMessage("Are you sure want to delete this data?");
        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_delete));
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel",null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteDataKelas();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteDataKelas() {
        class DeleteDataKelas extends AsyncTask<Void,Void, String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = ProgressDialog.show(KelasDetailActivity.this,
                        "Menghapus Data","Harap Tunggu",
                        false,false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(KonfigurasiKelas.URL_DELETE, id_kls);

                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                loading.dismiss();
                Toast.makeText(KelasDetailActivity.this,"Pesan: " + s,
                        Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(KelasDetailActivity.this, MainActivity.class);
                myIntent.putExtra("keyName", "kelas");
                startActivity(myIntent);
            }
        }
        DeleteDataKelas deleteDataKelas = new DeleteDataKelas();
        deleteDataKelas.execute();
    }

    private void updateDataKelas() {
        // variable data pegawai yang akan diubah
        final String tgl_mulai = edit_tgl_mulai.getText().toString().trim();
        final String tgl_akhir = edit_tgl_akhir.getText().toString().trim();
        final String id_ins_kls = spinner_value_ins;
        final String id_mat_kls = spinner_value_mat;

        class UpdateDataKelas extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(KelasDetailActivity.this,
                        "Mengubah Data","Harap Tunggu",
                        false,false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> params = new HashMap<>();
                params.put(KonfigurasiKelas.KEY_KLS_ID,id_kls);
                params.put(KonfigurasiKelas.KEY_KLS_TGL_MULAI,tgl_mulai);
                params.put(KonfigurasiKelas.KEY_KLS_TGL_AKHIR,tgl_akhir);
                params.put(KonfigurasiKelas.KEY_KLS_ID_INS,id_ins_kls);
                params.put(KonfigurasiKelas.KEY_KLS_ID_MAT,id_mat_kls);
                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(KonfigurasiKelas.URL_UPDATE, params);

                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();;

                Fragment fragment = null;

                Toast.makeText(KelasDetailActivity.this,
                        "Pesan: " + s, Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(KelasDetailActivity.this, MainActivity.class);
                myIntent.putExtra("keyName", "kelas");
                startActivity(myIntent);

            }
        }

        UpdateDataKelas updateDataKelas = new UpdateDataKelas();
        updateDataKelas.execute();
    }
}