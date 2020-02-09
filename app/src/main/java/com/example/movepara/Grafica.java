package com.example.movepara;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

import static java.lang.Thread.*;

public class Grafica extends AppCompatActivity {


    private TextView coorX;
    private TextView coorY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafica);
        LineGraphSeries<DataPoint> series;

        ArrayList<String> coordenadaX = getIntent().getStringArrayListExtra("coorX");
        ArrayList<String> coordenadaY = getIntent().getStringArrayListExtra("coorY");
        String distanciaX = getIntent().getStringExtra("alcance");

        coordenadaX.add(distanciaX);
        coordenadaY.add("0");

        //double maxX =  Double.parseDouble(distanciaX);

        for (int x = 0; x < coordenadaX.size(); x++) {
            Log.i("", "distancia X=  " + coordenadaX.get(x));
            Log.i("", "distancia Y=  " + coordenadaY.get(x));
        }

        GraphView graph = (GraphView) findViewById(R.id.graph);

        series = new LineGraphSeries<DataPoint>();

        for (int i = 0 ;i<coordenadaX.size(); i++){
            series.appendData(new DataPoint(Double.parseDouble(coordenadaX.get(i)),Double.parseDouble(coordenadaY.get(i))),true,500);
        }

        graph.addSeries(series);

        coorX = findViewById(R.id.txtCoorX);
        coorY = findViewById(R.id.txtCoorY);

        coorX.setText(coordenadaX.get(coordenadaX.size()-1));
        coorY.setText(coordenadaY.get(coordenadaY.size()-1));

    }

    public void regresar(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

    }
    //funcion para mostrar posteriormente los datos
    public void actualizar(ArrayList<String> coordenadaX, ArrayList<String> coordenadaY) {

        TextView coorX;
        TextView coorY;

        coorX = findViewById(R.id.txtCoorX);
        coorY = findViewById(R.id.txtCoorY);

        for (int i = 0; i < coordenadaX.size(); i++) {
            coorX.setText(coordenadaX.get(i));
            coorY.setText(coordenadaY.get(i));
        }
    }


}
