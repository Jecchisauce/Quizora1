
import com.example.quizora.models.LoginRequest
import com.example.quizora.models.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("login.php")
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>
}
