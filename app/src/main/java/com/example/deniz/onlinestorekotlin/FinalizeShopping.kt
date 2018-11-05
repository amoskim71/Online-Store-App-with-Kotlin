package com.example.deniz.onlinestorekotlin

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.paypal.android.sdk.payments.PayPalConfiguration
import com.paypal.android.sdk.payments.PayPalPayment
import com.paypal.android.sdk.payments.PayPalService
import com.paypal.android.sdk.payments.PaymentActivity
import kotlinx.android.synthetic.main.activity_finalize_shopping.*
import java.math.BigDecimal

class FinalizeShopping : AppCompatActivity() {

    var ttPrice: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finalize_shopping)

        var totalPriceURL = "http://192.168.10.103/OnlineStroreApp/calculate_total_price.php?invoice_num=${intent.getStringExtra("LATEST_INVOICE_NUMBER")}"
        var requestQ = Volley.newRequestQueue(this@FinalizeShopping)
        var stringRequest = StringRequest(Request.Method.GET, totalPriceURL, Response.Listener
        { response ->
            txtTotal.text = "TOTAL: Ð$response"
            ttPrice = response.toDouble()
        }, Response.ErrorListener { error ->

            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Message")
            dialogBuilder.setMessage(error.message)
            dialogBuilder.create().show()
        })
        requestQ.add(stringRequest)

        var paypalConfig: PayPalConfiguration = PayPalConfiguration()
                                                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                                                .clientId(MyPayPal.clientID)

        var ppService = Intent(this@FinalizeShopping, PayPalService::class.java)
        ppService.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig)
        startService(ppService)

        btnPaymentProcessing.setOnClickListener {

            var ppProcessing = PayPalPayment(BigDecimal.valueOf(ttPrice), "USD", "Online Store Kotlin", PayPalPayment.PAYMENT_INTENT_SALE)

            var paypalPaymentIntent = Intent(this, PaymentActivity::class.java)
            paypalPaymentIntent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig)
            paypalPaymentIntent.putExtra(PaymentActivity.EXTRA_PAYMENT, ppProcessing)
            startActivityForResult(paypalPaymentIntent, 1000)

        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1000){

            if(resultCode == Activity.RESULT_OK){

                var intent = Intent(this, ThankYou::class.java)
                startActivity(intent)

            } else{

                Toast.makeText(this, "Sorry Something went Wrong", Toast.LENGTH_LONG).show()
            }

        }


    }
    //for block to leaking cuz of paypal, u need to destroy end of to activity
    override fun onDestroy() {
        super.onDestroy()

        stopService(Intent(this, PayPalService::class.java))

    }
}
