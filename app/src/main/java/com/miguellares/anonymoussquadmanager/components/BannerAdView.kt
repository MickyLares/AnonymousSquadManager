package com.miguellares.anonymoussquadmanager.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.miguellares.anonymoussquadmanager.R

@Composable
fun HomeBannerView(padding:Int) {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(top = padding.dp),
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = "ca-app-pub-2107035123865943/9968826805"
                loadAd(AdRequest.Builder().build())
            }
        })

}