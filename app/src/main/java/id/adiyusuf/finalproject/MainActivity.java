package id.adiyusuf.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import id.adiyusuf.finalproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    private ActivityMainBinding binding;
    private ActionBarDrawerToggle toggle;
    private Button btn_ig;
    String myStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initView();
    }

    private void initView() {
        // CUSTOM Toolbar
        setSupportActionBar(binding.toolbar);

        //set menu
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Fragment fragmentMenu = null;
        myStr = "home";
        if(extras != null)
            if(extras != null){
                myStr = extras.getString("keyName");
            } else {
                myStr = "home";
            }

        switch (myStr){
            case "home":
                //default fragment dibuka pertama kali
                getSupportActionBar().setTitle("Home");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, new HomeFragment())
                        .commit();
                binding.navView.setCheckedItem(R.id.nav_home);
                break;
            case "instruktur":
                getSupportActionBar().setTitle("Instruktur");
                fragmentMenu = new InstrukturFragment();
                binding.drawer.closeDrawer(GravityCompat.START);
                callFragment(fragmentMenu);
                binding.navView.setCheckedItem(R.id.nav_instruktur);
                break;
            case "materi":
                getSupportActionBar().setTitle("Materi");
                fragmentMenu = new MateriFragment();
                binding.drawer.closeDrawer(GravityCompat.START);
                callFragment(fragmentMenu);
                binding.navView.setCheckedItem(R.id.nav_materi);
                break;
            case "peserta":
                getSupportActionBar().setTitle("Peserta");
                fragmentMenu = new PesertaFragment();
                binding.drawer.closeDrawer(GravityCompat.START);
                callFragment(fragmentMenu);
                binding.navView.setCheckedItem(R.id.nav_peserta);
                break;
            case "kelas":
                getSupportActionBar().setTitle("Kelas");
                fragmentMenu = new KelasFragment();
                binding.drawer.closeDrawer(GravityCompat.START);
                callFragment(fragmentMenu);
                binding.navView.setCheckedItem(R.id.nav_kelas);
                break;
            case "detail kelas":
                getSupportActionBar().setTitle("Detail Kelas");
                fragmentMenu = new DetailKelasFragment();
                binding.drawer.closeDrawer(GravityCompat.START);
                callFragment(fragmentMenu);
                binding.navView.setCheckedItem(R.id.nav_detail_kelas);
                break;
        }

        // membuka drawer
        toggle = new ActionBarDrawerToggle(this, binding.drawer, binding.toolbar,
                R.string.open, R.string.close);

        // drawer back button
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        //sinkronisasi drawer
        toggle.syncState();



        // salah satu menu navigasi dipilih
        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            Fragment fragment = null;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (myStr == "instruktur"){
                    item = findViewById(R.id.nav_home);
                }

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        fragment = new HomeFragment();
                        getSupportActionBar().setTitle("Home");
                        binding.drawer.closeDrawer(GravityCompat.START);
                        callFragment(fragment);
                        break;
                    case R.id.nav_instruktur:
                        getSupportActionBar().setTitle("Instruktur");
                        fragment = new InstrukturFragment();
                        binding.drawer.closeDrawer(GravityCompat.START);
                        callFragment(fragment);
                        break;
                    case R.id.nav_materi:
                        getSupportActionBar().setTitle("Materi");
                        fragment = new MateriFragment();
                        binding.drawer.closeDrawer(GravityCompat.START);
                        callFragment(fragment);
                        break;
                    case R.id.nav_peserta:
                        getSupportActionBar().setTitle("Peserta");
                        fragment = new PesertaFragment();
                        binding.drawer.closeDrawer(GravityCompat.START);
                        callFragment(fragment);
                        break;
                    case R.id.nav_kelas:
                        getSupportActionBar().setTitle("Kelas");
                        fragment = new KelasFragment();
                        binding.drawer.closeDrawer(GravityCompat.START);
                        callFragment(fragment);
                        break;
                    case R.id.nav_detail_kelas:
                        getSupportActionBar().setTitle("Detail Kelas");
                        fragment = new DetailKelasFragment();
                        binding.drawer.closeDrawer(GravityCompat.START);
                        callFragment(fragment);
                        break;
                }
                return true;
            }
        });

        NavigationView navigationView = findViewById(R.id.navView);
        View headerView = getLayoutInflater().inflate(R.layout.nav_header_layout,
                navigationView, false);
        navigationView.addHeaderView(headerView);

        Button btn_wa = headerView.findViewById(R.id.btn_wa);
        btn_wa.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(getApplicationContext(), "Whatsapp Admin", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://wa.me/6283879438051"));
                startActivity(intent);
            }
        });
    }

    private void callFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right
        );
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}