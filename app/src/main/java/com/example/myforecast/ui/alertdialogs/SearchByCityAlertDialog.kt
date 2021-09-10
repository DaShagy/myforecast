package com.example.myforecast.ui.alertdialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.myforecast.databinding.SearchByCityAlertDialogBinding
import kotlinx.android.synthetic.main.search_by_city_alert_dialog.view.*

class SearchByCityAlertDialog() : DialogFragment() {

    private var _binding: SearchByCityAlertDialogBinding? = null
    private val binding get() = _binding!!

    // Use this instance of the interface to deliver action events
    private lateinit var listener: NoticeDialogListener

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    interface NoticeDialogListener {
        fun onDialogSearchClick(dialog: DialogFragment, dialogEditText: String)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val inflater = requireActivity().layoutInflater

            _binding = SearchByCityAlertDialogBinding.inflate(inflater)

            val builder = AlertDialog.Builder(it)

            val text = binding.root.editText.text.toString()

            builder.setView(binding.root)
                .setTitle("Ingrese ciudad")
                .setNegativeButton("Volver"){
                        _, _ ->
                }
                .setPositiveButton("Buscar",){
                        _, _ -> listener.onDialogSearchClick(this, text)
                }
                .create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = context as NoticeDialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException((context.toString() +
                    " must implement NoticeDialogListener"))
        }
    }
}