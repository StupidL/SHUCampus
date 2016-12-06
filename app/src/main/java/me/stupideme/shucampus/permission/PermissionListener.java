package me.stupideme.shucampus.permission;

interface PermissionListener {

    void onGranted();

    void onDenied();

    void onShowRationale(String[] permissions);
}

