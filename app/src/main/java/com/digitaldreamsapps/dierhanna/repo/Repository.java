package com.digitaldreamsapps.dierhanna.repo;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.digitaldreamsapps.dierhanna.adapters.DierInfoWindowAdapter;
import com.digitaldreamsapps.dierhanna.models.Appointment;
import com.digitaldreamsapps.dierhanna.models.Business;
import com.digitaldreamsapps.dierhanna.models.BusinessCat;
import com.digitaldreamsapps.dierhanna.models.Form;
import com.digitaldreamsapps.dierhanna.models.News;
import com.digitaldreamsapps.dierhanna.models.Phones;
import com.digitaldreamsapps.dierhanna.models.ReportCat;
import com.digitaldreamsapps.dierhanna.models.Wedding;
import com.digitaldreamsapps.dierhanna.util.ConnectionLiveData;
import com.digitaldreamsapps.dierhanna.util.ConnectionStatus;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;



import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class Repository <T>{
    private DatabaseReference mDatabase ;
    private  FirebaseDatabaseRepository liveDataFireBase;
    private ConnectionLiveData connectionLiveData;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Application application;
    private MutableLiveData<T> data = new MutableLiveData<T>();
    private T t ;
    private LifecycleOwner lifecycleOwner;

    public Repository(Application application, LifecycleOwner lifecycleOwner){
        this.application=application;
        this.lifecycleOwner = lifecycleOwner;
    }
    public void setFirebaseChild(String childFirebase ,T t){
        mDatabase = FirebaseDatabase.getInstance().getReference().child(childFirebase);
        liveDataFireBase = new FirebaseDatabaseRepository(mDatabase);
        connectionLiveData = new ConnectionLiveData(application);
        this.t=t;
    }
    @NonNull
    public LiveData<T> getData(){
        getConnectionStatusLiveData().observe(lifecycleOwner, new Observer<ConnectionStatus>() {
            @Override
            public void onChanged(ConnectionStatus connectionStatus) {
                if (!connectionStatus.isConnected()){
                    getDataFromDatabase();
                }else{
                    getDataFromFireBase();
                    getDataFromDatabase();
                }
            }
        });

        return data;
    }
    public LiveData<ConnectionStatus> getConnectionStatusLiveData(){
        return  connectionLiveData;
    }
    private  void getDataFromDatabase(){
        if (DeirHannaDataBase.getInstance(application).isAvailableDao(t)) {
            Disposable disposable = DeirHannaDataBase.getInstance(application).getAll(t).subscribeOn(Schedulers.io())
                    .subscribe(new Consumer() {

                        @Override
                        public void accept(Object o) throws Exception {
                            T tObj = (T) o;
                            data.postValue(tObj);

                        }
                    });

            compositeDisposable.add(disposable);
        }

    }

    private void getDataFromFireBase(){
        final ArrayList<T> tArrayList = new ArrayList<>();
        liveDataFireBase.observe(lifecycleOwner, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(final DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    tArrayList.clear();





                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        T tObject = null;
                        if (t instanceof Form) {
                            tObject = (T) dataSnapshot1.getValue(Form.class);

                        }
                        if (t instanceof Wedding) {
                            tObject = (T) dataSnapshot1.getValue(Wedding.class);

                        }
                        if (t instanceof Phones) {
                            tObject = (T) dataSnapshot1.getValue(Phones.class);

                        }
                        if (t instanceof Appointment) {
                            tObject = (T) dataSnapshot1.getValue(Appointment.class);

                        }
                        if (t instanceof News) {
                            tObject = (T) dataSnapshot1.getValue(News.class);

                        }

                        if (t instanceof ReportCat) {
                            tObject = (T) dataSnapshot1.getValue(ReportCat.class);

                        }

                        if (t instanceof BusinessCat) {
                           // tObject = (T) dataSnapshot1.getValue(BusinessCat.class);

                           tObject = (T) convertToCat(dataSnapshot1);

                        }


                        tArrayList.add(tObject);
                    }

                    insertToDataBase(tArrayList);
                }
            }
        });


    }
    private BusinessCat convertToCat(DataSnapshot dataSnapshot) {

            BusinessCat businessCat = new BusinessCat();
            businessCat.setNameAr((String) dataSnapshot.child("nameAr").getValue());
            businessCat.setNameHe((String) dataSnapshot.child("nameHe").getValue());
            businessCat.setNameEn((String) dataSnapshot.child("nameEn").getValue());
            businessCat.setIcon((String) dataSnapshot.child("icon").getValue());


            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                if (snapshot.getKey().equals("nameAr")||snapshot.getKey().equals("nameEn")||snapshot.getKey().equals("nameHe")||snapshot.getKey().equals("icon")){

                    continue;
                }
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    final Business data = new Business();
                    data.setDetails(snapshot1);

                    businessCat.addPlace(data);




                }


            }


    return businessCat;


    }
    public void clear(){
        compositeDisposable.clear();
    }

    public <T> void insertToDataBase(T t) {
        DeirHannaDataBase.getInstance(application.getApplicationContext()).insertAll((List) t);
    }
}
