package com.example.jsonpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var numberEditText: EditText
    private lateinit var showBtn: Button
    lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        numberEditText = findViewById(R.id.editTextTextPersonName)
        showBtn = findViewById(R.id.button)
        textView = findViewById(R.id.textView)

        showBtn.setOnClickListener {
            val userEnter = numberEditText.text.toString().toInt()
            if(userEnter > 13 || userEnter < 1){
                Toast.makeText(applicationContext, "Please enter a correct number", Toast.LENGTH_SHORT).show()
            }else{
                val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
                apiInterface?.getUser()?.enqueue(object: Callback<Array<Users>>{
                    override fun onResponse(
                        call: Call<Array<Users>>,
                        response: Response<Array<Users>>
                    ) {
                        var counter = 1
                        for(i in response.body()!!){
                            if(counter == userEnter){
                                textView.text = i.name
                            }
                            counter++
                        }
                    }

                    override fun onFailure(call: Call<Array<Users>>, t: Throwable) {
                        Toast.makeText(applicationContext, "Error!", Toast.LENGTH_SHORT).show()

                    }
                })
            }
        }
    }
}