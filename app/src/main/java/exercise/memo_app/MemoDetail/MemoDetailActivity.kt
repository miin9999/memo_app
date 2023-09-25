package exercise.memo_app.MemoDetail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import exercise.memo_app.AddMemo.MemoModel
import exercise.memo_app.DBkeys.Companion.DB_MEMO
import exercise.memo_app.DBkeys.Companion.DB_TITLE
import exercise.memo_app.EditMemo.EditMemoActivity
import exercise.memo_app.R
import exercise.memo_app.databinding.ActivityMemoDetailBinding


class MemoDetailActivity : AppCompatActivity() {


    private lateinit var memoDB : DatabaseReference
    private lateinit var binding : ActivityMemoDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        getMemodata() // 저장된 메모 데이터를 가져옴

        val key = intent.getLongExtra("key",-1)

        binding.editButton.setOnClickListener {
            // 글 내에서의 수정 버튼, 누르면 eidtActivity로 이동
            val intent = Intent(this,EditMemoActivity::class.java)
            startActivity(intent)

        }


        // 애초에 add할때 넘어오는 key값을 받아서 child를 key값으로 경로 설정을 해서 데이터를 꺼내오는 방법이 나을 것 같음
        memoDB = Firebase.database.reference.child(DB_MEMO)



        memoDB.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                // i의 key값은 db의 push 고유키값으로 나옴
                for ( i in snapshot.children){
                    //Log.d("dsafsadf",i.getValue().toString())
                    val model = i.getValue(MemoModel::class.java)
                    if(key.equals(model!!.key)){
                        binding.titleTextViewOfDetailPage.text = model.title
                        binding.contentTextViewOfDetailPage.text = model.content
                        binding.currentTimetext.text = model.currentTime
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun getMemodata(){


    }

}