package tw.edu.pu.csim.wallyliou.s1132233

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ExamScreen(
    viewModel: ExamViewModel = viewModel(),
    screenWidth: Float,
    screenHeight: Float
) {
    val density = LocalDensity.current
    val iconSizePx = 300f
    val iconSizeDp = with(density) { iconSizePx.toDp() }
    val middleY = screenHeight / 2

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        // --- 角落圖示區域 (保持不變) ---
        Image(
            painter = painterResource(id = R.drawable.role0),
            contentDescription = "嬰幼兒",
            modifier = Modifier
                .size(iconSizeDp)
                .offset { IntOffset(x = 0, y = (middleY - iconSizePx).toInt()) }
        )

        Image(
            painter = painterResource(id = R.drawable.role1),
            contentDescription = "兒童",
            modifier = Modifier
                .size(iconSizeDp)
                .offset { IntOffset(x = (screenWidth - iconSizePx).toInt(), y = (middleY - iconSizePx).toInt()) }
        )

        Image(
            painter = painterResource(id = R.drawable.role2),
            contentDescription = "成人",
            modifier = Modifier
                .size(iconSizeDp)
                .align(Alignment.BottomStart)
        )

        Image(
            painter = painterResource(id = R.drawable.role3),
            contentDescription = "一般民眾",
            modifier = Modifier
                .size(iconSizeDp)
                .align(Alignment.BottomEnd)
        )

        // --- 中間內容區域 ---
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

                // 1. 標題改黑色
                Text(
                    text = "瑪利亞基金會服務大考驗",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(10.dp))

                // 2. 作者改黑色
                Text(
                    text = "作者：資管二B 劉宇崴",
                    fontSize = 16.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(10.dp))

                // 3. 螢幕大小改黑色
                Text(
                    text = "螢幕大小：$screenWidth * $screenHeight",
                    fontSize = 16.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(10.dp))

                // 4. 成績改黑色
                Text(
                    text = "成績：${viewModel.score}分",
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
        }
    }
}