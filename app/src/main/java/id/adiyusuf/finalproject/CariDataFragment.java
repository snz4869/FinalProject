package id.adiyusuf.finalproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CariDataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CariDataFragment extends Fragment {
    Button btn_cari_ins,btn_cari_pst,btn_cari_mat;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CariDataFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CariDataFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CariDataFragment newInstance(String param1, String param2) {
        CariDataFragment fragment = new CariDataFragment();
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
        View view = inflater.inflate(R.layout.fragment_cari_data, container, false);

        btn_cari_ins = view.findViewById(R.id.btn_cari_ins);
        btn_cari_pst = view.findViewById(R.id.btn_cari_pst);
        btn_cari_mat = view.findViewById(R.id.btn_cari_mat);

        btnHandler();

        return view;
    }

    private void btnHandler() {
        btn_cari_ins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),CariInstrukturActivity.class));
            }
        });

        btn_cari_pst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),CariPesertaActivity.class));
            }
        });

        btn_cari_mat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),CariMateriActivity.class));
            }
        });
    }
}