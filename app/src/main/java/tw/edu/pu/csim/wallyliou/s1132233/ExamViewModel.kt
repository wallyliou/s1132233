package tw.edu.pu.csim.wallyliou.s1132233

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class ExamViewModel : ViewModel() {
    // 成績變數
    var score by mutableStateOf(0)
}