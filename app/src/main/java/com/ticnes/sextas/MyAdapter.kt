package com.ticnes.sextas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ticnes.sextas.data.User
import com.squareup.picasso.Picasso

class MyAdapter(private val userList : ArrayList<User>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_item,
            parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = userList[position]

        holder.loja.text = currentitem.loja
        holder.data_preço.text = currentitem.data_preço
        //.nome_fruta.text = currentitem.nome_fruta
        holder.preço.text = currentitem.preço
        holder.nome_item.text = currentitem.item_nome
        //holder.item_image_url.setImageResource(position) = currentitem.imageurl
        //holder.imageurl. = currentitem.imageurl

        // Use Glide to reference the image URL
        //Images will work with Glide
        /*Glide.with() // What's the correct parameter HERE ???
            .load(holder.preço)
            .into(holder.imagem)*/

        //Picasso to reference the image URL
        Picasso.get()
            //load("https://firebasestorage.googleapis.com/v0/b/cestabasicaangola.appspot.com/o/Productos%2FAnanás%20?alt=media&token=6e245d51-f3a1-4d9b-8b9d-2d720a4c1234")
            //.load(userList[position].item_image_url) Status: Does Not Work, like this !!!
            .load(currentitem.item_image_url)
            .into(holder.imageurl)
    }

    override fun getItemCount(): Int {

        return userList.size
    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        /*val nome_fruta: TextView = itemView.findViewById(R.id.nome)*/
        val data_preço : TextView = itemView.findViewById(R.id.textView4)
        val loja : TextView = itemView.findViewById(R.id.textView3)
        val preço : TextView = itemView.findViewById(R.id.textView1)
        val imageurl : ImageView = itemView.findViewById(R.id.imageView2)
        val nome_item : TextView = itemView.findViewById(R.id.textView2)




    }

}