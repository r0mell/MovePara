package com.example.movepara;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

import java.lang.Math;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    static int GRAVEDAD = -10;
    private EditText val;
    private EditText ang;

    private TextView alc;
    private TextView alt;
    private TextView tT;

    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public double calculoAltura(double velocidad, double angulo) {

        double altura = 0;

        altura = (Math.pow(velocidad, 2)) * (Math.pow(Math.sin(angulo * (Math.PI / 180)), 2)) / (2 * -GRAVEDAD);

        Log.i("", "mi nombre es " + altura);

        return altura;
    }

    public double calcularAlcance(double velocidad, double angulo) {
        double alcance = 0;

        alcance = (Math.pow(velocidad, 2) * Math.sin((2 * angulo) * (Math.PI / 180))) / -GRAVEDAD;
        return alcance;
    }

    public double tiempoTotal(double alcance, double velocidadX) {
        double tTotal;

        tTotal = alcance / velocidadX;

        return tTotal;
    }

    public void calculos(View view) {

        double altura;
        double alcance;
        double tTotal;
        double velocidadX;
        double velocidadY;

        val = findViewById(R.id.txtVelocidad);
        double velocidad = Double.parseDouble(val.getText().toString());

        ang = findViewById(R.id.txtAngulo);
        double angulo = Double.parseDouble(ang.getText().toString());
        velocidadX = Math.cos(angulo * (Math.PI / 180)) * velocidad;
        velocidadY = Math.sin(angulo * (Math.PI / 180)) * velocidad;

        altura = calculoAltura(velocidad, angulo);
        alcance = calcularAlcance(velocidad, angulo);
        tTotal = tiempoTotal(alcance, velocidadX);

        alc = findViewById(R.id.txtAltura);
        alc.setText(String.format("%.4f", altura) + " metros");

        alt = findViewById(R.id.txtAlcance);
        alt.setText(String.format("%.4f", alcance) + " metros");

        tT = findViewById(R.id.txtTiempoT);
        tT.setText(String.format("%.4f", tTotal) + " segundos");


        graficar(alcance, altura, tTotal, velocidadX, velocidadY);

    }

    //esta funcion permite calcular el punto en Y para cada punto X
    public double calcularH(double tiempo, double velocidadY) {

        double respuesta = 0;
        respuesta = (velocidadY * tiempo) + ((GRAVEDAD * (tiempo * tiempo)) / 2);

        return respuesta;
    }

    public void graficar(double alcance, double altura, double tTotal, double velocidadX, double velocidadY) {

        double temp = 0;
        double distX = 0;
        double distY = 0;
        ArrayList<String> coordenadaX = new ArrayList<String>();
        ArrayList<String> coordenadaY = new ArrayList<String>();

        while (temp <= tTotal) {

            //Con esta funcion se generan los segmentos del eje x aplicando un porcentaje para cualquier distancia
            distX = temp * velocidadX;
            distY = calcularH(temp, velocidadY);
            coordenadaX.add(String.valueOf(distX));
            coordenadaY.add(String.valueOf(distY));
            //Log.i("", "distancia X=  " + distX + "distancia Y=  " + distY + "tiempo= " + temp);
            temp = temp + (tTotal * 0.02);
        }
        Button button = (Button) findViewById(R.id.btnGraficar);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                enviar(coordenadaX,coordenadaY,alcance);

            }
        });


        //muestro las coordenadas generadsa y guardadas en las listas
        /*for(int x=0;x<coordenadaX.size();x++) {
            Log.i("", "distancia X=  "+coordenadaX.get(x));
            Log.i("", "distancia Y=  "+coordenadaY.get(x));
        }*/
    }

    public void enviar(ArrayList<String> coordenadaX,ArrayList<String> coordenadaY,double alcance) {

        Intent i = new Intent(this, Grafica.class);
        i.putStringArrayListExtra("coorX", coordenadaX);
        i.putStringArrayListExtra("coorY", coordenadaY);
        i.putExtra("alcance",String.valueOf( alcance));
        startActivity(i);

    }
}


