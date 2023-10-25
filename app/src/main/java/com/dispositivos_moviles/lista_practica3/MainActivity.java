package com.dispositivos_moviles.lista_practica3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> elementos; //Almacenará los elementos que se añadan a la lista
    private ArrayAdapter<String> adapter; //Adapta la lista para mostrarlos en el ListView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lvCompra = (ListView) findViewById(R.id.lvCompra);
        Button btAdd = (Button) findViewById(R.id.button2);

        elementos = new ArrayList<String>();

        //Creamos el adapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, elementos);
        lvCompra.setAdapter(adapter);

        //Habilitamos la eliminación de los elementos con pulsacion larga
        lvCompra.setLongClickable(true);
        lvCompra.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {

                if(pos >= 0){

                    //Creamos el cuadro de dialogo confirmar el borrado
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Borrar Item");
                    builder.setMessage("Estas seguo de que desea borrar el elemento?");
                    String[] opciones = {"Cancelar", "Confirmar"};

                    //Creamos el boton de confirmar
                    builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            elementos.remove(pos); //Eliminamos el elemento seleccionado
                            adapter.notifyDataSetChanged();
                            updateStatus();
                        }
                    });

                    builder.setNegativeButton("Cancelar", null);
                    builder.create().show();

                }

                return true;
            }
        });


        //Habilitamos la edicion con pulsacion corta
        lvCompra.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                // Al hacer una pulsación simple, permite al usuario editar el elemento
                modificarElemento(pos);
            }
        });


        //Listener en el boton de Añadir para ejecutar el metodo Add cuando se pulse
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addElement();
            }
        });
    }

    //Añade un elemento al la lista
    private void addElement(){

        final EditText edText = new EditText(this); //Creamos un editText para que el usuario escriba el nuevo elemento

        //Creamos un AlertDialog para que el usuario añada un elemento
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Añadir nuevo item");
        builder.setMessage("Escriba el nombre del Item que deseas añadir");
        builder.setView(edText);

        //Creamos un boton positivo para añadir el elemento nuevo
        builder.setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String newElement = edText.getText().toString();

                //Comprobamos que haya escrito algo
                if(!newElement.isEmpty()){
                    adapter.add(newElement);
                    updateStatus();
                } else{
                    Toast.makeText(getApplicationContext(), "Debes escribir un nombre", Toast.LENGTH_LONG).show();
                }
            }
        });


        //Creamos un boton negativo para cancelar la operacion
        builder.setNegativeButton("Cancelar", null);
        builder.create().show();

    }


    //Modificar elemento
    private void modificarElemento(int pos){

        final EditText edText = new EditText(this); //Creamos un editText para que el usuario escriba el nuevo elemento

        //Creamos un AlertDialog para que el usuario mdoifique el elemento
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Modificar item");
        builder.setMessage("Escriba el nuevo nombre del Item");
        builder.setView(edText);

        //Creamos un boton positivo para confirmar el cambio
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String newElement = edText.getText().toString();

                //Comprobamos que haya escrito algo
                if(!newElement.isEmpty()){
                    elementos.set(pos, newElement);
                    adapter.notifyDataSetChanged();
                    updateStatus();
                } else{
                    Toast.makeText(getApplicationContext(), "Debes escribir un nombre", Toast.LENGTH_LONG).show();
                }
            }
        });


        //Creamos un boton negativo para cancelar la operacion
        builder.setNegativeButton("Cancelar", null);
        builder.create().show();

    }


    //Actualizamos los datos
    private void updateStatus(){
        TextView tvCantidad = (TextView) findViewById(R.id.tvCantidad);
        tvCantidad.setText(Integer.toString(adapter.getCount()));
    }
}