# MemoryEdit
可撤销和反撤销的EditText

添加依赖如下：
在工程包settings.gradle下添加

```groovy
		dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```
在app模块build.gradle下添加

```groovy
    implementation 'com.github.Android5730:MemoryEdit:v0.0.2'
```
使用：

1. 装饰：

```java
        AppCompatEditText editText = findViewById(R.id.editView);
        EditMemory editMemory = new EditMemory(editText);
```
2. 撤销：`                editMemory.rollNext();
`
3. 反撤销：`                editMemory.rollBack();
`

待优化：
4. 对控件可点击性的监听

```java
    public void iniSetCheckable(){
        if (editMemory.getLastStack().size()==0){
            back.setClickable(false);
        }else {
            back.setClickable(true);
        }
        if (editMemory.getNextStack().size()==0){
            nextBack.setClickable(false);
        }else {
            nextBack.setClickable(true);

        }
    }
```
