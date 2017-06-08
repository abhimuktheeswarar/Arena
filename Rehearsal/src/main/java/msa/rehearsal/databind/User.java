package msa.rehearsal.databind;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;

/**
 * Created by Abhimuktheeswarar on 07-06-2017.
 */

public class User extends BaseObservable {
    public final ObservableField<String> middleName = new ObservableField<>();
    private String firstName;
    private String lastName;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }


    @Bindable
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        //notifyPropertyChanged(BR.firstName);
    }

    @Bindable
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        //notifyPropertyChanged(BR.lastName);
        middleName.set("middle " + lastName);
    }

    public String getMiddleName() {
        return middleName.get();
    }


}