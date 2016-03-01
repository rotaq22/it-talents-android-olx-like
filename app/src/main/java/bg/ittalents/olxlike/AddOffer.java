package bg.ittalents.olxlike;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddOffer extends AppCompatActivity implements View.OnClickListener {

    public static final int IMAGE_GALLERY_REQUEST_1 = 21;
    public static final int IMAGE_GALLERY_REQUEST_2 = 22;
    public static final int IMAGE_GALLERY_REQUEST_3 = 23;
    public static final int IMAGE_GALLERY_REQUEST_4 = 24;
    public static final int IMAGE_GALLERY_REQUEST_5 = 25;
    public static final int IMAGE_GALLERY_REQUEST_6 = 26;
    public static final int IMAGE_GALLERY_REQUEST_7 = 27;


    private boolean mainPictureCheck = false;
    
    private static ImageButton mainPicture;
    private static ImageButton picture1;
    private static ImageButton picture2;
    private static ImageButton picture3;
    private static ImageButton picture4;
    private static ImageButton picture5;
    private static ImageButton picture6;
    private static EditText title;
    private static Spinner categorySpinner;
    private static String selectedCategory;
    private static EditText price;
    private static RadioGroup condition;
    private static EditText description;
    private static EditText city;
    private static Button addOfferButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_offer);

        mainPicture = (ImageButton) findViewById(R.id.add_offer_main_picture);
        picture1 = (ImageButton) findViewById(R.id.add_offer_picture1);
        picture2 = (ImageButton) findViewById(R.id.add_offer_picture2);
        picture3 = (ImageButton) findViewById(R.id.add_offer_picture3);
        picture4 = (ImageButton) findViewById(R.id.add_offer_picture4);
        picture5 = (ImageButton) findViewById(R.id.add_offer_picture5);
        picture6 = (ImageButton) findViewById(R.id.add_offer_picture6);
        title = (EditText) findViewById(R.id.add_offer_title_text);
        categorySpinner = (Spinner) findViewById(R.id.add_offer_category_spinner);
        price = (EditText) findViewById(R.id.add_offer_price_text);
        condition = (RadioGroup) findViewById(R.id.add_offer_condition_radio);
        description = (EditText) findViewById(R.id.add_offer_description_text);
        city = (EditText) findViewById(R.id.add_offer_city_text);
        addOfferButton = (Button) findViewById(R.id.add_offer_add_button);

        List<String> categories = new ArrayList<String>();
        categories.add("Select category");
        categories.add("Automobile");
        categories.add("Business Services");
        categories.add("Computers");
        categories.add("Education");
        categories.add("Personal");
        categories.add("Travel");



        mainPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String pictureDirectoryPath = pictureDirectory.getPath();
                Uri data = Uri.parse(pictureDirectoryPath);

                photoPickerIntent.setDataAndType(data, "image/*");

                startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST_1);

            }
        });

        picture1.setOnClickListener(this);
        picture2.setOnClickListener(this);
        picture3.setOnClickListener(this);
        picture4.setOnClickListener(this);
        picture5.setOnClickListener(this);
        picture6.setOnClickListener(this);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(dataAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addOfferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                boolean titleCheck = false;
                boolean categoryCheck = false;
                boolean priceCheck = false;
                boolean conditionCheck = false;
                boolean descriptionCheck = false;
                boolean cityCheck = false;

                if(!mainPictureCheck)
                    Toast.makeText(AddOffer.this, "Main picture is required.", Toast.LENGTH_SHORT).show();
                
                String titleString = title.getText().toString();
                if(titleString.isEmpty())
                    title.setError("Title is required");
                else if(titleString.length() > 255)
                    title.setError("Title is too long");
                else
                    titleCheck = true;

                if(selectedCategory.equalsIgnoreCase("Select category"))
                    Toast.makeText(AddOffer.this, "Please select category", Toast.LENGTH_SHORT).show();
                else
                    categoryCheck = true;

                int priceInt;
                if(!price.getText().toString().isEmpty())
                    priceInt = Integer.parseInt(price.getText().toString());
                else
                    priceInt = -1;

                if(price.getText().toString().isEmpty())
                    price.setError("Price is required.");
                else if(priceInt <=0)
                    price.setError("Please enter valid price.");
                else
                    priceCheck = true;

                if(condition.getCheckedRadioButtonId() != -1)
                    conditionCheck = true;
                else
                    Toast.makeText(AddOffer.this, "Condition is required.", Toast.LENGTH_SHORT).show();

                String descriptionString = description.getText().toString();

                if(descriptionString.isEmpty())
                    description.setError("Description is required");
                else if(descriptionString.length() < 100)
                    description.setError("Description is too short");
                else if(descriptionString.length() > 2000)
                    description.setError("Description is too long");
                else
                    descriptionCheck = true;

                if(city.getText().toString().isEmpty())
                    city.setError("City is required.");
                else
                    cityCheck = true;

                if(mainPictureCheck && titleCheck && categoryCheck && priceCheck && conditionCheck && descriptionCheck && cityCheck){
                    Toast.makeText(AddOffer.this, "Done", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){

            switch (requestCode){
                case IMAGE_GALLERY_REQUEST_1:
                    setPicture(mainPicture,data);
                    mainPictureCheck = true;
                    break;
                case IMAGE_GALLERY_REQUEST_2:
                    setPicture(picture1,data);
                    break;
                case IMAGE_GALLERY_REQUEST_3:
                    setPicture(picture2,data);
                    break;
                case IMAGE_GALLERY_REQUEST_4:
                    setPicture(picture3,data);
                    break;
                case IMAGE_GALLERY_REQUEST_5:
                    setPicture(picture4,data);
                    break;
                case IMAGE_GALLERY_REQUEST_6:
                    setPicture(picture5,data);
                    break;
                case IMAGE_GALLERY_REQUEST_7:
                    setPicture(picture6,data);
                    break;

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_offer:
                startActivity(new Intent(AddOffer.this, AddOffer.class));
                break;
            case R.id.action_home:
                startActivity(new Intent(AddOffer.this, Home.class));
                break;
            case R.id.action_messages:
                startActivity(new Intent(AddOffer.this, MyMessages.class));
                break;
            case R.id.action_profile:
                //TODO да се направи да праща към твоя профил ...
                startActivity(new Intent(AddOffer.this, ViewUser.class));
                break;
            case R.id.action_settings:
                startActivity(new Intent(AddOffer.this, Settings.class));
                break;
            case R.id.action_logout:
                //TODO да те логаутва...
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_offer_picture1:
                askForPhotoWithIntent(IMAGE_GALLERY_REQUEST_2);
                break;
            case R.id.add_offer_picture2:
                askForPhotoWithIntent(IMAGE_GALLERY_REQUEST_3);
                break;
            case R.id.add_offer_picture3:
                askForPhotoWithIntent(IMAGE_GALLERY_REQUEST_4);
                break;
            case R.id.add_offer_picture4:
                askForPhotoWithIntent(IMAGE_GALLERY_REQUEST_5);
                break;
            case R.id.add_offer_picture5:
                askForPhotoWithIntent(IMAGE_GALLERY_REQUEST_6);
                break;
            case R.id.add_offer_picture6:
                askForPhotoWithIntent(IMAGE_GALLERY_REQUEST_7);
                break;
        }
    }

    private void askForPhotoWithIntent(int request){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();
        Uri data = Uri.parse(pictureDirectoryPath);

        photoPickerIntent.setDataAndType(data, "image/*");

        startActivityForResult(photoPickerIntent, request);
    }

    private void setPicture(ImageButton button, Intent data){
        Uri imageUrl = data.getData();

        InputStream inputStream = null;

        try {
            inputStream = getContentResolver().openInputStream(imageUrl);

            Bitmap image = BitmapFactory.decodeStream(inputStream);

            Drawable drawable = new BitmapDrawable(getResources(), image);

            button.setImageDrawable(drawable);

        } catch (FileNotFoundException e) {
            Toast.makeText(AddOffer.this, "Unable to open image", Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (Exception e){

            }
        }
    }
}
