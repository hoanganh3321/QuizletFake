package com.example.quizletfake.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.quizletfake.model.Account;
import com.example.quizletfake.model.Answer;
import com.example.quizletfake.model.Question;
import com.example.quizletfake.model.Quiz;
import com.example.quizletfake.model.QuizAccount;

@Database(entities = {Account.class, Answer.class, Question.class, Quiz.class, QuizAccount.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDatabase;
    public abstract AccountDao accountDao();
    public abstract AnswerDao answerDao();
    public abstract  QuestionDao questionDao();
    public abstract QuizDao quizDao();
    public abstract  QuizAccountDao quizAccountDao();
    public static AppDatabase getInstance(Context context){
        if(appDatabase==null){
            appDatabase= Room.databaseBuilder(context,
                    AppDatabase.class,"flashcardApp").addCallback(rdc).build();
        }
        return appDatabase;
    }
    static Callback rdc = new Callback() {
        public void onCreate (SupportSQLiteDatabase db) {
            String sql = "INSERT INTO account(accountName, accountPassword)\n" +
                    "VALUES\n" +
                    "('nhat','nhat'),\n" +
                    "('Dung','dung'),\n" +
                    "('Duc','duc'),\n" +
                    "('Duy','duy'),\n" +
                    "('Nguyet','nguyet');";
            db.execSQL(sql);
            String sql1 = "INSERT INTO quiz(quizTitle,accountID)\n" +
                    "VALUES\n" +
                    "('Toan',1),\n" +
                    "('Ly',1),\n" +
                    "('Tin',1),\n" +
                    "('SQL',2),\n" +
                    "('Java',2),\n" +
                    "('C sharp',3),\n" +
                    "('Unity',4);";
            db.execSQL(sql1);
            String sql2 = "INSERT INTO question(questionContent,quizID)\n" +
                    "VALUES\n" +
                    "('cong',1),\n" +
                    "('tru',1),\n" +
                    "('nhan',1),\n" +
                    "('luc',2),\n" +
                    "('ban phim',3),\n" +
                    "('man hinh',3),\n" +
                    "('cau truy van',4),\n" +
                    "('sap xep tang dan',4),\n" +
                    "('chon gioi han',4),\n" +
                    "('Hat dau Java doanh nghiep',5),\n" +
                    "('PRJ301',5),\n" +
                    "('PRN211',6),\n" +
                    "('winform',6),\n" +
                    "('WPF',6),\n" +
                    "('Sprite & Animation',7),\n" +
                    "('Game Development',7);";
            db.execSQL(sql2);
            String sql3 = "INSERT INTO answer(answerContent,questionID)\n" +
                    "VALUES\n" +
                    "('+',1),\n" +
                    "('-',2),\n" +
                    "('*',3),\n" +
                    "('F',4),\n" +
                    "('keyboard',5),\n" +
                    "('monitor',6),\n" +
                    "('query',7),\n" +
                    "('ASC',8),\n" +
                    "('Limit',9),\n" +
                    "('Enterprise Java Beans',10),\n" +
                    "('servlet',11),\n" +
                    "('C# hoc ky 5',12),\n" +
                    "('window form C#',13),\n" +
                    "('windows presentation foundation',14),\n" +
                    "('png',15),\n" +
                    "('Game 2d',16);";
            db.execSQL(sql3);
            String sql4 = "INSERT INTO quizAccount(accountID,quizID,lastTimeJoin)\n" +
                    "VALUES\n" +
                    "(1,1,'2023-07-05 10:00:00'),\n" +
                    "(1,4,'2023-07-05 11:00:00'),\n" +
                    "(1,5,'2023-07-05 09:00:00'),\n" +
                    "(1,7,'2023-07-04 09:00:00'),\n" +
                    "(3,1,'2023-07-05 09:00:00'),\n" +
                    "(3,2,'2023-07-05 08:00:00'),\n" +
                    "(3,3,'2023-07-05 07:00:00'),\n" +
                    "(3,4,'2023-07-05 05:00:00'),\n" +
                    "(3,5,'2023-07-05 06:00:00'),\n" +
                    "(3,6,'2023-07-05 02:00:00'),\n" +
                    "(3,7,'2023-07-05 10:00:00'),\n" +
                    "(3,4,'2023-07-05 06:00:00'),\n" +
                    "(2,4,'2023-07-05 10:00:00');";
            db.execSQL(sql4);
        }
        public void onOpen (SupportSQLiteDatabase db) {
            // do something every time database is open

        }
    };
}
