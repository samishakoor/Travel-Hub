package com.example.travelhub;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.net.Uri;
import android.net.Uri;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.MediaStore;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.io.IOException;
import java.text.SimpleDateFormat;

import de.hdodenhof.circleimageview.CircleImageView;
import androidx.appcompat.widget.SearchView;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.Toast;

public class EditCustomerProfile extends AppCompatActivity {

    TextInputEditText editName;
    TextInputEditText editDOB;
    TextInputEditText editCNIC;
    TextInputEditText editAge;
    TextInputEditText editContact;
    Button save_btn;
    FirebaseAuth mAuth;
    String customerID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer_profile);

        mAuth=FirebaseAuth.getInstance();
        FirebaseUser currentUser=mAuth.getCurrentUser();

        if(currentUser!=null) {
            customerID=currentUser.getUid();
        }
        else {
            customerID=null;
        }


        String name=getIntent().getStringExtra("name");
        String dob=getIntent().getStringExtra("dob");
        String cnic=getIntent().getStringExtra("cnic");
        String contact=getIntent().getStringExtra("contact");
        String age=getIntent().getStringExtra("age");



        editName=(TextInputEditText) findViewById(R.id.editName);
        editDOB=(TextInputEditText) findViewById(R.id.editDOB);
        editCNIC=(TextInputEditText) findViewById(R.id.editCNIC);
        editAge=(TextInputEditText) findViewById(R.id.editAge);
        editContact=(TextInputEditText) findViewById(R.id.editContact);


        if(!name.equals("Nil")) {
            editName.setText(name);
        }

        if(!dob.equals("Nil")) {
            editDOB.setText(dob);
        }

        if(!cnic.equals("Nil")) {
            editCNIC.setText(cnic);
        }

        if(!contact.equals("Nil")) {
            editContact.setText(contact);
        }

        if(!age.equals("Nil")) {
            editAge.setText(age);
        }
        editDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Calendar calendar= Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog =new DatePickerDialog(EditCustomerProfile.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, day);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        editDOB.setText(dateFormat.format(calendar.getTime()));
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });



        save_btn=(Button) findViewById(R.id.save_edit_profile_btn);
        save_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String editedName=editName.getText().toString().trim();
                String editedDOB=editDOB.getText().toString().trim();
                String editedCNIC=editCNIC.getText().toString().trim();
                String editedContact=editContact.getText().toString().trim();
                String editedAge=editAge.getText().toString().trim();

                if (editedName.isEmpty()) {
                    editedName="Nil";
                }

                if (editedDOB.isEmpty()) {
                    editedDOB="Nil";
                }

                if (editedCNIC.isEmpty()) {
                    editedCNIC="Nil";
                }

                if (editedContact.isEmpty()) {
                    editedContact="Nil";
                }

                if (editedAge.isEmpty()) {
                    editedAge="Nil";
                }

                //save the edited customer data in database
                Customer customer =new Customer();
                customer.setCustomerID(customerID);
                customer.setName(editedName);
                customer.setPhoneNumber(editedContact);
                customer.setCNIC(editedCNIC);
                customer.setDOB(editedDOB);
                customer.setAge(editedAge);
                customer.UpdateCustomerToDatabase();

                Toast.makeText(EditCustomerProfile.this, "Profile Edited Successfully", Toast.LENGTH_SHORT).show();
                Intent user_profile_intent = new Intent(EditCustomerProfile.this,CustomerProfile.class);
                startActivity(user_profile_intent);
            }
       });

    }
}

