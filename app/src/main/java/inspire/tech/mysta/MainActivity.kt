package inspire.tech.mysta

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.mysta_categories.view.*

class MainActivity : AppCompatActivity() {

    var adapter: CategoryAdapter? = null
    var categoryList = ArrayList<Category>()
    lateinit var mAdView: AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MobileAds.initialize(this)
        //MobileAds.initialize(this, "ca-app-pub-5827352993183469~6819966789");
        setSupportActionBar(toolbar)
        MobileAds.initialize(this)
        mAdView = findViewById(R.id.adView_maiactivity)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        val database = Firebase.database
        val myRef = database.getReference("message")
        myRef.setValue("Hello, World!")


        //loading categories
        categoryList.add(Category("Relationships", R.drawable.img_relationships))
        categoryList.add(Category("Love", R.drawable.img_love))
        categoryList.add(Category("Goodness", R.drawable.img_goodness))
        categoryList.add(Category("Finances", R.drawable.img_finances))
        categoryList.add(Category("Spirtuality", R.drawable.img_spirtuality))
        categoryList.add(Category("Life", R.drawable.img_life))

        adapter = CategoryAdapter(this, categoryList)
        gv_Categories.adapter = adapter
        gv_Categories.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // Get the GridView selected/clicked item text
                val selectedItem = parent.getItemAtPosition(position).toString()

                val intent =
                    Intent(this@MainActivity, CategoryDetailActivity::class.java)
                startActivity(intent)

                val t = Toast.makeText(this@MainActivity, "Hello+$selectedItem", Toast.LENGTH_LONG)
                t.show()

            }
        }


        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue<String>()
                Log.d("TAG", "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })

    }

/*    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                return true
            }

            else -> super.onOptionsItemSelected(item)

        }
    }*/

    class CategoryAdapter : BaseAdapter {
        var categoryList = ArrayList<Category>()
        var context: Context? = null

        constructor(context: Context, categoryList: ArrayList<Category>) : super() {
            this.context = context
            this.categoryList = categoryList

        }


        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val category = this.categoryList[position]
            var inflator =
                context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var categoryView = inflator.inflate(R.layout.mysta_categories, null)
            categoryView.imgCategory.setImageResource(category.image!!)
            categoryView.tvCategoryName.text = category.name!!
            categoryView.imgCategory.setOnClickListener {
                val intent = Intent(context, CategoryDetailActivity::class.java)
                intent.putExtra("Name", category.name!!)
                intent.putExtra("Image", category.image!!)
                context!!.startActivity(intent)

            }

            return categoryView
        }

        override fun getItem(position: Int): Any {
            return categoryList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return categoryList.size
        }


    }
}

