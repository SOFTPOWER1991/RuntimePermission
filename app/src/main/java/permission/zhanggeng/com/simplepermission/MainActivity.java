package permission.zhanggeng.com.simplepermission;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private Button button;

    private static final int RC_CALL_PERM = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.btn_call);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call();
            }
        });
    }

    private void call_action() {
        Intent phoneIntent = new Intent(
                "android.intent.action.CALL", Uri.parse("tel:"
                + "15525723303"));
        startActivity(phoneIntent);
    }

    public void call() {

        //1. 检查是否有权限
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CALL_PHONE)) {
            // 有权限，直接打电话
            Toast.makeText(this, "TODO: Camera things", Toast.LENGTH_LONG).show();
            call_action();
        } else {
            //没权限，申请权限
            EasyPermissions.requestPermissions(this, "请开启打电话权限",
                    RC_CALL_PERM, Manifest.permission.CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // 请求权限的回调
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    /**
     * 权限申请成功
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        call_action();
    }

    /**
     * 权限被禁止
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }
}
