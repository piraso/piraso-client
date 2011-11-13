package ard.piraso.ui.io.cache;

import java.io.Serializable;

/**
 * Cache key
 */
public class IOEntryCacheKey implements Serializable {
    private String id;

    private long requestId;

    private long rowNum;

    public IOEntryCacheKey(String id, long requestId, long rowNum) {
        this.id = id;
        this.requestId = requestId;
        this.rowNum = rowNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IOEntryCacheKey that = (IOEntryCacheKey) o;

        if (requestId != that.requestId) return false;
        if (rowNum != that.rowNum) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (int) (requestId ^ (requestId >>> 32));
        result = 31 * result + (int) (rowNum ^ (rowNum >>> 32));
        return result;
    }
}
