package com.example.deniz.onlinestorekotlin

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.e_product_row.view.*

class EProductAdapter(var context: Context, var arrayList: ArrayList<Eproducts>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val productView = LayoutInflater.from(context).inflate(R.layout.e_product_row, parent, false)//it already attached to the recycling view,cuz of that we dont have to pass the value true

        return ProductViewHolder(productView)

    }

    override fun getItemCount(): Int {

        return arrayList.size

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as ProductViewHolder).initializeRow(arrayList.get(position).id,
                arrayList.get(position).name, arrayList.get(position).price, arrayList.get(position).picture)

    }


    inner class ProductViewHolder(pView : View): RecyclerView.ViewHolder(pView){

        fun initializeRow(id: Int, name: String, price: Double, picture: String){

            itemView.txtId.text = id.toString()
            itemView.txtName.text = name
            itemView.txtPrice.text = "Ð" + price.toString()

            var picURL = "http://192.168.10.103/OnlineStroreApp/osimages/"
            picURL = picURL.replace(" ","%20")
            Picasso.get().load(picURL + picture).into(itemView.imgProduct)


            itemView.imgCart.setOnClickListener {

                Person.addToCartProductID = id
                var amountFragment = AmountFragment()
                var fragmentManager = (itemView.context as Activity).fragmentManager
                amountFragment.show(fragmentManager, "TAG")

            }


        }

    }
}