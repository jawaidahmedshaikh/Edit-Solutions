/*
 * VOCache.java      Version 1.1  09/24/2001
 *
 * Copyright (c) 2001 Systems Engineering Group, LLC. All Rights Reserved.
 *
 *
 * This program is the confidential and proprietary information of
 * Systems Engineering Group, LLC and may not be copied in whole or in part
 * without the written permission of Systems Engineering Group, LLC.
 */
package fission.dm.valueobject;

import edit.services.db.VOCacheEntry;

import java.util.*;

public class VOCache implements Runnable {

//*******************************
//         Variables
//*******************************

    private Map voCacheEntries;

    private Object voCacheMonitor;

	private static final int CLEAR_CACHE_INTERVAL_SECS = 10;
    private static final int CACHE_LIFE_SECS = 5;

	private int countdown;

	private static VOCache voCache;


//*******************************
//          Constructor
//*******************************

	private VOCache() {

        init();
	}


//*******************************
//          Public Methods
//*******************************

	public static final VOCache getSingleton() {

		if (voCache == null)  	 {

			voCache = new VOCache();
		}

		return voCache;
	}


    public final VOCacheEntry getVOCacheEntry(String voCacheKey){

        synchronized (voCacheMonitor) {

            VOCacheEntry voCacheEntry = (VOCacheEntry) voCacheEntries.get(voCacheKey);

            return voCacheEntry;
        }
    }

    public void addVOCacheEntry(VOCacheEntry voCacheEntry){

        synchronized (voCacheMonitor) {

            if (!voCacheEntries.containsKey(voCacheEntry.getVOCacheKey())){

                voCacheEntries.put(voCacheEntry.getVOCacheKey(), voCacheEntry);

                Thread.yield();
            }
        }
    }

     public void removeVOCacheEntry(String voCacheKey){

        synchronized (voCacheMonitor) {

            voCacheEntries.remove(voCacheKey);

            Thread.yield();
        }
    }

	public final void run() {

		try {

			while (true) {

				while (countdown > 0) {

					Thread.sleep(1000);

					countdown--;
				}

                purgeVOCacheEntries();

				resetTimer();
			}
		}

		catch (Exception e) {

			System.out.println(e.getMessage());
		}
	}

    private void purgeVOCacheEntries(){

        synchronized (voCacheMonitor) {

            long currentTimeMillis = System.currentTimeMillis();

            List entriesToRemove = new ArrayList();

            Iterator keys = voCacheEntries.keySet().iterator();

            while (keys.hasNext()) {

                String key = (String) keys.next();

                VOCacheEntry voCacheEntry = (VOCacheEntry) voCacheEntries.get(key);

                long creationTimeMillis = voCacheEntry.getCreationTimeMillis();

                if ( (currentTimeMillis - creationTimeMillis)/1000 > CACHE_LIFE_SECS ){

                    entriesToRemove.add(key);
                }
            }

            for (int i = 0; i < entriesToRemove.size(); i++){

                this.removeVOCacheEntry(entriesToRemove.get(i).toString());
            }

            Thread.yield();
        }
    }

//*******************************
//          Private Methods
//*******************************

    private final void init() {

        voCacheEntries = new HashMap();

        voCacheMonitor  = new Object();

		Thread voThread = new Thread(this);
		voThread.setPriority(Thread.MIN_PRIORITY);
		voThread.setDaemon(true);
		voThread.start();

		resetTimer();
    }

	private final void resetTimer()	{

		countdown = CLEAR_CACHE_INTERVAL_SECS;
	}
}