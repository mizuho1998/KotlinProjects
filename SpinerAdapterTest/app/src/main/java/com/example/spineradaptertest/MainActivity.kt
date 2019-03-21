package com.example.spineradaptertest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val TAG = "TEST_SPINNER"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val keyValueArray = arrayOf(Pair("key0", "value0"), Pair("key1", "value1"), Pair("key2", "value2"))
        val keyValueAdapter = KeyValueAdapter(this, android.R.layout.simple_spinner_item, keyValueArray)
        spinner.adapter = keyValueAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            //　アイテムが選択された時
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.d(TAG, "position: $position, id: $id")
                Log.d(TAG, "getId(): ${keyValueAdapter.getKey(position)}")
            }

            //　アイテムが選択されなかった
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


        val imageKeyValueArray = arrayOf("kraken0", "kraken1" ,"kraken2")
        val keyValueImageAdapter = KeyValueImageAdapter(this, R.layout.spinner_image_list, imageKeyValueArray)
        spinner_image.adapter = keyValueImageAdapter
        spinner_image.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            //　アイテムが選択された時
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.d(TAG, "position: $position, id: $id")
                Log.d(TAG, "getId(): ${keyValueImageAdapter.getKey(position)}")
            }

            //　アイテムが選択されなかった
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

}
