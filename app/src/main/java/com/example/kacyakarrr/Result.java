package com.example.kacyakarrr;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Result extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        hesapla();

    }

    public void hesapla (){
        // 100 kmde harcanan
        double harcama = 0;

        for(int i = 0; i < ProfileActivity.markalar.size(); i++){
            if(ProfileActivity.markalar.get(i).name.equalsIgnoreCase(ProfileActivity.selected_model)){
                harcama = ProfileActivity.markalar.get(i).harcama;

            }
        }
        double dakika = (Map.results[0]/1000) / (1.4);

        // 100.0 çarp bölme nedeni virgülden sonrayı azaltmak
        double bolum = Math.round(((dakika / 60)* 100.0) / 100.0) ;
        double kalan = Math.round(((dakika % 60) * 100.0) / 100.0);

        final TextView mali = (TextView) findViewById(R.id.maliyet);
        final TextView sur = (TextView) findViewById(R.id.sure);
        final TextView dis = (TextView) findViewById(R.id.uzaklik);

        //matematiksel
        System.out.println(ProfileActivity.benzinFiyatı);
        double maliyett = Math.round((((Map.results[0]/1000)*harcama / 100 * ProfileActivity.benzinFiyatı)* 100.0)/ 100.0);
        System.out.println("Maliyet: " + maliyett);
        mali.setText(" Ortalama\n  Maliyet\n\n " + maliyett + " TL");
        sur.setText("     Ortalama Seyahat\n             Suresi\n\n            " + bolum +" saat\n         " + kalan + " dakika");
        dis.setText("Uzaklık\n\n " + Math.round(((Map.results[0]/1000) * 100.0) / 100.0) + " km" );


    }

}