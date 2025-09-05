package com.example.courses.presentation.list.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.core.domain.model.Course
import com.example.courses.R

class CoursesAdapter(
    private val onItemClick: (Course) -> Unit,
    private val onFavoriteClick: (Course) -> Unit
) : RecyclerView.Adapter<CoursesAdapter.CourseViewHolder>() {

    private var courses = listOf<Course>()

    inner class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val courseImage: ImageView = itemView.findViewById(R.id.courseImage)
        private val courseTitle: TextView = itemView.findViewById(R.id.courseTitle)
        private val courseDescription: TextView = itemView.findViewById(R.id.courseDescription)
        private val coursePrice: TextView = itemView.findViewById(R.id.coursePrice)
        private val courseCurrency: TextView = itemView.findViewById(R.id.courseCurrency)
        private val courseDate: TextView = itemView.findViewById(R.id.courseDate)
        private val courseRating: TextView = itemView.findViewById(R.id.courseRating)
        private val favoriteButton: ImageButton = itemView.findViewById(R.id.favoriteButton)

        fun bind(course: Course) {
            // Загрузка изображения с помощью библиотеки (например, Glide или Coil)
            // Glide.with(itemView.context).load(course.imageUrl).into(courseImage)

            courseTitle.text = course.title
            courseDescription.text = course.description
            coursePrice.text = course.price.toString()
            courseCurrency.text = course.currency
            courseDate.text = course.date
            courseRating.text = course.rating.toString()

            // Установка иконки избранного в зависимости от состояния
            val favoriteIcon = if (course.isFavorite) {
                R.drawable.ic_favorite_filled
            } else {
                R.drawable.ic_favorite_outline
            }
            favoriteButton.setImageResource(favoriteIcon)

            // Обработка клика на карточку
            itemView.setOnClickListener { onItemClick(course) }

            // Обработка клика на кнопку избранного
            favoriteButton.setOnClickListener { onFavoriteClick(course) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_course, parent, false)
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(courses[position])
    }

    override fun getItemCount(): Int = courses.size

    fun submitList(newCourses: List<Course>) {
        courses = newCourses
        notifyDataSetChanged()
    }
}