package com.example.myforecast.ui.alertdialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class AskPermissionsAlertDialog() : DialogFragment() {

    private lateinit var listener: PermissionDialogListener

    interface PermissionDialogListener {
        fun onPermissionGrantedClick(dialog: DialogFragment)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder
                .setTitle("Permission Needed")
                .setMessage("Permission needed for this")
                .setPositiveButton("OK"){ _, _ -> }
                .setNegativeButton("Cancel"){ _, _ -> listener.onPermissionGrantedClick(this)}
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}