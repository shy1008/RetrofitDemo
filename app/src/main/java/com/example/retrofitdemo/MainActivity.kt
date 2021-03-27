package com.example.retrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private lateinit var retService: AlbumService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textView)

        retService = RetrofitInstance.getRetrofitInstance().create(AlbumService::class.java)

//        getRequestWithPathParameters()
        getRequestWithQueryParameters()

    }

    private fun getRequestWithQueryParameters() {
        val responseLiveData: LiveData<Response<Album>> = liveData {
            val response: Response<Album> = retService.getSortedAlbums(3)
            emit(response)
        }

        responseLiveData.observe(this, Observer {
            val albumsList: MutableListIterator<AlbumItem>? = it.body()?.listIterator()
            if (albumsList != null) {
                while (albumsList.hasNext()) {
                    val albumsItem = albumsList.next()
                    val result = " " + "Album Title : ${albumsItem.title}" + "\n" +
                            " " + "Album id : ${albumsItem.id}" + "\n" +
                            " " + "user id : ${albumsItem.userId}" + "\n\n\n"
                    textView.append(result)
//                    Log.i("TAG", "${albumsItem.title}")
                }
            }
        })
    }

    private fun getRequestWithPathParameters() {
        //path parameter example
        val pathResponse: LiveData<Response<AlbumItem>> = liveData {
            val response = retService.getAlbums(3)
            emit(response)
        }

        pathResponse.observe(this, Observer {
            val title = it.body()?.title
            Toast.makeText(applicationContext, title, Toast.LENGTH_SHORT).show()
        })
    }
}