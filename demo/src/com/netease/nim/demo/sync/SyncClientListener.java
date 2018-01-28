package com.netease.nim.demo.sync;

/**
 * @author SevenCheng
 */

public interface SyncClientListener {

    public void onDownloadSyncSuccess(String result);

    public void onDownloadSyncFail(String Exception);

}
