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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import tw.edu.pu.csim.wallyliou.s1132233.ExamViewModel
import tw.edu.pu.csim.wallyliou.s1132233.R
@Composable
fun ExamScreen(
    viewModel: ExamViewModel = viewModel(),
    screenWidth: Float, // 接收 MainActivity 傳來的寬
    screenHeight: Float // 接收 MainActivity 傳來的高
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow), // 黃色背景
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // 圖片：請將 R.drawable.ic_launcher_foreground 換成您自己的圖片 id
        Image(
            painter = painterResource(id = R.drawable.happy),
            contentDescription = "Logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape) // 圓形裁切
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
            text = "作者：資管二B 劉宇崴", // 請記得修改這裡
            fontSize = 16.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(10.dp))

        // 顯示傳入的螢幕 px 數值
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