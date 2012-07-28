/*
 * Copyright (c) 2012 Alvin R. de Leon. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ard.piraso.ui.api.manager;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * Idle timeout manager.
 */
public enum IdleTimeout1Manager implements Runnable {
    INSTANCE;

    private static final Logger LOG = Logger.getLogger(IdleTimeout1Manager.class.getName());

    private static final long WAIT_TIME = 500l;

    private List<IdleTimeout1Aware> list;

    private List<IdleTimeout1Aware> queue;

    private ExecutorService executor;

    private boolean alive;

    private IdleTimeout1Manager() {
        list = new ArrayList<IdleTimeout1Aware>();
        queue = new ArrayList<IdleTimeout1Aware>();
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

    public void add(IdleTimeout1Aware aware) {
        synchronized (this) {
            list.add(aware);
        }
    }

    public void remove(IdleTimeout1Aware aware) {
        synchronized (this) {
            list.remove(aware);
        }
    }

    private List<IdleTimeout1Aware> getTimeoutList() {
        synchronized (this) {
            List<IdleTimeout1Aware> tmp = new ArrayList<IdleTimeout1Aware>(list);
            List<IdleTimeout1Aware> result = new ArrayList<IdleTimeout1Aware>();

            for(IdleTimeout1Aware aware : tmp) {
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

            List<IdleTimeout1Aware> idles = getTimeoutList();
            if(CollectionUtils.isNotEmpty(idles)) {
                synchronized (this) {
                    for(IdleTimeout1Aware aware : idles) {
                        executor.submit(new DoOnIdleTimeOutRunnable(aware));
                    }
                }
            }
        }
    }

    private void addQueue(IdleTimeout1Aware aware) {
        synchronized (this) {
            queue.add(aware);
        }
    }

    private void removeQueue(IdleTimeout1Aware aware) {
        synchronized (this) {
            queue.remove(aware);
        }
    }

    private class DoOnIdleTimeOutRunnable implements Runnable {

        private IdleTimeout1Aware aware;

        private DoOnIdleTimeOutRunnable(IdleTimeout1Aware aware) {
            this.aware = aware;
            addQueue(aware);
        }

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
