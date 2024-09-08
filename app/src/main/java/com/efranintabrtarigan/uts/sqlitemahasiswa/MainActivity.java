package com.efranintabrtarigan.uts.sqlitemahasiswa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lvdata;
    FloatingActionButton fabtambah;
    DatabaseHandler databaseHandler;
    List<String> listData;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvdata = findViewById(R.id.lv_data);
        fabtambah = findViewById(R.id.fab_Tambah);
        databaseHandler = new DatabaseHandler(this);

        fabtambah.setOnClickListener(v -> bukaDialogTambah());

        tampilkanSemuaData();

        lvdata.setOnItemClickListener((adapterView, view, i, l) -> {
            String data = listData.get(i);
            bukaDialogUpdate(data);
        });
    }

    private void bukaDialogUpdate(String data) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update atau Hapus Data");
        View dialogView = LayoutInflater.from(this).inflate(R.layout.edit_data, null);
        builder.setView(dialogView);

        EditText etNama = dialogView.findViewById(R.id.et_nama);
        EditText etNim = dialogView.findViewById(R.id.et_nim);
        EditText etProdi = dialogView.findViewById(R.id.et_prodi);
        Button btnUpdate = dialogView.findViewById(R.id.editText);
        Button btnDelete = dialogView.findViewById(R.id.buttonHapus);

        etNama.setText(data);

        String[] parts = data.split(" - ");
        if (parts.length == 3) {
            etNim.setText(parts[1]);
            etProdi.setText(parts[2]);
        }

        AlertDialog dialog = builder.create();

        btnUpdate.setOnClickListener(v -> {
            if (etNama.getText().toString().trim().isEmpty()) {
                etNama.setError("Nama Harus Diisi");
                return;
            }
            updateData(data, etNama.getText().toString(), etNim.getText().toString(), etProdi.getText().toString());
            dialog.dismiss();
            tampilkanSemuaData();
        });

        btnDelete.setOnClickListener(v -> {
            hapusData(data);
            dialog.dismiss();
            tampilkanSemuaData();
        });

        dialog.show();
    }

    private void tampilkanSemuaData() {
        listData = databaseHandler.tampilSemua();
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                listData
        );
        lvdata.setAdapter(adapter);
    }

    private void bukaDialogTambah() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tambah Data");
        View dialogView = LayoutInflater.from(this).inflate(R.layout.tambah_data, null);
        builder.setView(dialogView);

        EditText etNama = dialogView.findViewById(R.id.nama);
        EditText etNim = dialogView.findViewById(R.id.nim);
        EditText etProdi = dialogView.findViewById(R.id.prodi);
        Button btnSimpan = dialogView.findViewById(R.id.btn_simpan);

        AlertDialog dialog = builder.create();
        btnSimpan.setOnClickListener(v -> {
            if (etNama.getText().toString().trim().isEmpty()) {
                etNama.setError("Nama Harus Diisi");
                return;
            }
            simpanData(etNama.getText().toString(), etNim.getText().toString(), etProdi.getText().toString());
            dialog.dismiss();
            tampilkanSemuaData();
        });

        dialog.show();
    }

    // Method to save data using the DatabaseHandler
    private void simpanData(String nama, String nim, String prodi) {
        databaseHandler.simpan(nama, nim, prodi);
    }

    // Method to update data using the DatabaseHandler
    private void updateData(String oldData, String newName, String newNim, String newProdi) {
        // Asumsikan bahwa oldData berisi nama, nim, dan prodi
        databaseHandler.update(oldData, newName, newNim, newProdi);
    }

    private void hapusData(String nama) {
        databaseHandler.delete(nama);
    }
}
