# CourseHub
## 1. Обзор проекта
CourseHub — это Android-приложение для агрегации образовательных курсов. Оно решает проблему отсутствия единой платформы для поиска и сравнения курсов от разных провайдеров.

### Ключевая ценность:

- Единый каталог курсов

- Удобное добавление в избранное

- Текущий статус: MVP (Minimal Viable Product)

- Реализована базовая функциональность

- Используются mock-данные

## Скриншоты

### Экран авторизации
![Экран авторизации](docs/screenshots/auth_screen.png)

### Главный экран
![Главный экран](docs/screenshots/home_screen.png)

### Экран избранных курсов
![Экран избранных курсов](docs/screenshots/favorites_screen.png)

## 2. Функциональность

### Реализовано:
- Аутентификация (только вход, регистрация в разработке)

- Просмотр каталога курсов

- Система избранного

- Сортировка по дате публикации (по убыванию)

- Навигация между экранами через Bottom Navigation

### В разработке:
- Регистрация новых пользователей

- Поиск и фильтрация курсов

- Экран профиля пользователя

- Интеграция с реальным API

## 3. Технологический стек
Язык: Kotlin 100%

Архитектура:

- Clean Architecture (многомодульность)

- MVVM (Model-View-ViewModel)

Асинхронность:

- Kotlin Coroutines

- Kotlin Flow/StateFlow

Дизайн:

- XML Layouts

- Material Design 3

### Основные зависимости:
```gradle
// Network
implementation "com.squareup.retrofit2:retrofit:2.9.0"
implementation "com.squareup.retrofit2:converter-gson:2.9.0"

// DI
implementation "io.insert-koin:koin-android:3.4.0"

// Async
implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4"

// Navigation
implementation "androidx.navigation:navigation-fragment-ktx:2.5.3"
```

## 4. Структура проекта
Многомодульная структура по Clean Architecture:

```text
app/ - entry point
core/ - общие компоненты
  ├─ data/ - репозитории, модели
  └─ domain/ - интерфейсы репозиториев, use cases
features/
  ├─ auth/ - модуль аутентификации
  ├─ courses/ - модуль каталога курсов
  ├─ favorite/ - модуль избранного
  └─ account/ - модуль профиля (в разработке)
```
### Пример организации данных:

```kotlin
// Модель данных
data class Course(
    val id: String,
    val title: String,
    val description: String,
    val price: Int,
    val currency: String,
    val date: String,
    val rating: Float,
    val isFavorite: Boolean
)
```

## 5. Особенности реализации
- Навигация
- Кастомный Bottom Navigation компонент:

```kotlin
class CustomBottomNavigationView : LinearLayout {
    // Логика обработки выбранных элементов
    fun setOnItemSelectedListener(listener: OnItemSelectedListener)
}
```
- Работа с данными
- Репозиторий курсов с кешированием:

```kotlin
class CourseRepositoryImpl(
    private val apiService: CoursesApiService,
    private val favoriteRepository: FavoriteRepository
) : CourseRepository {
    
    override fun getCourses(): Flow<List<Course>> {
        // Объединение данных API и локальных избранных курсов
    }
}
```
- UI Состояния
- Унифицированная система состояний для всех экранов:

```kotlin
sealed interface CoursesState {
    object Loading : CoursesState
    data class Success(val courses: List<Course>) : CoursesState
    data class Error(val message: String) : CoursesState
}
```
## 6. Внешний вид
### Цветовая палитра:

- Основной фон: #151515

- Акцентный цвет: #12B956

- Текст: #F2F2F3 (primary), #A0A0A0 (secondary)

### Типографика:

- Кнопки: 16sp, bold

- Основной текст: 14-16sp

- Второстепенный текст: 12-14sp

## 7. Настройка и запуск
1. Клонировать репозиторий

2. Открыть в Android Studio

3. Собрать проект (не требуются дополнительные ключи API)

4. Запустить на устройстве/эмуляторе
