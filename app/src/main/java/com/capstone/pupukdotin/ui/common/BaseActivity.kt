package com.capstone.pupukdotin.ui.common

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.capstone.pupukdotin.databinding.DialogOneActionBinding
import com.capstone.pupukdotin.databinding.DialogOneActionWithsubBinding
import com.capstone.pupukdotin.databinding.DialogTwoActionBinding
import com.capstone.pupukdotin.databinding.DialogTwoActionWithsubBinding

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    abstract fun getViewBinding(): VB

    private var _binding: VB? = null
    private var mCurrentToast: Toast? = null

    protected val binding: VB
        get() {
            if (_binding != null) return _binding as VB
            else _binding = getViewBinding()
            return _binding as VB
        }


    protected fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        if (mCurrentToast != null) {
            mCurrentToast?.cancel()
        }
        mCurrentToast = Toast.makeText(this, message, duration)
        mCurrentToast?.show()
    }

    protected fun showTwoActionDialogWithSub(
        message: String,
        bodyMessage: String,
        btnPositiveMesssage: String,
        btnNegativeMesssage: String,
        onYesClickBtnClicked: (() -> Unit)? = null,
        onNoClickBtnClicked: (() -> Unit)? = null
    ) {
        val dialog = Dialog(this)
        val dialogBinding = DialogTwoActionWithsubBinding.inflate(layoutInflater)
        val window = dialog.window

        window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(dialogBinding.root)

        val deviceWidth: Int = Resources.getSystem().displayMetrics.widthPixels
        val margin = (60 * Resources.getSystem().displayMetrics.density).toInt()
        val width: Int = deviceWidth - margin
        window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.setCancelable(false)

        with(dialogBinding) {
            tvTitle.text = message
            tvDesc.text = bodyMessage
            yaButton.text = btnPositiveMesssage
            tidakButton.text = btnNegativeMesssage
            yaButton.setOnClickListener {
                if (onYesClickBtnClicked != null) {
                    onYesClickBtnClicked.invoke()
                    dialog.dismiss()
                }
            }
            tidakButton.setOnClickListener {
                if(onNoClickBtnClicked != null) {
                    onNoClickBtnClicked.invoke()
                    dialog.dismiss()
                }
            }
        }
        dialog.show()
    }

    protected fun showTwoActionDialog(
        message: String,
        btnPositiveMesssage: String,
        btnNegativeMesssage: String,
        onYesClickBtnClicked: (() -> Unit)? = null,
        onNoClickBtnClicked: (() -> Unit)? = null
    ) {
        val dialog = Dialog(this)
        val dialogBinding = DialogTwoActionBinding.inflate(layoutInflater)
        val window = dialog.window

        window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(dialogBinding.root)

        val deviceWidth: Int = Resources.getSystem().displayMetrics.widthPixels
        val margin = (60 * Resources.getSystem().displayMetrics.density).toInt()
        val width: Int = deviceWidth - margin
        window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.setCancelable(false)

        with(dialogBinding) {
            tvTitle.text = message
            yaButton.text = btnPositiveMesssage
            tidakButton.text = btnNegativeMesssage
            yaButton.setOnClickListener {
                if (onYesClickBtnClicked != null) {
                    onYesClickBtnClicked.invoke()
                    dialog.dismiss()
                }
            }
            tidakButton.setOnClickListener {
                if(onNoClickBtnClicked != null) {
                    onNoClickBtnClicked.invoke()
                    dialog.dismiss()
                }
            }
        }
        dialog.show()
    }

    protected fun showOneActionDialogWithSub(
        message: String,
        bodyMessage: String,
        btnPositiveMesssage: String,
        onYesClickBtnClicked: (() -> Unit)? = null,
    ) {
        val dialog = Dialog(this)
        val dialogBinding = DialogOneActionWithsubBinding.inflate(layoutInflater)
        val window = dialog.window

        window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(dialogBinding.root)

        val deviceWidth: Int = Resources.getSystem().displayMetrics.widthPixels
        val margin = (60 * Resources.getSystem().displayMetrics.density).toInt()
        val width: Int = deviceWidth - margin
        window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.setCancelable(false)

        with(dialogBinding) {
            tvTitle.text = message
            tvDesc.text = bodyMessage
            yaButton.text = btnPositiveMesssage
            yaButton.setOnClickListener {
                if (onYesClickBtnClicked != null) {
                    onYesClickBtnClicked.invoke()
                    dialog.dismiss()
                }
            }
        }
        dialog.show()
    }

    protected fun showOneActionDialog(
        message: String,
        btnPositiveMesssage: String,
        onYesClickBtnClicked: (() -> Unit)? = null
    ) {
        val dialog = Dialog(this)
        val dialogBinding = DialogOneActionBinding.inflate(layoutInflater)
        val window = dialog.window

        window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(dialogBinding.root)

        val deviceWidth: Int = Resources.getSystem().displayMetrics.widthPixels
        val margin = (60 * Resources.getSystem().displayMetrics.density).toInt()
        val width: Int = deviceWidth - margin
        window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.setCancelable(false)

        with(dialogBinding) {
            tvTitle.text = message
            yaButton.text = btnPositiveMesssage
            yaButton.setOnClickListener {
                if (onYesClickBtnClicked != null) {
                    onYesClickBtnClicked.invoke()
                    dialog.dismiss()
                }
            }
        }
        dialog.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewBinding()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}