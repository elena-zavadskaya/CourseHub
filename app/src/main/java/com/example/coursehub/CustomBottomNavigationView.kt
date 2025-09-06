package com.example.coursehub

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat

class CustomBottomNavigationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var menuItems = mutableListOf<MenuItem>()
    private var selectedItemId = 0
    private var listener: OnItemSelectedListener? = null

    interface OnItemSelectedListener {
        fun onItemSelected(itemId: Int)
    }

    data class MenuItem(
        val id: Int,
        val iconRes: Int,
        val activeIconRes: Int,
        val title: String
    )

    init {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER
        setBackgroundResource(R.drawable.bottom_nav_background)

        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            88.dpToPx()
        )
    }

    fun setOnItemSelectedListener(listener: OnItemSelectedListener) {
        this.listener = listener
    }

    fun addMenuItem(itemId: Int, iconRes: Int, activeIconRes: Int, title: String) {
        menuItems.add(MenuItem(itemId, iconRes, activeIconRes, title))
    }

    fun buildMenu() {
        removeAllViews()

        val weight = 1f / menuItems.size

        for (item in menuItems) {
            val itemView = createItemView(item, weight)
            addView(itemView)
        }

        setSelectedItem(selectedItemId)
    }

    fun setSelectedItem(itemId: Int) {
        selectedItemId = itemId

        for (i in 0 until childCount) {
            val itemView = getChildAt(i) as FrameLayout
            val item = menuItems[i]

            val iconBackground = itemView.getChildAt(0) as View
            val icon = itemView.getChildAt(1) as ImageView
            val text = itemView.getChildAt(2) as TextView

            val isSelected = item.id == itemId

            if (isSelected) {
                iconBackground.setBackgroundResource(R.drawable.bottom_nav_icon_bg)
                icon.setImageResource(item.activeIconRes)
                text.setTextColor(ContextCompat.getColor(context, R.color.text_secondary))
            } else {
                iconBackground.background = null
                icon.setImageResource(item.iconRes)
                text.setTextColor(ContextCompat.getColor(context, R.color.text_primary))
            }
        }
    }

    private fun createItemView(item: MenuItem, weight: Float): View {
        val itemLayout = FrameLayout(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                0,
                LayoutParams.WRAP_CONTENT,
                weight
            ).apply {
                gravity = Gravity.CENTER
            }
        }

        // Фон для иконки
        val iconBackground = View(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                64.dpToPx(),
                32.dpToPx()
            ).apply {
                gravity = Gravity.CENTER_HORIZONTAL
                topMargin = 16.dpToPx()
            }
        }
        itemLayout.addView(iconBackground)

        // Иконка
        val icon = ImageView(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                24.dpToPx(),
                24.dpToPx()
            ).apply {
                gravity = Gravity.CENTER_HORIZONTAL
                topMargin = 20.dpToPx()
            }
            setImageResource(item.iconRes)
        }
        itemLayout.addView(icon)

        // Текст с отступом от иконки
        val text = TextView(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER_HORIZONTAL
                topMargin = 32.dpToPx() + 20.dpToPx()
                bottomMargin = 12.dpToPx()
            }
            text = item.title
            textSize = 12f
            setTextColor(ContextCompat.getColor(context, R.color.text_primary))
        }
        itemLayout.addView(text)

        itemLayout.setOnClickListener {
            setSelectedItem(item.id)
            listener?.onItemSelected(item.id)
        }

        return itemLayout
    }

    private fun Int.dpToPx(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }
}