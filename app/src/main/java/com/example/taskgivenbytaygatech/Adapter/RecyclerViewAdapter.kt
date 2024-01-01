package com.example.taskgivenbytaygatech.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskgivenbytaygatech.R
import com.example.taskgivenbytaygatech.Room.PeopleEntity

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    private var personsList: MutableList<PeopleEntity> = mutableListOf()

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var personName: TextView = view.findViewById(R.id.textName)
        var personSurname: TextView = view.findViewById(R.id.textSurname)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val peopleListView =
            LayoutInflater.from(parent.context).inflate(R.layout.person_details, parent, false)
        return MyViewHolder(peopleListView)

    }

    override fun getItemCount(): Int {
        return personsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentPerson = personsList[position]
        holder.personName.text = currentPerson.name
        holder.personSurname.text = currentPerson.surName

    }

    fun setPersons(persons: MutableList<PeopleEntity>) {
        personsList = persons
        notifyDataSetChanged()
    }

    fun filterByCities(newList: List<PeopleEntity>) {
        personsList.clear()
        personsList.addAll(newList)
        notifyDataSetChanged()
    }
}