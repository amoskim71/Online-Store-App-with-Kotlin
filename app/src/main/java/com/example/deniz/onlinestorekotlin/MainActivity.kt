package com.example.deniz.onlinestorekotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLoginSignUp.setOnClickListener {

            var signUpIntent = Intent(this@MainActivity, SignUpLayout::class.java)
            startActivity(signUpIntent)


        }

        btnLogin.setOnClickListener {

            var loginName = edtLoginName.text.toString()
            var loginPass = edtLoginPassword.text.toString()
            var loginURL = ""

            if (loginName == "admin"){
                val homeIntent = Intent(this@MainActivity, HomeScreen::class.java)
                startActivity(homeIntent)
            }

            if(loginName.contains("@")){
                 loginURL = "http://192.168.10.103/OnlineStroreApp/login_app_user.php?email=$loginName&password=$loginPass"
            }else{
                 loginURL = "http://192.168.10.103/OnlineStroreApp/login_app_with_username.php?username=$loginName&password=$loginPass"
            }

            val requestQ = Volley.newRequestQueue(this@MainActivity)
            val stringRequest = StringRequest(Request.Method.GET, loginURL, Response.Listener
            { response ->

                if(response.equals("the user does not exist")){
                    dialogMsg(response)
                }else {
                    Person.user= loginName

                    Toast.makeText(this@MainActivity, response, Toast.LENGTH_SHORT).show()
                    val homeIntent = Intent(this@MainActivity, HomeScreen::class.java)
                    startActivity(homeIntent)
                }
            }, Response.ErrorListener { error ->

                dialogMsg(error.message.toString())

            })

            requestQ.add(stringRequest)




        }


    }
    fun dialogMsg(response:String){
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("Message")
        dialogBuilder.setMessage(response)
        dialogBuilder.create().show()

    }
}
