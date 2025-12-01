package tw.edu.pu.csim.wallyliou.s1132233

import android.graphics.Rect
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

@Composable
fun ExamScreen(
    viewModel: ExamViewModel = viewModel(),
    screenWidth: Float,
    screenHeight: Float
) {
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

    // --- 3. 狀態變數 ---
    val serviceImages = listOf(
        R.drawable.service0,
        R.drawable.service1,
        R.drawable.service2,
        R.drawable.service3
    )
    var currentServiceImage by remember { mutableIntStateOf(serviceImages.random()) }
    var serviceX by remember { mutableFloatStateOf((screenWidth - fallingIconSizePx) / 2) }
    var serviceY by remember { mutableFloatStateOf(0f) }

    var msg by remember { mutableStateOf("") }

    // --- 4. 動畫與碰撞偵測迴圈 ---
    LaunchedEffect(Unit) {
        while (true) {
            delay(100) // 0.1秒
            serviceY += 20

            // A. 建立目前服務圖示的矩形範圍
            val serviceRect = Rect(
                serviceX.toInt(),
                serviceY.toInt(),
                (serviceX + fallingIconSizePx).toInt(),
                (serviceY + fallingIconSizePx).toInt()
            )

            // B. 進行碰撞判斷
            // 為了程式碼簡潔，我們用一個變數 isHit 來標記是否發生碰撞
            var isHit = false

            if (Rect.intersects(serviceRect, role0Rect)) {
                msg = "(碰撞嬰幼兒圖示)"
                isHit = true
            } else if (Rect.intersects(serviceRect, role1Rect)) {
                msg = "(碰撞兒童圖示)"
                isHit = true
            } else if (Rect.intersects(serviceRect, role2Rect)) {
                msg = "(碰撞成人圖示)"
                isHit = true
            } else if (Rect.intersects(serviceRect, role3Rect)) {
                msg = "(碰撞一般民眾圖示)"
                isHit = true
            }

            // C. 如果發生碰撞，立刻重置 (這就是您要的功能)
            if (isHit) {
                serviceY = 0f // 回到最上方
                serviceX = (screenWidth - fallingIconSizePx) / 2 // 回到水平中間
                currentServiceImage = serviceImages.random() // 換一張圖
            }

            // D. 判斷是否掉到螢幕下方 (沒接到)
            if (serviceY > screenHeight) {
                msg = "(掉到最下方)"
                serviceY = 0f
                serviceX = (screenWidth - fallingIconSizePx) / 2
                currentServiceImage = serviceImages.random()
            }
        }
    }

    // --- 5. 畫面佈局 (保持不變) ---
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        // --- 角落角色圖示 ---
        Image(painter = painterResource(id = R.drawable.role0), contentDescription = "嬰幼兒", modifier = Modifier.size(cornerIconSizeDp).offset { IntOffset(0, (middleY - cornerIconSizePx).toInt()) })
        Image(painter = painterResource(id = R.drawable.role1), contentDescription = "兒童", modifier = Modifier.size(cornerIconSizeDp).offset { IntOffset((screenWidth - cornerIconSizePx).toInt(), (middleY - cornerIconSizePx).toInt()) })
        Image(painter = painterResource(id = R.drawable.role2), contentDescription = "成人", modifier = Modifier.size(cornerIconSizeDp).align(Alignment.BottomStart))
        Image(painter = painterResource(id = R.drawable.role3), contentDescription = "一般民眾", modifier = Modifier.size(cornerIconSizeDp).align(Alignment.BottomEnd))

        // --- 中間文字區塊 ---
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Image(painter = painterResource(id = R.drawable.happy), contentDescription = "Logo", contentScale = ContentScale.Crop, modifier = Modifier.size(200.dp).clip(CircleShape))
                Spacer(modifier = Modifier.height(20.dp))
                Text("瑪利亞基金會服務大考驗", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Spacer(modifier = Modifier.height(10.dp))
                Text("作者：資管二B 劉宇崴", fontSize = 16.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(10.dp))
                Text("螢幕大小：$screenWidth * $screenHeight", fontSize = 16.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(10.dp))

                // 顯示訊息
                Text(
                    text = "成績：${viewModel.score}分 $msg",
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
        }

        // --- 移動的服務圖示 ---
        Image(
            painter = painterResource(id = currentServiceImage),
            contentDescription = "Service Icon",
            modifier = Modifier
                .size(fallingIconSizeDp)
                .offset { IntOffset(serviceX.toInt(), serviceY.toInt()) }
                .clip(CircleShape)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        serviceX += dragAmount.x
                    }
                }
        )
    }
}