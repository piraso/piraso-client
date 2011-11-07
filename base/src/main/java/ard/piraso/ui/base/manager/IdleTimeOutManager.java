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

package ard.piraso.ui.base.manager;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * Idle timeout manager.
 */
public class IdleTimeoutManager implements Runnable {

    private static final Logger LOG = Logger.getLogger(IdleTimeoutManager.class.getName());

    public static final IdleTimeoutManager INSTANCE = new IdleTimeoutManager();

    private static final long WAIT_TIME = 500l;

    private List<IdleTimeoutAware> list;

    private List<IdleTimeoutAware> queue;

    private ExecutorService executor;

    private boolean alive;

    private IdleTimeoutManager() {
        list = new ArrayList<IdleTimeoutAware>();
        queue = new ArrayList<IdleTimeoutAware>();
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

    public void add(IdleTimeoutAware aware) {
        synchronized (this) {
            list.add(aware);
        }
    }

    public void remove(IdleTimeoutAware aware) {
        synchronized (this) {
            list.remove(aware);
        }
    }

    private List<IdleTimeoutAware> getTimeoutList() {
        synchronized (this) {
            List<IdleTimeoutAware> tmp = new ArrayList<IdleTimeoutAware>(list);
            List<IdleTimeoutAware> result = new ArrayList<IdleTimeoutAware>();

            for(IdleTimeoutAware aware : tmp) {
                if(aware.isIdleTimeout() && !queue.contains(aware)) {
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

            List<IdleTimeoutAware> idles = getTimeoutList();
            if(CollectionUtils.isNotEmpty(idles)) {
                synchronized (this) {
                    for(IdleTimeoutAware aware : idles) {
                        executor.submit(new DoOnIdleTimeOutRunnable(aware));
                    }
                }
            }
        }
    }

    private void addQueue(IdleTimeoutAware aware) {
        synchronized (this) {
            queue.add(aware);
        }
    }

    private void removeQueue(IdleTimeoutAware aware) {
        synchronized (this) {
            queue.remove(aware);
        }
    }

    private class DoOnIdleTimeOutRunnable implements Runnable {

        private IdleTimeoutAware aware;

        private DoOnIdleTimeOutRunnable(IdleTimeoutAware aware) {
            this.aware = aware;
            addQueue(aware);
        }

        @Override
        public void run() {
            try {
                aware.doOnTimeout();
            } catch (Exception e) {
                LOG.warning(e.getMessage());
            } finally {
                removeQueue(aware);
            }
        }
    }
}
