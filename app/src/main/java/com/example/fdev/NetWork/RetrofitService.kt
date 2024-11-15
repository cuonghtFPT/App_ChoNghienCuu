import com.example.fdev.NetWork.ApiService

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class RetrofitService {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3001/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val fdevApiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
