package com.oxyethylene.easynote.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyethylene.easynote.R
import com.oxyethylene.easynote.common.constant.MainPageRouteConstant
import com.oxyethylene.easynote.ui.theme.GreyLighter
/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynote.ui.components
 * @ClassName    : BottomNavBar.java
 * @createTime   : 2024/1/16 0:46
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
/**
 *  主界面的底部导航栏
 *  @param navHostController 主界面绑定过的 navHostController
 */
@Composable
fun MainPageNavBar (
    currentPage: MutableState<String>,
    modifier: Modifier = Modifier
) {

//    var currentPage by rememberSaveable { mutableStateOf(MainPageRouteConstant.FOLDER) }

    Column (modifier.wrapContentSize()){
        Row(Modifier.fillMaxWidth().height(1.dp).background(GreyLighter)) {  }

        Row(
            modifier = Modifier.navigationBarsPadding().fillMaxWidth().height(55.dp).background(Color.Transparent),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Button(
                modifier = Modifier.wrapContentHeight(Alignment.CenterVertically),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                onClick = {
                        currentPage.value = MainPageRouteConstant.FOLDER
                }
            ) {
                when(currentPage.value) {
                    MainPageRouteConstant.FOLDER -> Image(painter = painterResource(R.drawable.folder_selected_icon), contentDescription = "", modifier = Modifier.size(24.dp))
                    MainPageRouteConstant.EVENT -> Image(painter = painterResource(R.drawable.folder_unselected_icon), contentDescription = "", modifier = Modifier.size(24.dp))
                }
                Spacer(Modifier.size(4.dp))
                Text("目录", fontSize = 14.sp, color = Color.DarkGray, fontWeight = if (currentPage.value == MainPageRouteConstant.FOLDER) FontWeight.Bold else FontWeight.Normal)
            }

            Button(
                modifier = Modifier.wrapContentHeight(Alignment.CenterVertically),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                onClick = {
                        currentPage.value = MainPageRouteConstant.EVENT
                }
            ) {
                when(currentPage.value) {
                    MainPageRouteConstant.FOLDER -> Image(painter = painterResource(R.drawable.archive_unselected_icon), contentDescription = "", modifier = Modifier.size(24.dp))
                    MainPageRouteConstant.EVENT -> Image(painter = painterResource(R.drawable.archive_selected_icon), contentDescription = "", modifier = Modifier.size(24.dp))
                }
                Spacer(Modifier.size(4.dp))
                Text("归档", fontSize = 14.sp, color = Color.DarkGray, fontWeight = if (currentPage.value == MainPageRouteConstant.EVENT) FontWeight.Bold else FontWeight.Normal)
            }

        }
    }

}