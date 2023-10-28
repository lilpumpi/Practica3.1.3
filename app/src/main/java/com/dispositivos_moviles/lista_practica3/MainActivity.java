package com.dispositivos_moviles.lista_practica3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Entrenamiento> entrenos;
    private ArrayAdapter<Entrenamiento> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lvEntrenamientos = (ListView) findViewById(R.id.lv_tablaEntrenos);
        Button btAdd = (Button) findViewById(R.id.bAdd);
        Button btStats = (Button) findViewById(R.id.bStats);

        //Iniciamos las listas
        entrenos = new ArrayList<Entrenamiento>();
        adapter = new ArrayAdapter<Entrenamiento>(this, android.R.layout.simple_list_item_1, entrenos);
        lvEntrenamientos.setAdapter(adapter);

        //Configuramos el boton de agragar entrenamiento
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarEntrenamiento();
            }
        });

        //Configuramos boton de mostrar estadisticas
        btStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarStats();
            }
        });

    }

    private void agregarEntrenamiento(){
        //Creamos un AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Añadir Entrenamieno");
        builder.setMessage("Inroduzca los datos del nuevo entrenamiento");

        //Inflamos el layout personalizado que creamos para añadir entrenamiento
        View addView = getLayoutInflater().inflate(R.layout.dialog_agregar_entrenamiento, null);
        builder.setView(addView);

        EditText etTiempo = addView.findViewById(R.id.etTiempo);
        EditText etDistancia = addView.findViewById(R.id.etDistancia);

        //Añadimos botones de crear y cancelar
        builder.setPositiveButton("Crear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int tiempo = Integer.parseInt(etTiempo.getText().toString()); //Guardamos el tiempo introducido
                double distancia = Double.parseDouble(etDistancia.getText().toString()); //Guardamos la distancia introducida

                Entrenamiento nuevoEntreno = new Entrenamiento(tiempo, distancia);

                entrenos.add(nuevoEntreno);
                adapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Cancelar", null);
        builder.create().show();

    }

    public void mostrarStats(){
        //Creamos un AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Estadisticas Globales");

        //Inflamos el layout personalizado que creamos para mostrar los datos
        View statsView = getLayoutInflater().inflate(R.layout.dialog_stats, null);
        builder.setView(statsView);

        TextView etTotalKm = (TextView) statsView.findViewById(R.id.tvTotal);
        TextView etTotalMinKm = (TextView) statsView.findViewById(R.id.tvMinKm);

        //Calculamos los kilometros totales
        double totalKm = calcularTotalKm();
        etTotalKm.setText(Double.toString(totalKm));

        //Calculamos la media de minutos por kilometro
        double mediaMinKm = calcularMediaMinKm();
        etTotalMinKm.setText(Double.toString(mediaMinKm));

        //Añadimos boton de cerrar
        builder.setNegativeButton("Cerrar", null);
        builder.create().show();

    }

    public double calcularTotalKm(){
        double total = 0;
        for(Entrenamiento entreno: entrenos){
            total += entreno.getDistancia();
        }

        return total;
    }

    public double calcularMediaMinKm(){
        double total = 0;
        for(Entrenamiento entreno: entrenos){
            total += entreno.getMinutosPorKm(); //Sumamos todos los minutos por km de cada entreno
        }

        return total/adapter.getCount(); //Devolvemos la media diviendo el total entre el numero de entrenamientos
    }

}