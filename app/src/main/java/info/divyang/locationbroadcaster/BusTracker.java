package info.divyang.locationbroadcaster;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class BusTracker extends AppCompatActivity
{
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////PERMISSION FUNCTIONS/////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    static boolean permissionGranted = false;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch(requestCode)
        {
            case PermissionRequester.REQUEST_PERMISSION_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(BusTracker.this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                    permissionGranted = true;
                }
                else
                {
                    Toast.makeText(BusTracker.this, "Permission is Mandatory!", Toast.LENGTH_SHORT).show();
                    //Send's User to App Settings page to grant Permissions.
                    Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, PermissionRequester.REQUEST_PERMISSION_CODE);
                    finish();
                    permissionGranted = true;
                }
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////PERMISSION FUNCTIONS END///////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    final static String TAG = "BusTracker";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_tracker);

        final TextView selectedProfile = findViewById(R.id.Text_SelectedProfile);
        final Switch profileSwitch = findViewById(R.id.Switch_SelectProfile);

        final Intent trackerServiceIntent = new Intent(this, Tracker.class);

        final Activity activity = this;
        final Context context = this;

        profileSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isOn)
            {
                if(isOn)
                {
                    PermissionRequester requestPermission = new PermissionRequester(activity, context, Manifest.permission.ACCESS_FINE_LOCATION,
                            "Dumbo! if you won't allow App the access then how are you planning to use the app? AssHole");

                    if( requestPermission.requestPhoneStatePermission() )
                    {
                        selectedProfile.setText(R.string.Selected_Profile1);
                        startService(trackerServiceIntent);
                    }
                    else
                    {
                        if(permissionGranted)
                        {
                            selectedProfile.setText(R.string.Selected_Profile1);
                            startService(trackerServiceIntent);
                        }
                        else
                        {
                            compoundButton.setChecked(false);
                        }
                    }
                }
                else
                {
                    selectedProfile.setText(R.string.Selected_Profile2);
                    stopService(trackerServiceIntent);
                }
            }
        });
    }
}
