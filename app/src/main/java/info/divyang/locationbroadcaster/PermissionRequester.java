package info.divyang.locationbroadcaster;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

class PermissionRequester
{
    private Activity mainActivity;
    private Context mainActivityContext;

    private String permissionName;
    private String permissionDetails;

    final static int REQUEST_PERMISSION_CODE = 1;

    PermissionRequester(Activity mainActivity, Context mainActivityContext, String permissionName, String permissionDetails)
    {
        this.mainActivity = mainActivity;
        this.permissionName = permissionName;
        this.permissionDetails = permissionDetails;
        this.mainActivityContext = mainActivityContext;
    }

    boolean requestPhoneStatePermission()
    {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        {
            Log.i(BusTracker.TAG,"Granting permissions for Pre Marshmallow");
            return true;
        }
        else
        {
            int permissionCheck = ContextCompat.checkSelfPermission(mainActivityContext, permissionName);
            if( permissionCheck != PackageManager.PERMISSION_GRANTED )
            {
                if( ActivityCompat.shouldShowRequestPermissionRationale(mainActivity, permissionName) )
                {
                    showExplanation();
                    return false;
                }
                else
                {
                    requestPermission();
                    return false;
                }
            }
            else
            {
                return true;
            }
        }
    }

    private void showExplanation()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivityContext);
        builder.setTitle("Permission Needed")
                .setMessage(this.permissionDetails)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        requestPermission();
                    }
                });
        builder.create().show();
    }

    private void requestPermission()
    {
        ActivityCompat.requestPermissions(mainActivity, new String[]{permissionName}, REQUEST_PERMISSION_CODE);
    }
}
