package com.teoryul.currencyconverter.binding

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.teoryul.currencyconverter.utils.PicassoImageLoader

@BindingAdapter("imgViewSrc")
fun setImgViewSrc(imgView: ImageView, currencyId: String?) = PicassoImageLoader.setImage(imgView, currencyId)