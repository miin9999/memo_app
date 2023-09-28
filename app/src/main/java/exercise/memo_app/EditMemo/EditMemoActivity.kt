package exercise.memo_app.EditMemo

import android.os.Bundle
import android.renderscript.Sampler.Value
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import exercise.memo_app.AddMemo.MemoModel
import exercise.memo_app.DBkeys.Companion.DB_MEMO
import exercise.memo_app.databinding.ActivityEditMemoBinding

class EditMemoActivity : AppCompatActivity() {


    private lateinit var memoDB : DatabaseReference
    private lateinit var binding : ActivityEditMemoBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditMemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val key = intent.getLongExtra("key",-1)

        memoDB = Firebase.database.reference.child(DB_MEMO)

        memoDB.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                // key값으로 대조해서 맞는 데이터만 가져오기
                for(i in snapshot.children){
                    val model = i.getValue(MemoModel::class.java)
                    if(key.equals(model!!.key)){
                        //Log.d("fasdfasd",model.title)
                        // editTextView는 setText로만 설정가능, .text는 불가능
                        binding.titleEditTextOfEditPage.setText(model.title)
                        binding.contentEditTextOfEditPage.setText(model.content)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })


        binding.editCompleteButton.setOnClickListener {
            // 완료 버튼을 누르면 새 정보를 DB에 덮어쓰고

            memoDB.setValue(MemoModel(binding.titleEditTextOfEditPage.text.toString(),
                binding.contentEditTextOfEditPage.text.toString(),"kkk",key))
             finish()



        }

    }
}