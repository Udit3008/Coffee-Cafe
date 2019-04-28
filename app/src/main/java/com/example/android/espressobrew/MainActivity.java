package com.example.android.espressobrew;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int number_of_coffees = 0;

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        int total;
        boolean whippedCream, nutella, chocolate;
        String salutation;
        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whippedcream_checkbox);
        CheckBox nutellaCheckbox = (CheckBox) findViewById(R.id.nutella_checkbox);
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        whippedCream = whippedCreamCheckbox.isChecked();
        nutella = nutellaCheckbox.isChecked();
        chocolate = chocolateCheckbox.isChecked();
        salutation = "Thank you!";
        total = (number_of_coffees*70);
        createOrderSummary(total,salutation,whippedCream,nutella,chocolate);
    }

    public void increament(View view) {
        number_of_coffees = number_of_coffees + 1;
        display(number_of_coffees);
    }

    public void decreament(View view) {
        number_of_coffees = number_of_coffees - 1;
        if (number_of_coffees < 0){
            number_of_coffees=0;
        }
        display(number_of_coffees);
    }

    public void createOrderSummary(int price, String greet, boolean whippedCream, boolean nutella, boolean chocolate){
        String priceMessage = "";
        EditText name = (EditText) findViewById(R.id.name);
        String customer_name = name.getText().toString();
        EditText email = (EditText) findViewById(R.id. email);
        String customer_email = email.getText().toString();
        EditText contactno = (EditText) findViewById(R.id.mobile);
        String customer_contactno = contactno.getText().toString();
        priceMessage = priceMessage + "Ordered by " + customer_name;
        priceMessage = priceMessage + "\nBill to be mailed at : \t" + customer_email;
      //  priceMessage = priceMessage + "\nContact No. : " + customer_contactno;
        priceMessage = priceMessage +  "\nQuantity: " + number_of_coffees;
        if (whippedCream == false && nutella == false && chocolate == false)
            priceMessage = priceMessage + "\nWithout Toppings\n";
        if ((whippedCream || nutella || chocolate) == true)
            priceMessage = priceMessage + "\nWith added Toppings:\n";
        if(whippedCream==true) {
            priceMessage = priceMessage + "\tWhipped Cream\n";
            price = price + (25*number_of_coffees);
        }
        if(nutella==true) {
            priceMessage = priceMessage + "\tNutella\n";
            price = price + (30*number_of_coffees);
        }
        if(chocolate==true) {
            priceMessage = priceMessage + "\tChocolate\n";
            price = price + (40*number_of_coffees);
        }
        priceMessage = priceMessage + "Total: ₹" + price + "\n" + greet;
        Toast.makeText(this,"Amount to be paid: ₹ " + price + "\nBill will be mailed shortly",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto: "));
        intent.putExtra(Intent.EXTRA_EMAIL, customer_email);
        intent.putExtra(Intent.EXTRA_SUBJECT,"Coffee from Just Java for  " + customer_name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if(intent.resolveActivity(getPackageManager())!=null)
            startActivity(intent);
        displayMessage(priceMessage);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.orderSummary_text_view);
        orderSummaryTextView.setText(message);
    }
}
