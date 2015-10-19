package es.upm.miw.contactos;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static android.provider.ContactsContract.Contacts;

public class MostrarContactos extends AppCompatActivity {

    ListView listView;
    ArrayList<String> contactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mostrar_contactos);

        listView = (ListView) findViewById(R.id.listadoContactos);

        // TO DO cargar lista de contactos
        contactos = cargarDatos();

        // TO DO crear adaptador y enchufarlo al listView
        ArrayAdapter adaptador = new ArrayAdapter(this, R.layout.layout_contacto, contactos);
        listView.setAdapter(adaptador);
    }

    /**
     * Crea una lista con los nombres de los contactos
     * @return Lista de contactos
     */
    public ArrayList<String> cargarDatos() {
        ArrayList<String> datos = new ArrayList<>();

        // Obtener un cursor al proveedor de contactos y recuperar todos
        Uri URI_Contactos = Contacts.CONTENT_URI;  // "content://com.android.contacts/contacts"
        ContentResolver cr = getContentResolver();
        String ordenacion = Contacts.DISPLAY_NAME_PRIMARY + " ASC";     // display_name ASC
        Cursor cursor = cr.query(URI_Contactos, null, null, null, ordenacion);

        // Si hay datos -> cargar en la lista
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                datos.add(cursor.getString(cursor.getColumnIndex(Contacts.DISPLAY_NAME_PRIMARY)));
                Log.i("Contacto", cursor.getString(cursor.getColumnIndex(Contacts.DISPLAY_NAME_PRIMARY)));
                cursor.moveToNext();
            }
            cursor.close();  // liberar recursos
        }

        return datos;
    }
}
