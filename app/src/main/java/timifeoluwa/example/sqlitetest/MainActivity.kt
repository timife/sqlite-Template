package timifeoluwa.example.sqlitetest

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
//database object to execute the database class and strings for me
        val database = baseContext.openOrCreateDatabase("sqlite-test-1.db",MODE_PRIVATE, null)
        database.execSQL("DROP TABLE IF EXISTS contacts")

        var sql = "CREATE TABLE IF NOT EXISTS contacts(_id INTEGER PRIMARY KEY NOT NULL, name TEXT, phone INTEGER, email TEXT)"
        database.execSQL(sql)
        Log.d(TAG, "onCreate: sql is $sql")

        sql = "INSERT INTO contacts(name, phone, email) VALUES('timife',67489483, 'timife007@gmail.com')"
        Log.d(TAG, "onCreate: sql is $sql")
        database.execSQL(sql) //the method to execute the SQL in the database is the execSQL,
        // Execute a single SQL statement that is NOT a SELECT or any other SQL statement that returns data.

        val values = ContentValues().apply {
            put("name", "Fred")
            put("phone",1324535)
            put("email","fred@gmail.com")
        }
        val generatedId = database.insert("contacts",null,values)  //convenience method of adding row to a database.this is for sending data to the database.

        val query = database.rawQuery("SELECT * FROM contacts", null)  //retrieving the data stored in the database.cursor allows to access individual records in the
        //the database.c
        query.use {
            while(it.moveToNext()){             //query is a cursor and i'm asking it to move to first row.
                //Cycle through all records
                with(it){
                    val id = getLong(0)
                    val name = getString(1)
                    val phone = getInt(2)
                    val email = getString(3)
                    val result = "ID: $id.  Name: $name phone: $phone email: $email"
                    Log.d(TAG,"onCreate: reading data $result")
                    //reading values for the first record
                }
            }
        }
//        database.close()  //this shouldn't be, database is always still running while the app is running only closes when the app is closed.

        Log.d(TAG, "onCreate: record added with id $generatedId")

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
