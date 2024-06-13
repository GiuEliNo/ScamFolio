package com.dosti.scamfolio.ui.view

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.dosti.scamfolio.R
import com.dosti.scamfolio.viewModel.SplashScreenViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun SplashScreen(viewModel: SplashScreenViewModel) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        val success = withContext(Dispatchers.IO) {
            viewModel.initDb()
        }
        withContext(Dispatchers.Main) {
            if(!success)
                Toast.makeText(context, R.string.errorSplash, Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        BackgroundGradient()
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animationsplash))
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever
        )
        Text(modifier = Modifier.align(Alignment.BottomCenter).padding(50.dp),
            text = stringResource(R.string.splashText),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium

            )
    }
}
