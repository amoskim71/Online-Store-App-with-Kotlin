package com.example.deniz.onlinestorekotlin


import android.app.DialogFragment
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.util.logging.Logger


class AmountFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {



        // Inflate the layout for this fragment
        var fragmentView = inflater.inflate(R.layout.fragment_amount, container, false)

        var edtEnterAmount = fragmentView.findViewById<EditText>(R.id.edtEnterAmount)
        var btnAddToCart = fragmentView.findViewById<Button>(R.id.btnAddToCart)

        btnAddToCart.setOnClickListener {

            var orderHolderURL = "http://192.168.10.103/OnlineStroreApp/insert_temporary_order.php?email=${Person.user}&product_id=${Person.addToCartProductID}&amount=${edtEnterAmount.text.toString()}"
            var requestQ = Volley.newRequestQueue(activity)
            var stringRequest = StringRequest(Request.Method.GET, orderHolderURL, Response.Listener{ response ->
                var intent = Intent(activity, CartProductsActivity::class.java)
                startActivity(intent)

            }, Response.ErrorListener { error ->

                val dialogBuilder = AlertDialog.Builder(activity)
                dialogBuilder.setTitle("Message")
                dialogBuilder.setMessage(error.message)
                dialogBuilder.create().show()
            })

            requestQ.add(stringRequest)

        }



        return fragmentView

    }


}
