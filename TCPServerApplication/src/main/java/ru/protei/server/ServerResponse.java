package ru.protei.server;

import java.util.List;

public class ServerResponse<T> {
    private List<T> list;
    private int resultCode;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }
}
