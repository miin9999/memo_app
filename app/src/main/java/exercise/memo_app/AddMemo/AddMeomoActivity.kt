package exercise.memo_app.AddMemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Database
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import exercise.memo_app.DBkeys.Companion.DB_MEMO
import exercise.memo_app.databinding.ActivityAddmemoBinding
import java.lang.System.currentTimeMillis
import java.text.SimpleDateFormat
import java.util.Date

class AddMeomoActivity : AppCompatActivity() {

    private val memoDB : DatabaseReference by lazy{
        Firebase.database.reference.child(DB_MEMO)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAddmemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 두 개의 editText를 가져와서 db에 넣으면 됨
        binding.completeBtn.setOnClickListener {

            val format = SimpleDateFormat("yyyy.MM.dd a HH:mm:ss")
            val date = System.currentTimeMillis()

            val todoEditText = binding.todoEditText.text.toString()
            val contentEditText = binding.todoContentEditText.text.toString()
            val currentTime = format.format(date).toString()

            val model = MemoModel(todoEditText,contentEditText,currentTime)
            memoDB.push().setValue(model)

            finish()
        }

    }
}