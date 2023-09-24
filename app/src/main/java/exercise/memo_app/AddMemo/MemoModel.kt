package exercise.memo_app.AddMemo

data class MemoModel (
    val title : String,
    val content : String,

){
    constructor() : this("","")
}