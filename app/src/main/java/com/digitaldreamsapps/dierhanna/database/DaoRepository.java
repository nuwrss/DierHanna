package com.digitaldreamsapps.dierhanna.database;



import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.digitaldreamsapps.dierhanna.models.Appointment;
import com.digitaldreamsapps.dierhanna.models.Form;
import com.digitaldreamsapps.dierhanna.models.News;
import com.digitaldreamsapps.dierhanna.models.Phones;
import com.digitaldreamsapps.dierhanna.models.Wedding;
import com.digitaldreamsapps.dierhanna.util.Converters;


@Database(entities = {Form.class, Appointment.class, Phones.class, Wedding.class, News.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class DaoRepository extends RoomDatabase {
    public abstract FormsDao formsDao();
    public abstract AppointmentDao appointmentDao();
    public abstract PhonesDao phonesDao();
    public abstract NewsDao newsDao();
    public abstract WeddingDao weddingDao();
    public <T> BaseDao getDao(T t){
        if (t.getClass().equals(Form.class)){

            return formsDao();
        }
        if (t.getClass().equals(Appointment.class)){

            return appointmentDao();
        }
        if (t.getClass().equals(Phones.class)){

            return phonesDao();
        }
        if (t.getClass().equals(News.class)){

            return newsDao();
        }
        if (t.getClass().equals(Wedding.class)){

            return weddingDao();
        }

        return null;
    }
}
