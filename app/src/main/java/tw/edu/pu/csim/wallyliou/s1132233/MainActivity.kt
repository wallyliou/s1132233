package tw.edu.pu.csim.wallyliou.s1132233

import ExamScreen
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.window.layout.WindowMetricsCalculator // 記得要有這個 import
import tw.edu.pu.csim.wallyliou.s1132233.ui.theme.S1132233Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. 設定全螢幕與邊緣延伸
        enableEdgeToEdge()

        // 2. 強迫直式螢幕
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        // 3. 隱藏狀態列
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.hide(WindowInsetsCompat.Type.statusBars())

        // 4. 計算螢幕像素 (使用您指定的程式碼)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val windowMetricsCalculator = WindowMetricsCalculator.getOrCreate()
        val currentWindowMetrics = windowMetricsCalculator.computeCurrentWindowMetrics(this)
        val bounds = currentWindowMetrics.bounds

        val screenWidthPx = bounds.width().toFloat()
        val screenHeightPx = bounds.height().toFloat()

        setContent {
            S1132233Theme {
                // 將計算好的寬高傳進去
                ExamScreen(
                    screenWidth = screenWidthPx,
                    screenHeight = screenHeightPx
                )
            }
        }
    }
}