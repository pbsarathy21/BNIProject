package com.developer.spiderindia.bni.Activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.developer.spiderindia.bni.ApiClasses.RetrofitClient;
import com.developer.spiderindia.bni.ModelClasses.RegisterResponse;
import com.developer.spiderindia.bni.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText name, member, phone;
    EditText dob, wedding, company;
    Button submit;
    String dateOfBirth, weddingDate, CompanyDate, Name, Phone, Member;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering...");
        progressDialog.setCanceledOnTouchOutside(false);

        name = findViewById(R.id.name);
        member = findViewById(R.id.membercategories);
        phone = findViewById(R.id.phoneno);
        dob = findViewById(R.id.dob);
        wedding = findViewById(R.id.weddinganniversary);
        company = findViewById(R.id.companyanniversary);


        submit = findViewById(R.id.submit);

        dobListener();
        weddingListener();
        companyListener();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean valid = Validate();

                if (valid) {
                    progressDialog.show();
                    UploadToServer();

                }
            }
        });
    }

    private boolean Validate() {

        Name = name.getText().toString().trim();
        Phone = phone.getText().toString().trim();
        Member = member.getText().toString().trim();

        if (name.getText().toString().trim().length() == 0) {
            name.setError("required");
            name.requestFocus();
            return false;
        }

        if (member.getText().toString().trim().length() == 0) {
            member.setError("required");
            member.requestFocus();
            return false;
        }

        if (phone.getText().toString().trim().length() == 0) {
            phone.setError("required");
            phone.requestFocus();
            return false;
        }

        if (dob.getText().toString().trim().length() == 0) {
            dob.setError("select date of birth");
            dob.requestFocus();
            return false;
        }

        if (wedding.getText().toString().trim().length() == 0) {
            wedding.setError("select Wedding Date");
            wedding.requestFocus();
            return false;
        }

        if (company.getText().toString().trim().length() == 0) {
            company.setError("Select Company Date");
            company.requestFocus();
            return false;
        }

        return true;
    }

    private void UploadToServer() {

        JSONObject paramObject = new JSONObject();
        try {
            paramObject.put("name", Name);
            paramObject.put("member_category", Member);
            paramObject.put("phone_no", Phone);
            paramObject.put("date_of_birth", dateOfBirth);
            paramObject.put("wedding_anniversary", weddingDate);
            paramObject.put("company_anniversary", CompanyDate);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Call<RegisterResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .updateDetails(paramObject.toString());

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                progressDialog.dismiss();

                if (response.code() == 200)
                {
                    RegisterResponse registerResponse = response.body();

                    startActivity(new Intent(MainActivity.this, ThankyouActivity.class));

                    finish();
                }
                else
                {
                    Toast.makeText(MainActivity.this, response.code()+" : Server Error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void companyListener() {

        company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                DecimalFormat formatter = new DecimalFormat("00");

                                CompanyDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                company.setText(CompanyDate);

                            }

                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });
    }

    private void weddingListener() {

        wedding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                DecimalFormat formatter = new DecimalFormat("00");

                                weddingDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                wedding.setText(weddingDate);

                            }

                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });
    }

    private void dobListener() {

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                DecimalFormat formatter = new DecimalFormat("00");

                                dateOfBirth = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                dob.setText(dateOfBirth);

                            }

                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });
    }
}
