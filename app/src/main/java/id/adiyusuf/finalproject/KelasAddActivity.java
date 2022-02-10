package id.adiyusuf.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import android.app.DatePickerDialog;

import java.util.HashMap;

public class KelasAddActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText add_tgl_mulai, add_tgl_akhir, add_id_ins_kls, add_id_mat_kls;
    private Button btn_add_kls, btn_cancel_kls,btn_datepick_tgl_mulai,btn_datepick_tgl_akhir;
    private DatePickerDialog datePickerDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelas_add);

        add_tgl_mulai = findViewById(R.id.add_tgl_mulai);
        add_tgl_akhir = findViewById(R.id.add_tgl_akhir);
        add_id_ins_kls = findViewById(R.id.add_id_ins_kls);
        add_id_mat_kls = findViewById(R.id.add_id_mat_kls);
        btn_add_kls = findViewById(R.id.btn_add_kls);
        btn_cancel_kls = findViewById(R.id.btn_cancel_kls);
        btn_datepick_tgl_mulai = findViewById(R.id.btn_datepicker_tgl_mulai);
        btn_datepick_tgl_akhir = findViewById(R.id.btn_datepicker_tgl_akhir);


        btn_datepick_tgl_mulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialogeTanggalMulai();
            }
        });

        btn_datepick_tgl_akhir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialogeTanggalAkhir();
            }
        });


        btn_cancel_kls.setOnClickListener(this);
        btn_add_kls.setOnClickListener(this);
    }



    private void showDateDialogeTanggalAkhir() {
        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                //txt_date.setText("Tanggal dipilih : "+dateFormatter.format(newDate.getTime()).toString());
                add_tgl_akhir.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
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
                add_tgl_mulai.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        datePickerDialog.show();
    }


    @Override
    public void onClick(View v) {
        if (v == btn_cancel_kls) {
            Intent myIntent = new Intent(KelasAddActivity.this, MainActivity.class);
            myIntent.putExtra("keyName", "kelas");
            startActivity(myIntent);
        } else if (v == btn_add_kls) {
            confirmAddDataKelas();
        }
    }

    private void confirmAddDataKelas() {
        //get value text field
        final String kom_tgl_mulai = add_tgl_mulai.getText().toString().trim();
        final String kom_tgl_akhir = add_tgl_akhir.getText().toString().trim();
        final String kom_id_ins_kls = add_id_ins_kls.getText().toString().trim();
        final String kom_id_mat_kls = add_id_mat_kls.getText().toString().trim();

        //Confirmation altert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Insert Data");
        builder.setMessage("Are you sure want to insert this data? \n" +
                "\n Tanggal Mulai : " + kom_tgl_mulai +
                "\n Tanggal Akhir : " + kom_tgl_akhir +
                "\n ID Instruktur : " + kom_id_ins_kls +
                "\n ID Materi     : " + kom_id_mat_kls);
        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_input_add));
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel",null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                simpanDataKelas();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void simpanDataKelas() {
        final String tgl_mulai = add_tgl_mulai.getText().toString().trim();
        final String tgl_akhir = add_tgl_akhir.getText().toString().trim();
        final String id_ins_kls = add_id_ins_kls.getText().toString().trim();
        final String id_mat_kls = add_id_mat_kls.getText().toString().trim();

        class SimpanDataKelas extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(KelasAddActivity.this,
                        "Menyimpan Data", "Harap tunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put(KonfigurasiKelas.KEY_KLS_TGL_MULAI, tgl_mulai);
                params.put(KonfigurasiKelas.KEY_KLS_TGL_AKHIR, tgl_akhir);
                params.put(KonfigurasiKelas.KEY_KLS_ID_INS, id_ins_kls);
                params.put(KonfigurasiKelas.KEY_KLS_ID_MAT, id_mat_kls);
                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(KonfigurasiKelas.URL_ADD, params);
                //System.out.println("Result" + params);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(KelasAddActivity.this, "pesan: " + message,
                        Toast.LENGTH_SHORT).show();
                clearText();
                Intent myIntent = new Intent(KelasAddActivity.this, MainActivity.class);
                myIntent.putExtra("keyName", "kelas");
                startActivity(myIntent);
            }
        }
        SimpanDataKelas simpanDataKelas = new SimpanDataKelas();
        simpanDataKelas.execute();
    }

    private void clearText() {
        add_tgl_mulai.setText("");
        add_tgl_akhir.setText("");
        add_id_ins_kls.setText("");
        add_id_mat_kls.setText("");
        add_tgl_mulai.requestFocus();
    }
}