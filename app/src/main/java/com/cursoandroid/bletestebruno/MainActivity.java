package com.cursoandroid.bletestebruno;

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    BluetoothManager btManager;
    BluetoothAdapter btAdapter;
    BluetoothLeScanner btScanner;
    Button botaoComecaEscanear;
    Button botaoParaEscanear;
    Button botaoComecaEscanear2;
    Button botaoComecaEscanear3;
    Button botaoComecaEscanear4;
    Button botaoComecaEscanear5;

    TextView textView;
    private int resultado;

    int tempo = 300000;





    private final static int REQUEST_ENABLE_BT = 1;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        //Faz o cast do TextView
        textView = (TextView) findViewById(R.id.textViewId);
        //
        textView.setMovementMethod(new ScrollingMovementMethod());

        //Faz o cast do botão que começa a escanear
        //Após isso define o que fazer após o clique
        //Chama a função de começar a escanear
        botaoComecaEscanear = (Button) findViewById(R.id.botaoComecaEscanearId);
        botaoComecaEscanear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                escanear();
            }
        });

        botaoComecaEscanear2 = (Button) findViewById(R.id.botaoComecaEscanear2metrosId);
        botaoComecaEscanear2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                escanear2metros();
            }
        });

        botaoComecaEscanear3 = (Button) findViewById(R.id.botaoComecaEscanear3metrosId);
        botaoComecaEscanear3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                escanear3metros();
            }
        });

        botaoComecaEscanear4 = (Button) findViewById(R.id.botaoComecaEscanear4metrosId);
        botaoComecaEscanear4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                escanear4metros();
            }
        });

        botaoComecaEscanear5 = (Button) findViewById(R.id.botaoComecaEscanear5metrosId);
        botaoComecaEscanear5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                escanear5metros();
            }
        });

        //Faz o cast do botão que para de escanear
        //Após o clique chama a função de parar escanear
        botaoParaEscanear = (Button) findViewById(R.id.botaoParaEscanearId
        );

        // Faz o botão de parar de escanear ficar invisível após a parada
        botaoParaEscanear.setVisibility(View.INVISIBLE);


        //
        btManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        btAdapter = btManager.getAdapter();
        btScanner = btAdapter.getBluetoothLeScanner();


        if (btAdapter != null && !btAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }

        // Programa checa se o usuário autorizou o uso da localização, caso não tenha autorizado
        // oferece a opção para ativar ou não
        // Faz a checagem com um if analisando se a permissão já está ativa ou não
        if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("ALERTA ATIVAR LOCALIZAÇÃO.");
            builder.setMessage("Para esse app funcionar é necessário que autorize a permissão para uso da localização.");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                }
            });
            builder.show();
        }
    }

    // Chama o scancallback da escaner
    private ScanCallback leScanCallback = new ScanCallback() {
        @Override

        // Chama o método onScanResult
        // Tal método retorna quando encontra um dispositivo BLE
        // Append: adiciona os resultados necessários ao textView
        // Nesse caso solicitei o endereço do dispositivo e seu RSSI
        public void onScanResult(int callbackType, ScanResult result) {
                resultado = result.getRssi();
                databaseReference.child("RssiTxpower").child("Resultados").push().setValue(resultado);
            textView.append("ENDEREÇO: " + result.getDevice().getAddress() +
                    "\n RSSI: " + result.getRssi() +
                    "\n");

            // auto scroll for text view
            final int scrollAmount = textView.getLayout().getLineTop(textView.getLineCount()) - textView.getHeight();
            // if there is no need to scroll, scrollAmount will be <=0
            if (scrollAmount > 0)
                textView.scrollTo(0, scrollAmount);
        }
    };


    // Chama o scancallback da escaner
    private ScanCallback leScanCallback2metros = new ScanCallback() {
        @Override

        // Chama o método onScanResult
        // Tal método retorna quando encontra um dispositivo BLE
        // Append: adiciona os resultados necessários ao textView
        // Nesse caso solicitei o endereço do dispositivo e seu RSSI
        public void onScanResult(int callbackType, ScanResult result) {
            resultado = result.getRssi();
            databaseReference.child("RssiTxpower2metros").child("Resultados").push().setValue(resultado);
            textView.append("ENDEREÇO: " + result.getDevice().getAddress() +
                    "\n RSSI: " + result.getRssi() +
                    "\n");

            // auto scroll for text view
            final int scrollAmount = textView.getLayout().getLineTop(textView.getLineCount()) - textView.getHeight();
            // if there is no need to scroll, scrollAmount will be <=0
            if (scrollAmount > 0)
                textView.scrollTo(0, scrollAmount);
        }
    };

    // Chama o scancallback da escaner
    private ScanCallback leScanCallback3metros = new ScanCallback() {
        @Override

        // Chama o método onScanResult
        // Tal método retorna quando encontra um dispositivo BLE
        // Append: adiciona os resultados necessários ao textView
        // Nesse caso solicitei o endereço do dispositivo e seu RSSI
        public void onScanResult(int callbackType, ScanResult result) {
            resultado = result.getRssi();
            databaseReference.child("RssiTxpower3metros").child("Resultados").push().setValue(resultado);
            textView.append("ENDEREÇO: " + result.getDevice().getAddress() +
                    "\n RSSI: " + result.getRssi() +
                    "\n");

            // auto scroll for text view
            final int scrollAmount = textView.getLayout().getLineTop(textView.getLineCount()) - textView.getHeight();
            // if there is no need to scroll, scrollAmount will be <=0
            if (scrollAmount > 0)
                textView.scrollTo(0, scrollAmount);
        }
    };

    // Chama o scancallback da escaner
    private ScanCallback leScanCallback4metros = new ScanCallback() {
        @Override

        // Chama o método onScanResult
        // Tal método retorna quando encontra um dispositivo BLE
        // Append: adiciona os resultados necessários ao textView
        // Nesse caso solicitei o endereço do dispositivo e seu RSSI
        public void onScanResult(int callbackType, ScanResult result) {
            resultado = result.getRssi();
            databaseReference.child("RssiTxpower4metros").child("Resultados").push().setValue(resultado);
            textView.append("ENDEREÇO: " + result.getDevice().getAddress() +
                    "\n RSSI: " + result.getRssi() +
                    "\n");

            // auto scroll for text view
            final int scrollAmount = textView.getLayout().getLineTop(textView.getLineCount()) - textView.getHeight();
            // if there is no need to scroll, scrollAmount will be <=0
            if (scrollAmount > 0)
                textView.scrollTo(0, scrollAmount);
        }
    };

    // Chama o scancallback da escaner
    private ScanCallback leScanCallback5metros = new ScanCallback() {
        @Override

        // Chama o método onScanResult
        // Tal método retorna quando encontra um dispositivo BLE
        // Append: adiciona os resultados necessários ao textView
        // Nesse caso solicitei o endereço do dispositivo e seu RSSI
        public void onScanResult(int callbackType, ScanResult result) {
            resultado = result.getRssi();
            databaseReference.child("RssiTxpower5metros").child("Resultados").push().setValue(resultado);
            textView.append("ENDEREÇO: " + result.getDevice().getAddress() +
                    "\n RSSI: " + result.getRssi() +
                    "\n");

            // auto scroll for text view
            final int scrollAmount = textView.getLayout().getLineTop(textView.getLineCount()) - textView.getHeight();
            // if there is no need to scroll, scrollAmount will be <=0
            if (scrollAmount > 0)
                textView.scrollTo(0, scrollAmount);
        }
    };



    // Este método trata-se de dar autorização para o programa rodar em background
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    System.out.println("coarse location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
                return;
            }
        }
    }

    public void escanear() {

        //Declara na tela o estado do escaneamento
        //Seta como invisível o botão Começar a Escanear
        //Seta como visível o botão Escaneando, aguarde
        //Roda o escanemanto pelo tempo em mSegundos definido
        Toast.makeText(MainActivity.this, "Aguarde os resultados do escaneamento.", Toast.LENGTH_SHORT).show();
        botaoComecaEscanear.setVisibility(View.INVISIBLE);
        botaoParaEscanear.setVisibility(View.VISIBLE);
        btScanner.startScan(leScanCallback);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "O escaneamento foi desligado.", Toast.LENGTH_SHORT).show();
                botaoComecaEscanear.setVisibility(View.VISIBLE);
                botaoParaEscanear.setVisibility(View.INVISIBLE);
                btScanner.stopScan(leScanCallback);
            }
        }, tempo);

    }

    public void escanear2metros() {

        //Declara na tela o estado do escaneamento
        //Seta como invisível o botão Começar a Escanear
        //Seta como visível o botão Escaneando, aguarde
        //Roda o escanemanto pelo tempo em mSegundos definido
        Toast.makeText(MainActivity.this, "Aguarde os resultados do escaneamento.", Toast.LENGTH_SHORT).show();
        botaoComecaEscanear2.setVisibility(View.INVISIBLE);
        botaoParaEscanear.setVisibility(View.VISIBLE);
        btScanner.startScan(leScanCallback2metros);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "O escaneamento foi desligado.", Toast.LENGTH_SHORT).show();
                botaoComecaEscanear2.setVisibility(View.VISIBLE);
                botaoParaEscanear.setVisibility(View.INVISIBLE);
                btScanner.stopScan(leScanCallback2metros);
            }
        }, tempo);

    }

    public void escanear3metros() {

        //Declara na tela o estado do escaneamento
        //Seta como invisível o botão Começar a Escanear
        //Seta como visível o botão Escaneando, aguarde
        //Roda o escanemanto pelo tempo em mSegundos definido
        Toast.makeText(MainActivity.this, "Aguarde os resultados do escaneamento.", Toast.LENGTH_SHORT).show();
        botaoComecaEscanear3.setVisibility(View.INVISIBLE);
        botaoParaEscanear.setVisibility(View.VISIBLE);
        btScanner.startScan(leScanCallback3metros);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "O escaneamento foi desligado.", Toast.LENGTH_SHORT).show();
                botaoComecaEscanear3.setVisibility(View.VISIBLE);
                botaoParaEscanear.setVisibility(View.INVISIBLE);
                btScanner.stopScan(leScanCallback3metros);
            }
        }, tempo);

    }

    public void escanear4metros() {

        //Declara na tela o estado do escaneamento
        //Seta como invisível o botão Começar a Escanear
        //Seta como visível o botão Escaneando, aguarde
        //Roda o escanemanto pelo tempo em mSegundos definido
        Toast.makeText(MainActivity.this, "Aguarde os resultados do escaneamento.", Toast.LENGTH_SHORT).show();
        botaoComecaEscanear4.setVisibility(View.INVISIBLE);
        botaoParaEscanear.setVisibility(View.VISIBLE);
        btScanner.startScan(leScanCallback4metros);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "O escaneamento foi desligado.", Toast.LENGTH_SHORT).show();
                botaoComecaEscanear4.setVisibility(View.VISIBLE);
                botaoParaEscanear.setVisibility(View.INVISIBLE);
                btScanner.stopScan(leScanCallback4metros);
            }
        }, tempo);

    }

    public void escanear5metros() {

        //Declara na tela o estado do escaneamento
        //Seta como invisível o botão Começar a Escanear
        //Seta como visível o botão Escaneando, aguarde
        //Roda o escanemanto pelo tempo em mSegundos definido
        Toast.makeText(MainActivity.this, "Aguarde os resultados do escaneamento.", Toast.LENGTH_SHORT).show();
        botaoComecaEscanear5.setVisibility(View.INVISIBLE);
        botaoParaEscanear.setVisibility(View.VISIBLE);
        btScanner.startScan(leScanCallback5metros);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "O escaneamento foi desligado.", Toast.LENGTH_SHORT).show();
                botaoComecaEscanear5.setVisibility(View.VISIBLE);
                botaoParaEscanear.setVisibility(View.INVISIBLE);
                btScanner.stopScan(leScanCallback5metros);
            }
        }, tempo);

    }



}