package com.ezetap.render

import android.content.Context
import android.graphics.Typeface
import android.text.InputFilter
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import com.bumptech.glide.Glide
import com.ezetap.R
import com.ezetap.databinding.ChildButtonBinding
import com.ezetap.databinding.ChildTextInputBinding
import com.ezetap.databinding.ChildTextViewBinding
import com.ezetap.lib_network.constants.UIType
import com.ezetap.lib_network.data.CustomViewData
import com.ezetap.utils.dpToPx
import com.google.android.material.textfield.TextInputLayout

object ViewRenderer {

    fun renderView(
        container: LinearLayout,
        customViews: List<CustomViewData>,
        logoUrl: String? = null,
        onButtonClickListener: ((view: View) -> Unit)? = null
    ) {
        logoUrl?.let { url ->
            container.addView(addImageView(container.context)
                .apply {
                    tag = "iv_logo"
                    layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        context.dpToPx(80)
                    ).apply {
                        setMargins(0, context.dpToPx(16), 0, 0)
                    }
                }
                .also {
                    Glide.with(it).load(url).error(android.R.drawable.ic_menu_report_image).into(it)
                })
        }
        repeat(customViews.size) {
            val child = customViews[it]
            container.addView(
                when (child.uiType) {
                    UIType.LABEL -> addTextView(container.context).apply {
                        tag = child.key
                        text = child.value
                        setTextAppearance(R.style.TextNormal)
                        layoutParams = LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        ).apply {
                            setMargins(0, context.dpToPx(16), 0, 0)
                        }
                    }
                    UIType.LABEL_VALUE -> addEditText(container.context).apply {
                        editText?.let { et ->
                            et.isFocusable = false
                            et.isCursorVisible = false
                            tag = child.key
                            et.setText(child.value)
                            et.setTextAppearance(R.style.TextNormalBold)
                        }
                    }
                    UIType.EDITTEXT -> addEditText(container.context).apply {
                        editText?.let { et ->
                            et.hint = child.hint
                            tag = child.key
                            when (child.key) {
                                "text_phone" -> {
                                    et.filters = arrayOf(InputFilter.LengthFilter(10))
                                    et.inputType = InputType.TYPE_CLASS_NUMBER
                                }
                                "text_city",
                                "text_name" -> {
                                    et.filters = arrayOf(InputFilter.LengthFilter(30))
                                    et.inputType = InputType.TYPE_TEXT_FLAG_CAP_WORDS
                                }
                            }
                        }
                        layoutParams = LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        ).apply {
                            setMargins(0, context.dpToPx(8), 0, context.dpToPx(8))
                        }
                    }
                    UIType.BUTTON -> addButton(container.context).apply {
                        tag = "btn_submit"
                        text = child.value?.uppercase()
                        textSize = 16f
                        typeface = Typeface.DEFAULT_BOLD
                        layoutParams = LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        ).apply {
                            setMargins(0, container.context.dpToPx(48), 0, 0)
                        }
                        setOnClickListener { view -> onButtonClickListener?.invoke(view) }
                    }
                    else -> null
                }
            )
        }
    }

    private fun addTextView(context: Context): TextView =
        ChildTextViewBinding.inflate(LayoutInflater.from(context)).root

    private fun addEditText(context: Context): TextInputLayout =
        ChildTextInputBinding.inflate(LayoutInflater.from(context)).root

    private fun addImageView(context: Context): ImageView {
        return ImageView(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            id = System.currentTimeMillis().toInt()
        }
    }

    private fun addButton(context: Context): Button =
        ChildButtonBinding.inflate(LayoutInflater.from(context)).root

//    private fun addButton(context: Context): Button {
//        return Button(context).apply {
//            layoutParams = LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT
//            )
//            id = System.currentTimeMillis().toInt()
//        }
//    }

    fun getDataFromAllViews(container: LinearLayout): ArrayList<CustomViewData>? {
        val l = arrayListOf<CustomViewData>()
        repeat(container.childCount) { index ->
            when (val child = container[index]) {
                is Button -> {}
                is TextInputLayout -> {
                    if (child.editText?.text.isNullOrEmpty()) {
                        return null
                    } else {
                        l.add(
                            CustomViewData(
                                uiType = UIType.LABEL_VALUE,
                                value = child.editText?.text.toString()
                            )
                        )
                    }
                }
                is TextView ->
                    l.add(
                        CustomViewData(
                            uiType = UIType.LABEL,
                            value = child.text.toString()
                        )
                    )
            }
        }
        return l
    }
}