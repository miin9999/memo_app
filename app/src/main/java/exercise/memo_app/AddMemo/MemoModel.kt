package exercise.memo_app.AddMemo

data class MemoModel(
    val title: String,
    val content: String,
    val currentTime: String,
    val key: Long,

    ){
    constructor() : this("","","",0)
}