package exercise.memo_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

    private val contentKeyList = mutableListOf<String>()


    private val listener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

            // 이 함수는 push로 넣었을 때 생기는 고유 key 값을 타고 들어가는 함수인데
            // 그 key 값이 없으면 오류가 난다
            // 즉, 데이터를 push로 넣지 않고 setValue로만 넣었을 때 오류가 남
            // 그리고 여기는 for문이 없어도 루프를 알아서 돈다 ! 유의하기

            val memoModelFromDB = snapshot.getValue(MemoModel::class.java)
            //Log.d("getValueA",snapshot.key.toString())

            // 1. 첫 화면에서 contentKeyList에 데이터의 고유 key를 모두 넣는다
            // 그럼 아예 처음부터 content가 key값을 갖게 해야 하나
            // 고유키값은 snapshot.key로 찾을 수 있는데
            // todo, 그 키랑 아이템의 position을 연결시킬 방법이 없음
            // 근데 애초에 글을 등록할 때의 키값을 가져올 수 잇나???? 안됨 snapshot이 없어
            // 2. 그 키로 DetailActivity를 그린다
            // 3. editactivity에도 똑같이 키를 넘겨서 수정할때 그 키에 해당하는 데이터에 child,setvalue하기


            contentKeyList.add(snapshot.key.toString())
            memoModelFromDB ?: return

            Log.d("FEEEE",memoModelFromDB.toString())
            Log.d("@@@DD",contentKeyList.toString())


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

            val intent = Intent(this,MemoDetailActivity::class.java)
            intent.putExtra("positionKey",MemoModel.positionkey)
            intent.putExtra("snapshotKey",contentKeyList[MemoModel.positionkey])

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

    override fun onDestroy() {
        super.onDestroy()
        memoDB.removeEventListener(listener)
    }


}