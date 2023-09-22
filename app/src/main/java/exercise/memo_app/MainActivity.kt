package exercise.memo_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import exercise.memo_app.AddMemo.AddMeomoActivity
import exercise.memo_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.mainRecyclerView


        binding.addButton.setOnClickListener {
            // 새로운 액티비티로 넘어간다 ( 할 일 추가 액티비티 )
            val intent = Intent(this, AddMeomoActivity::class.java)
            startActivity(intent)

        }







    }
}