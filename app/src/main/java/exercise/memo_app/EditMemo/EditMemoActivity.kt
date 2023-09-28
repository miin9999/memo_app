package exercise.memo_app.EditMemo

import android.icu.text.SimpleDateFormat
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

        val positionKey = intent.getIntExtra("positionKey",-1)
        val snapshotKey = intent.getStringExtra("snapshotKey")


        val format = SimpleDateFormat("yyyy.MM.dd a HH:mm:ss")
        val editCurrentTime =format.format(System.currentTimeMillis()).toString()


        memoDB = Firebase.database.getReference(DB_MEMO)

        memoDB.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                // key값으로 대조해서 맞는 데이터만 가져오기
                for(i in snapshot.children){

                    val model = i.getValue(MemoModel::class.java)

                    if(positionKey==(model!!.positionkey)){
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

            Log.d("dvdvdvd",snapshotKey.toString())
            memoDB.child("$snapshotKey").setValue(MemoModel(binding.titleEditTextOfEditPage.text.toString(),
                binding.contentEditTextOfEditPage.text.toString(),editCurrentTime,positionKey))
             finish()



        }

    }
}