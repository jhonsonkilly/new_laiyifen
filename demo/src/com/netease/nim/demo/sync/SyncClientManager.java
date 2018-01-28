package com.netease.nim.demo.sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author SevenCheng
 */

public class SyncClientManager {
    private static SyncClientManager instance = null;

    private ExecutorService singleThreadExecutor = Executors
            .newSingleThreadExecutor();

    /**
     * 单列模式
     */
    public static SyncClientManager getInstance() {
        if (instance == null) {

            instance = new SyncClientManager();
        }
        return instance;
    }


    /**
     * 执行 SyncClient
     */
    public void executeSyncClient(SyncClient syncClient) {
        singleThreadExecutor.execute(syncClient);
    }
}
