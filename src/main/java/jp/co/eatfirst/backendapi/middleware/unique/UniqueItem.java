package jp.co.eatfirst.backendapi.middleware.unique;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Calendar;

@Data
public class UniqueItem <T extends Object>{
    T item = null;
    Calendar time = null;
    @JsonIgnore
    public boolean isTimeOut() {
        if (time == null)
            return true;
        else {
            return Calendar.getInstance().getTime().after(time.getTime());
        }
    }
}
