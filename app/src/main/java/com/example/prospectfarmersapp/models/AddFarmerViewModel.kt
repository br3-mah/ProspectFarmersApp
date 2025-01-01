import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prospectfarmersapp.api.RetrofitInstance
import com.example.prospectfarmersapp.models.AddFarmerRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddFarmerViewModel : ViewModel() {
    private val _isSubmitting = MutableStateFlow(false)
    val isSubmitting: StateFlow<Boolean> get() = _isSubmitting

    fun submitFarmer(
        context: Context,
        farmerRequest: AddFarmerRequest,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            _isSubmitting.value = true
            try {
                val response = RetrofitInstance.apiService.addFarmer(farmerRequest)
                if (response.isSuccessful) {
                    Toast.makeText(context, "Farmer added successfully!", Toast.LENGTH_SHORT).show()
                    onSuccess()
                } else {
                    Toast.makeText(context, "Failed to add farmer. Try again!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                _isSubmitting.value = false
            }
        }
    }
}
