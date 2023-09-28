package exercise.memo_app.AddMemo

data class MemoModel(
    val title: String,
    val content: String,
    val currentTime: String,
    val positionkey: Int,

    ){
    constructor() : this("","","",0)
}