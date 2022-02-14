package id.adiyusuf.finalproject;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    String JSON_STRING,c_mat,c_ins,c_pst,c_kls,c_d_kls;
    TextView txt_c_ins, txt_c_mat, txt_c_pst, txt_c_kls, txt_c_detail_kls;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        txt_c_ins = view.findViewById(R.id.txt_c_ins);
        txt_c_mat = view.findViewById(R.id.txt_c_mat);
        txt_c_pst = view.findViewById(R.id.txt_c_pst);
        txt_c_kls = view.findViewById(R.id.txt_c_kls);
        txt_c_detail_kls = view.findViewById(R.id.txt_c_detail_kls);

        getJSON();

        ImageView img_ins = (ImageView) view.findViewById(R.id.imageView);
        img_ins.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Confirmation altert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Information");
                builder.setMessage("Instruktur merupakan menu yang \n"
                        + "menyimpan data pengajar. Memiliki jumlah " + c_ins + " data ");
                builder.setIcon(getResources().getDrawable(android.R.drawable.ic_dialog_info));
                builder.setCancelable(false);
                builder.setNegativeButton("Ok",null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        ImageView img_mat = (ImageView) view.findViewById(R.id.imageView2);
        img_mat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Confirmation altert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Information");
                builder.setMessage("Materi merupakan menu yang \n"
                        + "menyimpan data materi. Memiliki jumlah " + c_mat + " data ");
                builder.setIcon(getResources().getDrawable(android.R.drawable.ic_dialog_info));
                builder.setCancelable(false);
                builder.setNegativeButton("Ok",null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        ImageView img_pst = (ImageView) view.findViewById(R.id.imageView3);
        img_pst.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Confirmation altert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Information");
                builder.setMessage("Peserta merupakan menu yang \n"
                        + "menyimpan data peserta. Memiliki jumlah " + c_pst + " data ");
                builder.setIcon(getResources().getDrawable(android.R.drawable.ic_dialog_info));
                builder.setCancelable(false);
                builder.setNegativeButton("Ok",null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        ImageView img_kls = (ImageView) view.findViewById(R.id.imageView4);
        img_kls.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Confirmation altert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Information");
                builder.setMessage("Kelas merupakan menu yang \n"
                        + "menyimpan data kelas. Memiliki jumlah " + c_kls + " data ");
                builder.setIcon(getResources().getDrawable(android.R.drawable.ic_dialog_info));
                builder.setCancelable(false);
                builder.setNegativeButton("Ok",null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        ImageView img_d_kls = (ImageView) view.findViewById(R.id.imageView5);
        img_d_kls.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Confirmation altert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Information");
                builder.setMessage("Detail Kelas merupakan menu yang \n"
                        + "menyimpan data peserta yang mengikuti kelas \n"
                        + "tertentu. Memiliki jumlah " + c_d_kls + " data ");
                builder.setIcon(getResources().getDrawable(android.R.drawable.ic_dialog_info));
                builder.setCancelable(false);
                builder.setNegativeButton("Ok",null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        return view;
    }

    private void getJSON() {
        //bantuan dari class AsyncTask
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(),
                        "Mengambil Data", "Harap Tunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(KonfigurasiHome.URL_GET_ALL);
//                System.out.println("Result: " + result);

                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                JSON_STRING = message;
                Log.d("DATA JSON: ", JSON_STRING);
                //Toast.makeText(getActivity(),
                //        message.toString(), Toast.LENGTH_SHORT).show();
                //menampilkan data dalam bentuk list view
                displayDetailData(message);
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayDetailData(String message) {
        try {
            JSONObject jsonObject = new JSONObject(message);
            JSONArray result = jsonObject.getJSONArray(KonfigurasiHome.TAG_JSON_ARRAY);
            JSONObject object = result.getJSONObject(0);

             c_mat = object.getString(KonfigurasiHome.TAG_JSON_C_MAT);
             c_ins = object.getString(KonfigurasiHome.TAG_JSON_C_INS);
             c_pst = object.getString(KonfigurasiHome.TAG_JSON_C_PST);
             c_kls = object.getString(KonfigurasiHome.TAG_JSON_C_KLS);
             c_d_kls = object.getString(KonfigurasiHome.TAG_JSON_C_DETAIL);

            txt_c_ins.setText(c_ins + " Data");
            txt_c_mat.setText(c_mat + " Data");
            txt_c_pst.setText(c_pst + " Data");
            txt_c_kls.setText(c_kls + " Data");;
            txt_c_detail_kls.setText(c_d_kls + " Data");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}