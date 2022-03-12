package com.meow.toilet.log.util.message

/**
 * メッセージID
 * ダイアログに表示するメッセージ文言を管理するクラスです
 * @param title タイトル
 * @param message メッセージ
 * @param positiveButtonText ポジティブボタンテキスト
 * @param negativeButtonText ネガティブボタンテキスト
 */
enum class MessageId(
    val title: String?,
    val message: String,
    val positiveButtonText: String,
    val negativeButtonText: String?,
) {
    C00001(null, "ログを登録しました。", "OK", null),
    C00002(null, "ペット情報を登録します。よろしいですか？", "登録する", "キャンセル"),
    E00001(null, "内部エラーが発生しました。時間をおいてからお試しください。", "OK", null),
}