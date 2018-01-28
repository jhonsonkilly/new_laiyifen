// This file was generated by PermissionsDispatcher. Do not modify!
package com.netease.nim.demo.contact.activity;

import android.support.v4.app.ActivityCompat;

import permissions.dispatcher.PermissionUtils;

final class UserProfileActivityPermissionsDispatcher {
  private static final int REQUEST_SHOWCONTACTS = 3;

  private static final String[] PERMISSION_SHOWCONTACTS = new String[] {"android.permission.READ_CONTACTS"};

  private UserProfileActivityPermissionsDispatcher() {
  }

  static void showContactsWithPermissionCheck(UserProfileActivity target) {
    if (PermissionUtils.hasSelfPermissions(target, PERMISSION_SHOWCONTACTS)) {
      target.showContacts();
    } else {
      ActivityCompat.requestPermissions(target, PERMISSION_SHOWCONTACTS, REQUEST_SHOWCONTACTS);
    }
  }

  static void onRequestPermissionsResult(UserProfileActivity target, int requestCode,
                                         int[] grantResults) {
    switch (requestCode) {
      case REQUEST_SHOWCONTACTS:
      if (PermissionUtils.verifyPermissions(grantResults)) {
        target.showContacts();
      }
      break;
      default:
      break;
    }
  }
}