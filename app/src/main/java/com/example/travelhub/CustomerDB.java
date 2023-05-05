package com.example.travelhub;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Map;
import java.util.HashMap;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
public class CustomerDB {

    private final String CUSTOMERS_REFERENCE="customers";
    private final String CUSTOMER_PROFILE_PHOTO="profile_photo";
    private final String CUSTOMER_NAME="name";
    private final String CUSTOMER_CONTACT_NO="phoneNumber";
    private final String CUSTOMER_CNIC="cnic";
    private final String CUSTOMER_AGE="age";
    private final String CUSTOMER_DOB="dob";

    public CustomerDB() {
    }

    public void AddCustomer(Customer obj) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference customersRef = db.getReference(CUSTOMERS_REFERENCE);
        String customerID=obj.getCustomerID();
        Customer customer=new Customer(customerID, obj.getName(),obj.getPhoneNumber(),obj.getDOB(),obj.getEmail(),obj.getPassword(),obj.getCNIC(),obj.getAge(),obj.getProfile_photo());
        customersRef.child(customerID).setValue(customer);
    }

    public void UpdateCustomerProfilePhoto(String cID,String photo)
    {
        DatabaseReference customerRef = FirebaseDatabase.getInstance().getReference().
                child(CUSTOMERS_REFERENCE)
                .child(cID);
        Map<String, Object> updates = new HashMap<>();
        updates.put(CUSTOMER_PROFILE_PHOTO,photo);
        customerRef.updateChildren(updates);
    }


    public void UpdateCustomer(Customer obj)
    {
        DatabaseReference customerRef = FirebaseDatabase.getInstance().getReference().
                child(CUSTOMERS_REFERENCE)
                .child(obj.getCustomerID());
        Map<String, Object> updates = new HashMap<>();
        updates.put(CUSTOMER_NAME,obj.getName());
        updates.put(CUSTOMER_CONTACT_NO,obj.getPhoneNumber());
        updates.put(CUSTOMER_CNIC,obj.getCNIC());
        updates.put(CUSTOMER_AGE,obj.getAge());
        updates.put(CUSTOMER_DOB,obj.getDOB());
        customerRef.updateChildren(updates);
    }


    public interface CustomerDataListener
    {
        void onCustomerDataLoaded(Customer customer);
    }
    public void LoadCustomerData(String customerID,CustomerDataListener listener) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference customerRef = database.getReference(CUSTOMERS_REFERENCE+"/"+customerID);
        customerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Customer customer = dataSnapshot.getValue(Customer.class);
                listener.onCustomerDataLoaded(customer);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }








}
