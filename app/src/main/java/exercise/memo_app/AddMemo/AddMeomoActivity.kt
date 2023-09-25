package exercise.memo_app.AddMemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import exercise.memo_app.DBkeys.Companion.DB_MEMO
import exercise.memo_app.databinding.ActivityAddmemoBinding
import java.text.SimpleDateFormat

class AddMeomoActivity : AppCompatActivity() {

    private val memoDB : DatabaseReference = Firebase.database.reference.child(DB_MEMO)

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
            val currentTimeString = format.format(date).toString()
            val keyOfCurrentTime = System.currentTimeMillis()


            //todo key값 보내기




            val model = MemoModel(todoEditText,contentEditText,currentTimeString,keyOfCurrentTime)
            memoDB.push().setValue(model)

            finish()
        }

    }
}