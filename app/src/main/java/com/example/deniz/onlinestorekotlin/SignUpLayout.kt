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
import kotlinx.android.synthetic.main.activity_sign_up_layout.*

class SignUpLayout : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_layout)

        btnSignUp.setOnClickListener {


            //Registration Process password
            if(edtSignPassword.text.toString().equals(edtSignPasswordCheck.text.toString())){

                if(edtSignPassword.text.length >= 8){
                    //192.168.10.103
                    var signEmail = edtSignEmail.text.toString()
                    var signUsername = edtSignUsername.text.toString()
                    var signPassword = edtSignPassword.text.toString()

                    val signUpURL = "http://192.168.10.103/OnlineStroreApp/join_new_user.php?email=$signEmail&username=$signUsername&password=$signPassword"
                    val requestQ = Volley.newRequestQueue(this@SignUpLayout)
                    val stringRequest = StringRequest(Request.Method.GET, signUpURL, Response.Listener
                    {response ->
                        if(response.equals("a user with this email already exists")){

                            dialogMsg(response)

                        }else {
                            Person.user = signEmail

                            Toast.makeText(this@SignUpLayout, response, Toast.LENGTH_SHORT).show()

                            val homeIntent = Intent(this@SignUpLayout, HomeScreen::class.java)
                            startActivity(homeIntent)
                        }

                    }, Response.ErrorListener { error ->

                        dialogMsg(error.message.toString())

                    })
                    requestQ.add(stringRequest)

//                    var signUpIntent = Intent(this@SignUpLayout, MainActivity::class.java)
//                    startActivity(signUpIntent)

                }else{
                    //short pass
                    dialogMsg("Short Password")

                }
                //192.168.10.103

            }else{
                //wrong pass
                dialogMsg("Password Mismatch")

            }

        }

        btnBacktoLogin.setOnClickListener {

            finish()


        }



    }

    fun dialogMsg(response:String){
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("Message")
        dialogBuilder.setMessage(response)
        dialogBuilder.create().show()

    }
}
