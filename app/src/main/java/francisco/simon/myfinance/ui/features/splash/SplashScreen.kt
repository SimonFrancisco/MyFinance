package francisco.simon.myfinance.ui.features.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import francisco.simon.myfinance.R
import francisco.simon.myfinance.navigation.ExpenseGraph
import francisco.simon.myfinance.navigation.LocalNavController
import francisco.simon.myfinance.navigation.SplashRoute
import francisco.simon.myfinance.ui.theme.Green

/**
 * Splash screen with Lottie
 * @author Simon Francisco
 */
@Composable
fun SplashScreen() {
    val navController = LocalNavController.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Green),
        contentAlignment = Alignment.Center,
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.wallet_animation))
        val logoAnimationState = animateLottieCompositionAsState(composition = composition)
        LottieAnimation(
            composition = composition,
            progress = { logoAnimationState.progress },
        )
        if (logoAnimationState.isAtEnd && logoAnimationState.isPlaying) {
            navController.navigate(ExpenseGraph){
                popUpTo(SplashRoute){
                    inclusive = true
                }
            }
        }
    }
}