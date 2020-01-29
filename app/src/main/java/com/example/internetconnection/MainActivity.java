package com.example.internetconnection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private TextView mensajeria;
    private EditText pagina;
    private String conexion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mensajeria = findViewById(R.id.cajaMensaje);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void sacarTextoWeb(View view) {
        mensajeria = findViewById(R.id.cajaMensaje);
        pagina = findViewById(R.id.pagina);
        conexion = pagina.getText().toString();
        new web().execute();

    }
    private class web extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String mensaje = "";

            try {
                URL url = new URL(conexion);

                // read text returned by server
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

                String line = "";

                //requiredString = s.substring(s.indexOf("(") + 1, s.indexOf(")"));
                mensaje = "";
                while ((line = in.readLine()) != null) {
                mensaje+=line;
                }
                in.close();

            } catch (MalformedURLException e) {
                System.out.println("Malformed URL: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("I/O Error: " + e.getMessage());
            }

            String[] text;
            text=mensaje.split("<div id=\"paste\">");
            text=text[1].split("</div>");
            mensaje=text[0];
            String editor = mensaje.replace("<p>", "\n");
            mensaje = editor.replace("</p>", "");
            return mensaje;
        }
        protected void onPostExecute(String finalMessage){
            mensajeria.setText(finalMessage);
        }
    }


}

