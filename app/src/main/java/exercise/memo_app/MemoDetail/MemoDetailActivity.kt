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

        binding.editButton.setOnClickListener {
            // 글 내에서의 수정 버튼, 누르면 eidtActivity로 이동
            val intent = Intent(this,EditMemoActivity::class.java)
            startActivity(intent)

        }


        memoDB = Firebase.database.reference.child(DB_MEMO)

        memoDB.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                // DB에 데이터를 push()메서드로 넣었을 땐 children쓰면 되는 것 같음
                // memo db를 보면 key 값의 한 단계 아래에 저장되는 것을 볼 수 있다
                // db의 key값을 가져오는 방법도 있을텐데 아직 잘 모르겠
                snapshot.children.forEach {

                    val model = it.getValue(MemoModel::class.java)
                    model ?: return
                    // .text를 잊지 말기
                    binding.titleTextViewOfDetailPage.text = model.title
                    binding.contentTextViewOfDetailPage.text = model.content
                    binding.currentTimetext.text = model.currentTime

                    //Log.d("memomomo",model.title)
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })


    }

    private fun getMemodata(){

    }

}