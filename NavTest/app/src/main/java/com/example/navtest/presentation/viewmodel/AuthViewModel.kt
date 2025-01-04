import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel : ViewModel() {

    fun getUserId(): LiveData<String?> = liveData {
        Log.d("AuthDebug", "Tentando obter o User ID...")
        val user = FirebaseAuth.getInstance().currentUser
        Log.d("AuthDebug", "User ID: ${user?.uid ?: "Não autenticado"}")
        emit(user?.uid) // Emite o valor do userId ou null
    }
}
