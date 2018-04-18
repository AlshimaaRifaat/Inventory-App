package com.example.shosho.inventory_app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class AddNewActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText name_txt = null, quantity_txt = null, price_txt = null,
                supplierEDtText = null;
    private ImageView img = null;
    private Button img_button = null, saveBtn = null;

    private Intent intent = null;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 123;
    private static final int RESULT_LOAD_IMAGE = 111;
    private String s_img;
    private boolean image_selected = false;

    private MyDatabase data_base = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newactivity_add);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name_txt = (EditText) findViewById(R.id.productname);
        quantity_txt = (EditText) findViewById(R.id.quantity);
        price_txt = (EditText) findViewById(R.id.price);
        supplierEDtText = (EditText) findViewById(R.id.mail);

        img = (ImageView) findViewById(R.id.image);

        img_button = (Button) findViewById(R.id.button);
        img_button.setOnClickListener(this);

        saveBtn = (Button) findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(this);

        data_base = new MyDatabase(this);
    }

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if(ContextCompat.checkSelfPermission(AddNewActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(AddNewActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
                }else{
                    startActivityForResult(intent, RESULT_LOAD_IMAGE);
                }
                break;
            case R.id.saveBtn:
                if (checkDataValidation()) {
                    MyProduct product = new MyProduct();
                    product.setName(name_txt.getText().toString());
                    product.setQuantity(Integer.parseInt(quantity_txt.getText().toString()));
                    product.setPrice(Integer.parseInt(price_txt.getText().toString()));
                    product.setImage(s_img);
                    product.setSupplierMail(supplierEDtText.getText().toString());
                    data_base.add_product(product);
                    Toast.makeText(getBaseContext(), "adding operation done.", Toast.LENGTH_SHORT).show();
                    name_txt.setText("");
                    quantity_txt.setText("");
                    price_txt.setText("");
                    supplierEDtText.setText("");
                    img.setImageBitmap(null);
                } else {
                    Toast.makeText(getBaseContext(), "check information you entered !!!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private boolean checkDataValidation() {
        String reg = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
        try {
            if (!name_txt.getText().toString().isEmpty() &&
                    !quantity_txt.getText().toString().isEmpty() &&
                    !price_txt.getText().toString().isEmpty() &&
                    !supplierEDtText.getText().toString().isEmpty() &&
                    image_selected) {
                if (Pattern.matches(reg, supplierEDtText.getText().toString()) &&
                        Integer.parseInt(quantity_txt.getText().toString()) != 0 &&
                        Integer.parseInt(price_txt.getText().toString()) != 0 ) {
                    return true;
                }
            }
        } catch (Exception ex) {
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK ) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                s_img = cursor.getString(columnIndex);
                cursor.close();
                img.setImageBitmap(BitmapFactory.decodeFile(s_img));
                image_selected = true;
            } else {
                image_selected = false;
                Toast.makeText(this, "please,select image.", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "error happened", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivityForResult(intent, RESULT_LOAD_IMAGE);
                } else {
                    Toast.makeText(getBaseContext(), "permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}