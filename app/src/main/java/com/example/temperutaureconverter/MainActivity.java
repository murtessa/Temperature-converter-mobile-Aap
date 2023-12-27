package com.example.temperutaureconverter;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText inputTemperature;
    private Spinner fromSpinner, toSpinner;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputTemperature = findViewById(R.id.inputTemperature);
        fromSpinner = findViewById(R.id.fromSpinner);
        toSpinner = findViewById(R.id.toSpinner);
        resultText = findViewById(R.id.resultText);

        // Set up the spinners with temperature units
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.temperature_units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        // Set default selections
        fromSpinner.setSelection(0);
        toSpinner.setSelection(1);

        // Perform conversion when input changes
        inputTemperature.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                convertTemperature();
            }
        });


        // Perform conversion when spinner selections change
        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                convertTemperature();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                convertTemperature();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    private void convertTemperature() {
        try {
            // Get input temperature
            String inputText = inputTemperature.getText().toString();
            if (inputText.isEmpty()) {
                resultText.setText("Result .....");
                return;
            }

            double temperature = Double.parseDouble(inputText);

            // Get selected units
            String fromUnit = fromSpinner.getSelectedItem().toString();
            String toUnit = toSpinner.getSelectedItem().toString();

            // Perform conversion
            double result = performConversion(temperature, fromUnit, toUnit);

            // Display result
            resultText.setText(String.format("%.2f %s = %.2f %s", temperature, fromUnit, result, toUnit));
        } catch (NumberFormatException e) {
            // Handle invalid input
            resultText.setText("Invalid input");
        }
    }

    private double performConversion(double temperature, String fromUnit, String toUnit) {
        switch (fromUnit) {
            case "Celsius":
                return convertCelsius(temperature, toUnit);
            case "Fahrenheit":
                return convertFahrenheit(temperature, toUnit);
            case "Kelvin":
                return convertKelvin(temperature, toUnit);
            default:
                return temperature; // Default case: no conversion
        }
    }

    private double convertCelsius(double temperature, String toUnit) {
        switch (toUnit) {
            case "Celsius":
                return temperature;
            case "Fahrenheit":
                return (temperature * 9 / 5) + 32;
            case "Kelvin":
                return temperature + 273.15;
            default:
                return temperature;
        }
    }

    private double convertFahrenheit(double temperature, String toUnit) {
        switch (toUnit) {
            case "Celsius":
                return (temperature - 32) * 5 / 9;
            case "Fahrenheit":
                return temperature;
            case "Kelvin":
                return (temperature + 459.67) * 5 / 9;
            default:
                return temperature;
        }
    }

    private double convertKelvin(double temperature, String toUnit) {
        switch (toUnit) {
            case "Celsius":
                return temperature - 273.15;
            case "Fahrenheit":
                return (temperature * 9 / 5) - 459.67;
            case "Kelvin":
                return temperature;
            default:
                return temperature;
        }
    }
}


