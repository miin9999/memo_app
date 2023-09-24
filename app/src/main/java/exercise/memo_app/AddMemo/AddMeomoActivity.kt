package exercise.memo_app.AddMemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Database
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import exercise.memo_app.DBkeys.Companion.DB_MEMO
import exercise.memo_app.databinding.ActivityAddmemoBinding

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
            val todoEditText = binding.todoEditText.text.toString()
            val contentEditText = binding.todoContentEditText.text.toString()


            // 마지막 인자엔 현재 시각을 key로 전달함
            val model = MemoModel(todoEditText,contentEditText)
            memoDB.push().setValue(model)

            finish()
        }

    }
}