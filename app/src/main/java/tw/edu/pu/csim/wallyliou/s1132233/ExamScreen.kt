package tw.edu.pu.csim.wallyliou.s1132233

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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun ExamScreen(
    viewModel: ExamViewModel = viewModel(),
    screenWidth: Float,
    screenHeight: Float
) {
    val density = LocalDensity.current

    // --- 1. 角落圖示的設定 ---
    val cornerIconSizePx = 300f
    val cornerIconSizeDp = with(density) { cornerIconSizePx.toDp() }
    val middleY = screenHeight / 2

    // --- 2. 隨機落下服務圖示的設定 (第四題新增) ---
    // 定義服務圖示的圖片清單
    val serviceImages = listOf(
        R.drawable.service0,
        R.drawable.service1,
        R.drawable.service2,
        R.drawable.service3
    )

    // 設定掉落圖示的大小 (這裡設為 200px 左右比較剛好，可自行調整)
    val fallingIconSizePx = 200f
    val fallingIconSizeDp = with(density) { fallingIconSizePx.toDp() }

    // [狀態] 目前顯示哪一張服務圖片 (隨機初始)
    var currentServiceImage by remember { mutableIntStateOf(serviceImages.random()) }

    // [狀態] 服務圖示的座標 (X, Y)
    // 初始位置：X = 螢幕水平置中, Y = 0 (最上方)
    var serviceX by remember { mutableFloatStateOf((screenWidth - fallingIconSizePx) / 2) }
    var serviceY by remember { mutableFloatStateOf(0f) }

    // [動畫邏輯] 啟動協程來處理掉落動畫
    LaunchedEffect(Unit) {
        while (true) {
            delay(100) // 每 0.1 秒 (100毫秒)
            serviceY += 20 // 往下掉 20px

            // 如果掉出螢幕下方 (Y > 螢幕高度)
            if (serviceY > screenHeight) {
                serviceY = 0f // 回到最上方
                // 回到水平中間
                serviceX = (screenWidth - fallingIconSizePx) / 2
                // 隨機換一張圖
                currentServiceImage = serviceImages.random()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        // --- A. 四個角落的角色圖示 (保持不變) ---
        // 左中
        Image(
            painter = painterResource(id = R.drawable.role0),
            contentDescription = "嬰幼兒",
            modifier = Modifier
                .size(cornerIconSizeDp)
                .offset { IntOffset(x = 0, y = (middleY - cornerIconSizePx).toInt()) }
        )
        // 右中
        Image(
            painter = painterResource(id = R.drawable.role1),
            contentDescription = "兒童",
            modifier = Modifier
                .size(cornerIconSizeDp)
                .offset { IntOffset(x = (screenWidth - cornerIconSizePx).toInt(), y = (middleY - cornerIconSizePx).toInt()) }
        )
        // 左下
        Image(
            painter = painterResource(id = R.drawable.role2),
            contentDescription = "成人",
            modifier = Modifier
                .size(cornerIconSizeDp)
                .align(Alignment.BottomStart)
        )
        // 右下
        Image(
            painter = painterResource(id = R.drawable.role3),
            contentDescription = "一般民眾",
            modifier = Modifier
                .size(cornerIconSizeDp)
                .align(Alignment.BottomEnd)
        )

        // --- B. 中間主要內容 (標題、作者、成績) ---
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.happy),
                    contentDescription = "Logo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "瑪利亞基金會服務大考驗",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "作者：資管二B 劉宇崴",
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "螢幕大小：$screenWidth * $screenHeight",
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "成績：${viewModel.score}分",
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
        }

        // --- C. 隨機掉落的服務圖示 (第四題新增) ---
        // 放在最後面，確保它會浮在其他元件上方
        Image(
            painter = painterResource(id = currentServiceImage),
            contentDescription = "Service Icon",
            modifier = Modifier
                .size(fallingIconSizeDp) // 設定圖示大小
                // 1. 設定絕對位置 (隨時間改變)
                .offset { IntOffset(serviceX.toInt(), serviceY.toInt()) }
                // 2. 圓形裁切 (看範例圖好像是圓的，若不需要可拿掉)
                .clip(CircleShape)
                // 3. 手勢偵測：水平拖曳
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume() // 消耗事件，避免衝突
                        serviceX += dragAmount.x // 只改變 X 軸，讓使用者左右拖曳
                    }
                }
        )
    }
}