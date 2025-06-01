package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class CreateCreatureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_creature);

        Button btnCreature = findViewById(R.id.btnChooseCreature);
        Button btnGender = findViewById(R.id.btnChooseGender);
        Button btnBodyType = findViewById(R.id.btnChooseBodyType);

        btnCreature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog(
                        new String[]{"Dragon", "Elf", "Human"},
                        selectedOption -> btnBodyType.setText(selectedOption)
                );
            }
        });

        btnGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog(
                        new String[]{"Male", "Female"},
                        selectedOption -> btnGender.setText(selectedOption)
                );
            }
        });

        btnBodyType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog(
                        new String[]{"Small", "Medium", "Heavy"},
                        selectedOption -> btnBodyType.setText(selectedOption)
                );
            }
        });
    }

    private void showBottomSheetDialog(String[] options, final OnOptionSelectedListener listener) {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_layout, null);
        dialog.setContentView(view);

        ListView listView = view.findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, options);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            listener.onOptionSelected(options[position]);
            dialog.dismiss();
        });

        dialog.show();
    }

    // Interface for option selection
    private interface OnOptionSelectedListener {
        void onOptionSelected(String option);
    }
}
