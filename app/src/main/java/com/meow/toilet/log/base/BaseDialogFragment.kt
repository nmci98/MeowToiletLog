package com.meow.toilet.log.base

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.meow.toilet.log.util.message.MessageId
import kotlinx.parcelize.Parcelize
import timber.log.Timber

/**
 * ダイアログ.
 */
class BaseDialogFragment : DialogFragment() {

    companion object {
        private const val BUNDLE_KEY_DIALOG_DATA = "BUNDLE_KEY_DIALOG_DATA"

        private fun newInstance(dialog: DialogData): BaseDialogFragment {
            return BaseDialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(BUNDLE_KEY_DIALOG_DATA, dialog)
                }
            }
        }

        fun show(
            dialog: DialogData,
            fragmentManager: FragmentManager,
        ) {
            newInstance(dialog).run {
                show(fragmentManager, tag)
            }
        }
    }

    /**
     * ダイアログリスナー.
     */
    interface DialogListener {
        fun onDialogPositive(id: String?)
        fun onDialogNegative(id: String?)
    }

    /** リスナー */
    var listener: DialogListener? = null

    /** ダイアログ情報 */
    private val dialogData by lazy {
        arguments?.getParcelable(BUNDLE_KEY_DIALOG_DATA) as? DialogData
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Timber.d("onCreateDialog $arguments")
        // ダイアログ外タップを無効とする
        this.isCancelable = false

        return AlertDialog.Builder(requireActivity())
            .setTitle(dialogData?.title)
            .setMessage(dialogData?.message)
            .setPositiveButton(dialogData?.positiveButtonText) { _, _ ->
                listener?.onDialogPositive(dialogData?.id)
            }
            .setNegativeButton(dialogData?.negativeButtonText) { _, _ ->
                listener?.onDialogNegative(dialogData?.id)
            }
            .create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = when {
                context is DialogListener -> context
                parentFragment is DialogListener -> parentFragment as DialogListener
                else -> null
            }
        } catch (e: ClassCastException) {
            Timber.e(e)
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}

/**
 * ダイアログ情報.
 * @param id ダイアログID
 * @param title タイトル
 * @param message メッセージ
 * @param positiveButtonText ポジティブボタンテキスト
 * @param negativeButtonText ネガティブボタンテキスト
 */
@Parcelize
data class DialogData(
    val id: String,
    val title: String?,
    val message: String,
    val positiveButtonText: String,
    val negativeButtonText: String?,
) : Parcelable {

    constructor(
        dialogId: String,
        messageId: MessageId
    ) : this(
        id = dialogId,
        title = messageId.title,
        message = messageId.message,
        positiveButtonText = messageId.positiveButtonText,
        negativeButtonText = messageId.negativeButtonText
    )
}