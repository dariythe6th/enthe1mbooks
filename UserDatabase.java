package com.enthe1m.myapplication;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {User.class, Book.class}, version = 3)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract BookDao bookDao();

    // Migration from version 1 to 2
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `Book` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT, `author` TEXT, `userId` INTEGER NOT NULL, FOREIGN KEY(`userId`) REFERENCES `User`(`id`) ON DELETE CASCADE)");
        }
    };

    // Migration from version 2 to 3
    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE `Book` ADD COLUMN `notes` TEXT");
        }
    };
}
