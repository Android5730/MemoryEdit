package com.itaem.memoryeditview;

import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;

import androidx.appcompat.widget.AppCompatEditText;

import java.util.Stack;

public class EditMemory {
    Stack<MemoryEditBean> lastStack = new Stack<>();
    Stack<MemoryEditBean> nextStack = new Stack<>();
    private boolean lastFlag = false;
    private boolean nextFlag = false;
    // 修改缓冲
    SpannableStringBuilder spannableStringBuilder;
    private final AppCompatEditText editText;
    public EditMemory(AppCompatEditText editText) {
        this.editText = editText;
        this.editText.addTextChangedListener(new TextWatcher() {
            @Override
            // start：开始 count：减少变化数 after：新增变化数
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (lastFlag||nextFlag){
                    return;
                }
                // 当不再使用撤销与反撤销功能，正常编辑后，清空下一步栈
                nextStack.clear();
                // 删除
                if (!(lastStack.isEmpty())&&count>0&&after==0){
                    lastStack.push(new MemoryEditBean(s.toString().substring(start,start+count),start,start+count,-1));
                }else if (count>0&&after>0){
                    // 修改
                    lastStack.push(new MemoryEditBean(s.toString().substring(start, start + count), start, start + after, 1));
                }

            }
            @Override
            // start：开始位置 before：删除数量;count:新增数量
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (lastFlag||nextFlag){
                    return;
                }

                if (before==0&&count>0){
                    // 新增
                    lastStack.push(new MemoryEditBean(s.toString().substring(start,count+start),start,start+count,0));
                }else if (before>0&&count>0){
                    // 修改
                    lastStack.push(new MemoryEditBean(s.toString().substring(start, count + start), start, start + count, 1));
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    // 撤销功能
    public void rollBack(){
        lastFlag = true;
        if (lastStack.size() == 0){
            return;
        }
        MemoryEditBean temp = lastStack.pop();
        spannableStringBuilder = new SpannableStringBuilder(editText.getText());
        nextStack.push(temp);
        if (temp.getState()==-1){
            // 删除状态
            spannableStringBuilder.insert(temp.getLastStart(),temp.getLastEdit());
            editText.setText(spannableStringBuilder);
            editText.setSelection(temp.getLastEnd());
        }else if (temp.getState() == 0){
            // 新增状态
            // 覆盖text的长度
            spannableStringBuilder.delete(temp.getLastStart(),temp.getLastEnd());
            editText.setText(spannableStringBuilder);
            editText.setSelection(temp.getLastStart());
        }else if (temp.getState() == 1){
            // 修改状态:
            // 1. 同位修改
            // 2. 少位修改
            // 3. 多位修改
            MemoryEditBean pop = lastStack.pop();
            spannableStringBuilder.replace(pop.getLastStart(),pop.getLastEnd(),pop.getLastEdit(),0,pop.getLastEdit().length());
            editText.setText(spannableStringBuilder);
            editText.setSelection(pop.getLastStart()+pop.getLastEdit().length());
        }
        lastFlag = false;
    }
    // 反撤销功能
    public void rollNext(){
        nextFlag = true;
        if (nextStack.size() == 0){
            return;
        }
        MemoryEditBean pop = nextStack.pop();
        lastStack.push(pop);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(editText.getText());
        if (pop.getState()==-1){
            //
            spannableStringBuilder.delete(pop.getLastStart(),pop.getLastEnd());
            editText.setText(spannableStringBuilder);
            editText.setSelection(pop.getLastStart());
        }else if (pop.getState()==0){
            // 新增
            spannableStringBuilder.insert(pop.getLastStart(),pop.getLastEdit());
            editText.setText(spannableStringBuilder);
            editText.setSelection(pop.getLastEnd());
        }else if (pop.getState()==1){
            // 修改状态:
            // 1. 同位修改
            // 2. 少位修改
            // 3. 多位修改
            spannableStringBuilder.replace(pop.getLastStart(),pop.getLastEnd(),pop.getLastEdit(),0,pop.getLastEdit().length());
            editText.setText(spannableStringBuilder);
            editText.setSelection(pop.getLastEnd());
        }
        nextFlag = false;
    }
    // 对笔记阶段性保存
    public void save(){
        // 清空栈
        lastStack.clear();
        nextStack.clear();
    }

    public Stack<MemoryEditBean> getLastStack() {
        return lastStack;
    }

    public Stack<MemoryEditBean> getNextStack() {
        return nextStack;
    }
}
