package com.example.deniz.onlinestorekotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_fetch_eproducts.*

class FetchEProductsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetch_eproducts)

        val selectedBrand: String = intent.getStringExtra("BRAND")
        txtBrandName.text =  "Products of $selectedBrand :"


        var productsList = ArrayList<Eproducts>()

        val productsURL = "http://192.168.10.103/OnlineStroreApp/fetch_products.php?brand=$selectedBrand"
        val requestQ = Volley.newRequestQueue(this@FetchEProductsActivity)
        val jsonAR = JsonArrayRequest(Request.Method.GET, productsURL, null, Response.Listener { response ->

            for(productIndex in 0.until(response.length())){

                productsList.add(Eproducts(response.getJSONObject(productIndex).getInt("id"),
                        response.getJSONObject(productIndex).getString("name"),
                        response.getJSONObject(productIndex).getDouble("price"),
                        response.getJSONObject(productIndex).getString("picture")))

            }

            val pAdapter = EProductAdapter(this@FetchEProductsActivity, productsList)
            productsRV.layoutManager = LinearLayoutManager(this@FetchEProductsActivity)
            productsRV.adapter = pAdapter

        }, Response.ErrorListener { error ->

            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Message")
            dialogBuilder.setMessage(error.message)
            dialogBuilder.create().show()


        })
        requestQ.add(jsonAR)
    }
}
