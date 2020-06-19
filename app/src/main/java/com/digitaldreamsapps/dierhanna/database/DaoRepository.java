package com.digitaldreamsapps.dierhanna.database;



import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.digitaldreamsapps.dierhanna.models.Appointment;
import com.digitaldreamsapps.dierhanna.models.Form;

@Database(entities = {Form.class, Appointment.class}, version = 1)
public abstract class DaoRepository extends RoomDatabase {
    public abstract FormsDao formsDao();
    public abstract AppointmentDao appointmentDao();
    public <T> BaseDao getDao(T t){
        if (t.getClass().equals(Form.class)){

            return formsDao();
        }
        if (t.getClass().equals(Appointment.class)){

            return appointmentDao();
        }

        return null;
    }
}
