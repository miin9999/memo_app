package exercise.memo_app.EditMemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import exercise.memo_app.databinding.ActivityEditMemoBinding

class EditMemoActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEditMemoBinding.inflate(layoutInflater)
        setContentView(binding.root)






    }
}