package com.example.deniz.onlinestorekotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_cart_products.*

class CartProductsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_products)


        var cartProductsURL = "http://192.168.10.103/OnlineStroreApp/fetch_temporary_order.php?user=${Person.user}"
        var cartProductsList = ArrayList<String>()
        var requestQ = Volley.newRequestQueue(this@CartProductsActivity)
        var jsonAR = JsonArrayRequest(Request.Method.GET, cartProductsURL, null, Response.Listener
            { response ->
                for (joIndex in 0.until(response.length())){
                    //id, name, price, user, amount
                    cartProductsList.add("Product ID: ${response.getJSONObject(joIndex).getInt("id")}\n" +
                                             "Product Name: ${response.getJSONObject(joIndex).getString("name")}" +
                                                "\nProduct Price: Ð${response.getJSONObject(joIndex).getDouble("price")}" +
                                                    "\nBuyer: ${response.getJSONObject(joIndex).getString("user")}" +
                                                        "\nAmount: ${response.getJSONObject(joIndex).getInt("amount")}")
                }
                var cartProductsAdapter = ArrayAdapter(this@CartProductsActivity, android.R.layout.simple_list_item_1,cartProductsList)
                cartProducstListView.adapter = cartProductsAdapter


            }, Response.ErrorListener { error ->

            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Message")
            dialogBuilder.setMessage(error.message)
            dialogBuilder.create().show()
        })

        requestQ.add(jsonAR)



        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean{

        menuInflater.inflate(R.menu.cart_menu, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item?.itemId == R.id.continueShoppingItem){

            var intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)

        }else if(item?.itemId == R.id.declineOrderItem){

            var declineURL = "http://192.168.10.103/OnlineStroreApp/decline_order.php?user=${Person.user}"
            var requestQ = Volley.newRequestQueue(this@CartProductsActivity)
            var stringRequest = StringRequest(Request.Method.GET, declineURL, Response.Listener
            { response ->

                Toast.makeText(this@CartProductsActivity, "order(s) canceled", Toast.LENGTH_SHORT).show()
                var intent = Intent(this, CartProductsActivity::class.java)
                startActivity(intent)

            },Response.ErrorListener { error ->
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("Message")
                dialogBuilder.setMessage(error.message)
                dialogBuilder.create().show()
            })
            requestQ.add(stringRequest)

        }else if(item?.itemId == R.id.verifyOrderItem){

            var verifyOrderURL="http://192.168.10.103/OnlineStroreApp/verify_order.php?user=${Person.user}"
            var requestQ = Volley.newRequestQueue(this@CartProductsActivity)
            var stringRequest = StringRequest(Request.Method.GET, verifyOrderURL, Response.Listener
            {response ->

                var intent = Intent(this, FinalizeShopping::class.java)
                Toast.makeText(this, response, Toast.LENGTH_LONG).show()
                intent.putExtra("LATEST_INVOICE_NUMBER", response)
                startActivity(intent)



            },
                    Response.ErrorListener { error -> val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("Message")
                dialogBuilder.setMessage(error.message)
                dialogBuilder.create().show() })
            requestQ.add(stringRequest)



        }

        return super.onOptionsItemSelected(item)
    }
}
