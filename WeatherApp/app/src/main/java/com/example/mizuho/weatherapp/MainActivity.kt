package com.example.mizuho.weatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.widget.Toast


private const val TAG: String = "MAIN_ACTIVITY"
private const val REQUEST_PERMISSION = 1000


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }









    //
    // パーミッションの確認
    //

    // permissionの確認
    fun checkPermission() {

        Log.d(TAG, "check permission")

        // 既に許可している
        if (ActivityCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            // do something

        } else {
            requestLocationPermission()
        }// 拒否していた場合
    }

    // 許可を求める
    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this@MainActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_PERMISSION
            )

        } else {
            Toast.makeText(this@MainActivity, "アプリ実行に許可が必要です", Toast.LENGTH_SHORT).show()

            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_PERMISSION
            )
        }
    }

    // 結果の受け取り
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestCode == REQUEST_PERMISSION) {
            // 使用が許可された
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // do something

            } else {
                // それでも拒否された時の対応
                Toast.makeText(this, "cannot do anything", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
