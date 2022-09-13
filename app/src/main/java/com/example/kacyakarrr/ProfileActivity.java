package com.example.kacyakarrr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileActivity extends AppCompatActivity {

    ArrayAdapter<String> arrayAdapter_marka;
    ArrayAdapter<String> arrayAdapter_model;
    ArrayAdapter<String> arrayAdapter_motor;
    private FirebaseAuth auth;
    static String selected_marka = "", selected_model, selected_motor = "";
    Spinner spinnerMarka, spinnerModel, spinnerMotor;

    DatabaseReference reference;
    DatabaseReference referenceModel;

    ArrayList<String> model;
    static List<Marka> markalar;
    ArrayList<String> names;
    ArrayList depos;
    DatabaseReference benzin = FirebaseDatabase.getInstance().getReference("Benzin");
    static double benzinFiyatı = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        spinnerMarka = (Spinner) findViewById(R.id.spinner_marka);
        spinnerModel = (Spinner) findViewById(R.id.spinner_model);
        spinnerMotor = (Spinner) findViewById(R.id.spinner_motor);
        auth = FirebaseAuth.getInstance();

        reference = FirebaseDatabase.getInstance().getReference("Marka");

        markalar = new ArrayList<>();
        names = new ArrayList<>();
        depos = new ArrayList();

        getList();
        getBenzin();
        prepared_car_list();
        load_initial();

        spinnerMarka.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selected_marka = spinnerMarka.getSelectedItem().toString().trim() + "";

                if (position == 0) {

                } else {
                    getModel(selected_marka);
                    System.out.println(selected_marka);
                    arrayAdapter_model = new ArrayAdapter<>(ProfileActivity.this, R.layout.textview_green, names);
                }

                spinnerModel.setAdapter(arrayAdapter_model);
                set_selection();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                selected_model = spinnerModel.getSelectedItem().toString().trim() + "";
                if (position == 0) {

                } else {
                    prepare_depo(selected_model);
                    arrayAdapter_motor = new ArrayAdapter<>(ProfileActivity.this, R.layout.textview_blue, depos);
                }

                spinnerMotor.setAdapter(arrayAdapter_motor);
                set_selection();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerMotor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0) {

                } else {
                    selected_motor = spinnerMotor.getSelectedItem().toString().trim() + "";
                    set_selection();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    void prepare_depo(String selected_middle) {
        depos.clear();
        depos.add("Seciniz");
        for (int i = 0; i < markalar.size(); i++) {
            if (markalar.get(i).name.equalsIgnoreCase(selected_middle)) {

                depos.add(markalar.get(i).motorTipi);
                break;
            }
        }
    }

    void prepared_car_list() {

        model = new ArrayList<>();
        model.add("Seciniz");
        names.add("Seciniz");
        depos.add("Seciniz");
        names.add("");
        names.add("");

    }

    void load_initial() {
        arrayAdapter_marka = new ArrayAdapter<>(ProfileActivity.this, R.layout.textview_red, model);
        spinnerMarka.setAdapter(arrayAdapter_marka);
        arrayAdapter_model = new ArrayAdapter<>(ProfileActivity.this, R.layout.textview_green, names);
        spinnerModel.setAdapter(arrayAdapter_model);
        arrayAdapter_motor = new ArrayAdapter<>(ProfileActivity.this, R.layout.textview_blue, depos);
        spinnerMotor.setAdapter(arrayAdapter_motor);
    }

    void set_selection() {
        System.out.println(selected_marka);
        System.out.println(selected_model);
        System.out.println(selected_motor);

    }

    public void getBenzin() {

        benzin.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                benzinFiyatı = snapshot.getValue(Double.class) != null ? snapshot.getValue(Double.class) : 0;
                System.out.println("Fiyat is ");
                System.out.println(benzinFiyatı);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    public void getList() {

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    model.clear();
                    model.add(0, "Seciniz");
                    for (DataSnapshot item : snapshot.getChildren()) {
                        String name = item.getValue(String.class);
                        System.out.println(name);
                        model.add(name);

                    }
                    System.out.println(model.size());
                    for (int i = 0; i < model.size(); i++) {
                        System.out.println(model.get(i));
                        System.out.println("\n");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getModel(String name) {
        referenceModel = FirebaseDatabase.getInstance().getReference(name);
        referenceModel.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    markalar.clear();
                    names.clear();
                    depos.clear();
                    names.add("Seciniz");
                    for (DataSnapshot item : snapshot.getChildren()) {
                        Marka name = item.getValue(Marka.class);
                        markalar.add(name);
                        names.add(name.name);

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void onClick(View view) {
        startActivity(new Intent(this, Map.class));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.signout) {
            auth.signOut();


            Intent intentToMain = new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(intentToMain);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}