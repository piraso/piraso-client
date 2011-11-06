/*
 * Copyright (c) 2011. Piraso Alvin R. de Leon. All Rights Reserved.
 *
 * See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The Piraso licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ard.piraso.ui.base.extension;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * Idle timeout manager.
 */
public class IdleTimeOutManager implements Runnable {

    private static final Logger LOG = Logger.getLogger(IdleTimeOutManager.class.getName());

    public static final IdleTimeOutManager INSTANCE = new IdleTimeOutManager();

    private static final long WAIT_TIME = 500l;

    private List<IdleTimeOutAware> list;

    private List<IdleTimeOutAware> queue;

    private ExecutorService executor;

    private boolean alive;

    private IdleTimeOutManager() {
        list = new ArrayList<IdleTimeOutAware>();
        queue = new ArrayList<IdleTimeOutAware>();
    }

    public void stop() {
        synchronized (this) {
            alive = false;
            notifyAll();

            if(executor != null) {
                executor.shutdown();
            }
        }
    }

    public void start() {
        synchronized (this) {
            executor = Executors.newFixedThreadPool(3);
            executor.submit(this);
        }
    }

    public void add(IdleTimeOutAware aware) {
        synchronized (this) {
            list.add(aware);
        }
    }

    public void remove(IdleTimeOutAware aware) {
        synchronized (this) {
            list.remove(aware);
        }
    }

    private List<IdleTimeOutAware> getTimeoutList() {
        synchronized (this) {
            List<IdleTimeOutAware> tmp = new ArrayList<IdleTimeOutAware>(list);
            List<IdleTimeOutAware> result = new ArrayList<IdleTimeOutAware>();

            for(IdleTimeOutAware aware : tmp) {
                if(aware.isIdleTimeOut() && !queue.contains(aware)) {
                    result.add(aware);
                }
            }

            return result;
        }
    }

    @Override
    public void run() {
        while(alive) {
            synchronized(this) {
                try {
                    wait(WAIT_TIME);                
                } catch (InterruptedException ignored) {}
            }

            List<IdleTimeOutAware> idles = getTimeoutList();
            if(CollectionUtils.isNotEmpty(idles)) {
                synchronized (this) {
                    for(IdleTimeOutAware aware : idles) {
                        executor.submit(new DoOnIdleTimeOutRunnable(aware));
                    }
                }
            }
        }
    }

    private void addQueue(IdleTimeOutAware aware) {
        synchronized (this) {
            queue.add(aware);
        }
    }

    private void removeQueue(IdleTimeOutAware aware) {
        synchronized (this) {
            queue.remove(aware);
        }
    }

    private class DoOnIdleTimeOutRunnable implements Runnable {

        private IdleTimeOutAware aware;

        private DoOnIdleTimeOutRunnable(IdleTimeOutAware aware) {
            this.aware = aware;
            addQueue(aware);
        }

        @Override
        public void run() {
            try {
                aware.doOnTimeOut();
            } catch (Exception e) {
                LOG.warning(e.getMessage());
            } finally {
                removeQueue(aware);
            }
        }
    }
}
