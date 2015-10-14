package es.upm.miw.contactos;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> contactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listadoContactos);

        // cargamos la lista de contactos
        contactos = cargarDatos();

        // creamos el adaptador y se lo asignamos al lisvView
        ArrayAdapter adaptador = new ArrayAdapter(this, R.layout.contacto, contactos);
        listView.setAdapter(adaptador);
    }

    /**
     * Carga los nombres de los contactos en una lista
     * @return Lista de contactos
     */
    public ArrayList<String> cargarDatos() {
        ArrayList<String> datos = new ArrayList<>();

        // obtenemos un cursor al proveedor de contactos y recuperamos todos
        ContentResolver cr = getContentResolver();
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " ASC";
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, sortOrder);

        // Si hay datos -> se cargan en la lista
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Log.i("Datos", cursor.getString(cursor.getColumnIndex("display_name")));
                datos.add(cursor.getString(cursor.getColumnIndex("display_name")));
                cursor.moveToNext();
            }
            cursor.close();  // liberar recursos al terminar
        }
        return datos;
    }
}
