package com.elmorshdi.trainingtask.helper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.elmorshdi.trainingtask.R
import com.elmorshdi.trainingtask.databinding.BottomSheetDialogBinding
import com.elmorshdi.trainingtask.view.ui.mainpage.MainViewModel
import com.github.ybq.android.spinkit.SpinKitView
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.DoubleBounce
import com.google.android.material.bottomsheet.BottomSheetDialog

fun alertDialog(
    title: String,
    message: String,
    context: Context,
    myFunction: (Int, View) -> Unit,
    id: Int,
    view: View
) {
    val builder = AlertDialog.Builder(context)
    builder.setTitle(title)
    builder.setMessage(message)
    builder.setIcon(android.R.drawable.ic_dialog_alert)
    //performing positive action
    builder.setPositiveButton("Yes") { _, _ ->
        myFunction(id, view)
    }
    builder.setNeutralButton("Cancel") { _, _ ->

    }
    val alertDialog: AlertDialog = builder.create()
    alertDialog.setCancelable(false)
    alertDialog.show()
}

fun alertDialog(
    title: String,
    message: String,
    context: Context,
    myFunction: (View) -> Unit,
    view: View
) {
    val builder = AlertDialog.Builder(context)
    builder.setTitle(title)
    builder.setMessage(message)
    builder.setIcon(android.R.drawable.ic_dialog_alert)
    //performing positive action
    builder.setPositiveButton("Yes") { _, _ ->
        myFunction(view)
    }
    builder.setNeutralButton("Cancel") { _, _ ->

    }
    val alertDialog: AlertDialog = builder.create()
    alertDialog.setCancelable(false)
    alertDialog.show()
}

fun setProgressBar(loginSpinKit: SpinKitView): ProgressBar {
    val progressBar = loginSpinKit as ProgressBar
    val doubleBounce: Sprite = DoubleBounce()
    progressBar.indeterminateDrawable = doubleBounce
    progressBar.visibility = View.VISIBLE
    return progressBar
}

fun showBottomSheet(
    context: Context,
    viewModel: MainViewModel,
    layoutInflater: LayoutInflater
): BottomSheetDialog {
    val bottomSheet = BottomSheetDialog(context, R.style.BottomSheetDialogStyle)

    val bindingSheet = DataBindingUtil.inflate<BottomSheetDialogBinding>(
        layoutInflater,
        R.layout.bottom_sheet_dialog,
        null,
        false
    )
    bottomSheet.setContentView(bindingSheet.root)

    bindingSheet.buttonMostRecent.setOnClickListener {
        viewModel.sortBy(0)
        bottomSheet.dismiss()
    }
    bindingSheet.buttonPriceLtoH.setOnClickListener {
        viewModel.sortBy(1)
        bottomSheet.dismiss()
    }
    bindingSheet.buttonPriceHtoL.setOnClickListener {
        viewModel.sortBy(2)
        bottomSheet.dismiss()
    }
    bindingSheet.buttonName.setOnClickListener {
        viewModel.sortBy(3)
        bottomSheet.dismiss()
    }
    bindingSheet.textViewSortBy.setOnClickListener {
        bottomSheet.dismiss()
    }
    bottomSheet.setCancelable(false)

    bottomSheet.setContentView(bindingSheet.root)

    bottomSheet.show()
    return bottomSheet
}
