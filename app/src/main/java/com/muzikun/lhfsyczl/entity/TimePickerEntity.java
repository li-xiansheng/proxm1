package com.muzikun.lhfsyczl.entity;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Created by  on 2016/11/4.
 */

public class TimePickerEntity<T> implements IPickerViewData {
    private String id;
    private String name;
    private T entity;

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}
