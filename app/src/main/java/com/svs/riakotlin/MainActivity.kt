package com.svs.riakotlin

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.svs.riakotlin.adapter.ListAdapter
import com.svs.riakotlin.models.DataMod
import com.svs.riakotlin.models.PostData
import com.svs.riakotlin.network.APIExecutor
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var listAdapter: ListAdapter
    var dataList : ArrayList<DataMod> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialize()
        getData()
    }

    private fun initialize(){
        fab.setOnClickListener {
            showAddPostPopup()
        }
        initializeRcView()
    }

    private fun initializeRcView(){
        val layoutManager = LinearLayoutManager(applicationContext)
        rcViewList.layoutManager = layoutManager
        listAdapter = ListAdapter(dataList, applicationContext) { item: DataMod, position: Int ->
            Unit
            onItemClclicked(item, position)
        }
        rcViewList.adapter=listAdapter
    }
    private fun onItemClclicked(item: DataMod, position: Int) {
       
    }

    private fun getData(){
            showLoading(true)
            GlobalScope.launch(Dispatchers.Main) {
                val response = APIExecutor.getApiService().getData()
                if(response.isSuccessful){
                        var list = ArrayList(response.body())
                    Log.e("model13223", "${list}")
                    listAdapter.updateList(list)
                    showLoading(false)
                }
            }
    }

    private fun postData(dataMod: PostData){
        showLoading(true)
        GlobalScope.launch(Dispatchers.Main) {
            val response = APIExecutor.getApiService().postData(dataMod)
            if(response.isSuccessful){
                getData()
            }
        }
    }

    private fun showAddPostPopup(){

        val dialog = Dialog(this@MainActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setContentView(R.layout.add_post)
        val etTitle = dialog.findViewById(R.id.etTitle) as EditText
        val etDesc = dialog.findViewById(R.id.etDesc) as EditText
        val buttonPost = dialog.findViewById(R.id.buttonPost) as Button

        buttonPost.setOnClickListener {
            val title = etTitle.text.toString()
            val desc = etDesc.text.toString()
            val postData = PostData(title,desc)
            postData(postData)
            dialog.dismiss()
        }
        dialog.show()
    }

    fun showLoading(stat:Boolean){
        if(stat){
            loadingLayout.visibility= View.VISIBLE
            contentLayout.visibility=View.GONE
        }else{
            loadingLayout.visibility= View.GONE
            contentLayout.visibility=View.VISIBLE
        }
    }
}