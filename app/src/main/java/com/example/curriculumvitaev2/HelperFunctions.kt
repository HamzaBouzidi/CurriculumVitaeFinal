package com.example.curriculumvitaev2


import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager


fun navigateToFragment(
    fragContainer: Int,
    fragManager: FragmentManager,
    fragment: Fragment,
    navStack: String
) {
    fragManager.beginTransaction().replace(
        fragContainer, fragment
    ).addToBackStack(navStack).commit()
}

fun alert(context: Context, title: Int, body: Int, positiveAction: () -> Unit) {
    val builder = AlertDialog.Builder(context)

    builder.setTitle(title)
    builder.setMessage(body)
    builder.setIcon(android.R.drawable.ic_dialog_alert)


    builder.setPositiveButton("Yes") { dialogInterface, _ ->
        positiveAction()
        dialogInterface.dismiss()
    }

    builder.setNegativeButton("No") { dialogInterface, _ ->
        dialogInterface.dismiss()
    }

    val alertDialog: AlertDialog = builder.create()
    alertDialog.setCancelable(true)
    alertDialog.show()
}



