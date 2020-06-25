package com.digitaldreamsapps.dierhanna.database;



import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.digitaldreamsapps.dierhanna.models.Appointment;
import com.digitaldreamsapps.dierhanna.models.BusinessCat;
import com.digitaldreamsapps.dierhanna.models.Form;
import com.digitaldreamsapps.dierhanna.models.News;
import com.digitaldreamsapps.dierhanna.models.Phones;
import com.digitaldreamsapps.dierhanna.models.ReportCat;
import com.digitaldreamsapps.dierhanna.models.Wedding;
import com.digitaldreamsapps.dierhanna.util.Converters;


@Database(entities = {Form.class, Appointment.class, Phones.class, Wedding.class, News.class , BusinessCat.class,ReportCat.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class DaoRepository extends RoomDatabase {
    public abstract FormsDao formsDao();
    public abstract AppointmentDao appointmentDao();
    public abstract PhonesDao phonesDao();
    public abstract NewsDao newsDao();
    public abstract WeddingDao weddingDao();
    public abstract BusinessCatDao businessCatDao();
    public abstract ReportCatDao reportCatDao();
    public  BaseDao getDao(String t){




        if (t.equals("Form")){

            return formsDao();
        }
        if (t.equals("Appointment")){

            return appointmentDao();
        }
        if (t.equals("Phones")){

            return phonesDao();
        }
        if (t.equals("News")){

            return newsDao();
        }
        if (t.equals("Wedding")){

            return weddingDao();
        }

        if (t.equals("BusinessCat")){

            return businessCatDao();
        }

        if (t.equals("ReportCat")){

            return reportCatDao();
        }

        return null;
    }
}
