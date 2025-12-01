package tw.edu.pu.csim.wallyliou.s1132233

import android.graphics.Rect
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay

@Composable
fun ExamScreen(
    viewModel: ExamViewModel = viewModel(),
    screenWidth: Float,
    screenHeight: Float
) {
    val context = LocalContext.current
    val density = LocalDensity.current

    // --- 1. 定義尺寸 ---
    val cornerIconSizePx = 300f
    val cornerIconSizeDp = with(density) { cornerIconSizePx.toDp() }
    val middleY = screenHeight / 2

    val fallingIconSizePx = 200f
    val fallingIconSizeDp = with(density) { fallingIconSizePx.toDp() }

    // --- 2. 建立四個角色的碰撞矩形 ---
    val role0Rect = Rect(0, (middleY - cornerIconSizePx).toInt(), cornerIconSizePx.toInt(), middleY.toInt())
    val role1Rect = Rect((screenWidth - cornerIconSizePx).toInt(), (middleY - cornerIconSizePx).toInt(), screenWidth.toInt(), middleY.toInt())
    val role2Rect = Rect(0, (screenHeight - cornerIconSizePx).toInt(), cornerIconSizePx.toInt(), screenHeight.toInt())
    val role3Rect = Rect((screenWidth - cornerIconSizePx).toInt(), (screenHeight - cornerIconSizePx).toInt(), screenWidth.toInt(), screenHeight.toInt())

    // --- 3. 資料準備 ---
    val serviceImages = listOf(
        R.drawable.service0, R.drawable.service1, R.drawable.service2, R.drawable.service3
    )
    val serviceAnswers = listOf(
        "極早期療育，屬於嬰幼兒方面的服務",
        "離島服務，屬於兒童方面的服務",
        "極重多障，屬於成人方面的服務",
        "輔具服務，屬於一般民眾方面的服務"
    )

    // --- 4. 狀態變數 ---
    var currentServiceIndex by remember { mutableIntStateOf((0..3).random()) }
    var serviceX by remember { mutableFloatStateOf((screenWidth - fallingIconSizePx) / 2) }
    var serviceY by remember { mutableFloatStateOf(0f) }

    // 預設訊息
    var msg by remember { mutableStateOf("遊戲開始") }
    var isPlaying by remember { mutableStateOf(true) }

    // --- 5. 遊戲主迴圈 ---
    LaunchedEffect(Unit) {
        while (true) {
            delay(100)

            if (!isPlaying) continue

            serviceY += 20

            val serviceRect = Rect(
                serviceX.toInt(),
                serviceY.toInt(),
                (serviceX + fallingIconSizePx).toInt(),
                (serviceY + fallingIconSizePx).toInt()
            )

            // 碰撞偵測
            var hitRoleIndex = -1
            if (Rect.intersects(serviceRect, role0Rect)) hitRoleIndex = 0
            else if (Rect.intersects(serviceRect, role1Rect)) hitRoleIndex = 1
            else if (Rect.intersects(serviceRect, role2Rect)) hitRoleIndex = 2
            else if (Rect.intersects(serviceRect, role3Rect)) hitRoleIndex = 3

            // --- 狀況 A: 碰到角色 ---
            if (hitRoleIndex != -1) {
                isPlaying = false // 暫停

                // 1. 計算分數 (保留 v6 邏輯)
                if (currentServiceIndex == hitRoleIndex) {
                    viewModel.score += 1
                } else {
                    viewModel.score -= 1
                }

                // 2. 設定訊息 (改回 v5 樣式)
                when (hitRoleIndex) {
                    0 -> msg = "碰撞嬰幼兒圖示"
                    1 -> msg = "碰撞兒童圖示"
                    2 -> msg = "碰撞成人圖示"
                    3 -> msg = "碰撞一般民眾圖示"
                }

                // 3. 顯示 Toast (保留 v6 邏輯)
                Toast.makeText(context, serviceAnswers[currentServiceIndex], Toast.LENGTH_LONG).show()

                delay(3000) // 暫停 3 秒

                // 重置
                serviceY = 0f
                serviceX = (screenWidth - fallingIconSizePx) / 2
                currentServiceIndex = (0..3).random()
                msg = "下一題"
                isPlaying = true
            }

            // --- 狀況 B: 掉到最下方 ---
            if (serviceY > screenHeight) {
                isPlaying = false // 暫停

                viewModel.score -= 1
                msg = "掉到最下方" // 改回 v5 樣式

                delay(3000) // 暫停 3 秒

                // 重置
                serviceY = 0f
                serviceX = (screenWidth - fallingIconSizePx) / 2
                currentServiceIndex = (0..3).random()
                msg = "下一題"
                isPlaying = true
            }
        }
    }

    // --- 6. 畫面佈局 ---
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        // --- 角落圖示 ---
        Image(
            painter = painterResource(id = R.drawable.role0), contentDescription = "嬰幼兒",
            modifier = Modifier.size(cornerIconSizeDp).offset { IntOffset(0, (middleY - cornerIconSizePx).toInt()) }
        )
        Image(
            painter = painterResource(id = R.drawable.role1), contentDescription = "兒童",
            modifier = Modifier.size(cornerIconSizeDp).offset { IntOffset((screenWidth - cornerIconSizePx).toInt(), (middleY - cornerIconSizePx).toInt()) }
        )
        Image(
            painter = painterResource(id = R.drawable.role2), contentDescription = "成人",
            modifier = Modifier.size(cornerIconSizeDp).align(Alignment.BottomStart)
        )
        Image(
            painter = painterResource(id = R.drawable.role3), contentDescription = "一般民眾",
            modifier = Modifier.size(cornerIconSizeDp).align(Alignment.BottomEnd)
        )

        // --- 中間資訊 ---
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.happy), contentDescription = "Logo",
                    contentScale = ContentScale.Crop, modifier = Modifier.size(200.dp).clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text("瑪利亞基金會服務大考驗", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Spacer(modifier = Modifier.height(10.dp))
                Text("作者：資管二B 劉宇崴", fontSize = 16.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(10.dp))
                Text("螢幕大小：$screenWidth * $screenHeight", fontSize = 16.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(10.dp))

                // 這裡會顯示：成績：X分 (碰撞嬰幼兒圖示)
                Text("成績：${viewModel.score}分 ($msg)", fontSize = 16.sp, color = Color.Black)
            }
        }

        // --- 掉落的服務圖示 ---
        Image(
            painter = painterResource(id = serviceImages[currentServiceIndex]),
            contentDescription = "Service Icon",
            modifier = Modifier
                .size(fallingIconSizeDp)
                .offset { IntOffset(serviceX.toInt(), serviceY.toInt()) }
                .clip(CircleShape)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        if (isPlaying) {
                            serviceX += dragAmount.x
                        }
                    }
                }
        )
    }
}