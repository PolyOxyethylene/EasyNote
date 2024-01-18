package com.oxyethylene.easynotedemo.ui.appinfoactivity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyethylene.easynotedemo.R
import com.oxyethylene.easynotedemo.ui.components.SimpleTitleBar
import com.oxyethylene.easynotedemo.ui.theme.GreyDarker

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynotedemo.ui.appinfoactivity
 * @ClassName    : InfoPageUI.java
 * @createTime   : 2024/1/10 20:39
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
@Composable
fun InfoPageArea () {

    val context = LocalContext.current

    val infoContent = context.resources.getString(R.string.app_info)

    Column(
        modifier = Modifier
            .fillMaxSize().navigationBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {

        InfoPageTopBar("关于应用", Modifier)

        Text("作者", fontSize = 10.sp, color = GreyDarker, modifier = Modifier.padding(top = 20.dp, start = 26.dp))

        Row (
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 6.dp)
                .fillMaxWidth().height(60.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(painter = painterResource(R.drawable.developer_avatar), "this is developer's GitHub account avatar.", modifier = Modifier.padding(start = 16.dp).size(40.dp).clip(CircleShape))
            Text("PolyOxyethylene", fontSize = 16.sp, modifier = Modifier.padding(start = 12.dp))
        }

        Row (
            modifier = Modifier.padding(20.dp)
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(infoContent, modifier = Modifier.padding(start = 16.dp, end = 16.dp))
        }

    }

}

@Composable
fun InfoPageTopBar (title: String, modifier: Modifier) = SimpleTitleBar(title, modifier)