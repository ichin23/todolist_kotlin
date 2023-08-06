package com.ichin23.todolist.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ichin23.todolist.data.TodoDatabase
import com.ichin23.todolist.data.TodoRepository
import com.ichin23.todolist.data.TodoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideTodoDatabase(app: Application): TodoDatabase {
        return Room.databaseBuilder(
            app,
            TodoDatabase::class.java,
            "todo_db").build()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(db: TodoDatabase):TodoRepository{
        return TodoRepositoryImpl(db.dao);
    }
}