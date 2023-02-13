package com.itaem.memoryedit;

public class MemoryEditBean {
    // 修改文本
    private String lastEdit;
    // 修改起始位置
    private int lastStart;
    // 修改末位置
    private int lastEnd;
    private int state;
    private int changeEnd;

    public MemoryEditBean(String lastEdit, int lastStart, int lastEnd, int state) {
        this.lastEdit = lastEdit;
        this.lastStart = lastStart;
        this.lastEnd = lastEnd;
        this.state = state;
    }

    public String getLastEdit() {
        return lastEdit;
    }

    public int getLastStart() {
        return lastStart;
    }

    public int getLastEnd() {
        return lastEnd;
    }

    public int getState() {
        return state;
    }

    public int getChangeEnd() {
        return changeEnd;
    }

    public void setChangeEnd(int changeEnd) {
        this.changeEnd = changeEnd;
    }
}
