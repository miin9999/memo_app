package exercise.memo_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import exercise.memo_app.AddMemo.AddMeomoActivity
import exercise.memo_app.AddMemo.ItemAdapter
import exercise.memo_app.AddMemo.MemoModel
import exercise.memo_app.DBkeys.Companion.DB_MEMO
import exercise.memo_app.MemoDetail.MemoDetailActivity
import exercise.memo_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var memoDB: DatabaseReference
    private val memoList = mutableListOf<MemoModel>()
    private lateinit var memoAdapter : ItemAdapter


    private val listener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

            // DB에 데이터가 없을 시 여기서 오류가 남, 안 날 때도 있네???? 머지

            val memoModelFromDB = snapshot.getValue(MemoModel::class.java)
            memoModelFromDB ?: return
            memoList.add(memoModelFromDB)
            memoAdapter.submitList(memoList)
            //memoAdapter.notifyDataSetChanged()


        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
        override fun onChildRemoved(snapshot: DataSnapshot) {}
        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
        override fun onCancelled(error: DatabaseError) {}

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.mainRecyclerView


        memoDB = Firebase.database.reference.child(DB_MEMO)
        binding.addButton.setOnClickListener {
            // 새로운 액티비티로 넘어간다 ( 할 일 추가 액티비티 )
            val intent = Intent(this, AddMeomoActivity::class.java)
            startActivity(intent)

        }

        memoAdapter = ItemAdapter ( onItemClicked = { MemoModel ->
            //TODO 메모들 중 하나를 눌렀을 때, 수정,삭제 버튼이 있는 액티비티로 넘어가기
            // 그 함수를 어댑터에 넘기는 것임

            // Memo 모델의 key 값을 detail 액티비로 넘겨서
            // 그 키와 대조하여 맞는 데이터를 db에서 가져오면 된다

            val intent = Intent(this,MemoDetailActivity::class.java)
            startActivity(intent)

        })


        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = memoAdapter

        memoDB.addChildEventListener(listener)



    }

    override fun onResume() {
        super.onResume()
        memoAdapter.notifyDataSetChanged()

        // 테스트 해보니 이 부분이 없으면 메모 추가 액티비티에서 '완료' 버튼을 누르고
        // 메인으로 돌아올 때 리스트가 갱신되지 않음
        // 새로운 acitivity를 finish 해주고 다시 돌아오는 것도 resume 판정인 것 같음

    }
}